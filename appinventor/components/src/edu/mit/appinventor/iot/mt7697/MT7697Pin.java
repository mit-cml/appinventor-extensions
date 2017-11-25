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

import static edu.mit.appinventor.iot.mt7697.Constants.PIN_BLE_PROFILES;
import static edu.mit.appinventor.iot.mt7697.Constants.AVAILABLE_PINS;

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
        if (mode.equals(MODE_ANALOG_INPUT) || mode.equals(MODE_DIGITAL_INPUT))
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

  private static final String MODE_ANALOG_INPUT   = "Analog Input";
  private static final String MODE_ANALOG_OUTPUT  = "Analog Output";
  private static final String MODE_DIGITAL_INPUT  = "Digital Input";
  private static final String MODE_DIGITAL_OUTPUT = "Digital Output";

  private static final String DEFAULT_PIN = "0";
  private static final String DEFAULT_MODE = MODE_ANALOG_INPUT;

  private String pin = DEFAULT_PIN;
  private String mode = DEFAULT_MODE;
  private PinBLEProfile pinProfile = PIN_BLE_PROFILES.get(DEFAULT_PIN);
  private int state = 0; // increase this number when setupPin() is called

  public MT7697Pin(Form form) {
    super(form);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = DEFAULT_PIN,
                    editorArgs = {"0", "1", "2", "3", "4", "5", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"})
  @SimpleProperty
  public void Pin(String _pin) {
    this.pin = _pin;
    this.pinProfile = PIN_BLE_PROFILES.get(_pin);
    setupPin();
  }

  @SimpleProperty(description = "The pin mode on the MT7697 board that the device is wired in to.")
  public String Pin() {
    return pin;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = DEFAULT_MODE,
                    editorArgs = { MODE_DIGITAL_INPUT, MODE_DIGITAL_OUTPUT, MODE_ANALOG_INPUT, MODE_ANALOG_OUTPUT })
  @SimpleProperty
  public void Mode(String _mode) {
    this.mode = _mode;
    setupPin();
  }

  @SimpleProperty(description = "The pin mode on the MT7697 board that the device is wired in to.")
  public String Mode() {
    return mode;
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

    bleConnection.ExReadByteValues(
      this.pinProfile.serviceUUID,
      this.pinProfile.dataCharUUID,
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

    bleConnection.ExWriteByteValues(
      this.pinProfile.serviceUUID,
      this.pinProfile.dataCharUUID,
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

    bleConnection.ExRegisterForByteValues(this.pinProfile.serviceUUID,
                                          this.pinProfile.dataCharUUID,
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

    bleConnection.ExUnregisterForValues(this.pinProfile.serviceUUID,
                                        this.pinProfile.dataCharUUID,
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
      bleConnection.isCharacteristicPublished(this.pinProfile.serviceUUID, this.pinProfile.modeCharUUID) &&
      bleConnection.isCharacteristicPublished(this.pinProfile.serviceUUID, this.pinProfile.dataCharUUID);
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

  private void setupPin() {
    if (!IsSupported())
      return;

    int modeCommand;

    if (MODE_ANALOG_INPUT.equals(this.mode)) {
      modeCommand = 0x00;
    } else if (MODE_ANALOG_OUTPUT.equals(this.mode)) {
      modeCommand = 0x01;
    } else if (MODE_DIGITAL_INPUT.equals(this.mode)) {
      modeCommand = 0x10;
    } else {
      assert this.mode == MODE_DIGITAL_OUTPUT;
      modeCommand = 0x11;
    }

    bleConnection.ExWriteByteValues(
      this.pinProfile.serviceUUID,
      this.pinProfile.modeCharUUID,
      false,
      modeCommand
      );
  }
}
