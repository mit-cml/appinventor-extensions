// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE.BLEResponseHandler;

import static edu.mit.appinventor.iot.mt7697.Constants.PIN_SERVICE;
import static edu.mit.appinventor.iot.mt7697.Constants.ANALOG_PIN_CHARACTERISTIC;
import static edu.mit.appinventor.iot.mt7697.Constants.DIGITAL_PIN_CHARACTERISTIC;
import static edu.mit.appinventor.iot.mt7697.Constants.PIN_MODE_CHARACTERISTIC;
import static edu.mit.appinventor.iot.mt7697.Constants.PIN_WRITE_CHARACTERISTIC;

import java.util.ArrayList;
import java.util.List;

/**
 * The MT7697Pins extension provides a general purpose interface to any hardware connected
 * to the digital or analog I/O pins.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697Pins extension provides a general purpose interface to any " +
                                 "hardware connected to the Arduino's digital or analog I/O pins.",
                   category = ComponentCategory.EXTENSION,
                   helpUrl = "http://iot.appinventor.mit.edu/#/arduino101/arduinopins",
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Pins extends MT7697ExtensionBase {
  private int pin;
  private boolean analog;
  private boolean output;
  private volatile boolean reading;
  private volatile boolean listening;

  private final BLEResponseHandler<Integer> stateHandler = new BLEResponseHandler<Integer>() {
    @Override
    public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
      if (values.size() > pin && (reading || listening)) {
        reading = false;
        PinStateReceived(values.get(pin));
      }
    }

    @Override
    public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
      if (values.size() > pin) {
        PinStateWritten(values.get(pin));
      }
    }
  };

  /**
   * Creates a new AndroidNonvisibleComponent.
   *
   * @param form the container that this component will be placed in
   */
  public MT7697Pins(Form form) {
    super(form);
  }

  /**
   * Check whether the feature is currently available for the device connected via the
   * <a href="#BluetoothDevice"><code>BluetoothDevice</code></a> property. If no device is currently
   * connected, this method will always return false.
   * @return true if the connected device is advertising the service represented by the extension,
   * otherwise false.
   */
  @Override
  @SimpleFunction
  public boolean IsSupported() {
    return bleConnection != null &&
      bleConnection.isCharacteristicPublished(PIN_SERVICE,
                                              PIN_MODE_CHARACTERISTIC);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
                    defaultValue = "0")
  @SimpleProperty
  public void Pin(int pin) {
    this.pin = pin;
  }

  @SimpleProperty(description = "The Arduino pin to read or write. Default: 0.")
  public int Pin() {
    return pin;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
                    defaultValue = "false")
  @SimpleProperty
  public void Analog(boolean analog) {
    this.analog = analog;
  }

  @SimpleProperty(description = "Set or get whether the pin is an analog pin (true) or digital " +
      "pin (false). Default: digital (false).")
  public boolean Analog() {
    return analog;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
                    defaultValue = "false")
  @SimpleProperty
  public void Output(boolean output) {
    this.output = output;
  }

  @SimpleProperty(description = "Set or get whether the pin is an input or output pin. This only " +
                                "applies to digital pins. Analog pins are read-only. See the <a " +
                                "href=\"#/component/arduinopwm\">MT7697 PWM</a> extension for treating digital pins " +
                                "as 'analog' outputs. Default: input (false).")
  public boolean Output() {
    return output;
  }

  /**
   * Read the current state of the pin. After the value is read, it will be reported through the
   * <code><a href="#PinStateReceived">PinStateReceived</a></code> event.
   */
  @SimpleFunction
  public void ReadPinState() {
    if (isConnected() && !reading) {
      reading = true;
      bleConnection.ExReadByteValues(PIN_SERVICE,
                                     analog ? ANALOG_PIN_CHARACTERISTIC : DIGITAL_PIN_CHARACTERISTIC,
                                     false,
                                     stateHandler);
    }
  }

  /**
   * Request updates to the state of the pin. New values will be reported by the
   * <code><a href="#PinStateReceived">PinStateReceived</a></code>
   * event.
   */
  @SimpleFunction
  public void RequestPinStateUpdates() {
    if (isConnected() && !listening) {
      listening = true;
      if (analog) {
        bleConnection.ExRegisterForShortValues(PIN_SERVICE, ANALOG_PIN_CHARACTERISTIC, false, stateHandler);
      } else {
        bleConnection.ExRegisterForByteValues(PIN_SERVICE, DIGITAL_PIN_CHARACTERISTIC, false, stateHandler);
      }
    }
  }

  /**
   * Stop receiving updates for the pin. Note that there still may be pending notifications to the
   * <code>PinStateReceived</code> event that will need to be processed after this call.
   */
  @SimpleFunction
  public void UnregisterForUpdates() {
    listening = false;
    if (analog) {
      bleConnection.ExUnregisterForValues(PIN_SERVICE, ANALOG_PIN_CHARACTERISTIC, stateHandler);
    } else {
      bleConnection.ExUnregisterForValues(PIN_SERVICE, DIGITAL_PIN_CHARACTERISTIC, stateHandler);
    }
  }

  /**
   * Write a new value for the pin. This is only a valid operation if the Output property is set
   * to true. For digital pins, a non zero value will be converted to 1 (HIGH) and a zero value will
   * be converted to 0 (LOW). For analog pins, the value must be between 0 and 1023, inclusive. Any
   * values outside of this range will be truncated. To write analog outputs to digital pins using
   * pulse width modulation, see the <a href="#/arduino101/arduinopwm">MT7697PWM</a> extension.
   *
   * __Parameters__:
   *
   *     * <code>value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The value to write to the pin. Valid values depend on whether the pin is digital or analog.
   *
   * @param value The value to write to the pin. Valid values depend on whether the pin is digital
   *              or analog.
   */
  @SimpleFunction
  public void WritePinState(int value) {
    if (isConnected()) {
      List<Integer> values = new ArrayList<Integer>(2);
      values.add(pin + (analog? 65536 : 0));
      values.add(value);
      bleConnection.ExWriteShortValuesWithResponse(PIN_SERVICE, PIN_WRITE_CHARACTERISTIC, false, values, stateHandler);
    }
  }

  /**
   * After the pin is written, the <code>PinStateWritten</code> event will be run to indicate a
   * successful operation. The value parameter will indicate the value written to the Arduino from
   * the App Inventor app, not necessarily the value passed by the caller to
   * <code>WritePinState</code>. For example, in the case of the analog pins the value will be
   * truncated to fit within the range of [0, 1023].
   *
   * __Parameters__:
   *
   *     * <code>value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The value written to the Arduino. Valid values depend on whether the pin is
   *       digital or analog. This value is the value written after any transformation by
   *       the extension to fit the range appropriate to the pin type.
   *
   * @param value The value written to the Arduino. Valid values depend on whether the pin is
   *              digital or analog. This value is the value written after any transformation by
   *              the extension to fit the range appropriate to the pin type.
   */
  @SimpleEvent
  public void PinStateWritten(int value) {
    EventDispatcher.dispatchEvent(this, "PinWritten", value);
  }

  /**
   * After the pin is read or an update is received, the <code>PinStateReceived</code> event will
   * be run to inform the app about the state of the pin. The value parameter will indicate the
   * pin state. For digital pins, it will be either 0 for off or 1 for on. For analog pins, it will
   * be an integer in the range [0, 1023].
   *
   * __Parameters__:
   *
   *     * <code>value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The value of the pin read from the Arduino. Valid values depend on whether the pin
   *       is digital or analog. For digital pins, a 0 indicates LOW and 1 indicates HIGH.
   *       For analog, an integer in the range of [0, 1023] will be returned.
   *
   * @param value The value of the pin read from the Arduino. Valid values depend on whether the pin
   *              is digital or analog. For digital pins, a 0 indicates LOW and 1 indicates HIGH.
   *              For analog, an integer in the range of [0, 1023] will be returned.
   */
  @SimpleEvent
  public void PinStateReceived(int value) {
    EventDispatcher.dispatchEvent(this, "PinStateReceived", value);
  }
}
