// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import java.util.List;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.EventDispatcher;
import edu.mit.appinventor.ble.BluetoothLE;

import static edu.mit.appinventor.iot.mt7697.Constants.PIN_UUID_LOOKUP;

/**
 * Base class for MT7697 extensions that require a pin configuration.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@SimpleObject
public abstract class MT7697Pin extends MT7697ExtensionBase {
  private final BluetoothLE.BLEResponseHandler<Integer> inputUpdateCallback =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUUID, String characteristicUUID, List<Integer> values) {
        InputUpdated(values.get(0));
      }
    };

  // private final BluetoothLE.BLEResponseHandler<Integer> pinSetupCallback =
  //   new BLEResponseHandler<Integer>() {
  //     @Override
  //     public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
  //       this.isModeUpdated = true;
  //     }
  //   };

  private static final String STRING_ANALOG_INPUT   = "analog input";
  private static final String STRING_ANALOG_OUTPUT  = "analog output";
  private static final String STRING_DIGITAL_INPUT  = "digital input";
  private static final String STRING_DIGITAL_OUTPUT = "digital output";
  private static final int MODE_ANALOG_INPUT   = 0;
  private static final int MODE_ANALOG_OUTPUT  = 1;
  private static final int MODE_DIGITAL_INPUT  = 2;
  private static final int MODE_DIGITAL_OUTPUT = 3;

  private static final String DEFAULT_PIN = "0";

  private String mPin = DEFAULT_PIN;
  private int mMode;
  private String mServiceUuid;
  private String mAnalogInputCharUuid;
  private String mAnalogOutputCharUuid;
  private String mDigitalInputCharUuid;
  private String mDigitalOutputCharUuid;

  public MT7697Pin(Form form) {
    super(form);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = DEFAULT_PIN,
                    editorArgs = {"2", "3", "4", "5", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"})
  @SimpleProperty
  public void Pin(String pin) {
    mPin = pin;
    mServiceUuid           = PIN_UUID_LOOKUP.get(mPin).mServiceUuid;
    mAnalogInputCharUuid   = PIN_UUID_LOOKUP.get(mPin).mAnalogInputCharUuid;
    mDigitalOutputCharUuid = PIN_UUID_LOOKUP.get(mPin).mDigitalOutputCharUuid;
    mAnalogInputCharUuid   = PIN_UUID_LOOKUP.get(mPin).mAnalogInputCharUuid;
    mDigitalOutputCharUuid = PIN_UUID_LOOKUP.get(mPin).mDigitalOutputCharUuid;
  }

  @SimpleProperty(description = "The pin mode on the MT7697 board that the device is wired in to.")
  public String Pin() {
    return mPin;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = STRING_ANALOG_INPUT,
                    editorArgs = { STRING_ANALOG_INPUT, STRING_ANALOG_OUTPUT, STRING_DIGITAL_INPUT, STRING_DIGITAL_OUTPUT })
  @SimpleProperty
  public void Mode(String mode) {
    if (mode.equals(STRING_ANALOG_INPUT))
      mMode = MODE_ANALOG_INPUT;
    else if (mode.equals(STRING_ANALOG_OUTPUT))
      mMode = MODE_ANALOG_OUTPUT;
    else if (mode.equals(STRING_DIGITAL_INPUT))
      mMode = MODE_DIGITAL_INPUT;
    else if (mode.equals(STRING_DIGITAL_OUTPUT))
      mMode = MODE_DIGITAL_OUTPUT;
    else
      assert false;
  }

  @SimpleProperty(description = "The pin mode on the MT7697 board that the device is wired in to.")
  public String Mode() {
    switch (mMode) {
    case MODE_ANALOG_INPUT:
      return STRING_ANALOG_INPUT;

    case MODE_ANALOG_OUTPUT:
      return STRING_ANALOG_OUTPUT;

    case MODE_DIGITAL_INPUT:
      return STRING_DIGITAL_INPUT;

    default:
      assert mMode == MODE_DIGITAL_OUTPUT;
      return STRING_DIGITAL_OUTPUT;
    }
  }

  /**
   * Obtain the most recent reading from the light sensor as reported by the Arduino. On success,
   * the <a href="#LightSensorDataReceived"><code>LightSensorDataReceived</code></a> event will be
   * run.
   */
  @SimpleFunction
  public void Read() {
    if (!IsSupported())
      return;

    String charUuid;
    switch (mMode) {
    case MODE_ANALOG_INPUT:
      charUuid = mAnalogInputCharUuid;
      break;

    case MODE_DIGITAL_INPUT:
      charUuid = mDigitalInputCharUuid;
      break;

    default:
      return;
    }

    bleConnection.ExReadByteValues(
      mServiceUuid,
      charUuid,
      false,
      inputUpdateCallback
      );
  }

  /**
   * Obtain the most recent reading from the light sensor as reported by the Arduino. On success,
   * the <a href="#LightSensorDataReceived"><code>LightSensorDataReceived</code></a> event will be
   * run.
   */
  @SimpleFunction
  public void Write(int value) {
    if (!IsSupported())
      return;

    // TODO check if value is in [0, 255]

    String charUuid;
    switch (mMode) {
    case MODE_ANALOG_OUTPUT:
      charUuid = mAnalogOutputCharUuid;
      break;

    case MODE_DIGITAL_OUTPUT:
      charUuid = mDigitalOutputCharUuid;
      break;

    default:
      return;
    }

    bleConnection.ExWriteByteValues(
      mServiceUuid,
      charUuid,
      false,
      value
      );
  }

  /**
   * Request notification of updates for the light sensor attached to the MT7697. The <a
   * href="#LightSensorDataReceived"><code>LightSensorDataReceived</code></a> event will be run as
   * light sensor readings are received from the Arduino.
   */
  @SimpleFunction
  public void RequestInputUpdates() {
    if (!IsSupported())
      return;

    String charUuid;
    switch (mMode) {
    case MODE_ANALOG_INPUT:
      charUuid = mAnalogInputCharUuid;
      break;

    case MODE_DIGITAL_INPUT:
      charUuid = mDigitalInputCharUuid;
      break;

    default:
      return;
    }

    bleConnection.ExRegisterForByteValues(mServiceUuid,
                                          charUuid,
                                          false,
                                          this.inputUpdateCallback);
  }

  /**
   * Stop listening for notifications of light sensor readings from the Arduino. This only has an
   * effect if there was a previous call to <a
   * href="#RequestLightSensorUpdates"><code>RequestLightSensorUpdates</code></a>. There may be
   * additional pending messages that will be processed after this call.
   */
  @SimpleFunction
  public void StopInputUpdates() {
    if (!IsSupported())
      return;

    String charUuid;
    switch (mMode) {
    case MODE_ANALOG_INPUT:
      charUuid = mAnalogInputCharUuid;
      break;

    case MODE_DIGITAL_INPUT:
      charUuid = mDigitalInputCharUuid;
      break;

    default:
      return;
    }
    bleConnection.ExUnregisterForValues(mServiceUuid,
                                        charUuid,
                                        this.inputUpdateCallback);
  }

  /**
   * Tests whether the Bluetooth low energy device is broadcasting support for the service. If true,
   * calls to TurnOn and TurnOff should work correctly. Otherwise an error will be reported through
   * the Screen's ErrorOccurred event.
   */
  @Override
  @SimpleFunction
  public boolean IsSupported() {
    return bleConnection != null &&
      bleConnection.isCharacteristicPublished(mServiceUuid, mAnalogInputCharUuid) &&
      bleConnection.isCharacteristicPublished(mServiceUuid, mAnalogOutputCharUuid) &&
      bleConnection.isCharacteristicPublished(mServiceUuid, mDigitalInputCharUuid) &&
      bleConnection.isCharacteristicPublished(mServiceUuid, mDigitalOutputCharUuid);
  }

  /**
   * The <code>LightSensorDataReceived</code> event is run when a sample is received from the light
   * sensor attached to the MT7697.
   *
   * __Parameters__:
   *
   *     * <code>intensity</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The intensity of the light received from the sensor, linear in the voltage provided by the light sensor.
   *
   * @param intensity The intensity of the light received from the sensor, linear in the voltage provided by the light sensor.
   */
  @SimpleEvent
  public void InputUpdated(final int value) {
    EventDispatcher.dispatchEvent(this, "InputUpdated", value);
  }
}
