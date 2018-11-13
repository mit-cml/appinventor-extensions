package com.example.ble;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import edu.mit.appinventor.ble.BluetoothLE;
import java.util.List;

@DesignerComponent(version = 1,
  description = "My first BLE extension",
  category = ComponentCategory.EXTENSION,
  nonVisible = true,
  iconName = "images/bluetooth.png")
@SimpleObject(external = true)
public class SimpleBLEExtension extends AndroidNonvisibleComponent implements BluetoothLE.BluetoothConnectionListener {

  // Define the service and characteristic UUIDs for Foo
  private static final String FOO_SERVICE = "12345678-90AB-CDEF-1234-567890ABCDEF";
  private static final String FOO_CHARACTERISTIC = "12345678-90AC-CDEF-1234-567890ABCDEF";

  private BluetoothLE bleDevice;

  // Create a response handler that will run when Foo is received.
  private final BluetoothLE.BLEResponseHandler<Integer> fooHandler = new BluetoothLE.BLEResponseHandler<Integer>() {
    @Override
    public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
      FooReceived(values.get(0));
    }

    @Override
    public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
      FooWritten(values.get(0));
    }
  };

  public SimpleBLEExtension(ComponentContainer container) {
    super(container.$form());
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT + ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty(category = PropertyCategory.BEHAVIOR)
  public void BluetoothDevice(BluetoothLE device) {
    if (bleDevice == device) return;
    if (bleDevice != null) bleDevice.removeConnectionListener(this);
    bleDevice = device;
    if (bleDevice != null) bleDevice.addConnectionListener(this);
  }

  @SimpleProperty
  public BluetoothLE BluetoothDevice() {
    return bleDevice;
  }

  @SimpleFunction(description = "Reads Foo from the device. The read Foo will be reported in the FooReceived event.")
  public void ReadFoo() {
    if (bleDevice != null) {
      bleDevice.ExReadByteValues(FOO_SERVICE, FOO_CHARACTERISTIC, /* signed */ false, fooHandler);
    }
  }

  @SimpleFunction(description = "Requests updates to Foo from the device. The updates to Foo will be reported in the FooReceived event.")
  public void RequestFooUpdates() {
    if (bleDevice != null) {
      bleDevice.ExRegisterForByteValues(FOO_SERVICE, FOO_CHARACTERISTIC, /* signed */ false, fooHandler);
    }
  }

  @SimpleFunction(description = "Stop receiving updates for Foo from the device.")
  public void StopFooUpdates() {
    if (bleDevice != null) {
      bleDevice.ExUnregisterForValues(FOO_SERVICE, FOO_CHARACTERISTIC, fooHandler);
    }
  }

  @SimpleFunction(description = "Write foo to the connected Bluetooth low energy peripheral.")
  public void WriteFoo(int foo) {
    if (foo < -128 || foo > 127) {  // restrict foo to byte range
      form.dispatchErrorOccurredEvent(this, "WriteFoo", ErrorMessages.ERROR_EXTENSION_ERROR, 1, "SampleBLEExtension", "Value for foo was out of range. Valid values are [-128, 127].");
      return;
    }
    bleDevice.ExWriteByteValues(FOO_SERVICE, FOO_CHARACTERISTIC, /* signed */ false, foo);
  }

  @SimpleFunction
  public void WriteFooWithResponse(int foo) {
    if (foo < -128 || foo > 127) {  // restrict foo to byte range
      form.dispatchErrorOccurredEvent(this, "WriteFooWithResponse", ErrorMessages.ERROR_EXTENSION_ERROR, 1, "SampleBLEExtension", "Value for foo was out of range. Valid values are [-128, 127].");
      return;
    }
    bleDevice.ExWriteByteValuesWithResponse(FOO_SERVICE, FOO_CHARACTERISTIC, /* signed */ false, foo, fooHandler);
  }

  @SimpleEvent
  public void Connected() {
    EventDispatcher.dispatchEvent(this, "Connected");
  }

  @SimpleEvent
  public void Disconnected() {
    EventDispatcher.dispatchEvent(this, "Disconnected");
  }

  @SimpleEvent
  public void FooReceived(int foo) {
    EventDispatcher.dispatchEvent(this, "FooReceived", foo);
  }

  @SimpleEvent
  public void FooWritten(int foo) {
    EventDispatcher.dispatchEvent(this, "FooWritten", foo);
  }

  // BluetoothConnectionListener implementation
  public void onConnected(BluetoothLE bleConnection) {
    Connected();
    // At this point you may want to write configuration to the device
    // or read/register for various BLE services.
  }

  public void onDisconnected(BluetoothLE bleConnection) {
    Disconnected();
  }
}
