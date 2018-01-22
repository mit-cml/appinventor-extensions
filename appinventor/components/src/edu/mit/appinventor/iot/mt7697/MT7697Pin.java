// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import java.util.List;
import android.util.Log;
import android.os.Handler;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.TimerInternal;
import com.google.appinventor.components.runtime.AlarmHandler;
import edu.mit.appinventor.ble.BluetoothLE;

import static edu.mit.appinventor.iot.mt7697.Constants.PIN_UUID_LOOKUP;
import static edu.mit.appinventor.iot.mt7697.Constants.PIN_SERVICE_UUID;

/**
 * Base class for MT7697 extensions that require a pin configuration.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 2,
                   description = "The Arduino LED component lets users control light-emitting diodes (LEDs) from" +
                                 " their App Inventor projects. If the LED is plugged into a pin supporting pulse width " +
                                 "modulation (PWM), then the LED's brightness can be controlled by varying the Intensity " +
                                 "property. TurnOn and TurnOff methods are used to control the power state of the LED." +
                                 "<br>\n\n<strong>More Links:</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697LED.aia' " +
                                 "target='_blank'>sample project</a> for the MT7697 LED.</li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_LED_Control.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 LED.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Pin extends MT7697ExtensionBase {
  // constants
  private static final int ERROR_INVALID_PIN_ARGUMENT  = 9101;
  private static final int ERROR_INVALID_MODE_ARGUMENT = 9102;
  private static final int ERROR_INVALID_WRITE_VALUE   = 9103;

  private static final int TIMER_INTERVAL = 80; // ms

  private static final String LOG_TAG = "MT7697Pin";

  private static final String STRING_ANALOG_INPUT   = "analog input";
  private static final String STRING_ANALOG_OUTPUT  = "analog output";
  private static final String STRING_DIGITAL_INPUT  = "digital input";
  private static final String STRING_DIGITAL_OUTPUT = "digital output";
  private static final int MODE_UNSET          = 0;
  private static final int MODE_ANALOG_INPUT   = 1;
  private static final int MODE_ANALOG_OUTPUT  = 2;
  private static final int MODE_DIGITAL_INPUT  = 3;
  private static final int MODE_DIGITAL_OUTPUT = 4;

  private static final String DEFAULT_PIN = "2";
  private final String mServiceUuid = PIN_SERVICE_UUID; // always unchanged

  // variable
  private String mPin;
  private int mMode;
  private String mModeCharUuid;
  private String mDataCharUuid;
  private int mData;

  private TimerInternal mTimerInternal;

  public MT7697Pin(Form form) {
    super(form);

    // setup timer
    final BluetoothLE.BLEResponseHandler<Integer> inputUpdateCallback =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUUID, String characteristicUUID, List<Integer> values) {
          int receivedValue = values.get(0);

          if (mMode == MODE_DIGITAL_INPUT) {
            mData = receivedValue == 0 ? 0 : 1;
            InputUpdated(receivedValue);
          } else if (mMode == MODE_ANALOG_INPUT) {
            mData = receivedValue;
            InputUpdated(receivedValue);
          }
        }
      };

    AlarmHandler handler = new AlarmHandler() {
      public void alarm() {
        if (!IsSupported())
          return;

        if (mMode == MODE_ANALOG_INPUT || mMode == MODE_DIGITAL_INPUT) {
          bleConnection.ExWriteByteValues(mServiceUuid, mModeCharUuid, false, 0);
          bleConnection.ExReadByteValues(mServiceUuid, mDataCharUuid, false, inputUpdateCallback);

        } else if (mMode == MODE_ANALOG_OUTPUT || mMode == MODE_DIGITAL_OUTPUT) {
          bleConnection.ExWriteByteValues(mServiceUuid, mModeCharUuid, false, 1);

          if (mData <= 0) {
            int dataToSend = -mData;
            if (mMode == MODE_DIGITAL_OUTPUT && dataToSend != 0)
              dataToSend = 255;

            bleConnection.ExWriteByteValues(mServiceUuid, mDataCharUuid, false, dataToSend);
          }
        }
      }
    };

    mTimerInternal = new TimerInternal(handler, true, TIMER_INTERVAL);
  }


  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = DEFAULT_PIN,
                    editorArgs = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"})
  @SimpleProperty
  public void Pin(String pin) {
    String[] validPins = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};
    boolean isValidPin = false;

    // sanity check
    for (int ind = 0; ind < validPins.length; ind += 1)
      if (validPins[ind].equals(pin))
        isValidPin = true;

    if (!isValidPin) {
      form.dispatchErrorOccurredEvent(this,
                                      "Pin",
                                      ErrorMessages.ERROR_EXTENSION_ERROR,
                                      ERROR_INVALID_PIN_ARGUMENT,
                                      LOG_TAG,
                                      "Invalid pin value");
      return;
    }

    // assign values
    mPin = pin;
    mModeCharUuid = PIN_UUID_LOOKUP.get(mPin).mModeCharUuid;
    mDataCharUuid = PIN_UUID_LOOKUP.get(mPin).mDataCharUuid;
  }

  @SimpleProperty(category = PropertyCategory.BEHAVIOR,
                  description = "The pin mode on the MT7697 board that the device is wired in to.")
  public String Pin() {
    return mPin;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = STRING_ANALOG_INPUT,
                    editorArgs = { STRING_ANALOG_INPUT, STRING_ANALOG_OUTPUT, STRING_DIGITAL_INPUT, STRING_DIGITAL_OUTPUT })
  @SimpleProperty
  public void Mode(String mode) {
    if (mode.equals(STRING_ANALOG_INPUT)) {
      mMode = MODE_ANALOG_INPUT;
      mData = -1;
    }
    else if (mode.equals(STRING_ANALOG_OUTPUT)) {
      mMode = MODE_ANALOG_OUTPUT;
      mData = 1;
    }
    else if (mode.equals(STRING_DIGITAL_INPUT)) {
      mMode = MODE_DIGITAL_INPUT;
      mData = -1;
    }
    else if (mode.equals(STRING_DIGITAL_OUTPUT)) {
      mMode = MODE_DIGITAL_OUTPUT;
      mData = 1;
    }
    else
      form.dispatchErrorOccurredEvent(this,
                                      "Mode",
                                      ErrorMessages.ERROR_EXTENSION_ERROR,
                                      ERROR_INVALID_MODE_ARGUMENT,
                                      LOG_TAG,
                                      "Invalid mode value");
  }

  @SimpleProperty(category = PropertyCategory.BEHAVIOR,
                  description = "The pin mode on the MT7697 board that the device is wired in to.")
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
  public int Read() {
    if (!IsSupported())
      return -1;

    if (mData < 0)
      return -1;
    else
      return mData;
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

    if (value < 0 || value > 255)
    form.dispatchErrorOccurredEvent(this,
                                    "Pin",
                                    ErrorMessages.ERROR_EXTENSION_ERROR,
                                    ERROR_INVALID_WRITE_VALUE,
                                    LOG_TAG,
                                    "Invalid write value");

    mData = -value;
  }

  /**
   * Tests whether the Bluetooth low energy device is broadcasting support for the service. If true,
   * calls to TurnOn and TurnOff should work correctly. Otherwise an error will be reported through
   * the Screen's ErrorOccurred event.
   */
  @Override
  @SimpleFunction
  public boolean IsSupported() {
    return mMode != MODE_UNSET &&
      mPin != null &&
      bleConnection != null &&
      mModeCharUuid != null &&
      mDataCharUuid != null &&
      bleConnection.isCharacteristicPublished(mServiceUuid, mModeCharUuid) &&
      bleConnection.isCharacteristicPublished(mServiceUuid, mDataCharUuid);
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
