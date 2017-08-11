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
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.List;

@DesignerComponent(version = 1,
    description = "",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Generic_Access extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String GENERIC_ACCESS_UUID = "00001800-0000-1000-8000-00805F9B34FB";
  private static final String DEVICE_NAME_CHARACTERISTIC_UUID = "00002A00-0000-1000-8000-00805F9B34FB";
  private static final String APPEARANCE_CHARACTERISTIC_UUID = "00002A01-0000-1000-8000-00805F9B34FB";
  private static final String PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS_CHARACTERISTIC_UUID = "00002A04-0000-1000-8000-00805F9B34FB";

  private final BluetoothLE.BLEResponseHandler<String> deviceNameHandler =
    new BluetoothLE.BLEResponseHandler<String>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicuuid, List<String> values) {
        DeviceNameReceived(values.get(0));
      }
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<String> values) {
        WroteDeviceName(values.get(0));
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

  public Microbit_Generic_Access(Form form) {
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
  public void ReadDeviceName() {
    bleConnection.ExReadStringValues(GENERIC_ACCESS_UUID, DEVICE_NAME_CHARACTERISTIC_UUID, false, deviceNameHandler);
  }

  @SimpleEvent
  public void DeviceNameReceived(final String Name) {
    EventDispatcher.dispatchEvent(this, "DeviceNameReceived", Name);
  }

  @SimpleFunction
  public void WriteDeviceName(final String Name) {
    bleConnection.ExWriteStringValuesWithResponse(GENERIC_ACCESS_UUID, DEVICE_NAME_CHARACTERISTIC_UUID, false, Name, deviceNameHandler);
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
  public void PeripheralPreferredConnectionParametersReceived(final int Minimum_Connection_interval,
                                                              final int Maximum_Connection_Interval,
                                                              final int Slave_Latency,
                                                              final int Connection_Supervision_Timeout_Multiplier) {
    EventDispatcher.dispatchEvent(this, "PeripheralPreferredConnectionParameters", Minimum_Connection_interval, Maximum_Connection_Interval, Slave_Latency, Connection_Supervision_Timeout_Multiplier);
  }

}
