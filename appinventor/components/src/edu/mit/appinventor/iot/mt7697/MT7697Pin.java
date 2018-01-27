// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

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
 * The class controls a pin I/O on a MT7697 board.
 *
 * @author jerry73204@gmail.com (Lin, Hsiang-Jui)
 * @author az6980522@gmail.com (Yuan, Yu-Yuan)
 */
@DesignerComponent(version = 2,
                   description = "The MT7697Pin component lets users control the pin from their App Inventor projects.",
                   category = ComponentCategory.EXTENSION,
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Pin extends MT7697ExtensionBase {
  // constants
  private static final int ERROR_INVALID_PIN_ARGUMENT  = 9101;
  private static final int ERROR_INVALID_MODE_ARGUMENT = 9102;
  private static final int ERROR_INVALID_WRITE_VALUE   = 9103;
  private static final int ERROR_INVALID_STATE         = 9104;

  private static final int TIMER_INTERVAL = 80; // ms

  private static final String LOG_TAG = "MT7697Pin";

  private static final String STRING_ANALOG_INPUT   = "analog input";
  private static final String STRING_ANALOG_OUTPUT  = "analog output";
  private static final String STRING_DIGITAL_INPUT  = "digital input";
  private static final String STRING_DIGITAL_OUTPUT = "digital output";
  private static final String STRING_SERVO          = "servo";
  private static final int MODE_UNSET          = 0;
  private static final int MODE_ANALOG_INPUT   = 1;
  private static final int MODE_ANALOG_OUTPUT  = 2;
  private static final int MODE_DIGITAL_INPUT  = 3;
  private static final int MODE_DIGITAL_OUTPUT = 4;
  private static final int MODE_SERVO          = 5;

  private static final String DEFAULT_PIN = "2";
  private final String mServiceUuid = PIN_SERVICE_UUID; // always unchanged

  // variable
  private String mPin = DEFAULT_PIN;
  private int mMode = MODE_UNSET;
  private String mModeCharUuid;
  private String mDataCharUuid;
  private int mData;
  private boolean mInputUpdate = false;

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

            if (mInputUpdate)
              InputUpdated(receivedValue);

          } else if (mMode == MODE_ANALOG_INPUT) {
            mData = receivedValue;

            if (mInputUpdate)
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

        } else if (mMode == MODE_ANALOG_OUTPUT || mMode == MODE_DIGITAL_OUTPUT || mMode == MODE_SERVO) {
          bleConnection.ExWriteByteValues(mServiceUuid, mModeCharUuid, false, 1);

          if (mData <= 0) {
            int dataToSend = mData;
            if (mMode == MODE_DIGITAL_OUTPUT && dataToSend != 0)
              dataToSend = -255;

            bleConnection.ExWriteByteValues(mServiceUuid, mDataCharUuid, false, dataToSend);
          }
        }
      }
    };

    mTimerInternal = new TimerInternal(handler, true, TIMER_INTERVAL);
  }


  /**
   * Set the target pin by pin number. To set this property in blocky editor, assign text value
   * in either one of "2", "3", "4", "5", "6", "7", "10", "11", "12", "13", "14", "15", "16" or "17".
   *
   * __Parameters__:
   *
   *     * pin (_text_); The target pin number.
   *
   * @param The target pin number.
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = DEFAULT_PIN,
                    editorArgs = {"2", "3", "4", "5", "6", "7", "10", "11", "12", "13", "14", "15", "16", "17"})
  @SimpleProperty
  public void Pin(String pin) {
    // duplicated with editorArgs, but we have to make compiler happy
    String[] validPins = {"2", "3", "4", "5", "6", "7", "10", "11", "12", "13", "14", "15", "16", "17"};
    boolean isValidPin = false;

    // sanity check
    for (int ind = 0; ind < validPins.length; ind += 1) {
      if (validPins[ind].equals(pin)) {
        isValidPin = true;
        break;
      }
    }

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

  /**
   * Get the target pin number.
   */
  @SimpleProperty(category = PropertyCategory.BEHAVIOR,
                  description = "The pin mode on the MT7697 board that the device is wired in to.")
  public String Pin() {
    return mPin;
  }

  /**
   * Set the target pin mode. To set this property in blocky editor, assign text value
   * in either one of "analog input", "analog output", "ditital input", "ditigal output",
   * or "servo".
   *
   * __Parameters__:
   *
   *     * mode (_text_); The mode description set on the target pin.
   *
   * @param The mode description set on the target pin.
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES,
                    defaultValue = STRING_ANALOG_INPUT,
                    editorArgs = { STRING_ANALOG_INPUT, STRING_ANALOG_OUTPUT, STRING_DIGITAL_INPUT, STRING_DIGITAL_OUTPUT, STRING_SERVO })
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
    else if (mode.equals(STRING_SERVO)) {
      mMode = MODE_SERVO;
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

  /**
   * Get the target pin mode.
   */
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

    case MODE_DIGITAL_OUTPUT:
      return STRING_DIGITAL_OUTPUT;

    case MODE_SERVO:
      return STRING_SERVO;
    }
    assert false;
    return null;
  }

  /**
   * Obtain the most recent reading from the input pin on MT7697. This function returns the value from
   * 0 to 255 under analog input mode, or returns 0 or 1 under digital input mode. If the mode is neither
   * the analog or digital input mode, it will raise an error.
   */
  @SimpleFunction
  public int Read() {
    if (!IsSupported())
      return -1;

    if (mMode == MODE_ANALOG_INPUT || mMode == MODE_DIGITAL_INPUT) {
      if (mData >= 0)
        return mData;
      else
        return -1;
    }
    else {
      form.dispatchErrorOccurredEvent(this,
                                      "Read",
                                      ErrorMessages.ERROR_EXTENSION_ERROR,
                                      ERROR_INVALID_STATE,
                                      LOG_TAG,
                                      "Call Read() on non-input mode");
      return -1;
    }
  }

  /**
   * Set the output intensity of a pin on MT7697. In analog output mode, the argument should be
   * non-negative and not exceed 255, otherwise it will be trimmed. In digital output mode, zero
   * and non-zero argument are respectively treated as LOW and HIGH outputs. In servo mode, the
   * argument should be in range from 0 to 180.
   *
   * __Parameters__:
   *
   *     * value (_number_); The output intensity of the target pin.
   *
   * @param The output intensity of the target pin.
   */
  @SimpleFunction
  public void Write(int value) {
    if (!IsSupported())
      return;

    if (mMode == MODE_ANALOG_OUTPUT || mMode == MODE_DIGITAL_OUTPUT) {
      // trim value
      value = value >= 0 ? value : 0;
      value = value <= 255 ? value : 255;
      mData = -value;
    }
    else if (mMode == MODE_SERVO) {
      value = value >= 0 ? value : 0;
      value = value <= 180 ? value : 180;
      mData = -value;
    }
    else {
      form.dispatchErrorOccurredEvent(this,
                                      "Write",
                                      ErrorMessages.ERROR_EXTENSION_ERROR,
                                      ERROR_INVALID_STATE,
                                      LOG_TAG,
                                      "Call Write() on non-output or non-servo mode");
    }
  }

  /**
   * Enable the InputUpdated event.
   */
  @SimpleFunction
  public void RequestInputUpdates() {
    mInputUpdate = true;
  }

  /**
   * Disable the previously requested InputUpdated event.
   */
  @SimpleFunction
  public void StopInputUpdates() {
    mInputUpdate = false;
  }

  /**
   * Tests whether the Bluetooth low energy device is broadcasting support for the service.
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
   * The InputUpdated event is triggered when a value is received from the input pin of MT7697.
   *
   * __Parameters__:
   *
   *     * value (_number_); The intensity which is read from the input pin.
   *
   * @param The intensity which is read from the input pin.
   */
  @SimpleEvent
  public void InputUpdated(final int value) {
    EventDispatcher.dispatchEvent(this, "InputUpdated", value);
  }
}
