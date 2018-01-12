package com.bbc.microbit.profile;

import android.util.Log;
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
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.List;

@DesignerComponent(version = 1,
    description = "",
    category = ComponentCategory.INTERNAL,
    nonVisible = true,
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String ACCELEROMETER_SERVICE_UUID = "E95D0753-251D-470A-A062-FA1922DFA9A8";
  private static final String ACCELEROMETER_DATA_CHARACTERISTIC_UUID = "E95DCA4B-251D-470A-A062-FA1922DFA9A8";
  private static final String ACCELEROMETER_PERIOD_CHARACTERISTIC_UUID = "E95DFB24-251D-470A-A062-FA1922DFA9A8";

  private static final String BUTTON_SERVICE_UUID = "E95D9882-251D-470A-A062-FA1922DFA9A8";
  private static final String BUTTON_A_STATE_CHARACTERISTIC_UUID = "E95DDA90-251D-470A-A062-FA1922DFA9A8";
  private static final String BUTTON_B_STATE_CHARACTERISTIC_UUID = "E95DDA91-251D-470A-A062-FA1922DFA9A8";

  private static final String DEVICE_INFORMATION_UUID = "0000180A-0000-1000-8000-00805F9B34FB";
  private static final String MODEL_NUMBER_STRING_CHARACTERISTIC_UUID = "00002A24-0000-1000-8000-00805F9B34FB";
  private static final String SERIAL_NUMBER_STRING_CHARACTERISTIC_UUID = "00002A25-0000-1000-8000-00805F9B34FB";
  private static final String HARDWARE_REVISION_STRING_CHARACTERISTIC_UUID = "00002A27-0000-1000-8000-00805F9B34FB";
  private static final String FIRMWARE_REVISION_STRING_CHARACTERISTIC_UUID = "00002A26-0000-1000-8000-00805F9B34FB";
  private static final String MANUFACTURER_NAME_STRING_CHARACTERISTIC_UUID = "00002A29-0000-1000-8000-00805F9B34FB";

  private static final String DFU_CONTROL_SERVICE_UUID = "E95D93B0-251D-470A-A062-FA1922DFA9A8";
  private static final String DFU_CONTROL_CHARACTERISTIC_UUID = "E95D93B1-251D-470A-A062-FA1922DFA9A8";

  private static final String EVENT_SERVICE_UUID = "E95D93AF-251D-470A-A062-FA1922DFA9A8";
  private static final String MICROBIT_REQUIREMENTS_CHARACTERISTIC_UUID = "E95DB84C-251D-470A-A062-FA1922DFA9A8";
  private static final String MICROBIT_EVENT_CHARACTERISTIC_UUID = "E95D9775-251D-470A-A062-FA1922DFA9A8";
  private static final String CLIENT_REQUIREMENTS_CHARACTERISTIC_UUID = "E95D23C4-251D-470A-A062-FA1922DFA9A8";
  private static final String CLIENT_EVENT_CHARACTERISTIC_UUID = "E95D5404-251D-470A-A062-FA1922DFA9A8";

  private static final String GENERIC_ACCESS_UUID = "00001800-0000-1000-8000-00805F9B34FB";
  private static final String DEVICE_NAME_CHARACTERISTIC_UUID = "00002A00-0000-1000-8000-00805F9B34FB";
  private static final String APPEARANCE_CHARACTERISTIC_UUID = "00002A01-0000-1000-8000-00805F9B34FB";
  private static final String PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_CHARACTERISTIC_UUID = "00002A04-0000-1000-8000-00805F9B34FB";

  private static final String GENERIC_ATTRIBUTE_UUID = "00001801-0000-1000-8000-00805F9B34FB";
  private static final String SERVICE_CHANGED_CHARACTERISTIC_UUID = "00002A05-0000-1000-8000-00805F9B34FB";

  private static final String IO_PIN_SERVICE_UUID = "E95D127B-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_DATA_CHARACTERISTIC_UUID = "E95D8D00-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID = "E95D5899-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID = "E95DB9FE-251D-470A-A062-FA1922DFA9A8";
  private static final String PWM_CONTROL_CHARACTERISTIC_UUID = "E95DD822-251D-470A-A062-FA1922DFA9A8";

  private static final String LED_SERVICE_UUID = "E95DD91D-251D-470A-A062-FA1922DFA9A8";
  private static final String LED_MATRIX_STATE_CHARACTERISTIC_UUID = "E95D7B77-251D-470A-A062-FA1922DFA9A8";
  private static final String LED_TEXT_CHARACTERISTIC_UUID = "E95D93EE-251D-470A-A062-FA1922DFA9A8";
  private static final String SCROLLING_DELAY_CHARACTERISTIC_UUID = "E95D0D2D-251D-470A-A062-FA1922DFA9A8";

  private static final String MAGNETOMETER_SERVICE_UUID = "E95DF2D8-251D-470A-A062-FA1922DFA9A8";
  private static final String MAGNETOMETER_DATA_CHARACTERISTIC_UUID = "E95DFB11-251D-470A-A062-FA1922DFA9A8";
  private static final String MAGNETOMETER_PERIOD_CHARACTERISTIC_UUID = "E95D386C-251D-470A-A062-FA1922DFA9A8";
  private static final String MAGNETOMETER_BEARING_CHARACTERISTIC_UUID = "E95D9715-251D-470A-A062-FA1922DFA9A8";

  private static final String TEMPERATURE_SERVICE_UUID = "E95D6100-251D-470A-A062-FA1922DFA9A8";
  private static final String TEMPERATURE_CHARACTERISTIC_UUID = "E95D9250-251D-470A-A062-FA1922DFA9A8";
  private static final String TEMPERATURE_PERIOD_CHARACTERISTIC_UUID = "E95D1B25-251D-470A-A062-FA1922DFA9A8";

  private static final String UART_SERVICE_UUID = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E";
  private static final String TX_CHARACTERISTIC_CHARACTERISTIC_UUID = "6E400002-B5A3-F393-E0A9-E50E24DCCA9E";
  private static final String RX_CHARACTERISTIC_CHARACTERISTIC_UUID = "6E400003-B5A3-F393-E0A9-E50E24DCCA9E";

  private final BluetoothLE.BLEResponseHandler<String> modelNumberHandler =
      new BluetoothLE.BLEResponseHandler<String>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
          ModelNumberStringReceived(values.get(0));
        }
      };

  private final BluetoothLE.BLEResponseHandler<String> serialNumberHandler =
      new BluetoothLE.BLEResponseHandler<String>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
          SerialNumberStringReceived(values.get(0));
        }
      };

  private final BluetoothLE.BLEResponseHandler<String> hardwareRevisionHandler =
      new BluetoothLE.BLEResponseHandler<String>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
          HardwareRevisionStringReceived(values.get(0));
        }
      };

  private final BluetoothLE.BLEResponseHandler<String> firmwareRevisionHandler =
      new BluetoothLE.BLEResponseHandler<String>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
          FirmwareRevisionStringReceived(values.get(0));
        }
      };

  private final BluetoothLE.BLEResponseHandler<String> manufacturerNameHandler =
      new BluetoothLE.BLEResponseHandler<String>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
          ManufacturerNameStringReceived(values.get(0));
        }
      };

  private final BluetoothLE.BLEResponseHandler<Integer> appearanceHandler =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
          AppearanceReceived(values.get(0));
        }
      };

  private final BluetoothLE.BLEResponseHandler<Integer> peripheralPreferredConnectionParametersHandler =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
          PeripheralPreferredConnectionParametersReceived(values.get(0), values.get(1), values.get(2), values.get(3));
        }
      };

  private final BluetoothLE.BLEResponseHandler<Integer> accelerometerDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        AccelerometerDataReceived(values.get(0), values.get(1), values.get(2));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> accelerometerPeriodWriteHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        AccelerometerPeriodReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteAccelerometerPeriod(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> buttonAStateHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        Log.d("Microbit", "buttonAStateHandler.onReceive");
        ButtonAStateReceived(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> buttonBStateHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        Log.d("Microbit", "buttonBStateHandler.onReceive");
        ButtonBStateReceived(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> dFUControlHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        DFUControlReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteDFUControl(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> microBitRequirementsHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MicroBitRequirementsReceived(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> microBitEventHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MicroBitEventReceived(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> clientRequirementsWriteHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteClientRequirements(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> clientEventWriteHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteClientEvent(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<String> deviceNameHandler =
    new BluetoothLE.BLEResponseHandler<String>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
        DeviceNameReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<String> values) {
        WroteDeviceName(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> pinDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        PinDataReceived(values);
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePinData(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> pinADConfigurationHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        PinADConfigurationReceived(values);
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
         WrotePinADConfiguration(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> pinIOConfigurationHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        PinIOConfigurationReceived(values);
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePinIOConfiguration(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> pWMControlWriteHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePWMControl(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> lEDMatrixStateHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        LEDMatrixStateReceived(values);
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteLEDMatrixState(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<String> lEDTextWriteHandler =
    new BluetoothLE.BLEResponseHandler<String>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<String> values) {
        WroteLEDText(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> scrollingDelayHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        ScrollingDelayReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteScrollingDelay(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> magnetometerDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MagnetometerDataReceived(values.get(0), values.get(1), values.get(2));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> magnetometerPeriodHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MagnetometerPeriodReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteMagnetometerPeriod(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> magnetometerBearingHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MagnetometerBearingReceived(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> temperatureHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        TemperatureReceived(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> temperaturePeriodHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        TemperaturePeriodReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteTemperaturePeriod(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> txCharacteristicHandler =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
          TXCharacteristicReceived(values);
        }
      };

  private final BluetoothLE.BLEResponseHandler<Integer> rXCharacteristicWriteHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteRXCharacteristic(values);
      }
    };

  public Microbit(Form form) {
    super(form);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT +
      ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    bleConnection = bluetoothLE;  }

  @SimpleProperty(description = "The BluetoothLE component connected to the micro:bit device.")
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  @SimpleFunction
  public void ReadAccelerometerData() {
    bleConnection.ExReadShortValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_DATA_CHARACTERISTIC_UUID, true, accelerometerDataHandler);
  }

  @SimpleFunction
  public void RequestAccelerometerDataUpdates() {
    bleConnection.ExRegisterForShortValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_DATA_CHARACTERISTIC_UUID, true, accelerometerDataHandler);
  }

  @SimpleFunction
  public void StopAccelerometerDataUpdates() {
    bleConnection.ExUnregisterForValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_DATA_CHARACTERISTIC_UUID, accelerometerDataHandler);
  }

  @SimpleEvent
  public void AccelerometerDataReceived(final int Accelerometer_X_, final int Accelerometer_Y, final int Accelerometer_Z) {
    EventDispatcher.dispatchEvent(this, "AccelerometerDataReceived", Accelerometer_X_, Accelerometer_Y, Accelerometer_Z);
  }

  @SimpleFunction
  public void ReadAccelerometerPeriod() {
    bleConnection.ExReadShortValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_PERIOD_CHARACTERISTIC_UUID, false, accelerometerPeriodWriteHandler);
  }

  @SimpleFunction
  public void WriteAccelerometerPeriod(int value) {
    bleConnection.ExWriteShortValuesWithResponse(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_PERIOD_CHARACTERISTIC_UUID, false, value, accelerometerPeriodWriteHandler);
  }

  @SimpleEvent
  public void AccelerometerPeriodReceived(final int Accelerometer_Period) {
    EventDispatcher.dispatchEvent(this, "AccelerometerPeriodReceived", Accelerometer_Period);
  }

  @SimpleEvent
  public void WroteAccelerometerPeriod(final int Accelerometer_Period) {
    EventDispatcher.dispatchEvent(this, "WroteAccelerometerPeriod", Accelerometer_Period);
  }

  @SimpleFunction
  public void ReadButtonAState() {
    bleConnection.ExReadByteValues(BUTTON_SERVICE_UUID, BUTTON_A_STATE_CHARACTERISTIC_UUID, false, buttonAStateHandler);
  }

  @SimpleFunction
  public void RequestButtonAStateUpdates() {
    bleConnection.ExRegisterForByteValues(BUTTON_SERVICE_UUID, BUTTON_A_STATE_CHARACTERISTIC_UUID, false, buttonAStateHandler);
  }

  @SimpleFunction
  public void StopButtonAStateUpdates() {
    bleConnection.ExUnregisterForValues(BUTTON_SERVICE_UUID, BUTTON_A_STATE_CHARACTERISTIC_UUID, buttonAStateHandler);
  }

  @SimpleEvent
  public void ButtonAStateReceived(final int Button_State_Value) {
    EventDispatcher.dispatchEvent(this, "ButtonAStateReceived", Button_State_Value);
  }

  @SimpleFunction
  public void ReadButtonBState() {
    bleConnection.ExReadByteValues(BUTTON_SERVICE_UUID, BUTTON_B_STATE_CHARACTERISTIC_UUID, false, buttonBStateHandler);
  }

  @SimpleFunction
  public void RequestButtonBStateUpdates() {
    bleConnection.ExRegisterForByteValues(BUTTON_SERVICE_UUID, BUTTON_B_STATE_CHARACTERISTIC_UUID, false, buttonBStateHandler);
  }

  @SimpleFunction
  public void StopButtonBStateUpdates() {
    bleConnection.ExUnregisterForValues(BUTTON_SERVICE_UUID, BUTTON_B_STATE_CHARACTERISTIC_UUID, buttonBStateHandler);
  }

  @SimpleEvent
  public void ButtonBStateReceived(final int Button_State_Value) {
    EventDispatcher.dispatchEvent(this, "ButtonBStateReceived", Button_State_Value);
  }

  @SimpleFunction
  public void ReadModelNumberString() {
    bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, MODEL_NUMBER_STRING_CHARACTERISTIC_UUID, false, modelNumberHandler);
  }

  @SimpleEvent
  public void ModelNumberStringReceived(String Model_Number) {
    EventDispatcher.dispatchEvent(this, "ModelNumberStringReceived", Model_Number);
  }

  @SimpleFunction
  public void ReadSerialNumberString() {
    bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, SERIAL_NUMBER_STRING_CHARACTERISTIC_UUID, false, serialNumberHandler);
  }

  @SimpleEvent
  public void SerialNumberStringReceived(String Serial_Number) {
    EventDispatcher.dispatchEvent(this, "SerialNumberStringReceived", Serial_Number);
  }

  @SimpleFunction
  public void ReadHardwareRevisionString() {
    bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, HARDWARE_REVISION_STRING_CHARACTERISTIC_UUID, false, hardwareRevisionHandler);
  }

  @SimpleEvent
  public void HardwareRevisionStringReceived(String Hardware_Revision) {
    EventDispatcher.dispatchEvent(this, "HardwareRevisionStringReceived", Hardware_Revision);
  }

  @SimpleFunction
  public void ReadFirmwareRevisionString() {
    bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, FIRMWARE_REVISION_STRING_CHARACTERISTIC_UUID, false, firmwareRevisionHandler);
  }

  @SimpleEvent
  public void FirmwareRevisionStringReceived(String Firmware_Revision) {
    EventDispatcher.dispatchEvent(this, "FirmwareRevisionStringReceived", Firmware_Revision);
  }

  @SimpleFunction
  public void ReadManufacturerNameString() {
    bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, MANUFACTURER_NAME_STRING_CHARACTERISTIC_UUID, false, manufacturerNameHandler);
  }

  @SimpleEvent
  public void ManufacturerNameStringReceived(String Manufacturer_Name) {
    EventDispatcher.dispatchEvent(this, "ManufacturerNameStringReceived", Manufacturer_Name);
  }

  @SimpleFunction
  public void ReadDFUControl() {
    bleConnection.ExReadByteValues(DFU_CONTROL_SERVICE_UUID, DFU_CONTROL_CHARACTERISTIC_UUID, false, dFUControlHandler);
  }

  @SimpleFunction
  public void WriteDFUControl(int dfu_control) {
    bleConnection.ExWriteByteValuesWithResponse(DFU_CONTROL_SERVICE_UUID, DFU_CONTROL_CHARACTERISTIC_UUID, false, dfu_control, dFUControlHandler);
  }

  @SimpleEvent
  public void DFUControlReceived(final int dfu_control) {
    EventDispatcher.dispatchEvent(this, "DFUControlReceived", dfu_control);
  }

  @SimpleEvent
  public void WroteDFUControl(final int dfu_control) {
    EventDispatcher.dispatchEvent(this, "WroteDFUControl", dfu_control);
  }

  @SimpleFunction
  public void ReadMicroBitRequirements() {
    bleConnection.ExReadShortValues(EVENT_SERVICE_UUID, MICROBIT_REQUIREMENTS_CHARACTERISTIC_UUID, false, microBitRequirementsHandler);
  }

  @SimpleFunction
  public void RequestMicroBitRequirementsUpdates() {
    bleConnection.ExRegisterForShortValues(EVENT_SERVICE_UUID, MICROBIT_REQUIREMENTS_CHARACTERISTIC_UUID, false, microBitRequirementsHandler);
  }

  @SimpleFunction
  public void StopMicroBitRequirementsUpdates() {
    bleConnection.ExUnregisterForValues(EVENT_SERVICE_UUID, MICROBIT_REQUIREMENTS_CHARACTERISTIC_UUID, microBitRequirementsHandler);
  }

  @SimpleEvent
  public void MicroBitRequirementsReceived(final int microbit_reqs_value) {
    EventDispatcher.dispatchEvent(this, "MicroBitRequirementsReceived", microbit_reqs_value);
  }

  @SimpleFunction
  public void ReadMicroBitEvent() {
    bleConnection.ExReadShortValues(EVENT_SERVICE_UUID, MICROBIT_EVENT_CHARACTERISTIC_UUID, false, microBitEventHandler);
  }

  @SimpleFunction
  public void RequestMicroBitEventUpdates() {
    bleConnection.ExRegisterForShortValues(EVENT_SERVICE_UUID, MICROBIT_EVENT_CHARACTERISTIC_UUID, false, microBitEventHandler);
  }

  @SimpleFunction
  public void StopMicroBitEventUpdates() {
    bleConnection.ExUnregisterForValues(EVENT_SERVICE_UUID, MICROBIT_EVENT_CHARACTERISTIC_UUID, microBitEventHandler);
  }

  @SimpleEvent
  public void MicroBitEventReceived(final int Event_Type_And_Value) {
    EventDispatcher.dispatchEvent(this, "MicroBitEventReceived", Event_Type_And_Value);
  }

  @SimpleFunction
  public void WriteClientRequirements(Object client_requirements_value) {
    bleConnection.ExWriteByteValuesWithResponse(EVENT_SERVICE_UUID, CLIENT_REQUIREMENTS_CHARACTERISTIC_UUID, false, client_requirements_value, clientRequirementsWriteHandler);
  }

  @SimpleEvent
  public void WroteClientRequirements(final int Client_Requirements_Value) {
    EventDispatcher.dispatchEvent(this, "WroteClientRequirements", Client_Requirements_Value);
  }

  @SimpleFunction
  public void WriteClientEvent(Object Event_Types_And_Values) {
    bleConnection.ExWriteByteValuesWithResponse(EVENT_SERVICE_UUID, CLIENT_EVENT_CHARACTERISTIC_UUID, false, Event_Types_And_Values, clientEventWriteHandler);
  }

  @SimpleEvent
  public void WroteClientEvent(final int Event_Types_And_Values) {
    EventDispatcher.dispatchEvent(this, "WroteClientEvent", Event_Types_And_Values);
  }

  @SimpleFunction
  public void ReadDeviceName() {
    bleConnection.ExReadStringValues(GENERIC_ACCESS_UUID, DEVICE_NAME_CHARACTERISTIC_UUID, false, deviceNameHandler);
  }

  @SimpleFunction
  public void WriteDeviceName(String name) {
    bleConnection.ExWriteStringValuesWithResponse(GENERIC_ACCESS_UUID, DEVICE_NAME_CHARACTERISTIC_UUID, false, name, deviceNameHandler);
  }

  @SimpleEvent
  public void DeviceNameReceived(final String Name) {
    EventDispatcher.dispatchEvent(this, "DeviceNameReceived", Name);
  }

  @SimpleEvent
  public void WroteDeviceName(final String Name) {
    EventDispatcher.dispatchEvent(this, "WroteDeviceName", Name);
  }

  @SimpleFunction
  public void ReadAppearance() {
    bleConnection.ExReadShortValues(GENERIC_ACCESS_UUID, APPEARANCE_CHARACTERISTIC_UUID, false, appearanceHandler);
  }

  @SimpleEvent
  public void AppearanceReceived(final int Category) {
    EventDispatcher.dispatchEvent(this, "AppearanceReceived", Category);
  }

  @SimpleFunction
  public void ReadPeripheralPreferredConnectionParameters() {
    bleConnection.ExReadShortValues(GENERIC_ACCESS_UUID, PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_CHARACTERISTIC_UUID, false, peripheralPreferredConnectionParametersHandler);
  }

  @SimpleEvent
  public void PeripheralPreferredConnectionParametersReceived(int Minimum_Connection_Interval,
                                                              int Maximum_Connection_Interval,
                                                              int Slave_Latency,
                                                              int Connection_Supervision_Timeout_Multiplier) {
    EventDispatcher.dispatchEvent(this, "PeripheralPreferredConnectionParametersReceived", Minimum_Connection_Interval, Maximum_Connection_Interval, Slave_Latency, Connection_Supervision_Timeout_Multiplier);
  }

  @SimpleFunction
  public void ReadPinData() {
    bleConnection.ExReadShortValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, pinDataHandler);
  }

  @SimpleFunction
  public void WritePinData(Object IO_Pin_Data) {
    bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, IO_Pin_Data, pinDataHandler);
  }

  @SimpleEvent
  public void WrotePinData(final List<Integer> IO_Pin_Data) {
    EventDispatcher.dispatchEvent(this, "WrotePinData", IO_Pin_Data);
  }

  @SimpleFunction
  public void RequestPinDataUpdates() {
    bleConnection.ExRegisterForByteValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, pinDataHandler);
  }

  @SimpleFunction
  public void StopPinDataUpdates() {
    bleConnection.ExUnregisterForValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, pinDataHandler);
  }

  @SimpleEvent
  public void PinDataReceived(final List<Integer> IO_Pin_Data) {
    EventDispatcher.dispatchEvent(this, "PinDataReceived", IO_Pin_Data);
  }

  @SimpleFunction
  public void ReadPinADConfiguration() {
    bleConnection.ExReadByteValues(IO_PIN_SERVICE_UUID, PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID, false, pinADConfigurationHandler);
  }

  @SimpleFunction
  public void WritePinADConfiguration(Object Pin_AD_Config_Value) {
    bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID, false, Pin_AD_Config_Value, pinADConfigurationHandler);
  }

  @SimpleEvent
  public void PinADConfigurationReceived(final List<Integer> Pin_AD_Config_Value) {
    EventDispatcher.dispatchEvent(this, "PinADConfigurationReceived", Pin_AD_Config_Value);
  }

  @SimpleEvent
  public void WrotePinADConfiguration(final List<Integer> Pin_AD_Config_Value) {
    EventDispatcher.dispatchEvent(this, "WrotePinADConfiguration", Pin_AD_Config_Value);
  }

  @SimpleFunction
  public void ReadPinIOConfiguration() {
    bleConnection.ExReadByteValues(IO_PIN_SERVICE_UUID, PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID, false, pinIOConfigurationHandler);
  }

  @SimpleFunction
  public void WritePinIOConfiguration(Object Pin_IO_Config_Value) {
    bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID, false, Pin_IO_Config_Value, pinIOConfigurationHandler);
  }

  @SimpleEvent
  public void PinIOConfigurationReceived(final List<Integer> Pin_IO_Config_Value) {
    EventDispatcher.dispatchEvent(this, "PinIOConfigurationReceived", Pin_IO_Config_Value);
  }

  @SimpleEvent
  public void WrotePinIOConfiguration(final List<Integer> Pin_IO_Config_Value) {
    EventDispatcher.dispatchEvent(this, "WrotePinIOConfiguration", Pin_IO_Config_Value);
  }

  @SimpleFunction
  public void WritePWMControl(Object PWM_Control_Field) {
    bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PWM_CONTROL_CHARACTERISTIC_UUID, false, PWM_Control_Field, pWMControlWriteHandler);
  }

  @SimpleEvent
  public void WrotePWMControl(final List<Integer> PWM_Control_Field) {
    EventDispatcher.dispatchEvent(this, "WrotePWMControl", PWM_Control_Field);
  }

  @SimpleFunction
  public void ReadLEDMatrixState() {
    bleConnection.ExReadByteValues(LED_SERVICE_UUID, LED_MATRIX_STATE_CHARACTERISTIC_UUID, false, lEDMatrixStateHandler);
  }

  @SimpleFunction
  public void WriteLEDMatrixState(Object LED_Matrix_State) {
    bleConnection.ExWriteByteValuesWithResponse(LED_SERVICE_UUID, LED_MATRIX_STATE_CHARACTERISTIC_UUID, false, LED_Matrix_State, lEDMatrixStateHandler);
  }

  @SimpleEvent
  public void LEDMatrixStateReceived(final List<Integer> LED_Matrix_State) {
    EventDispatcher.dispatchEvent(this, "LEDMatrixStateReceived", LED_Matrix_State);
  }

  @SimpleEvent
  public void WroteLEDMatrixState(final List<Integer> LED_Matrix_State) {
    EventDispatcher.dispatchEvent(this, "WroteLEDMatrixState", LED_Matrix_State);
  }

  @SimpleFunction
  public void WriteLEDText(String LED_Text_Value) {
    bleConnection.ExWriteStringValuesWithResponse(LED_SERVICE_UUID, LED_TEXT_CHARACTERISTIC_UUID, false, LED_Text_Value, lEDTextWriteHandler);
  }

  @SimpleEvent
  public void WroteLEDText(final String LED_Text_Value) {
    EventDispatcher.dispatchEvent(this, "WroteLEDText", LED_Text_Value);
  }

  @SimpleFunction
  public void ReadScrollingDelay() {
    bleConnection.ExReadShortValues(LED_SERVICE_UUID, SCROLLING_DELAY_CHARACTERISTIC_UUID, false, scrollingDelayHandler);
  }

  @SimpleFunction
  public void WriteScrollingDelay(short Scrolling_Delay_Value) {
    bleConnection.ExWriteShortValuesWithResponse(LED_SERVICE_UUID, SCROLLING_DELAY_CHARACTERISTIC_UUID, false, Scrolling_Delay_Value, scrollingDelayHandler);
  }

  @SimpleEvent
  public void ScrollingDelayReceived(int Scrolling_Delay_Value) {
    EventDispatcher.dispatchEvent(this, "ScrollingDelayReceived", Scrolling_Delay_Value);
  }

  @SimpleEvent
  public void WroteScrollingDelay(final int Scrolling_Delay_Value) {
    EventDispatcher.dispatchEvent(this, "WroteScrollingDelay", Scrolling_Delay_Value);
  }

  @SimpleFunction
  public void ReadMagnetometerData() {
    bleConnection.ExReadShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_DATA_CHARACTERISTIC_UUID, true, magnetometerDataHandler);
  }

  @SimpleFunction
  public void RequestMagnetometerDataUpdates() {
    bleConnection.ExRegisterForShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_DATA_CHARACTERISTIC_UUID, true, magnetometerDataHandler);
  }

  @SimpleFunction
  public void StopMagnetometerDataUpdates() {
    bleConnection.ExUnregisterForValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_DATA_CHARACTERISTIC_UUID, magnetometerDataHandler);
  }

  @SimpleEvent
  public void MagnetometerDataReceived(final int Magnetometer_X, final int Magnetometer_Y, final int Magnetometer_Z) {
    EventDispatcher.dispatchEvent(this, "MagnetometerDataReceived", Magnetometer_X, Magnetometer_Y, Magnetometer_Z);
  }

  @SimpleFunction
  public void ReadMagnetometerPeriod() {
    bleConnection.ExReadShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_PERIOD_CHARACTERISTIC_UUID, false, magnetometerPeriodHandler);
  }

  @SimpleFunction
  public void WriteMagnetometerPeriod(short Magnetometer_Period) {
    bleConnection.ExWriteShortValuesWithResponse(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_PERIOD_CHARACTERISTIC_UUID, false, Magnetometer_Period, magnetometerPeriodHandler);
  }

  @SimpleEvent
  public void MagnetometerPeriodReceived(final int Magnetometer_Period) {
    EventDispatcher.dispatchEvent(this, "ReceivedMagnetometerPeriod", Magnetometer_Period);
  }

  @SimpleEvent
  public void WroteMagnetometerPeriod(final int Magnetometer_Period) {
    EventDispatcher.dispatchEvent(this, "WroteMagnetometerPeriod", Magnetometer_Period);
  }

  @SimpleFunction
  public void ReadMagnetometerBearing() {
    bleConnection.ExReadShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_BEARING_CHARACTERISTIC_UUID, false, magnetometerBearingHandler);
  }

  @SimpleFunction
  public void RequestMagnetometerBearingUpdates() {
    bleConnection.ExRegisterForShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_BEARING_CHARACTERISTIC_UUID, false, magnetometerBearingHandler);
  }

  @SimpleFunction
  public void StopMagnetometerBearingUpdates() {
    bleConnection.ExUnregisterForValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_BEARING_CHARACTERISTIC_UUID, magnetometerBearingHandler);
  }

  @SimpleEvent
  public void MagnetometerBearingReceived(final int bearing_value) {
    EventDispatcher.dispatchEvent(this, "MagnetometerBearingReceived", bearing_value);
  }

  @SimpleFunction
  public void ReadTemperature() {
    bleConnection.ExReadByteValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_CHARACTERISTIC_UUID, true, temperatureHandler);
  }

  @SimpleFunction
  public void RequestTemperatureUpdates() {
    bleConnection.ExRegisterForByteValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_CHARACTERISTIC_UUID, true, temperatureHandler);
  }

  @SimpleFunction
  public void StopTemperatureUpdates() {
    bleConnection.ExUnregisterForValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_CHARACTERISTIC_UUID, temperatureHandler);
  }

  @SimpleEvent
  public void TemperatureReceived(final int temperature_value) {
    EventDispatcher.dispatchEvent(this, "TemperatureReceived", temperature_value);
  }

  @SimpleFunction
  public void ReadTemperaturePeriod() {
    bleConnection.ExReadShortValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_PERIOD_CHARACTERISTIC_UUID, false, temperaturePeriodHandler);
  }

  @SimpleFunction
  public void WriteTemperaturePeriod(int value) {
    bleConnection.ExWriteShortValuesWithResponse(TEMPERATURE_SERVICE_UUID, TEMPERATURE_PERIOD_CHARACTERISTIC_UUID, false, value, temperaturePeriodHandler);
  }

  @SimpleEvent
  public void TemperaturePeriodReceived(final int temperature_period_value) {
    EventDispatcher.dispatchEvent(this, "TemperaturePeriodReceived", temperature_period_value);
  }

  @SimpleEvent
  public void WroteTemperaturePeriod(final int temperature_period_value) {
    EventDispatcher.dispatchEvent(this, "WroteTemperaturePeriod", temperature_period_value);
  }

  @SimpleFunction
  public void RequestTXCharacteristic() {
    bleConnection.ExRegisterForByteValues(UART_SERVICE_UUID, TX_CHARACTERISTIC_CHARACTERISTIC_UUID, false, txCharacteristicHandler);
  }

  @SimpleFunction
  public void StopTXCharacteristicUpdates() {
    bleConnection.ExUnregisterForValues(UART_SERVICE_UUID, TX_CHARACTERISTIC_CHARACTERISTIC_UUID, txCharacteristicHandler);
  }

  @SimpleEvent
  public void TXCharacteristicReceived(final List<Integer> UART_TX_Field) {
    EventDispatcher.dispatchEvent(this, "TXCharacteristicReceived", UART_TX_Field);
  }

  @SimpleFunction
  public void WriteRXCharacteristic(Object UART_TX) {
    bleConnection.ExWriteByteValuesWithResponse(UART_SERVICE_UUID, RX_CHARACTERISTIC_CHARACTERISTIC_UUID, false, UART_TX, rXCharacteristicWriteHandler);
  }

  @SimpleEvent
  public void WroteRXCharacteristic(final List<Integer> UART_TX_Field) {
    EventDispatcher.dispatchEvent(this, "WroteRXCharacteristic", UART_TX_Field);
  }

}
