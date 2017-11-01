// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Base class for MT7697 extensions that require a pin configuration.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@SimpleObject
public abstract class MT7697ExtensionWithPin<T extends MT7697ExtensionWithPin> extends MT7697ExtensionBase {
  public static final int DIGITAL = 8;
  public static final int ANALOG = 2;
  private final int pinBytes;
  protected int pin = 0;

  // This is an ugly way of managing multiple instantiations of the extension. We may need to
  // significantly redesign the BLE services to make this work well.

  public MT7697ExtensionWithPin(Form form, int pinBytes) {
    super(form);
    this.pinBytes = pinBytes;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT + ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    Log.d("MT7697ExtensionWithPin", "BluetoothDevice");
    bleConnection = bluetoothLE;
  }

  @SimpleProperty
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
                    defaultValue = "0")
  @SimpleProperty
  public void Pin(int pin) {
    this.pin = pin;
  }

  @SimpleProperty(description = "The Pin on the Arduino board that the device is wired in to.")
  public int Pin() {
    return pin;
  }

  /**
   * Tests whether the Bluetooth low energy device is broadcasting support for the service. If true,
   * calls to TurnOn and TurnOff should work correctly. Otherwise an error will be reported through
   * the Screen's ErrorOccurred event.
   */
  @Override
  @SimpleFunction
  public boolean IsSupported() {
    return bleConnection != null &&
      bleConnection.isCharacteristicPublished(getPinServiceUuid(), getPinCharacteristicUuid());
  }

  public abstract String getPinServiceUuid();
  public abstract String getPinCharacteristicUuid();

}
