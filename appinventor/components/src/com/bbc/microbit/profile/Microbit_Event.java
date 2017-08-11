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
public class Microbit_Event extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String EVENT_SERVICE_UUID = "E95D93AF-251D-470A-A062-FA1922DFA9A8";
  private static final String MICROBIT_REQUIREMENTS_CHARACTERISTIC_UUID = "E95DB84C-251D-470A-A062-FA1922DFA9A8";
  private static final String MICROBIT_EVENT_CHARACTERISTIC_UUID = "E95D9775-251D-470A-A062-FA1922DFA9A8";
  private static final String CLIENT_REQUIREMENTS_CHARACTERISTIC_UUID = "E95D23C4-251D-470A-A062-FA1922DFA9A8";
  private static final String CLIENT_EVENT_CHARACTERISTIC_UUID = "E95D5404-251D-470A-A062-FA1922DFA9A8";

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
        WroteClientRequirements(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> clientEventWriteHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteClientEvent(values);
      }
    };

  public Microbit_Event(Form form) {
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
  public void WriteClientRequirements(List<Integer> Client_Requirements_Value) {
    bleConnection.ExWriteByteValuesWithResponse(EVENT_SERVICE_UUID, CLIENT_REQUIREMENTS_CHARACTERISTIC_UUID, false, Client_Requirements_Value, clientRequirementsWriteHandler);
  }

  @SimpleEvent
  public void WroteClientRequirements(final List<Integer> Client_Requirements_Value) {
    EventDispatcher.dispatchEvent(this, "WroteClientRequirements", Client_Requirements_Value);
  }

  @SimpleFunction
  public void WriteClientEvent(List<Integer> Event_Types_And_Values) {
    bleConnection.ExWriteByteValuesWithResponse(EVENT_SERVICE_UUID, CLIENT_EVENT_CHARACTERISTIC_UUID, false, Event_Types_And_Values, clientEventWriteHandler);
  }

  @SimpleEvent
  public void WroteClientEvent(final List<Integer> Event_Types_And_Values) {
    EventDispatcher.dispatchEvent(this, "WroteClientEvent", Event_Types_And_Values);
  }

}
