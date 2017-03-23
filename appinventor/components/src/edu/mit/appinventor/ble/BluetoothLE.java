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
import com.google.common.collect.Lists;
import gnu.lists.FString;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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

  public static abstract class BLEResponseHandler<T> {
    public void onReceive(String serviceUuid, String characteristicUuid, List<T> values) {
    }
    public void onWrite(String serviceUuid, String characteristicUuid, List<T> values) {
    }
  }

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

  @SimpleEvent
  public void ByteValuesWritten(final String serviceUuid, final String characteristicUuid,
                                final YailList byteValues) {
    EventDispatcher.dispatchEvent(this, "ByteValuesWritten", serviceUuid, characteristicUuid,
        byteValues);
  }

  @SimpleEvent(description = "Trigger event when one or more short values from a connected " +
      "BluetoothLE device are received.")
  public void ShortValuesReceived(final String serviceUuid, final String characteristicUuid,
                                  final YailList shortValues) {
    EventDispatcher.dispatchEvent(this, "ShortValuesReceived", serviceUuid, characteristicUuid,
        shortValues);
  }

  @SimpleEvent
  public void ShortValuesWritten(final String serviceUuid, final String characteristicUuid,
                                 final YailList shortValues) {
    EventDispatcher.dispatchEvent(this, "ShortValuesWritten", serviceUuid, characteristicUuid,
        shortValues);
  }

  @SimpleEvent(description = "Trigger event when one or more integer values from a connected " +
      "BluetoothLE device are received.")
  public void IntegerValuesReceived(final String serviceUuid, final String characteristicUuid,
                                    final YailList intValues) {
    EventDispatcher.dispatchEvent(this, "IntegerValuesReceived", serviceUuid, characteristicUuid,
        intValues);
  }

  @SimpleEvent
  public void IntegerValuesWritten(final String serviceUuid, final String characteristicUuid,
                                   final YailList intValues) {
    EventDispatcher.dispatchEvent(this, "IntegerValuesWritten", serviceUuid, characteristicUuid,
        intValues);
  }

  @SimpleEvent(description = "Trigger event when one or more floating point values from a " +
      "BluetoothLE device are received.")
  public void FloatValuesReceived(final String serviceUuid, final String characteristicUuid,
                                  final YailList floatValues) {
    EventDispatcher.dispatchEvent(this, "FloatValuesReceived", serviceUuid, characteristicUuid,
        floatValues);
  }

  @SimpleEvent
  public void FloatValuesWritten(final String serviceUuid, final String characteristicUuid,
                                 final YailList floatValues) {
    EventDispatcher.dispatchEvent(this, "FloatValuesWritten", serviceUuid, characteristicUuid,
        floatValues);
  }

  @SimpleEvent(description = "Trigger event when one or more string values from a connected " +
      "BluetoothLE device a received.")
  public void StringValuesReceived(final String serviceUuid, final String characteristicUuid,
                                   final YailList stringValues) {
    EventDispatcher.dispatchEvent(this, "StringValuesReceived", serviceUuid, characteristicUuid,
        stringValues);
  }

  @SimpleEvent
  public void StringValuesWritten(final String serviceUuid, final String characteristicUuid,
                                  final YailList stringValues) {
    EventDispatcher.dispatchEvent(this, "StringValuesWritten", serviceUuid, characteristicUuid, stringValues);
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

  @SimpleFunction(description = "Return list of supported characteristics for the given service. " +
      "The list will contain (UUID, name) pairs for each characteristic provided by the given " +
      "service UUID.")
  public YailList GetCharacteristicsForService(String serviceUuid) {
    if (inner != null) {
      return inner.GetCharacteristicsForService(serviceUuid);
    }
    return YailList.makeEmptyList();
  }

  @SimpleFunction(description = "Return Unique ID of selected characteristic with index. Index specified by list" +
      " of supported characteristics for a connected device, starting from 1.")
  public String CharacteristicByIndex(int index) {
    if (inner != null) {
      return inner.GetCharacteristicByIndex(index);
    }
    return null;
  }

  // Helper methods for profile extensions for BLE
  /**
   * Read bytes from the given characteristic. Bytes on the wire are
   * interpreted as unsigned integers if signed is false. The {@link
   * BLEResponseHandler#onReceive(String, String, List)} of
   * <code>callback</code> will be called when the operation completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed (true) or unsigned (false)
   * @param callback Callback that will receive the bytes read
   */
  public void ExReadByteValues(String serviceUuid, String characteristicUuid, boolean signed,
                               BLEResponseHandler<Integer> callback) {
    if (inner != null) {
      inner.ReadByteValues(serviceUuid, characteristicUuid, signed, callback);
    }
  }

  /**
   * Register for notifications/indications of byte values for the
   * given characteristic. Bytes on the wire are interpreted as
   * unsigned integers if signed is false. The
   * {@link BLEResponseHandler#onReceive(String, String, List)} of
   * <code>callback</code> will be called when the operation
   * completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed (true) or unsigned (false)
   * @param callback Callback that will receive the bytes read
   */
  public void ExRegisterForByteValues(String serviceUuid, String characteristicUuid, boolean signed,
                                      BLEResponseHandler<Integer> callback) {
    if (inner != null) {
      inner.RegisterForByteValues(serviceUuid, characteristicUuid, signed, callback);
    }
  }

  /**
   * Write the byte values to the given characteristic without waiting
   * for a response.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed or unsigned
   */
  public void ExWriteByteValues(String serviceUuid, String characteristicUuid, boolean signed,
                                List<Integer> values) {
    if (inner != null) {
      inner.WriteByteValues(serviceUuid, characteristicUuid, signed, values);
    }
  }

  /**
   * Write the value to the given characteristic without waiting for a
   * response. The type of value will control how it is
   * interpreted. For example, a {@link java.lang.String} value will
   * be converted into UTF-8 and sent as a series of bytes. A {@link
   * java.util.Collection} will be sent to the device as an array of
   * bytes. A single value that extends {@link java.lang.Number} will
   * be cast to a byte.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed or unsigned
   * @param value Value to write
   */
  public void ExWriteByteValues(String serviceUuid, String characteristicUuid, boolean signed,
                                Object value) {
    if (inner != null) {
      inner.WriteByteValues(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, value, 1));
    }
  }

  /**
   * Write the value to the given characteristic without waiting for a response.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed or unsigned
   * @param value Integer value to write
   */
  public void ExWriteByteValues(String serviceUuid, String characteristicUuid, boolean signed,
                                int value) {
    ExWriteByteValues(serviceUuid, characteristicUuid, signed, Collections.singletonList(value));
  }

  /**
   * Write the values to the given characteristic waiting for a
   * response. The {@link BLEResponseHandler#onWrite(String, String, List)}
   * method of <code>callback</code> will be called on completion
   * of the write.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed or unsigned
   * @param values List of integer values to write
   * @param callback Callback that will be notified when the write completes
   */
  public void ExWriteByteValuesWithResponse(String serviceUuid, String characteristicUuid,
                                            boolean signed, List<Integer> values,
                                            BLEResponseHandler<Integer> callback) {
    if (inner != null) {
      inner.WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed, values, callback);
    }
  }

  /**
   * Write the values to the given characteristic waiting for a
   * response. The {@link BLEResponseHandler#onWrite(String, String, List)}
   * method of <code>callback</code> will be called on completion
   * of the write. See {@link #ExWriteByteValues(String,String,boolean,Object)}
   * for how <code>value</code> is converted into a byte array.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed or unsigned
   * @param value Object to be cast to an array of bytes
   * @param callback Callback that will be notified when the write completes
   */
  public void ExWriteByteValuesWithResponse(String serviceUuid, String characteristicUuid,
                                            boolean signed, Object value,
                                            BLEResponseHandler<Integer> callback) {
    if (inner != null) {
      inner.WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, value, 1), callback);
    }
  }

  /**
   * Write the integer value to the given characteristic waiting for a
   * response. The {@link BLEResponseHandler#onWrite(String, String, List)}
   * method of the <code>callback</cod> will be called on completion of
   * the write. The integer will be interpreted as signed or unsigned
   * based on the truth value of the <code>signed</code> parameter.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the bytes as signed (true) or unsigned (false)
   * @param value The integer value (either -128 to 127 or 0 to 255)
   * @param callback Callback that will be notified when the write completes
   */
  public void ExWriteByteValuesWithResponse(String serviceUuid, String characteristicUuid,
                                            boolean signed, int value,
                                            BLEResponseHandler<Integer> callback) {
    ExWriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed,
        Collections.singletonList(value), callback);
  }

  /**
   * Read short values from the given characteristic, least
   * significant byte first, interpreting the shorts as signed or
   * unsigned based on the truth value of <code>signed</code>. The
   * {@link BLEResponseHandler#onReceive(String, String, List)}
   * method of <code>callback</code> will be called with a
   * {@link java.util.List} containing the read shorts.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the shorts as signed (true) or unsigned (false)
   * @param callback Callback that will be notified when the read completes
   */
  public void ExReadShortValues(String serviceUuid, String characteristicUuid, boolean signed,
                                BLEResponseHandler<Integer> callback) {
    if (inner != null) {
      inner.ReadShortValues(serviceUuid, characteristicUuid, signed, callback);
    }
  }

  /**
   * Register for short value updates from the given characteristic,
   * least significant byte first, interpreting the shorts as signed
   * or unsigned based on the truth value of <code>signed</code>. The
   * {@link BLEResponseHandler#onReceive(String, String, List)} method
   * of <code>callback</code> will be called with a
   * {@link java.util.List} containing the received shorts.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the shorts as signed (true) or unsigned (false)
   * @param callback Callback that will be notified when the characteristic's value changes
   */
  public void ExRegisterForShortValues(String serviceUuid, String characteristicUuid,
                                       boolean signed, BLEResponseHandler<Integer> callback) {
    if (inner != null) {
      inner.RegisterForShortValues(serviceUuid, characteristicUuid, signed, callback);
    }
  }

  /**
   * Write short values to the characteristic, not waiting for a
   * response. The shorts are interpreted as signed or unsigned based
   * on the truth value of <code>signed</code>.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the shorts as signed (true) or unsigned (false)
   * @param values The integer values (either -32768 to 32767 or 0 to 65535)
   */
  public void ExWriteShortValues(String serviceUuid, String characteristicUuid, boolean signed,
                                 List<Integer> values) {
    if (inner != null) {
      inner.WriteShortValues(serviceUuid, characteristicUuid, signed, values);
    }
  }

  /**
   * Write the short value to the characteristic, not waiting for a
   * response. The shorts are interpreted as signed or unsigned based
   * on the truth value of <code>signed</code>.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the shorts as signed (true) or unsigned (false)
   * @param value The integer value (either -32768 to 32767 or 0 to 65536)
   */
  public void ExWriteShortValues(String serviceUuid, String characteristicUuid, boolean signed,
                                 int value) {
    ExWriteShortValues(serviceUuid, characteristicUuid, signed, Collections.singletonList(value));
  }

  /**
   * Write short values to the characteristic, least significant byte
   * first, waiting for a response. The shorts will be interpreted as
   * signed or unsigned based on the truth value of
   * <code>signed</code>. The
   * {@link BLEResponseHandler#onReceive(String, String, List)} method
   * of <code>callback</code> will be called once the write completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the shorts as signed (true) or unsigned (false)
   * @param values The integer values (either -32768 to 32767 or 0 to 65535)
   * @param callback Callback that will be notified when the characteristic is written
   */
  public void ExWriteShortValuesWithResponse(String serviceUuid, String characteristicUuid,
                                             boolean signed, List<Integer> values,
                                             BLEResponseHandler<Integer> callback) {
    if (inner != null) {
      inner.WriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed, values, callback);
    }
  }

  /**
   * Write the short value to the characteristic, least significant
   * byte first, waiting for a response. The shorts will be
   * interpreted as signed or unsigned based on the truth value of
   * <code>signed</code>. The
   * {@link BLEResponseHandler#onWrite(String, String, List)} method
   * of <code>callback</code> ill be called once the write completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the shorts as signed (true) or unsigned (false)
   * @param value The integer value (either -32768 to 32767 or 0 to 65536)
   * @param callback Callback that will be notified when the characteristic is written
   */
  public void ExWriteShortValuesWithResponse(String serviceUuid, String characteristicUuid,
                                             boolean signed, int value,
                                             BLEResponseHandler<Integer> callback) {
    ExWriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed,
        Collections.singletonList(value), callback);
  }

  /**
   * Read integer values from the characteristic, least significant
   * byte first. The ints will be interpreted as signed or unsigned
   * based on the truth value of <code>signed</code>. The {@link
   * BLEResponseHandler#onReceive(String, String, List)} method of
   * <code>callback</code> will be called once the read completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the integers as signed (true) or unsigned (false)
   * @param callback Callback that will be notified when the characteristic is read
   */
  public void ExReadIntegerValues(String serviceUuid, String characteristicUuid, boolean signed,
                                  BLEResponseHandler<Long> callback) {
    if (inner != null) {
      inner.ReadIntegerValues(serviceUuid, characteristicUuid, signed, callback);
    }
  }

  /**
   * Register for integer value updates from the characteristic, least
   * significant byte first. The ints will be interpreted as signed or
   * unsigned based on the truth value of <code>signed</code>. The
   * {@link BLEResponseHandler#onReceive(String, String, List)} method
   * of <code>callback</code> will be called once the read completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the integers as signed (true) or unsigned (false)
   * @param callback Callback that will be called when notifications are received
   */
  public void ExRegisterForIntegerValues(String serviceUuid, String characteristicUuid,
                                         boolean signed, BLEResponseHandler<Long> callback) {
    if (inner != null) {
      inner.ReadIntegerValues(serviceUuid, characteristicUuid, signed, callback);
    }
  }

  /**
   * Write integer values to the characteristic, least significant
   * byte first, without waiting for a response. The ints will be
   * interpreted as signed or unsigned based on the truth value of
   * <code>signed</code>.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the integers as signed (true) or unsigned (false)
   * @param values Integer values to send (-2147483648 to 2147483647 or 0 to 4294967295)
   */
  public void ExWriteIntegerValues(String serviceUuid, String characteristicUuid, boolean signed,
                                   List<Long> values) {
    if (inner != null) {
      inner.WriteIntegerValues(serviceUuid, characteristicUuid, signed, values);
    }
  }

  /**
   * Write an integer value to the characteristic, least significant
   * byte first, without waiting for a response. The ints will be
   * interpreted as signed or unsigned based on the truth value of
   * <code>signed</code>.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the integers as signed (true) or unsigned (false)
   * @param value Integer value to send (-2147483648 to 2147483647 or 0 to 4294967295)
   */
  public void ExWriteIntegerValues(String serviceUuid, String characteristicUuid, boolean signed,
                                   long value) {
    ExWriteIntegerValues(serviceUuid, characteristicUuid, signed, Collections.singletonList(value));
  }

  /**
   * Write integer values to the characteristic, least significant
   * byte first, waiting for a response. The ints will be interpreted
   * as signed or unsigned based on the truth value of
   * <code>signed</code>. The {@link
   * BLEResponseHandler#onWrite(String, String, List)} method of
   * <code>callback</code> will be called when the write is completed.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the integers as signed (true) or unsigned (false)
   * @param values Integer value to send (-2147483648 to 2147483647 or 0 to 4294967295)
   * @param callback Callback that will be called on completion of the write
   */
  public void ExWriteIntegerValuesWithResponse(String serviceUuid, String characteristicUuid,
                                               boolean signed, List<Long> values,
                                               BLEResponseHandler<Long> callback) {
    if (inner != null) {
      inner.WriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed, values, callback);
    }
  }

  /**
   * Write the integer value to the characteristic, least significant
   * byte first, waiting for a response. The ints will be interpreted
   * as signed or unsigned based on the truth value of
   * <code>signed</code>. The {@link
   * BLEResponseHandler#onWrite(String, String, List)} method of
   * <code>callback</code> will be called when the write is completed.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param signed Interpret the integers as signed (true) or unsigned (false)
   * @param value Integer value to send (-2147483648 to 2147483647 or 0 to 4294967295)
   * @param callback Callback that will be called on completion of the write
   */
  public void ExWriteIntegerValuesWithResponse(String serviceUuid, String characteristicUuid,
                                               boolean signed, long value,
                                               BLEResponseHandler<Long> callback) {
    ExWriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed,
        Collections.singletonList(value), callback);
  }

  /**
   * Read float values from the characteristic. If the characteristic
   * uses short floats (sfloat) then <code>shortFloats</code> should
   * be passed as true. A {@link java.util.List} of floats will be
   * passed to the <code>callback</code>'s {@link
   * BLEResponseHandler#onReceive(String, String, List)} method upon
   * completion.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param shortFloats Interpret the floats as half-precision (true) or single precision (false)
   * @param callback Callback that will be notified when the characteristic is read
   */
  public void ExReadFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloats,
                                BLEResponseHandler<Float> callback) {
    if (inner != null) {
      inner.ReadFloatValues(serviceUuid, characteristicUuid, shortFloats, callback);
    }
  }

  /**
   * Register to receive updates of floating point values from the
   * characteristic. If the characteristic uses short floats (sfloat)
   * then <code>shortFloats</code> should be passed as true. A {@link
   * java.util.List} of floats will be passed to the
   * <code>callback</code>'s {@link
   * BLEResponseHandler#onReceive(String, String, List)} method upon
   * completion.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param shortFloats Interpret the floats as half-precision (true) or single precision (false)
   * @param callback Callback that will be notified when the characteristic receives updates
   */
  public void ExRegisterForFloatValues(String serviceUuid, String characteristicUuid,
                                       boolean shortFloats, BLEResponseHandler<Float> callback) {
    if (inner != null) {
      inner.RegisterForFloatValues(serviceUuid, characteristicUuid, shortFloats, callback);
    }
  }

  /**
   * Write floating point values to the characteristic without waiting
   * for a response. If the characteristic uses short floats (sfloat)
   * then <code>shortFloats</code> should be passed as true.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param shortFloats Interpret the floats as half-precision (true) or single precision (false)
   * @param values Float values to write to the characteristic
   */
  public void ExWriteFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloats,
                                 List<Float> values) {
    if (inner != null) {
      inner.WriteFloatValues(serviceUuid, characteristicUuid, shortFloats, values);
    }
  }

  /**
   * Write a floating point value to the characteristic without
   * waiting for a response. If the characteristic uses short floats
   * (sfloat) then <code>shortFloats</code> should be passed as true.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param shortFloats Interpret the floats as half-precision (true) or single precision (false)
   * @param value Float value to write to the characteristic
   */
  public void ExWriteFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloats,
                                 float value) {
    ExWriteFloatValues(serviceUuid, characteristicUuid, shortFloats, Collections.singletonList(value));
  }

  /**
   * Write floating point values to the characteristic and wait for an
   * acknowledgement. If the characteristic uses short floats (sfloat)
   * then <code>shortFloats</code> should be passed as true. The
   * {@link BLEResponseHandler#onWrite(String, String, List)} method
   * of <code>callback</code> will be called when the write operation
   * is complete.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param shortFloats Interpret the floats as half-precision (true) or single precision (false)
   * @param values Float values to write to the characteristic
   * @param callback Callback to be called after the write operation completes
   */
  public void ExWriteFloatValuesWithResponse(String serviceUuid, String characteristicUuid,
                                             boolean shortFloats, List<Float> values,
                                             BLEResponseHandler<Float> callback) {
    if (inner != null) {
      inner.WriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloats, values, callback);
    }
  }

  /**
   * Write a floating point value to the characteristic and wait for
   * an acknowledgement. If the characteristic uses short floats
   * (sfloat) then <code>shortFloats</code> should be passed as
   * true. The {@link BLEResponseHandler#onWrite(String, String, List)}
   * method of <code>callback</code> will be called when the write
   * operation is completed.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param shortFloats Interpret the floats as half-precision (true) or single precision (false)
   * @param value Float value to write to the characteristic
   * @param callback Callback to be called after the write operation completes
   */
  public void ExWriteFloatValuesWithResponse(String serviceUuid, String characteristicUuid,
                                             boolean shortFloats, float value,
                                             BLEResponseHandler<Float> callback) {
    ExWriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloats,
        Collections.singletonList(value), callback);
  }

  /**
   * Read null-terminated strings from the characteristic and wait for
   * an acknowledgement. If the characteristic uses UTF-16 strings,
   * the <code>utf16</code> parameter should be true. The {@link
   * BLEResponseHandler#onReceive(String, String, List)} method of
   * <code>callback</code> will be called when the characteristic is read.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param utf16 Interpret strings as UTF-8 (false) or UTF-16 (true)
   * @param callback Callback to be called when the string value is read
   */
  public void ExReadStringValues(String serviceUuid, String characteristicUuid, boolean utf16,
                                 BLEResponseHandler<String> callback) {
    if (inner != null) {
      inner.ReadStringValues(serviceUuid, characteristicUuid, utf16, callback);
    }
  }

  /**
   * Register for null-terminated string updates from the
   * characteristic and wait for an acknowledgement. If the
   * characteristic uses UTf-16 strings, the <code>utf16</code>
   * parameter should be true. The {@link
   * BLEResponseHandler#onReceive(String, String, List)} method of
   * <code>callback</code> will be called when the characteristic is
   * read.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param utf16 Interpret strings as UTF-8 (false) or UTF-16 (true)
   * @param callback Callback to be called when the string value is updated
   */
  public void ExRegisterForStringValues(String serviceUuid, String characteristicUuid,
                                        boolean utf16, BLEResponseHandler<String> callback) {
    if (inner != null) {
      inner.RegisterForStringValues(serviceUuid, characteristicUuid, utf16, callback);
    }
  }

  /**
   * Write one or more null-terminated strings to the characteristic
   * without waiting for an acknowledgement. If the characteristic
   * uses UTF-16 strings, the <code>utf16</code> parameter should be
   * true.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param utf16 Interpret strings as UTF-8 (false) or UTF-16 (true)
   * @param values One or more strings to write to the characteristic. If more than one string is
   *               provided they will be sent null-terminated.
   */
  public void ExWriteStringValues(String serviceUuid, String characteristicUuid, boolean utf16,
                                  List<String> values) {
    if (inner != null) {
      inner.WriteStringValues(serviceUuid, characteristicUuid, utf16, values);
    }
  }

  /**
   * Write a null-terminated string to the characteristic without
   * waiting for an acknowledgement. If the characteristic uses UTF-16
   * strings, the <code>utf16</code> parameter should be true.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param utf16 Interpret strings as UTF-8 (false) or UTF-16 (true)
   * @param value String to write to the characteristic
   */
  public void ExWriteStringValues(String serviceUuid, String characteristicUuid, boolean utf16,
                                  String value) {
    ExWriteStringValues(serviceUuid, characteristicUuid, utf16, Collections.singletonList(value));
  }

  /**
   * Write one or more null-terminated strings to the characteristic
   * and wait for an acknowledgement. If the characteristic uses
   * UTF-16 strings, the <code>utf16</code> parameter should be
   * true. The {@link BLEResponseHandler#onWrite(String, String, List)}
   * method of <code>callback</code> will be called when the write
   * completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param utf16 Interpret strings as UTF-8 (false) or UTF-16 (true)
   * @param values One or more strings to write to the characteristic. If more than one string is
   *               provided they will be sent null-terminated.
   * @param callback Callback to be called when the write operation completes
   */
  public void ExWriteStringValuesWithResponse(String serviceUuid, String characteristicUuid,
                                              boolean utf16, List<String> values,
                                              BLEResponseHandler<String> callback) {
    if (inner != null) {
      inner.WriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16, values, callback);
    }
  }

  /**
   * Write one null-terminated strings to the characteristic and wait
   * for an acknowledgement. If the characteristic uses UTF-16, the
   * <code>utf16</code> parameter should be true. The {@link
   * BLEResponseHandler#onWrite(String, String, List)} method of
   * <code>callback</code> will be called when the write completes.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param utf16 Interpret strings as UTF-8 (false) or UTF-16 (true)
   * @param value String to write to the characteristic
   * @param callback Callback to be called when the write operation completes
   */
  public void ExWriteStringValuesWithResponse(String serviceUuid, String characteristicUuid,
                                              boolean utf16, String value,
                                              BLEResponseHandler<String> callback) {
    ExWriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16,
        Collections.singletonList(value), callback);
  }

  /**
   * Unregister the <code>callback</code> for the given characteristic.
   *
   * @param serviceUuid UUID of the Bluetooth service
   * @param characteristicUuid UUID of the Bluetooth characteristic
   * @param callback The callback to be removed for the characteristic
   */
  public void ExUnregisterForValues(String serviceUuid, String characteristicUuid,
                                    BLEResponseHandler<?> callback) {
    if (inner != null) {
      inner.UnregisterForValues(serviceUuid, characteristicUuid, callback);
    }
  }

  /**
   * Convert an unknown object type to a list of a certain type,
   * casting when available/appropriate.
   *
   * The general flow is as follows:
   *
   * 1. If value is of type T, return a list containing the value
   * 2. If value is a YailList:
   *    a. Skip the *list* header and proceed as a list
   * 3. If value is a collection or list:
   *    a. Create a new list checking the type of each entry against T
   * 4. If value is a CharSequence (String or FString)
   *    a. Convert the string to bytes and cast up to T
   * 5. Throw an exception because we don't know how to process the value
   *
   * @param <T> The target type
   * @param tClass Class of the target type, for casting
   * @param value Original value to be converted to a List
   * @param size The size of the type (not necessarily the size of T)
   * @throws ClassCastException if an entity cannot be converted to a list or
   * an element cannot be converted to type T.
   */
  private static <T> List<T> toList(Class<T> tClass, Object value, int size) {
    if (tClass.isAssignableFrom(value.getClass())) {
      return Collections.singletonList(tClass.cast(value));
    } else if (value instanceof YailList) {  // must come before List and Collection due to *list* header
      Iterator<?> i = ((YailList) value).iterator();
      i.next();  // skip *list* symbol
      return listFromIterator(tClass, i);
    } else if (value instanceof List) {
      return listFromIterator(tClass, ((List<?>) value).iterator());
    } else if (value instanceof Collection) {
      return listFromIterator(tClass, ((Collection<?>) value).iterator());
    } else if (value instanceof String) {
      // this assumes that the string is being cast to a list of UTF-8 bytes or UTF-16LE chars
      try {
        byte[] content = ((String) value).getBytes(size == 1 ? "UTF-8" : "UTF-16LE");
        if (tClass.equals(Integer.class)) {
          return checkedCast(tClass, toIntList(content));
        }
        return Collections.emptyList();
      } catch (UnsupportedEncodingException e) {
        // Both UTF-8 and UTF-16LE are required by JVM. This should never happen
        Log.wtf(LOG_TAG, "No support for UTF-8 or UTF-16", e);
        return Collections.emptyList();
      }
    } else if (value instanceof FString) {
      // this assumes that the string is being cast to a list of UTF-8 bytes
      return toList(tClass, value.toString(), size);
    } else {
      throw new ClassCastException("Unable to convert " + value + " to list");
    }
  }

  /**
   * Create a checked list from an iterator.
   *
   * @param <T> The target type
   * @param tClass The class of T, for casting
   * @param i Iterator yielding elements for the new list
   * @throws ClassCastException if an entity cannot be converted to a list or
   * an element cannot be converted to type T.
   */
  @SuppressWarnings("unchecked")
  private static <T> List<T> listFromIterator(Class<T> tClass, Iterator<?> i) {
    // Primitive types cannot be cast to one another using boxed values...
    if (tClass.equals(Integer.class)) {
      return (List<T>) toIntList((List<? extends Number>)(List) Lists.newArrayList(i));
    } else if (tClass.equals(Long.class)) {
      return (List<T>) toLongList((List<? extends Number>)(List) Lists.newArrayList(i));
    } else if (tClass.equals(Float.class)) {
      return (List<T>) toFloatList((List<? extends Number>)(List) Lists.newArrayList(i));
    }
    List<T> result = new ArrayList<T>();
    while (i.hasNext()) {
      Object o = i.next();
      if (!tClass.isInstance(o) && !tClass.isAssignableFrom(o.getClass())) {
        throw new ClassCastException("Unable to convert " + o + " of type " + o.getClass().getName() + " to type " + tClass.getName());
      }
      result.add(tClass.cast(o));
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  private static <T> List<T> checkedCast(Class<T> tClass, List<?> list) {
    for (Object o : list) {
      if (!tClass.isInstance(o) && !tClass.isAssignableFrom(o.getClass())) {
        throw new ClassCastException("Unable to convert " + o + " to type " + tClass.getName());
      }
    }
    return (List<T>) list;
  }

  private static <T extends Number> List<Float> toFloatList(List<T> value) {
    List<Float> result = new ArrayList<Float>(value.size());
    for (T o : value) {
      result.add(o.floatValue());
    }
    return result;
  }

  private static <T extends Number> List<Long> toLongList(List<T> value) {
    List<Long> result = new ArrayList<Long>(value.size());
    for (T o : value) {
      result.add(o.longValue());
    }
    return result;
  }

  private static <T extends Number> List<Integer> toIntList(List<T> value) {
    List<Integer> result = new ArrayList<Integer>(value.size());
    for (T o : value) {
      result.add(o.intValue());
    }
    return result;
  }

  private static List<Integer> toIntList(byte[] values) {
    List<Integer> result = new ArrayList<Integer>(values.length);
    for(byte b : values) {
      result.add((int) b);
    }
    return result;
  }
}


