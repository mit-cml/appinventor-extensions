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

import java.util.List;

@DesignerComponent(version = 1,
    description = "",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Device_Information extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String DEVICE_INFORMATION_UUID = "0000180A-0000-1000-8000-00805F9B34FB";
  private static final String MODEL_NUMBER_STRING_CHARACTERISTIC_UUID = "00002A24-0000-1000-8000-00805F9B34FB";
  private static final String SERIAL_NUMBER_STRING_CHARACTERISTIC_UUID = "00002A25-0000-1000-8000-00805F9B34FB";
  private static final String HARDWARE_REVISION_STRING_CHARACTERISTIC_UUID = "00002A27-0000-1000-8000-00805F9B34FB";
  private static final String FIRMWARE_REVISION_STRING_CHARACTERISTIC_UUID = "00002A26-0000-1000-8000-00805F9B34FB";
  private static final String MANUFACTURER_NAME_STRING_CHARACTERISTIC_UUID = "00002A29-0000-1000-8000-00805F9B34FB";

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

  public Microbit_Device_Information(Form form) {
    super(form);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT + ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    bleConnection = bluetoothLE;  }

  @SimpleProperty
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  @SimpleFunction
  public void ReadModelNumberString() {
    if (bleConnection != null) {
      bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, MODEL_NUMBER_STRING_CHARACTERISTIC_UUID, false, modelNumberHandler);
    } else {
      reportNullConnection("ReadModelNumberString");
    }
  }

  @SimpleFunction
  public void ReadSerialNumberString() {
    if (bleConnection != null) {
      bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, SERIAL_NUMBER_STRING_CHARACTERISTIC_UUID, false, serialNumberHandler);
    } else {
      reportNullConnection("ReadSerialNumberString");
    }
  }

  @SimpleFunction
  public void ReadHardwareRevisionString() {
    if (bleConnection != null) {
      bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, HARDWARE_REVISION_STRING_CHARACTERISTIC_UUID, false, hardwareRevisionHandler);
    } else {
      reportNullConnection("ReadHardwareRevisionString");
    }
  }

  @SimpleFunction
  public void ReadFirmwareRevisionString() {
    if (bleConnection != null) {
      bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, FIRMWARE_REVISION_STRING_CHARACTERISTIC_UUID, false, firmwareRevisionHandler);
    } else {
      reportNullConnection("ReadFirmwareRevisionString");
    }
  }

  @SimpleFunction
  public void ReadManufacturerNameString() {
    if (bleConnection != null) {
      bleConnection.ExReadStringValues(DEVICE_INFORMATION_UUID, MANUFACTURER_NAME_STRING_CHARACTERISTIC_UUID, false, manufacturerNameHandler);
    } else {
      reportNullConnection("ReadManufacturerNameString");
    }
  }

  @SimpleEvent
  public void ModelNumberStringReceived(final String Model_Number_String) {
    EventDispatcher.dispatchEvent(this, "ModelNumberStringReceived", Model_Number_String);
  }

  @SimpleEvent
  public void SerialNumberStringReceived(final String Serial_Number_String) {
    EventDispatcher.dispatchEvent(this, "SerialNumberStringReceived", Serial_Number_String);
  }

  @SimpleEvent
  public void HardwareRevisionStringReceived(final String Hardware_Revision_String) {
    EventDispatcher.dispatchEvent(this, "HardwareRevisionStringReceived", Hardware_Revision_String);
  }

  @SimpleEvent
  public void FirmwareRevisionStringReceived(final String Firmware_Revision_String) {
    EventDispatcher.dispatchEvent(this, "FirmwareRevisionStringReceived", Firmware_Revision_String);
  }

  @SimpleEvent
  public void ManufacturerNameStringReceived(final String Manufacturer_Name_String) {
    EventDispatcher.dispatchEvent(this, "ManufacturerNameStringReceived", Manufacturer_Name_String);
  }

  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Led.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
