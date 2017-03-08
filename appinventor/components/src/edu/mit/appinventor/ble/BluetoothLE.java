// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2015-2016 MIT, All rights reserved
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

import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;

import java.util.List;


/**
 * @author Andrew McKinney (mckinney@mit.edu)
 * @author Cristhian Ulloa (cristhian2ulloa@gmail.com)
 * @author tiffanyle (le.tiffanya@gmail.com)
 * @author William Byrne (will2596@gmail.com) (minor bugfixes)
 */

@DesignerComponent(version = 2,
    description = "Bluetooth Low Energy, also referred to as Bluetooth LE " +
        "or simply BLE, is a new protocol similar to classic Bluetooth except " +
        "that it is designed to consume less power while maintaining comparable " +
        "functionality. For this reason, Bluetooth LE is the preferred choice of " +
        "communication with IoT devices that have limited power resources. " +
        "Starting with Android 4.3, Google introduced built-in support for Bluetooth Low Energy. " +
        "The BluetoothLE extension requires Android 5.0 or higher to avoid known " +
        "issues with Google's Bluetooth LE support prior to Android 5.0.",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/bluetooth.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.BLUETOOTH, " + "android.permission.BLUETOOTH_ADMIN,"
    + "android.permission.ACCESS_COARSE_LOCATION")
public class BluetoothLE extends AndroidNonvisibleComponent implements Component {

  /**
   * Basic Variables
   */
  private static final String LOG_TAG = "BluetoothLE";
  private final Activity activity;
  private BluetoothLEint inner;

  public BluetoothLE(ComponentContainer container) {
    super(container.$form());

    activity = container.$context();

    // Perform preliminary checks to see if we are usable on this device.
    // If this test does not pass, we log an advanced warning then proceed.
    // Individualized errors are signaled in each @SimpleFunction.
    if(!container.$form().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
      Log.e(LOG_TAG, "Bluetooth LE is unsupported on this hardware. " +
          "Any subsequent function calls will complain.");
    } else if(SdkLevel.getLevel() < SdkLevel.LEVEL_LOLLIPOP) {
      Log.e(LOG_TAG, "The BluetoothLE extension is unsupported at this API Level. " +
          "Any subsequent function calls will complain.");
    } else {
      Log.d(LOG_TAG, "Appear to have Bluetooth LE support, continuing...");
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

  @SimpleFunction(description = "Connect to a BluetoothLE device with index. Index specifies the position in" +
      " BluetoothLE device list, starting from 1.")
  public void Connect(int index) {
    if (inner != null) {
      inner.Connect(index);
    }
  }

  @SimpleFunction(description = "Connect to BluetoothLE device with address. Address specifies bluetooth address" +
      " of the BluetoothLE device.")
  public void ConnectWithAddress(String address) {
    if (inner != null) {
      inner.ConnectWithAddress(address);
    }
  }

  @SimpleFunction(description = "Disconnect from the currently connected BluetoothLE device if a device is connected.")
  public void Disconnect() {
    if(inner != null) {
      inner.Disconnect();
    }
  }

  @SimpleFunction(description = "Disconnect from connected BluetoothLE device with address. Address specifies" +
      " bluetooth address of the BluetoothLE device.")
  public void DisconnectWithAddress(String address) {
    if (inner != null) {
      inner.DisconnectWithAddress(address);
    }
  }

  @SimpleFunction(description = "Write String value to a connected BluetoothLE device. Service Unique ID," +
      " Characteristic Unique ID and String value are required.")
  public void WriteStringValue(String service_uuid, String characteristic_uuid, String value) {
    if (inner != null) {
      inner.WriteStringValue(service_uuid, characteristic_uuid, value);
    }
  }

  @SimpleFunction(description = "Write Integer value to a connected BluetoothLE device. Service Unique ID," +
      " Characteristic Unique ID, Integer value and offset are required. Offset specifies the start position" +
      " of writing data.")
  public void WriteIntValue(String service_uuid, String characteristic_uuid, int value, int offset) {
    if (inner != null) {
      inner.WriteIntValue(service_uuid, characteristic_uuid, value, offset);
    }
  }

  @SimpleFunction(description="Write Float value to a connected BluetoothLE device. Service Unique ID," +
      " Characteristic Unique ID, Integer value and offset are required. Offset specifies the start position" +
      " of writing data. Value converted to IEEE 754 floating-point 32-bit layout before writing.")
  public void WriteFloatValue(String service_uuid, String characteristic_uuid, float value, int offset) {
    if (inner != null) {
      inner.WriteFloatValue(service_uuid, characteristic_uuid, value, offset);
    }
  }

  @SimpleFunction(description = "Write byte value to a connected BluetoothLE device. Service Unique ID," +
      " Characteristic Unique ID, Integer value and offset are required. Offset specifies the start" +
      " position of writing data.")
  public void WriteByteValue(String service_uuid, String characteristic_uuid, String value) {
    if (inner != null) {
      inner.WriteByteValue(service_uuid, characteristic_uuid, value);
    }
  }

  @SimpleFunction(description = "Read Integer value from a connected BluetoothLE device. Service Unique ID," +
      " Characteristic Unique ID and offset are required. Offset specifies the start position of reading data.")
  public void ReadIntValue(String service_uuid, String characteristic_uuid, int intOffset) {
    if (inner != null) {
      inner.ReadIntValue(service_uuid, characteristic_uuid, intOffset);
    }
  }

  @SimpleFunction(description = "Read String value from a connected BluetoothLE device. Service Unique ID," +
      " Characteristic Unique ID and offset are required. Offset specifies the start position of reading data.")
  public void ReadStringValue(String service_uuid, String characteristic_uuid, int strOffset) {
    if (inner != null) {
      inner.ReadStringValue(service_uuid, characteristic_uuid, strOffset);
    }
  }

  @SimpleFunction(description = "Read Float value from a connected BluetoothLE device. Service Unique ID," +
      " Characteristic Unique ID and offset are required. Offset specifies the start position of reading data.")
  public void ReadFloatValue(String service_uuid, String characteristic_uuid, int floatOffset) {
    if (inner != null) {
      inner.ReadFloatValue(service_uuid, characteristic_uuid, floatOffset);
    }
  }

  @SimpleFunction(description = "Read Byte value from a connected BluetoothLE device. Service Unique ID" +
      " and Characteristic Unique ID are required.")
  public void ReadByteValue(String service_uuid, String characteristic_uuid) {
    if (inner != null) {
      inner.ReadByteValue(service_uuid, characteristic_uuid);
    }
  }

  @SimpleFunction(description = "Read one or more 8-bit integer values from a connected " +
      "BluetoothLE device. Service Unique ID and Characteristic Unique ID are required.")
  public void ReadByteValues(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.ReadByteValues(serviceUuid, characteristicUuid, signed);
    }
  }

  @SimpleFunction(description = "Register to receive updates when one or more 16-bit integer " +
      "values from a connected BluetoothLE change. Service Unique ID and Characteristic Unique " +
      "ID are required.")
  public void RegisterForByteValues(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.RegisterForByteValues(serviceUuid, characteristicUuid, signed);
    }
  }

  @SimpleFunction(description = "Read one or more 16-bit integer values from a connected " +
      "BluetoothLE device. Service Unique ID and Characteristic Unique ID are required.")
  public void ReadShortValues(String service_uuid, String characteristic_uuid, boolean signed) {
    if (inner != null) {
      inner.ReadShortValues(service_uuid, characteristic_uuid, signed);
    }
  }

  @SimpleFunction(description = "Register to receive updates when one or more 16-bit integer " +
      "values from a connected BluetoothLE change. Service Unique ID and Characteristic Unique ID" +
      "are required.")
  public void RegisterForShortValues(String service_uuid, String characteristic_uuid,
                                     boolean signed) {
    if (inner != null) {
      inner.RegisterForShortValues(service_uuid, characteristic_uuid, signed);
    }
  }

  @SimpleFunction(description = "Read one or more 32-bit integer values from a connected " +
      "BluetoothLE device.")
  public void ReadIntegerValues(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.ReadIntegerValues(serviceUuid, characteristicUuid, signed);
    }
  }

  @SimpleFunction(description = "Register to receive updates when one or more 32-bit integer " +
      "values from a connected Bluetooth device change.")
  public void RegisterForIntegerValues(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.RegisterForIntegerValues(serviceUuid, characteristicUuid, signed);
    }
  }

  @SimpleFunction(description = "Read one or more floating point values from a connected " +
      "Bluetooth device.")
  public void ReadFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloat) {
    if (inner != null) {
      inner.ReadFloatValues(serviceUuid, characteristicUuid, shortFloat);
    }
  }

  @SimpleFunction(description = "Register to receive updates when one or more floating point " +
      "values from a connected Bluetooth device change.")
  public void RegisterForFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloat) {
    if (inner != null) {
      inner.RegisterForFloatValues(serviceUuid, characteristicUuid, shortFloat);
    }
  }

  @SimpleFunction(description = "Read one or more strings from a connected Bluetooth device.")
  public void ReadStringValues(String serviceUuid, String characteristicUuid, boolean utf16) {
    if (inner != null) {
      inner.ReadStringValues(serviceUuid, characteristicUuid, utf16);
    }
  }

  @SimpleFunction(description = "Register to receive updates when one or more string values from" +
      "a connected Bluetooth device change.")
  public void RegisterForStringValues(String serviceUuid, String characteristicUuid, boolean utf16) {
    if (inner != null) {
      inner.RegisterForStringValues(serviceUuid, characteristicUuid, utf16);
    }
  }

  @SimpleFunction(description = "Unregister for updates for the given service and characteristic.")
  public void UnregisterForValues(String service_uuid, String characteristic_uuid) {
    if (inner != null) {
      inner.UnregisterForValues(service_uuid, characteristic_uuid);
    }
  }

  @SimpleFunction(description = "Get the RSSI (Received Signal Strength Indicator) of found device with index." +
      " Index specifies the position in BluetoothLE device list, starting from 1.")
  public int FoundDeviceRssi(int index) {
    if (inner != null) {
      return inner.FoundDeviceRssi(index);
    }
    return 0;
  }

  @SimpleFunction(description = "Get the name of found device with index. Index specifies the position in" +
      " BluetoothLE device list, starting from 1.")
  public String FoundDeviceName(int index) {
    if (inner != null) {
      return inner.FoundDeviceName(index);
    }
    return null;
  }

  @SimpleFunction(description = "Get the address of found device with index. Index specifies the position" +
      " in BluetoothLE device list, starting from 1.")
  public String FoundDeviceAddress(int index) {
    if (inner != null) {
      return inner.FoundDeviceAddress(index);
    }
    return null;
  }

  @SimpleFunction(description = "Create and publish a Bluetooth LE advertisement. inData specifies the data that" +
      " will be included in the advertisement. serviceUuid specifies the UUID of the advertisement.")
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

  @SimpleFunction(description = "Scans for Bluetooth LE advertisements. scanPeriod specifies how" +
      " long the scan will run.")
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

  @SimpleProperty(description = "Return true if a BluetoothLE device is connected; Otherwise, return false.",
      category = PropertyCategory.BEHAVIOR)
  public boolean IsDeviceConnected() {
    if (inner != null) {
      return inner.IsDeviceConnected();
    }
    return false;
  }

  @SimpleProperty(description = "Return a sorted list of BluetoothLE devices as a String.",
      category = PropertyCategory.BEHAVIOR)
  public String DeviceList() {
    if (inner != null) {
      return inner.DeviceList();
    }
    return null;
  }

  @SimpleProperty(description = "Return the RSSI (Received Signal Strength Indicator) of connected device.",
      category = PropertyCategory.BEHAVIOR)
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

  @SimpleEvent(description = "This event is triggered when a BluetoothLE device is disconnected.")
  public void Disconnected() {

  }

  @SimpleEvent(description = "Trigger event when RSSI (Received Signal Strength Indicator) of found" +
      " BluetoothLE device changes")
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

  @SimpleEvent(description = "Trigger event when one or more byte values from a connected " +
      "BluetoothLE device are received.")
  public void ByteValuesReceived(final String serviceUuid, final String characteristicUuid,
                                 final YailList byteValues) {
    EventDispatcher.dispatchEvent(this, "ByteValuesReceived", serviceUuid, characteristicUuid,
        byteValues);
  }

  @SimpleEvent(description = "Trigger event when one or more short values from a connected " +
      "BluetoothLE device are received.")
  public void ShortValuesReceived(final String serviceUuid, final String characteristicUuid,
                                  final YailList shortValues) {
    EventDispatcher.dispatchEvent(this, "ShortValuesReceived", serviceUuid, characteristicUuid,
        shortValues);
  }

  @SimpleEvent(description = "Trigger event when one or more integer values from a connected " +
      "BluetoothLE device are received.")
  public void IntegerValuesReceived(final String serviceUuid, final String characteristicUuid,
                                    final YailList intValues) {
    EventDispatcher.dispatchEvent(this, "IntegerValuesReceived", serviceUuid, characteristicUuid,
        intValues);
  }

  @SimpleEvent(description = "Trigger event when one or more floating point values from a " +
      "BluetoothLE device are received.")
  public void FloatValuesReceived(final String serviceUuid, final String characteristicUuid,
                                  final YailList floatValues) {
    EventDispatcher.dispatchEvent(this, "FloatValuesReceived", serviceUuid, characteristicUuid,
        floatValues);
  }

  @SimpleEvent(description = "Trigger event when one or more string values from a connected " +
      "BluetoothLE device a received.")
  public void StringValuesReceived(final String serviceUuid, final String characteristicUuid,
                                   final YailList stringValues) {
    EventDispatcher.dispatchEvent(this, "StringValuesReceived", serviceUuid, characteristicUuid,
        stringValues);
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

  @SimpleFunction(description = "Return Unique ID of selected service with index. Index specified by list of" +
      " supported services for a connected device, starting from 1.")
  public String ServiceByIndex(int index) {
    if (inner != null) {
      return inner.GetServiceByIndex(index);
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

  @SimpleFunction(description = "Return Unique ID of selected characteristic with index. Index specified by list" +
      " of supported characteristics for a connected device, starting from 1.")
  public String CharacteristicByIndex(int index) {
    if (inner != null) {
      return inner.GetCharacteristicByIndex(index);
    }
    return null;
  }
}


