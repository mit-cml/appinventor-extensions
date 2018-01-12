package com.bbc.microbit.profile;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE;

@DesignerComponent(version = 1,
    description = "",
    category = ComponentCategory.INTERNAL,
    nonVisible = true,
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Generic_Attribute extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String GENERIC_ATTRIBUTE_UUID = "00001801-0000-1000-8000-00805F9B34FB";
  private static final String SERVICE_CHANGED_CHARACTERISTIC_UUID = "00002A05-0000-1000-8000-00805F9B34FB";

  public Microbit_Generic_Attribute(Form form) {
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

}

