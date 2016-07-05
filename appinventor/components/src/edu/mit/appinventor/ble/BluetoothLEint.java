// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2016 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import android.app.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;

import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Handler;
import android.os.ParcelUuid;

import android.text.TextUtils;

import android.util.Log;

import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * Author: Andrew McKinney <mckinney@mit.edu>
 * Author: Cristhian Ulloa <cristhian2ulloa@gmail.com>
 * Author: tiffanyle <le.tiffanya@gmail.com>
 */

public class BluetoothLEint {

  /**
   * Reference to our "outer" class
   */

  BluetoothLE outer;
  ComponentContainer container;

  /**
   * Basic Variable
   */
  private static final String LOG_TAG = "BluetoothLEint";
  private final Activity activity;
  private BluetoothAdapter mBluetoothAdapter;
  private BluetoothGatt currentBluetoothGatt;
  private int device_rssi = 0;
  private final Handler uiThread;
  private boolean mLogEnabled = true;
  private String mLogMessage;

  /**
   * BluetoothLE Info List
   */
  private HashMap<String, BluetoothGatt> gattList;
  private String deviceInfoList = "";
  private List<BluetoothDevice> mLeDevices;
  private List<BluetoothGattService> mGattService;
  private ArrayList<BluetoothGattCharacteristic> gattChars;
  private String serviceUUIDList;
  private String charUUIDList;
  private BluetoothGattCharacteristic mGattChar;
  private HashMap<BluetoothDevice, Integer> mLeDeviceRssi;

  /**
   * BluetoothLE Device Status
   */
  private boolean isEnabled = false;
  private boolean isScanning = false;
  private boolean isConnected = false;
  private boolean isCharRead = false;
  private boolean isCharWrite = false;
  private boolean isServiceRead = false;

  /**
   * GATT value
   */
  private int battery = -1;
  private String tempUnit = "";
  private byte[] bodyTemp;
  private byte[] heartRate;
  private int linkLoss_value = -1;
  private int txPower = -1;
  private byte[] data;
  private byte[] descriptorValue;
  private int intValue = 0;
  private float floatValue = 0;
  private String stringValue = "";
  private String byteValue = "";
  private int intOffset = 0;
  private int strOffset = 0;
  private int floatOffset = 0;

  private int charType = 0; //byte = 0; int = 1; string = 2; float = 3

  // BLE Advertisements
  private BluetoothLeScanner mBluetoothLeScanner;
  private Handler mHandler = new Handler();
  private AdvertiseCallback mAdvertiseCallback;
  private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
  private long SCAN_PERIOD = 5000;
  private String advertisementScanResult = "";
  private List<String> scannedAdvertiserNames = new ArrayList<String>();
  private HashMap<String, ScanResult> scannedAdvertisers = new HashMap<String, ScanResult>();
  private List<String> advertiserAddresses = new ArrayList<String>();
  private HashMap<String, String> nameToAddress = new HashMap<String, String>();
  private boolean isAdvertising = false;

  public BluetoothLEint(BluetoothLE outer, Activity activity, ComponentContainer container) {

    this.outer = outer;
    this.activity = activity;
    this.container = container;

    mLeDevices = new ArrayList<BluetoothDevice>();
    mGattService = new ArrayList<BluetoothGattService>();
    gattChars = new ArrayList<BluetoothGattCharacteristic>();
    mLeDeviceRssi = new HashMap<BluetoothDevice, Integer>();
    gattList = new HashMap<String, BluetoothGatt>();
    uiThread = new Handler();

    // See if we are usable on this device
    if (!container.$form().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
      Notifier.oneButtonAlert(container.$context(), "Bluetooth Not Supported, sorry.", "Unsupported", "Oh Well");
      return;
    }

    if (SdkLevel.getLevel() < SdkLevel.LEVEL_LOLLIPOP) {
      Notifier.oneButtonAlert(container.$context(), "Bluetooth Not Supported, sorry.", "Unsupported", "Oh Well");
      mBluetoothAdapter = null;
    } else {
      mBluetoothAdapter = newBluetoothAdapter(activity);
    }

    if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
      isEnabled = false;
      LogMessage("No Valid BTLE Device on platform", "e");
      container.$form().dispatchErrorOccurredEvent(outer, "BluetoothLE", ErrorMessages.ERROR_BLUETOOTH_NOT_ENABLED);
    } else {
      isEnabled = true;
      LogMessage("BluetoothLE Device found", "i");
    }

    if( !BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported() ) {
      Notifier.oneButtonAlert(container.$context(), "Bluetooth Advertisements Not Supported, sorry.", "Unsupported", "Oh Well");
    }
  }

  private void LogMessage(String message, String level) {
    if (mLogEnabled) {
      mLogMessage = message;
      String errorLevel = "e";
      String warningLevel = "w";

      // push to appropriate logging
      if (level.equals(errorLevel)) {
        Log.e(LOG_TAG, message);
      } else if (level.equals(warningLevel)) {
        Log.w(LOG_TAG, message);
      } else {
        Log.i(LOG_TAG, message);
      }
    }
  }

  public static BluetoothAdapter newBluetoothAdapter(Context context) {
    final BluetoothManager bluetoothManager = (BluetoothManager) context
        .getSystemService(Context.BLUETOOTH_SERVICE);
    return bluetoothManager.getAdapter();
  }

  public void StartScanning() {
    if (!mLeDevices.isEmpty()) {
      mLeDevices.clear();
      mLeDeviceRssi.clear();
    }
    mBluetoothAdapter.startLeScan(mLeScanCallback);
    LogMessage("StartScanning Successfully.", "i");
  }

  public void StopScanning() {
    mBluetoothAdapter.stopLeScan(mLeScanCallback);
    LogMessage("StopScanning Successfully.", "i");
  }

  public void Connect(int index) {
    BluetoothGattCallback newGattCallback = null;
    currentBluetoothGatt = mLeDevices.get(index - 1).connectGatt(activity, false, initCallBack(newGattCallback));
    if (currentBluetoothGatt != null) {
      gattList.put(mLeDevices.get(index - 1).toString(), currentBluetoothGatt);
      LogMessage("Connect Successfully.", "i");
    } else {
      LogMessage("Connect Fail.", "e");
    }
  }

  public void ConnectWithAddress(String address) {
    for (BluetoothDevice bluetoothDevice : mLeDevices) {
      if (bluetoothDevice.toString().equals(address)) {
        BluetoothGattCallback newGattCallback = null;
        currentBluetoothGatt = bluetoothDevice.connectGatt(activity, false, initCallBack(newGattCallback));
        if (currentBluetoothGatt != null) {
          gattList.put(bluetoothDevice.toString(), currentBluetoothGatt);
          LogMessage("Connect with Address Successfully.", "i");
          break;
        } else {
          LogMessage("Connect with Address Fail.", "e");
        }
      }
    }
  }

  public void DisconnectWithAddress(String address) {
    if (gattList.containsKey(address)) {
      gattList.get(address).disconnect();
      isConnected = false;
      gattList.remove(address);
      LogMessage("Disconnect Successfully.", "i");
    } else {
      LogMessage("Disconnect Fail. No Such Address in the List", "e");
    }
  }

  public void WriteStringValue(String service_uuid, String characteristic_uuid, String value) {
    LogMessage("stringValue: " + value, "i");
    writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), value);
  }

  public void WriteIntValue(String service_uuid, String characteristic_uuid, int value, int offset) {
    LogMessage("intValue: " + value, "i");
    writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), value, BluetoothGattCharacteristic.FORMAT_SINT32, offset);
  }

  public void WriteFloatValue(String service_uuid, String characteristic_uuid, float value, int offset) {

   int floatrep = Float.floatToIntBits(value);

    LogMessage("floatrep: " + floatrep, "i");

    writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), floatrep, BluetoothGattCharacteristic.FORMAT_SINT32, offset);
  }

  public void WriteByteValue(String service_uuid, String characteristic_uuid, String value) {
    byte[] bval = value.getBytes();
    LogMessage("byteValue: " + bval, "i");
    writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), bval);
  }

  public void ReadIntValue(String service_uuid, String characteristic_uuid, int intOffset) {
    charType = 1;
    this.intOffset = intOffset;
    readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
  }

  public void ReadStringValue(String service_uuid, String characteristic_uuid, int strOffset) {
    charType = 2;
    this.strOffset = strOffset;
    readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
  }

  public void ReadFloatValue(String service_uuid, String characteristic_uuid, int floatOffset) {
    charType = 3;
    this.floatOffset = floatOffset;
    readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
  }

  public void ReadByteValue(String service_uuid, String characteristic_uuid) {
    charType = 0;
    readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
  }

  public int FoundDeviceRssi(int index) {
    if (index <= mLeDevices.size())
      return mLeDeviceRssi.get(mLeDevices.get(index - 1));
    else
      return -1;
  }

  public String FoundDeviceName(int index) {
    if (index <= mLeDevices.size()) {
      LogMessage("Device Name is found", "i");
      return mLeDevices.get(index - 1).getName();
    } else {
      LogMessage("Device Name isn't found", "e");
      return null;
    }
  }

  public String FoundDeviceAddress(int index) {
    if (index <= mLeDevices.size()) {
      LogMessage("Device Address is found", "i");
      return mLeDevices.get(index - 1).getAddress();
    } else {
      LogMessage("Device Address is found", "e");
      return "";
    }
  }

  public void StartAdvertising(String inData, String serviceUuid) {
    //create a scan callback if it does not already exist. If it does, you're already scanning for ads.
    if (mBluetoothAdapter != null && mBluetoothAdapter.isMultipleAdvertisementSupported()) {
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();

    AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
          isAdvertising = true;
          super.onStartSuccess(settingsInEffect);
        }

        @Override
        public void onStartFailure(int errorCode) {
          LogMessage("Advertising onStartFailure: " + errorCode, "e");
          super.onStartFailure(errorCode);
        }
      };

      AdvertiseSettings advSettings = new AdvertiseSettings.Builder()
          .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
          .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
          .setConnectable(false)
          .build();

      ParcelUuid pUuid = new ParcelUuid(UUID.fromString(serviceUuid));

      AdvertiseData advData = new AdvertiseData.Builder()
          .setIncludeDeviceName(true)
          .addServiceUuid(pUuid)
          .addServiceData(pUuid, inData.getBytes(Charset.forName("UTF-8")))
          .build();

      if (mAdvertiseCallback == null) {
        AdvertiseSettings settings = advSettings;
        AdvertiseData data = advData;

        mAdvertiseCallback = advertisingCallback;

        if (mBluetoothLeAdvertiser != null) {
          mBluetoothLeAdvertiser.startAdvertising(settings, data, mAdvertiseCallback);
        }
      }
      LogMessage("StartScanningAdvertisements Successfully.", "i");
    } else {
      LogMessage("No bluetooth adapter", "i");
      Notifier.oneButtonAlert(container.$context(), "Bluetooth Advertisements Not Supported, sorry.", "Unsupported", "Oh Well");
    }
  }

  public void StopAdvertising() {
    LogMessage("Stopping BLE Advertising", "i");
    if (mBluetoothLeAdvertiser != null) {
      mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
      isAdvertising = false;
      mAdvertiseCallback = null;
    }
  }

  public void ScanAdvertisements(long scanPeriod) {
    SCAN_PERIOD = scanPeriod;

    // clear the information that was saved during previous scan
    advertiserAddresses = new ArrayList<String>();
    scannedAdvertisers = new HashMap<String, ScanResult>();
    scannedAdvertiserNames = new ArrayList<String>();
    nameToAddress = new HashMap<String, String>();


    // Will stop the scanning after a set time.
    uiThread.postDelayed(new Runnable() {
      @Override
      public void run() {
        stopAdvertisementScanning();
      }
    }, scanPeriod);

    if (mBluetoothAdapter != null) {
      mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

      if (mScanCallback != null) {

        if (mBluetoothLeScanner != null) {
          ScanSettings settings = new ScanSettings.Builder()
              .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
              .build();

          List<ScanFilter> filters = new ArrayList<ScanFilter>();
          ScanFilter filter = new ScanFilter.Builder()
              .build();
          // NOTE: removed service uuid from filter: ".setServiceUuid( new ParcelUuid(UUID.fromString( "0000b81d-0000-1000-8000-00805f9b34fb" ) ) )""

          filters.add(filter);

          if (settings != null) {
            mBluetoothLeScanner.startScan(filters, settings, mScanCallback);
          } else {
            LogMessage("settings or filters are null.", "i");
          }
        } else {
          LogMessage("Bluetooth LE scanner is null", "i");
        }
      } else {
        LogMessage("mScanCallback is null", "i");
      }
    } else {
      LogMessage("No bluetooth adapter found", "i");
    }
  }

  public void StopScanningAdvertisements() {
    LogMessage("Stopping BLE advertsiment scan.", "i");
    stopAdvertisementScanning();
  }

  public String GetAdvertisementData(String deviceAddress, String serviceUuid) {
    return "" + Arrays.toString(scannedAdvertisers.get(deviceAddress).getScanRecord().getServiceData().get(ParcelUuid.fromString(serviceUuid)));
  }

  public String GetAdvertiserAddress(String deviceName) {
    return nameToAddress.get(deviceName);
  }

  public List<String> GetAdvertiserServiceUuids(String deviceAddress) {
    return stringifyServiceList(scannedAdvertisers.get(deviceAddress).getScanRecord().getServiceUuids());
  }

  public String BatteryValue() {
    if (isCharRead) {
      return Integer.toString(battery);
    } else {
      return "Cannot Read Battery Level";
    }
  }

  public int TxPower() {
    return txPower;
  }

  public boolean IsDeviceConnected() {
    if (isConnected) {
      return true;
    } else {
      return false;
    }
  }

  public String DeviceList() {
    deviceInfoList = "";
    mLeDevices = sortDeviceList(mLeDevices);
    if (!mLeDevices.isEmpty()) {
      for (int i = 0; i < mLeDevices.size(); i++) {
        if (i != (mLeDevices.size() - 1)) {
          deviceInfoList += mLeDevices.get(i).getAddress() + " " + mLeDevices.get(i).getName() + " "
              + Integer.toString(mLeDeviceRssi.get(mLeDevices.get(i))) + ",";
        } else {
          deviceInfoList += mLeDevices.get(i).getAddress() + " " + mLeDevices.get(i).getName() + " "
              + Integer.toString(mLeDeviceRssi.get(mLeDevices.get(i)));
        }
      }
    }
    return deviceInfoList;
  }

  public String ConnectedDeviceRssi() {
    return Integer.toString(device_rssi);
  }

  public long AdvertisementScanPeriod() {
    return SCAN_PERIOD;
  }

  public List<String> GetAdvertiserNames() {
    return scannedAdvertiserNames;
  }

  public List<String> GetAdvertiserAddresses() {
    return advertiserAddresses;
  }

  public boolean IsDeviceAdvertising() {
    return isAdvertising;
  }

  public void Connected() {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "Connected");
      }
    });
  }

  public void RssiChanged(final int device_rssi) {
    uiThread.postDelayed(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "RssiChanged", device_rssi);
      }
    }, 1000);
  }

  public void DeviceFound() {
    EventDispatcher.dispatchEvent(outer, "DeviceFound");
  }

  public void ByteValueRead(final String byteValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "ByteValueRead", byteValue);
      }
    });
  }

  public void IntValueRead(final int intValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "IntValueRead", intValue);
      }
    });
  }

  public void StringValueRead(final String stringValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "StringValueRead", stringValue);
      }
    });
  }

  public void FloatValueRead(final float floatValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "FloatValueRead", floatValue);
      }
    });
  }

  public void ByteValueChanged(final String byteValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "ByteValueChanged", byteValue);
      }
    });
  }

  public void IntValueChanged(final int intValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "IntValueChanged", intValue);
      }
    });
  }

  public void FloatValueChanged(final float floatValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "FloatValueChanged", floatValue);
      }
    });
  }

  public void StringValueChanged(final String stringValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "StringValueChanged", stringValue);
      }
    });
  }

  public void ValueWrite() {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "ValueWrite");
      }
    });
  }

  public String GetSupportedServices() {
    if (mGattService == null) return ",";
    serviceUUIDList = ", ";
    for (int i = 0; i < mGattService.size(); i++) {
      if (i == 0) {
        serviceUUIDList = "";
      }
      UUID serviceUUID = mGattService.get(i).getUuid();
      String unknownServiceString = "Unknown Service";
      String serviceName = BluetoothLEGattAttributes.lookup(serviceUUID, unknownServiceString);
      serviceUUIDList += serviceUUID + " " + serviceName + ",";

    }
    return serviceUUIDList;
  }

  public String GetServicebyIndex(int index) {
    return mGattService.get(index).getUuid().toString();
  }

  public String GetSupportedCharacteristics() {
    if (mGattService == null) return ",";
    charUUIDList = ", ";
    for (int i = 0; i < mGattService.size(); i++) {
      if (i == 0) {
        charUUIDList = "";
      }
      for (BluetoothGattCharacteristic characteristic : mGattService.get(i).getCharacteristics()) {
        gattChars.add(characteristic);
      }
    }
    String unknownCharString = "Unknown Characteristic";
    for (int j = 0; j < gattChars.size(); j++) {
      UUID charUUID = gattChars.get(j).getUuid();
      String charName = BluetoothLEGattAttributes.lookup(charUUID, unknownCharString);
      charUUIDList += charUUID + " " + charName + ",";
    }
    return charUUIDList;
  }

  public String GetCharacteristicbyIndex(int index) {
    return gattChars.get(index).getUuid().toString();
  }

  /**
   * Functions
   */
  // sort the device list by RSSI
  private List<BluetoothDevice> sortDeviceList(List<BluetoothDevice> deviceList) {
    Collections.sort(deviceList, new Comparator<BluetoothDevice>() {
      @Override
      public int compare(BluetoothDevice device1, BluetoothDevice device2) {
        return mLeDeviceRssi.get(device1) - mLeDeviceRssi.get(device2);
      }
    });
    Collections.reverse(deviceList);
    return deviceList;
  }

  // add device when scanning
  private void addDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
    if (!mLeDevices.contains(device)) {
      mLeDevices.add(device);
      mLeDeviceRssi.put(device, rssi);
      DeviceFound();
    } else {
      mLeDeviceRssi.put(device, rssi);
    }
    RssiChanged(rssi);
  }

  // read characteristic based on UUID
  private void readChar(UUID ser_uuid, UUID char_uuid) {
    if (isServiceRead && !mGattService.isEmpty()) {
      for (BluetoothGattService aMGattService : mGattService) {
        if (aMGattService.getUuid().equals(ser_uuid)) {

          BluetoothGattDescriptor desc = aMGattService.getCharacteristic(char_uuid)
              .getDescriptor(BluetoothLEGattAttributes.CLIENT_CHARACTERISTIC_CONFIGURATION);

          mGattChar = aMGattService.getCharacteristic(char_uuid);

          if (desc != null) {
            if ((aMGattService.getCharacteristic(char_uuid).getProperties() &
                BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
              desc.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            } else {
              desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            }
            currentBluetoothGatt.writeDescriptor(desc);
          }
          if (mGattChar != null) {
            currentBluetoothGatt.setCharacteristicNotification(mGattChar, true);
            isCharRead = currentBluetoothGatt.readCharacteristic(mGattChar);
          }
          break;
        }
      }
    }

    if (isCharRead) {
      LogMessage("Read Character Successfully.", "i");
    } else {
      LogMessage("Read Character Fail.", "i");
    }
  }

  // Write characteristic based on uuid
  private void writeChar(UUID ser_uuid, UUID char_uuid, int value, int format, int offset) {
    if (isServiceRead && !mGattService.isEmpty()) {
      for (BluetoothGattService aMGattService : mGattService) {
        if (aMGattService.getUuid().equals(ser_uuid)) {
          mGattChar = aMGattService.getCharacteristic(char_uuid);
          if (mGattChar != null) {
            mGattChar.setValue(value, format, offset);
            isCharWrite = currentBluetoothGatt.writeCharacteristic(mGattChar);
          }
          break;
        }
      }
    }

    if (isCharWrite) {
      LogMessage("Write Gatt Characteristic Successfully", "i");
    } else {
      LogMessage("Write Gatt Characteristic Fail", "e");
    }
  }

  private void writeChar(UUID ser_uuid, UUID char_uuid, byte[] value) {
    if (isServiceRead && !mGattService.isEmpty()) {
      for (int i = 0; i < mGattService.size(); i++) {
        if (mGattService.get(i).getUuid().equals(ser_uuid)) {
          mGattChar = mGattService.get(i).getCharacteristic(char_uuid);
          if (mGattChar != null) {
            mGattChar.setValue(value);
            isCharWrite = currentBluetoothGatt.writeCharacteristic(mGattChar);
          }
          break;
        }
      }
    }

    if (isCharWrite) {
      LogMessage("Write Gatt Characteristic Successfully", "i");
    } else {
      LogMessage("Write Gatt Characteristic Fail", "e");
    }
  }

  private void writeChar(UUID ser_uuid, UUID char_uuid, String value) {
    if (isServiceRead && !mGattService.isEmpty()) {
      for (int i = 0; i < mGattService.size(); i++) {
        if (mGattService.get(i).getUuid().equals(ser_uuid)) {
          mGattChar = mGattService.get(i).getCharacteristic(char_uuid);
          if (mGattChar != null) {
            mGattChar.setValue(value);
            isCharWrite = currentBluetoothGatt.writeCharacteristic(mGattChar);
          }
          break;
        }
      }
    }

    if (isCharWrite) {
      LogMessage("Write Gatt Characteristic Successfully", "i");
    } else {
      LogMessage("Write Gatt Characteristic Fail", "e");
    }
  }

  private BluetoothAdapter.LeScanCallback mLeScanCallback;

    {
      mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
          @Override
          public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  isScanning = true;
                  addDevice(device, rssi, scanRecord);
                }
              });
          }
        };
    }

  private BluetoothGattCallback initCallBack(BluetoothGattCallback newGattCallback) {
    return this.mGattCallback;
  }

  private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
      if (newState == BluetoothProfile.STATE_CONNECTED) {
        isConnected = true;
        gatt.discoverServices();
        gatt.readRemoteRssi();
        Connected();
      }

      if (newState == BluetoothProfile.STATE_DISCONNECTED) {
        isConnected = false;
      }
    }

    @Override
    // New services discovered
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
      if (status == BluetoothGatt.GATT_SUCCESS) {
        mGattService = gatt.getServices();
        isServiceRead = true;
      }
    }

    @Override
    // Result of a characteristic read operation
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
      if (status == BluetoothGatt.GATT_SUCCESS) {
        data = characteristic.getValue();
        intValue = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, intOffset);
        stringValue = characteristic.getStringValue(strOffset);
        floatValue = characteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_FLOAT, floatOffset);
        byteValue = "";
        for (byte i : data) {
          byteValue += i;
        }
        isCharRead = true;
        ByteValueRead(byteValue);
        IntValueRead(intValue);
        StringValueRead(stringValue);
        FloatValueRead(floatValue);
      }
    }

    @Override
    // Result of a characteristic read operation is changed
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
      data = characteristic.getValue();
      LogMessage("dataLength: " + data.length, "i");
      switch (charType) {
        case 0:
          byteValue = "";
          for (byte i : data) {
            byteValue += i;
          }
          LogMessage("byteValue: " + byteValue, "i");
          ByteValueChanged(byteValue);
          break;
        case 1:
          intValue = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, intOffset);
          LogMessage("intValue: " + intValue, "i");
          IntValueChanged(intValue);
          break;
        case 2:
          stringValue = characteristic.getStringValue(strOffset);
          LogMessage("stringValue: " + stringValue, "i");
          StringValueChanged(stringValue);
          break;
        case 3:
          if (data.length == 1) {
            floatValue = (float) (data[0] * Math.pow(10, 0));
          } else if (data.length == 2 || data.length == 3) {
            floatValue = characteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_SFLOAT, floatOffset);
          } else {
            floatValue = characteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_FLOAT, floatOffset);
          }
          LogMessage("floatValue: " + floatValue, "i");
          FloatValueChanged(floatValue);
        default:
          byteValue = "";
          for (byte i : data) {
            byteValue += i;
          }
          LogMessage("byteValue: " + byteValue, "i");
          ByteValueChanged(byteValue);
          break;
      }
      isCharRead = true;
    }

    @Override
    // set value of characteristic
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
      LogMessage("Write Characteristic Successfully.", "i");
      ValueWrite();
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
      descriptorValue = descriptor.getValue();
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
      LogMessage("Write Descriptor Successfully.", "i");
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
      device_rssi = rssi;
      RssiChanged(device_rssi);
    }
  };

  /**
   * Function that stops the Bluetooth LE from scanning for advertisements
   */
  private void stopAdvertisementScanning() {
    LogMessage("Stopping advertisement scanning.", "i");
    mBluetoothLeScanner.stopScan(mScanCallback);
  }

  /**
   * Function that takes in a list of Uuid's and converts them to Strings
   * Input: List serviceUuids - a List containing ParcelUuid types
   * Return: List containing String types representing the Uuid's
   */
  private List<String> stringifyServiceList(List<ParcelUuid> serviceUuids) {
    List<String> deviceServices = new ArrayList<String>();
    for (ParcelUuid serviceUuid : serviceUuids) {
      deviceServices.add(serviceUuid.toString());
    }
    return deviceServices;
  }

  /**
   * Callback function called when a Bluetooth LE Advertisement is found
   */
  private ScanCallback mScanCallback = new ScanCallback() {
    @Override
    public void onScanResult(int callbackType, ScanResult result) {
      super.onScanResult(callbackType, result);

      if (result == null || result.getDevice() == null || TextUtils.isEmpty(result.getDevice().getName())) {
        return;
      }

      StringBuilder builder = new StringBuilder(result.getDevice().getName());

      String advertiserAddress = result.getDevice().getAddress();
      advertiserAddresses.add(advertiserAddress);
      scannedAdvertisers.put(advertiserAddress, result);
      scannedAdvertiserNames.add(result.getDevice().getName());
      nameToAddress.put(result.getDevice().getName(), advertiserAddress);
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
      super.onBatchScanResults(results);
    }

    @Override
    public void onScanFailed(int errorCode) {
      LogMessage("Discovery onScanFailed: " + errorCode, "e");
      super.onScanFailed(errorCode);
    }
  };
}
