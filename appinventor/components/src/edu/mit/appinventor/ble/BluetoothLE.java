// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2011-2016 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import android.app.Activity;

import android.content.pm.PackageManager;

import android.util.Log;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;

import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.YaVersion;

import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.util.SdkLevel;

import java.util.List;


/**
 * Author: Andrew McKinney <mckinney@mit.edu>
 * Author: Cristhian Ulloa <cristhian2ulloa@gmail.com>
 * Author: tiffanyle <le.tiffanya@gmail.com>
 */

@DesignerComponent(version = 1,
    description = "Bluetooth Low Energy, also referred to as Bluetooth LE " +
        "or simply BLE, is a new protocol similar to classic Bluetooth except " +
        "that it is designed to consume less power while maintaining comparable " +
        "functionality. For this reason, Bluetooth LE is the preferred choice of " +
        "communication with IoT devices that have limited power resources." +
        "Starting with Android 4.3, Google introduced built-in support for Bluetooth Low Energy.",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/bluetooth.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.BLUETOOTH, " + "android.permission.BLUETOOTH_ADMIN," + "android.permission.ACCESS_COARSE_LOCATION")

public class BluetoothLE extends AndroidNonvisibleComponent implements Component {

  /**
   * Basic Variable
   */
  private static final String LOG_TAG = "BluetoothLEComponent";
  private final Activity activity;
  private BluetoothLEint inner;

  public BluetoothLE(ComponentContainer container) {
    super(container.$form());

    activity = (Activity) container.$context();
    inner = null;

    // See if we are usable on this device
    if (!container.$form().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
      Notifier.oneButtonAlert(container.$context(), "Bluetooth Not Supported, sorry.", "Unsupported", "Oh Well");
      return;
    }

    Log.d(LOG_TAG, "Appear to have Bluetooth LE support, continuing...");

    if (SdkLevel.getLevel() < SdkLevel.LEVEL_LOLLIPOP) {
      Notifier.oneButtonAlert(container.$context(), "Bluetooth Not Supported, sorry.", "Unsupported", "Oh Well");
      return;
    }

    inner = new BluetoothLEint(this, activity, container);
  }

  @SimpleFunction(description = "Start Scanning for BluetoothLE devices.")
  public void StartScanning() {
    if (inner != null) {
      inner.StartScanning();
    }
  }

  @SimpleFunction(description = "Stop Scanning for BluetoothLE devices.")
  public void StopScanning() {
    if (inner != null) {
      inner.StopScanning();
    }
  }

  @SimpleFunction(description = "Connect to a BluetoothLE device with index. Index specifies the position in BluetoothLE device list, starting from 0.")
  public void Connect(int index) {
    if (inner != null) {
      inner.Connect(index);
    }
  }

  @SimpleFunction(description = "Connect to BluetoothLE device with address. Address specifies bluetooth address of the BluetoothLE device.")
  public void ConnectWithAddress(String address) {
    if (inner != null) {
      inner.ConnectWithAddress(address);
    }
  }

  @SimpleFunction(description = "Disconnect from connected BluetoothLE device with address. Address specifies bluetooth address of the BluetoothLE device.")
  public void DisconnectWithAddress(String address) {
    if (inner != null) {
      inner.DisconnectWithAddress(address);
    }
  }

  @SimpleFunction(description = "Write String value to a connected BluetoothLE device. Service Unique ID, Characteristic Unique ID and String value"
      + "are required.")
  public void WriteStringValue(String service_uuid, String characteristic_uuid, String value) {
    if (inner != null) {
      inner.WriteStringValue(service_uuid, characteristic_uuid, value);
    }
  }

  @SimpleFunction(description = "Write Integer value to a connected BluetoothLE device. Service Unique ID, Characteristic Unique ID, Integer value"
      + " and offset are required. Offset specifies the start position of writing data.")
  public void WriteIntValue(String service_uuid, String characteristic_uuid, int value, int offset) {
    if (inner != null) {
      inner.WriteIntValue(service_uuid, characteristic_uuid, value, offset);
    }
  }

  @SimpleFunction(description="Write Float value to a connected BluetoothLE device. Service Unique ID, Characteristic Unique ID, Integer value"
      + " and offset are required. Offset specifies the start position of writing data. Value converted to IEEE 754 floating-point 32-bit layout before writing.")
  public void WriteFloatValue(String service_uuid, String characteristic_uuid, float value, int offset) {
    if (inner != null) {
      inner.WriteFloatValue(service_uuid, characteristic_uuid, value, offset);
    }
  }

  @SimpleFunction(description = "Write byte value to a connected BluetoothLE device. Service Unique ID, Characteristic Unique ID, Integer value"
      + " and offset are required. Offset specifies the start position of writing data.")
  public void WriteByteValue(String service_uuid, String characteristic_uuid, String value) {
    if (inner != null) {
      inner.WriteByteValue(service_uuid, characteristic_uuid, value);
    }
  }

  @SimpleFunction(description = "Read Integer value from a connected BluetoothLE device. Service Unique ID, Characteristic Unique ID and offset"
      + " are required. Offset specifies the start position of reading data.")
  public void ReadIntValue(String service_uuid, String characteristic_uuid, int intOffset) {
    if (inner != null) {
      inner.ReadIntValue(service_uuid, characteristic_uuid, intOffset);
    }
  }

  @SimpleFunction(description = "Read String value from a connected BluetoothLE device. Service Unique ID, Characteristic Unique ID and offset"
      + " are required. Offset specifies the start position of reading data.")
  public void ReadStringValue(String service_uuid, String characteristic_uuid, int strOffset) {
    if (inner != null) {
      inner.ReadStringValue(service_uuid, characteristic_uuid, strOffset);
    }
  }

  @SimpleFunction(description = "Read Float value from a connected BluetoothLE device. Service Unique ID, Characteristic Unique ID and offset"
      + " are required. Offset specifies the start position of reading data.")
  public void ReadFloatValue(String service_uuid, String characteristic_uuid, int floatOffset) {
    if (inner != null) {
      inner.ReadFloatValue(service_uuid, characteristic_uuid, floatOffset);
    }
  }

  @SimpleFunction(description = "Read Byte value from a connected BluetoothLE device. Service Unique ID and Characteristic Unique ID are required.")
  public void ReadByteValue(String service_uuid, String characteristic_uuid) {
    if (inner != null) {
      inner.ReadByteValue(service_uuid, characteristic_uuid);
    }
  }

  @SimpleFunction(description = "Get the RSSI (Received Signal Strength Indicator) of found device with index. Index specifies the position in BluetoothLE device list, starting from 0.")
  public int FoundDeviceRssi(int index) {
    if (inner != null) {
      return inner.FoundDeviceRssi(index);
    }
    return 0;
  }

  @SimpleFunction(description = "Get the name of found device with index. Index specifies the position in BluetoothLE device list, starting from 0.")
  public String FoundDeviceName(int index) {
    if (inner != null) {
      return inner.FoundDeviceName(index);
    }
    return null;
  }

  @SimpleFunction(description = "Get the address of found device with index. Index specifies the position in BluetoothLE device list, starting from 0.")
  public String FoundDeviceAddress(int index) {
    if (inner != null) {
      return inner.FoundDeviceAddress(index);
    }
    return null;
  }

  @SimpleFunction(description = "Create and publish a Bluetooth LE advertisement. inData specifies the data that will be included in the advertisement. serviceUuid specifies the UUID of the advertisement.")
  public void StartAdvertising(String inData, String serviceUuid) {
    if (inner != null) {
      inner.StartAdvertising(inData, serviceUuid);
    }
  }

  @SimpleFunction(description = "Stop Bluetooth LE Advertising.")
  public void StopAdvertising() {
    if (inner != null) {
      inner.StopAdvertising();
    }
  }

  @SimpleFunction(description = "Scans for Bluetooth LE advertisements. scanPeriod specifies how long the scan will run.")
  public void ScanAdvertisements(long scanPeriod) {
    if (inner != null) {
      inner.ScanAdvertisements(scanPeriod);
    }
  }

  @SimpleFunction(description = "Stops scanning for Bluetooth LE advertisements.")
  public void StopScanningAdvertisements() {
    if (inner != null) {
      inner.StopScanningAdvertisements();
    }
  }

  @SimpleFunction(description = "Returns the advertisement data associated with the specified device address.")
  public String AdvertisementData(String deviceAddress, String serviceUuid) {
    if (inner != null) {
      return inner.GetAdvertisementData(deviceAddress, serviceUuid);
    }
    return null;
  }

  @SimpleFunction(description = "Returns the address of the device with the name specified.")
  public String AdvertiserAddress(String deviceName) {
    if (inner != null) {
      return inner.GetAdvertiserAddress(deviceName);
    }
    return null;
  }

  @SimpleFunction(description = "Returns the list of services available on the Adverising device.")
  public List<String> AdvertiserServiceUuids(String deviceAddress) {
    if (inner != null) {
      return inner.GetAdvertiserServiceUuids(deviceAddress);
    }
    return null;
  }

  @SimpleProperty(description = "Return the battery level.", category = PropertyCategory.BEHAVIOR)
  public String BatteryValue() {
    if (inner != null) {
      return inner.BatteryValue();
    }
    return null;
  }

  @SimpleProperty(description = "Return the Tx power.", category = PropertyCategory.BEHAVIOR)
  public int TxPower() {
    if (inner != null) {
      return inner.TxPower();
    }
    return 0;
  }

  @SimpleProperty(description = "Return true if a BluetoothLE device is connected; Otherwise, return false.", category = PropertyCategory.BEHAVIOR)
  public boolean IsDeviceConnected() {
    if (inner != null) {
      return inner.IsDeviceConnected();
    }
    return false;
  }

  @SimpleProperty(description = "Return a sorted list of BluetoothLE devices as a String.", category = PropertyCategory.BEHAVIOR)
  public String DeviceList() {
    if (inner != null) {
      return inner.DeviceList();
    }
    return null;
  }

  @SimpleProperty(description = "Return the RSSI (Received Signal Strength Indicator) of connected device.", category = PropertyCategory.BEHAVIOR)
  public String ConnectedDeviceRssi() {
    if (inner != null) {
      return inner.ConnectedDeviceRssi();
    }
    return null;
  }

  @SimpleProperty(description = "Returns the value of ScanPeriod.")
  public long AdvertisementScanPeriod() {
    if (inner != null) {
      return inner.AdvertisementScanPeriod();
    }
    return 0;
  }

  @SimpleProperty(description = "Returns a list of the names of the devices found during Advertisment scanning.")
  public List<String> AdvertiserNames() {
    if (inner != null) {
      return inner.GetAdvertiserNames();
    }
    return null;
  }

  @SimpleProperty(description = "Returns a list of the addresses of devices found during Advertisement scanning.")
  public List<String> AdvertiserAddresses() {
    if (inner != null) {
      return inner.GetAdvertiserAddresses();
    }
    return null;
  }

  @SimpleProperty(description = "Returns true if the device is currently advertising, false otherwise.")
  public boolean IsDeviceAdvertising() {
    if (inner != null) {
      return inner.IsDeviceAdvertising();
    }
    return false;
  }

  @SimpleEvent(description = "Trigger event when a BluetoothLE device is connected.")
  public void Connected() {
  }

  @SimpleEvent(description = "Trigger event when RSSI (Received Signal Strength Indicator) of found BluetoothLE device changes")
  public void RssiChanged(final int device_rssi) {
  }

  @SimpleEvent(description = "Trigger event when a new BluetoothLE device is found.")
  public void DeviceFound() {
  }

  @SimpleEvent(description = "Trigger event when byte value from connected BluetoothLE device is read.")
  public void ByteValueRead(final String byteValue) {
  }

  @SimpleEvent(description = "Trigger event when an int value from connected BluetoothLE device is read.")
  public void IntValueRead(final int intValue) {
  }

  @SimpleEvent(description = "Trigger event when a string value from connected BluetoothLE device is read.")
  public void StringValueRead(final String stringValue) {
  }

  @SimpleEvent(description = "Trigger event when a float value from connected BluetoothLE device is read.")
  public void FloatValueRead(final float floatValue) {
  }

  @SimpleEvent(description = "Trigger event when byte value from connected BluetoothLE device is changed.")
  public void ByteValueChanged(final String byteValue) {
  }

  @SimpleEvent(description = "Trigger event when int value from connected BluetoothLE device is changed.")
  public void IntValueChanged(final int intValue) {
  }

  @SimpleEvent(description = "Trigger event when int value from connected BluetoothLE device is changed.")
  public void FloatValueChanged(final float floatValue) {
  }

  @SimpleEvent(description = "Trigger event when String value from connected BluetoothLE device is changed.")
  public void StringValueChanged(final String stringValue) {
  }

  @SimpleEvent(description = "Trigger event when value is successfully written to connected BluetoothLE device.")
  public void ValueWrite() {
  }

  @SimpleFunction(description = "Return list of supported services for connected device as a String")
  public String SupportedServices() {
    if (inner != null) {
      return inner.GetSupportedServices();
    }
    return null;
  }

  @SimpleFunction(description = "Return Unique ID of selected service with index. Index specified by list of supported services for a connected device, starting from 0.")
  public String ServiceByIndex(int index) {
    if (inner != null) {
      return inner.GetServicebyIndex(index);
    }
    return null;
  }

  @SimpleFunction(description = "Return list of supported characteristics for connected device as a String")
  public String SupportedCharacteristics() {
    if (inner != null) {
      return inner.GetSupportedCharacteristics();
    }
    return null;
  }

  @SimpleFunction(description = "Return Unique ID of selected characteristic with index. Index specified by list of supported characteristics for a connected device, starting from 0.")
  public String CharacteristicByIndex(int index) {
    if (inner != null) {
      return inner.GetCharacteristicbyIndex(index);
    }
    return null;
  }
}


