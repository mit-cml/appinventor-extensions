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
public class Microbit_Dfu_Control extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String DFU_CONTROL_SERVICE_UUID = "E95D93B0-251D-470A-A062-FA1922DFA9A8";
  private static final String DFU_CONTROL_CHARACTERISTIC_UUID = "E95D93B1-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> dFUControlWriteHandler =
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

  public Microbit_Dfu_Control(Form form) {
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
  public void ReadDFUControl() {
    bleConnection.ExReadShortValues(DFU_CONTROL_SERVICE_UUID, DFU_CONTROL_CHARACTERISTIC_UUID, false, dFUControlWriteHandler);
  }

  @SimpleEvent
  public void DFUControlReceived(final int dfu_control) {
    EventDispatcher.dispatchEvent(this, "DFUControlReceived", dfu_control);
  }

  @SimpleFunction
  public void WriteDFUControl(final int DFU_Control) {
    bleConnection.ExWriteShortValuesWithResponse(DFU_CONTROL_SERVICE_UUID, DFU_CONTROL_CHARACTERISTIC_UUID, false, DFU_Control, dFUControlWriteHandler);
  }

  @SimpleEvent
  public void WroteDFUControl(final int dfu_control) {
    EventDispatcher.dispatchEvent(this, "WroteDFUControl", dfu_control);
  }

}
