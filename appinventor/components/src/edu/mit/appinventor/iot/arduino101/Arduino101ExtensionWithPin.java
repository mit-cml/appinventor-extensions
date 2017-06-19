// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.arduino101;

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
 * Base class for Arduino 101 extensions that require a pin configuration.
 *
 * @author ewpatton@mit.edu (Evan W. Patton)
 */
@SimpleObject
public abstract class Arduino101ExtensionWithPin<T extends Arduino101ExtensionWithPin> extends Arduino101ExtensionBase {
  public static final int DIGITAL = 8;
  public static final int ANALOG = 2;
  private final int pinBytes;
  protected int pin = 0;

  // This is an ugly way of managing multiple instantiations of the extension. We may need to
  // significantly redesign the BLE services to make this work well.
  private static final Map<Class<?>, Map<BluetoothLE, ActivePinSet<Arduino101ExtensionWithPin>>> pinSets =
      new WeakHashMap<Class<?>, Map<BluetoothLE, ActivePinSet<Arduino101ExtensionWithPin>>>();

  public Arduino101ExtensionWithPin(Form form, int pinBytes) {
    super(form);
    this.pinBytes = pinBytes;
    Map<BluetoothLE, ActivePinSet<Arduino101ExtensionWithPin>> connectionMap = pinSets.get(getClass());
    if (connectionMap == null) {
      pinSets.put(getClass(), new WeakHashMap<BluetoothLE, ActivePinSet<Arduino101ExtensionWithPin>>());
    }
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT +
      ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    Log.d("Arduino101ExtensionWithPin", "BluetoothDevice");
    if (bleConnection != null) {
      ActivePinSet pinSet = pinSets.get(getClass()).get(bluetoothLE);
      if (pinSet != null) {
        pinSet.remove(this);
      }
    }
    bleConnection = bluetoothLE;
    if (bleConnection != null) {
      ActivePinSet<Arduino101ExtensionWithPin> pinSet = pinSets.get(getClass()).get(bluetoothLE);
      if (pinSet == null) {
        pinSet = new ActivePinSet<Arduino101ExtensionWithPin>(bluetoothLE, pinBytes, this);
        pinSets.get(getClass()).put(bluetoothLE, pinSet);
      } else {
        pinSet.add(this);
      }
    }
  }

  @SimpleProperty
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
      defaultValue = "0")
  @SimpleProperty
  public void Pin(int pin) {
    if (this.pin != pin) {
      int oldPin = this.pin;
      this.pin = pin;
      if (bleConnection != null) {
        ActivePinSet<Arduino101ExtensionWithPin> pinSet = pinSets.get(this.getClass()).get(bleConnection);
        if (pinSet == null) {
          pinSet = new ActivePinSet<Arduino101ExtensionWithPin>(bleConnection, pinBytes, this);
          pinSets.get(getClass()).put(bleConnection, pinSet);
        } else {
          pinSet.update(this, oldPin);
        }
      }
    }
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
    return bleConnection != null && bleConnection.isCharacteristicPublished(getPinServiceUuid(),
        getPinCharacteristicUuid());
  }

  public abstract String getPinServiceUuid();
  public abstract String getPinCharacteristicUuid();

}
