// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017-2023 Massachusetts Institute of Technology, All rights reserved.
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.bbc.microbit.profile;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import edu.mit.appinventor.ble.BluetoothLE;

import android.util.Log;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

@DesignerComponent(version = 1,
    description = "The <code>Microbit_Io_Pin</code> component lets users configure the BBC micro:bit's " +
        "analog pins for input and output, and to read, write, and request notifications for the " +
        "I/O pin states."
        /* removed until we have tutorials
        + "<!--<br>\n\n<strong>More links:</strong><ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/MicrobitIoPins.aia' " +
        "target='_blank'>sample project</> for the micro:bit IO pins.</li><li>View the <a " +
        "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Microbit_IO_Pins.pdf' " +
        "target='_blank'>how to instructions</a> for the micro:bit IO pins.</li></ul>-->"
        */,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbitiopin",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Io_Pin_Simple extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String IO_PIN_SERVICE_UUID = "E95D127B-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_DATA_CHARACTERISTIC_UUID = "E95D8D00-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID = "E95D5899-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID = "E95DB9FE-251D-470A-A062-FA1922DFA9A8";
  private static final String PWM_CONTROL_CHARACTERISTIC_UUID = "E95DD822-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> pinDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        InputPinDataReceived(values);
      }
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteOutputPinData(values);
      }
    };

  private class ConfigurationHandler extends BluetoothLE.BLEResponseHandler<Long> {
    boolean adReceived = false;
    boolean ioReceived = false;
    boolean adWritten = false;
    boolean ioWritten = false;
    List<Long> adValues;
    List<Long> ioValues;

    @Override
    public void onReceive(String serviceUuid, String characteristicUuid, List<Long> values) {
      if (characteristicUuid == PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID) {
        adReceived = true;
        adValues = values;
      } else {
        ioReceived = true;
        ioValues = values;
      }
      if (adReceived && ioReceived) {
        adReceived = false;
        ioReceived = false;
        PinConfigurationReceived(adValues, ioValues);
      }
    }

    @Override
    public void onWrite(String serviceUuid, String characteristicUuid, List<Long> values) {
      if (characteristicUuid == PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID) {
        adWritten = true;
        adValues = values;
      } else {
        ioWritten = true;
        ioValues = values;
      }
      if (adWritten && ioWritten) {
        adWritten = false;
        ioWritten = false;
        WrotePinConfiguration(adValues, ioValues);
      }
    }

  }

  private final ConfigurationHandler pinConfigurationHandler = new ConfigurationHandler();

  private final BluetoothLE.BLEResponseHandler<Integer> PWMControlHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePWMControl(values);
      }
    };

  private final int NUMPINS = 19;
  private long ioConfig;
  private long adConfig;
  private final boolean[] isAnalog;
  private final boolean[] isInput;

  public Microbit_Io_Pin_Simple(Form form) {
    super(form);
    isAnalog = new boolean[NUMPINS];
    isInput = new boolean[NUMPINS];
    for (int i = 0; i < NUMPINS; i++) {
      isAnalog[i] = false;
      isInput[i] = false;
    }
  }

  /**
   * The BluetoothLE component connected to the micro:bit device (setter).
   * @param bluetoothLE the BluetoothLE device
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT +
      ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    bleConnection = bluetoothLE;  }

  /**
   * The BluetoothLE component connected to the micro:bit device (getter).
   * @return The BluetoothLE connection used to talk to the micro:bit device.
   */
  @SimpleProperty(description = "The BluetoothLE component connected to the micro:bit device.")
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }




  @SimpleFunction
  public void ReadInputPinData() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, pinDataHandler);
    } else {
      reportNullConnection("ReadInputPinData");
    }
  }

  @SimpleEvent
  public void InputPinDataReceived(final List<Integer> IO_Pin_Data) {
    EventDispatcher.dispatchEvent(this, "InputPinDataReceived", IO_Pin_Data);
  }

  /**
   * @param pinNumber - the micro:bit pin to write
   * @param value - value of the pin
   */
 @SimpleFunction
  public void WriteOutputPinData(int pinNumber, int pinValue) {

    if (pinNumber < 0 || pinNumber > NUMPINS) {
      form.dispatchErrorOccurredEvent(this, "WriteOutputPinData", ErrorMessages.ERROR_EXTENSION_ERROR, 1,
        Microbit_Io_Pin_Simple.class.getSimpleName(), "Pin number out of bounds: " + pinNumber);
      return;
    }
    if (isAnalog[pinNumber]) {
      if (pinValue < 0 || pinValue > 255) {
        form.dispatchErrorOccurredEvent(this, "WriteOutputPinData", ErrorMessages.ERROR_EXTENSION_ERROR,
          1, Microbit_Io_Pin_Simple.class.getSimpleName(), "Pin value out of bounds: " + pinValue);
        return;
      }
    } else {
      if (pinValue != 0 && pinValue != 1) {
        form.dispatchErrorOccurredEvent(this, "WriteOutputPinData", ErrorMessages.ERROR_EXTENSION_ERROR,
          1, Microbit_Io_Pin_Simple.class.getSimpleName(), "Pin value out of bounds: " + pinValue);
        return;
      }
    }

    if (isInput[pinNumber]) {
      form.dispatchErrorOccurredEvent(this, "WriteOutputPinData", ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Io_Pin_Simple.class.getSimpleName(), "Can't write to an input pin");
      return;
    }

    List<Integer> pinVals = new LinkedList<Integer>();
    pinVals.add(pinNumber);
    pinVals.add(pinValue);

    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, pinVals, pinDataHandler);
    } else {
      reportNullConnection("WriteOutputPinData");
    }
  }

  /**
  * @param pinNumber - the micro:bit pin to configure
  * @param analog - analog if true, digital if false
  * @param input - input if true, output if false
  */
  @SimpleFunction
  public void ConfigurePin(int pinNumber, boolean analog, boolean input) {
    if (pinNumber < 0 || pinNumber > NUMPINS) {
      form.dispatchErrorOccurredEvent(this, "ConfigurePin", ErrorMessages.ERROR_EXTENSION_ERROR, 1,
        Microbit_Io_Pin_Simple.class.getSimpleName(), "Pin number out of bounds: " + pinNumber);
      return;
    }
    isAnalog[pinNumber] = analog;
    isInput[pinNumber] = input;

    if (analog) {
      adConfig |= (1 << pinNumber);
    } else {
      adConfig &= ~(1 << pinNumber);
    }

    if (input) {
      ioConfig |= (1 << pinNumber);
    } else {
      ioConfig &= ~(1 << pinNumber);
    }

    if (bleConnection != null) {
      bleConnection.ExWriteIntegerValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID, false, ioConfig, pinConfigurationHandler);
      bleConnection.ExWriteIntegerValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID, false, adConfig, pinConfigurationHandler);
    } else {
      reportNullConnection("ConfigurePin");
    }
  }

  @SimpleFunction
  public void RequestPinDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, pinDataHandler);
    } else {
      reportNullConnection("RequestPinDataUpdates");
    }
  }

  @SimpleFunction
  public void StopPinDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, pinDataHandler);
    } else {
      reportNullConnection("StopPinDataUpdates");
    }
  }

  @SimpleEvent
  public void WroteOutputPinData(final List<Integer> IO_Pin_Data) {
    EventDispatcher.dispatchEvent(this, "WroteOutputPinData", IO_Pin_Data);
  }

 //@SimpleFunction
  public void ReadPinConfiguration() {
    if (bleConnection != null) {
      bleConnection.ExReadIntegerValues(IO_PIN_SERVICE_UUID, PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID, false, pinConfigurationHandler);
      bleConnection.ExReadIntegerValues(IO_PIN_SERVICE_UUID, PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID, false, pinConfigurationHandler);
    } else {
      reportNullConnection("ReadPinConfiguration");
    }
  }

  //@SimpleEvent
  public void PinConfigurationReceived(final List<Long> Pin_AD_Config_Value, final List<Long> Pin_IO_Config_Value) {
    EventDispatcher.dispatchEvent(this, "PinConfigurationReceived", Pin_AD_Config_Value, Pin_IO_Config_Value);
  }


  //@SimpleEvent
  public void WrotePinConfiguration(final List<Long> Pin_AD_Config_Value, final List<Long> Pin_IO_Config_Value) {
    EventDispatcher.dispatchEvent(this, "WrotePinConfiguration", Pin_AD_Config_Value, Pin_IO_Config_Value);
  }


  @SimpleFunction
  public void WritePWMControl(final byte pinNumber, final short value, final int period) {
    // 1 byte
    if (pinNumber < 0 && pinNumber > NUMPINS) {
      form.dispatchErrorOccurredEvent(this, "WritePWMControl", ErrorMessages.ERROR_EXTENSION_ERROR, 1,
        Microbit_Io_Pin_Simple.class.getSimpleName(), "Pin number out of bounds: " + pinNumber);
      return;
    // 2 bytes
    } else if (value < 0 && value > 1023) {
      form.dispatchErrorOccurredEvent(this, "WritePWMControl", ErrorMessages.ERROR_EXTENSION_ERROR, 1,
        Microbit_Io_Pin_Simple.class.getSimpleName(), "Value out of bounds: " + value);
      return;
    // 4 bytes
    } else if (period < 1) {
      form.dispatchErrorOccurredEvent(this, "WritePWMControl", ErrorMessages.ERROR_EXTENSION_ERROR, 1,
        Microbit_Io_Pin_Simple.class.getSimpleName(), "Period out of bounds: " + period);
      return;
    }

    byte[] PWMControlField = new byte[7];
    PWMControlField[0] = pinNumber;
    PWMControlField[1] = (byte)(value & 0xFF);
    PWMControlField[2] = (byte)(value & 0xFF00);
    PWMControlField[3] = (byte)(period & 0xFF);
    PWMControlField[4] = (byte)((period & 0xFF00) >> 8);
    PWMControlField[5] = (byte)((period & 0xFF0000) >> 16);
    PWMControlField[6] = (byte)((period & 0xFF000000) >> 24);


    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PWM_CONTROL_CHARACTERISTIC_UUID, false,  Arrays.asList(PWMControlField), PWMControlHandler);
    } else {
      reportNullConnection("WritePWMControl");
    }
  }

  @SimpleEvent
  public void WrotePWMControl(final List<Integer> PWM_Control_Field) {
    EventDispatcher.dispatchEvent(this, "WrotePWMControl", PWM_Control_Field);
  }

  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Io_Pin_Simple.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
