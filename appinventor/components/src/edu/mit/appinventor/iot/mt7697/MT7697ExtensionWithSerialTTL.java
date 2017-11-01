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
 * Extension base class for peripherals that communicate with the MT7697 using a TTL serial
 * connection.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@SimpleObject
public abstract class MT7697ExtensionWithSerialTTL extends MT7697ExtensionBase {
  private int pin1;
  private int pin2;


  public MT7697ExtensionWithSerialTTL(Form form) {
    super(form);
  }

  public void setPin1(int pin) {
    this.pin1 = pin;
  }

  public int getPin1() {
    return pin1;
  }

  public void setPin2(int pin) {
    this.pin2 = pin;
  }

  public int getPin2() {
    return pin2;
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
