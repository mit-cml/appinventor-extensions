// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for MT7697 extensions that interface with the App Inventor Companion Sketch
 * for MT7697.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */

@SimpleObject
public abstract class MT7697ExtensionBase extends AndroidNonvisibleComponent {
  protected BluetoothLE bleConnection = null;

  MT7697ExtensionBase(Form form) {
    super(form);
  }

  /**
   * The BluetoothLE component with a connection to the MT7697 (setter).
   * @param bluetoothLE the BluetoothLE device
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT +
                    ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    bleConnection = bluetoothLE;
  }

  /**
   * The BluetoothLE component with a connection to the MT7697 (getter).
   * @return The BluetoothLE connection used to talk to the micro:bit device.
   */
  @SimpleProperty(description = "The <a href='http://iot.appinventor.mit.edu/#/bluetoothle/bluetoothleintro'>BluetoothLE</a>" +
                  " component with a connection to the MT7697.")
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  protected boolean isConnected() {
    return bleConnection != null && bleConnection.IsDeviceConnected();
  }

  public static List<Integer> toList(byte... values) {
    List<Integer> result = new ArrayList<Integer>(values.length);
    for (byte b : values) {
      result.add((int) b);
    }
    return result;
  }

  /**
   * Check whether the feature is currently available for the device connected via the
   * <a href="#BluetoothDevice"><code>BluetoothDevice</code></a> property. If no device is currently
   * connected, this method will always return false.
   * @return true if the connected device is advertising the service represented by the extension,
   * otherwise false.
   */
  @SimpleFunction
  public abstract boolean IsSupported();
}
