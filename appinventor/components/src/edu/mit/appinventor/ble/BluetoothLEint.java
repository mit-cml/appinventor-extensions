// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2015-2016 MIT, All rights reserved
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
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Handler;
import android.os.ParcelUuid;

import android.text.TextUtils;

import android.util.Log;

import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.util.SdkLevel;

import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

/**
 * The internal implementation of the BluetoothLE component.
 *
 * @author Andrew McKinney (mckinney@mit.edu)
 * @author Cristhian Ulloa (cristhian2ulloa@gmail.com)
 * @author tiffanyle (le.tiffanya@gmail.com)
 * @author William Byrne (will2596@gmail.com) (refactoring, bug fixes,
 *                                             error handling, and UUID validation)
 */

final class BluetoothLEint {

  /**
   * Class representing an action on the device's Bluetooth hardware
   *
   * @param <T> the return type for this action
   */
  private abstract class BLEAction<T> implements ActivityResultListener {
    private final int requestEnableBT;
    private final String functionName;

    BLEAction(String functionName) {
      this.requestEnableBT = container.$form().registerForActivityResult(this);
      this.functionName = functionName;
    }

    public abstract T action();

    public final T run() {
      // Determine whether we are usable on this device
      if (!container.$form().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
        signalError(functionName, ERROR_BLUETOOTH_LE_NOT_SUPPORTED);
        return null;
      }

      if (SdkLevel.getLevel() < SdkLevel.LEVEL_LOLLIPOP) {
        signalError(functionName, ERROR_API_LEVEL_TOO_LOW);
        return null;
      }

      return action();
    }

    final BluetoothAdapter obtainBluetoothAdapter() {
      final BluetoothManager bluetoothManager = (BluetoothManager) activity
          .getSystemService(Context.BLUETOOTH_SERVICE);
      BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

      if (bluetoothAdapter != null) {
        if (!bluetoothAdapter.isEnabled()) {
          Log.i(LOG_TAG, "Bluetooth is not enabled, attempting to enable now...");
          // Technically, we have the BLUETOOTH_ADMIN permission and could simply
          // call bluetoothAdapter.enable(), but doing so is intrusive to and
          // possibly unwanted by the user. Instead, we start an activity to enable
          // Bluetooth and listen for the result.
          activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), requestEnableBT);
        }
      } else {
        signalError(functionName, ERROR_BLUETOOTH_LE_NOT_SUPPORTED);
      }

      return bluetoothAdapter;
    }

    /**
     * Callback method to get the result returned by the implicit intent activity
     * for enabling Bluetooth.
     *
     * @param requestCode a code identifying the request.
     * @param resultCode a code specifying success or failure of the activity
     * @param data the returned data, in this case an Intent whose data field
     *        contains the selected item.
     */
    @Override
    public void resultReturned(int requestCode, int resultCode, Intent data) {
      if(requestCode == requestEnableBT) {
        if (resultCode == Activity.RESULT_OK) {
          // Now that Bluetooth is enabled, we attempt to run the action again.
          run();
        } else if (resultCode == Activity.RESULT_CANCELED) {
          signalError(functionName, ERROR_BLUETOOTH_LE_NOT_ENABLED);
        }
      }
    }
  }

  /**
   * Reference to our "outer" class
   */

  BluetoothLE outer;
  ComponentContainer container;

  /**
   * Constants
   */
  private static final String LOG_TAG = "BluetoothLEint";
  private static final Map<Integer, String> errorMessages;
  private static final int ERROR_BLUETOOTH_LE_NOT_SUPPORTED = 9001;
  private static final int ERROR_BLUETOOTH_LE_NOT_ENABLED = 9002;
  private static final int ERROR_API_LEVEL_TOO_LOW = 9003;
  private static final int ERROR_NO_DEVICE_SCAN_IN_PROGRESS = 9004;
  private static final int ERROR_NOT_CURRENTLY_CONNECTED = 9005;
  private static final int ERROR_INDEX_OUT_OF_BOUNDS = 9006;
  private static final int ERROR_DEVICE_LIST_EMPTY = 9007;
  private static final int ERROR_INVALID_UUID_CHARACTERS = 9008;
  private static final int ERROR_INVALID_UUID_FORMAT = 9009;
  private static final int ERROR_ADVERTISEMENTS_NOT_SUPPORTED = 9010;

  static {
    errorMessages = new HashMap<Integer, String>();
    errorMessages.put(ERROR_BLUETOOTH_LE_NOT_SUPPORTED,
        "BluetoothLE is not supported on your phone's hardware!");
    errorMessages.put(ERROR_BLUETOOTH_LE_NOT_ENABLED,
        "BluetoothLE is not enabled!");
    errorMessages.put(ERROR_API_LEVEL_TOO_LOW,
        "BluetoothLE requires Android 5.0 or newer!");
    errorMessages.put(ERROR_NO_DEVICE_SCAN_IN_PROGRESS,
        "StopScan cannot be called before StartScan! There is no scan currently in progress.");
    errorMessages.put(ERROR_NOT_CURRENTLY_CONNECTED,
        "Disconnect cannot be called before you are connected! There is no Bluetooth LE device currently connected.");
    errorMessages.put(ERROR_INDEX_OUT_OF_BOUNDS,
        "Block %1s attempted to access %2s with an invalid index. Index out of bounds!");
    errorMessages.put(ERROR_DEVICE_LIST_EMPTY,
        "You cannot connect to a device when the device list is empty! Try scanning again.");
    errorMessages.put(ERROR_INVALID_UUID_CHARACTERS,
        "%1s UUID string in block %2s contains invalid characters! " +
            "Try typing it in again and rebuilding your app.");
    errorMessages.put(ERROR_INVALID_UUID_FORMAT,
        "%1s UUID string in block %2s does not use the proper format! " +
            "Try typing it in again and rebuilding your app.");
    errorMessages.put(ERROR_ADVERTISEMENTS_NOT_SUPPORTED,
        "Bluetooth Advertisements not supported!");
  }

  /**
   * Basic Variables
   */
  private final Activity activity;
  private BluetoothLeScanner mBluetoothLeDeviceScanner;
  private BluetoothGatt mBluetoothGatt;
  private int device_rssi = 0;
  private final Handler uiThread;

  /**
   * BluetoothLE Device Scanning and Connection Callbacks
   */
  private ScanCallback mDeviceScanCallback;
  private BluetoothGattCallback mGattCallback;

  /**
   * Advertising and Advertisement Scanning Callbacks
   */
  private ScanCallback mAdvertisementScanCallback; // Entered when a BLE Advertisement is found
  private AdvertiseCallback mAdvertiseCallback; // Entered when Advertising is started

  /**
   * BluetoothLE Info List
   */
  //TODO(Will): Delete the gattMap variable when DisconnectWithAddress is removed
  private HashMap<String, BluetoothGatt> gattMap;
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
  private boolean isScanning = false;
  private boolean isConnected = false;
  private boolean isCharRead = false;
  private boolean isCharWritten = false;
  private boolean isServiceRead = false;

  /**
   * GATT values
   */
  private int battery = -1;
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

  /**
   * Enum describing the data type of a characteristic.
   */
  private enum CharType {
    BYTE, INT, STRING, FLOAT
  }

  // The data type of a characteristic: Byte, Int, String, or Float.
  private CharType charType = CharType.BYTE;

  /**
   * Advertising Support
   */
  private BluetoothLeScanner mBluetoothLeAdvertisementScanner;
  private Handler mHandler = new Handler();
  private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
  private long SCAN_PERIOD = 5000;
  private String advertisementScanResult = "";
  private List<String> scannedAdvertiserNames = new ArrayList<String>();
  private HashMap<String, ScanResult> scannedAdvertisers = new HashMap<String, ScanResult>();
  private List<String> advertiserAddresses = new ArrayList<String>();
  private HashMap<String, String> nameToAddress = new HashMap<String, String>();
  private boolean isAdvertising = false;

  BluetoothLEint(BluetoothLE outer, final Activity activity, ComponentContainer container) {

    this.outer = outer;
    this.activity = activity;
    this.container = container;

    this.mLeDevices = new ArrayList<BluetoothDevice>();
    this.mGattService = new ArrayList<BluetoothGattService>();
    this.gattChars = new ArrayList<BluetoothGattCharacteristic>();
    this.mLeDeviceRssi = new HashMap<BluetoothDevice, Integer>();
    this.gattMap = new HashMap<String, BluetoothGatt>();
    this.uiThread = new Handler();

    this.mDeviceScanCallback = new ScanCallback() {
      @Override
      public void onScanResult(final int callbackType, final ScanResult scanResult) {
        super.onScanResult(callbackType, scanResult);

        if (scanResult == null || scanResult.getDevice() == null) {
          return;
        }

        uiThread.post(new Runnable() {
          @Override
          public void run() {
            isScanning = true;
            addDevice(scanResult.getDevice(), scanResult.getRssi());
          }
        });
      }
      @Override
      public void onBatchScanResults(List<ScanResult> results) {
        super.onBatchScanResults(results);
      }

      @Override
      public void onScanFailed(int errorCode) {
        switch(errorCode) {
          case SCAN_FAILED_ALREADY_STARTED:
            Log.e(LOG_TAG, "Device Scan failed. There is already a scan in progress.");
            break;
          default:
            Log.e(LOG_TAG, "Device Scan failed due to an internal error. Error Code: " + errorCode);
        }
        super.onScanFailed(errorCode);
      }
    };

    this.mGattCallback = new BluetoothGattCallback() {
      @Override
      public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        if (newState == BluetoothProfile.STATE_CONNECTED) {
          isConnected = true;
          gatt.discoverServices();
          gatt.readRemoteRssi();
          Connected();
          Log.i(LOG_TAG, "Connect successful.");
        }

        if (newState == BluetoothProfile.STATE_DISCONNECTED) {
          isConnected = false;
          mBluetoothGatt.close();
          mBluetoothGatt = null;
          Disconnected();
          Log.i(LOG_TAG, "Disconnect successful.");
        }

        Log.i(LOG_TAG, "onConnectionStateChange fired with status: " + status);
        Log.i(LOG_TAG, "onConnectionStateChange fired with newState: " + newState);
      }

      @Override
      // New services discovered
      public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
          Log.i(LOG_TAG, "Services Discovered!");
          mGattService = gatt.getServices();
          isServiceRead = true;
        }
        Log.i(LOG_TAG, "onServicesDiscovered fired with status: " + status);
      }

      @Override
      // Result of a characteristic read operation
      public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
          data = characteristic.getValue();
          Log.i(LOG_TAG, "dataLength: " + data.length);
          switch(charType) {
            case BYTE:
              byteValue = "";
              for (byte i : data) {
                byteValue += i;
              }
              Log.i(LOG_TAG, "byteValue: " + byteValue);
              ByteValueRead(byteValue);
              break;
            case INT:
              intValue = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, intOffset);
              Log.i(LOG_TAG, "intValue: " + intValue);
              IntValueRead(intValue);
              break;
            case STRING:
              stringValue = characteristic.getStringValue(strOffset);
              Log.i(LOG_TAG, "stringValue: " + stringValue);
              StringValueRead(stringValue);
              break;
            case FLOAT:
              floatValue = characteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_FLOAT, floatOffset);
              Log.i(LOG_TAG, "floatValue: " + floatValue);
              FloatValueRead(floatValue);
          }

          isCharRead = true;
        }
      }

      @Override
      // Result of a characteristic read operation is changed
      public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        data = characteristic.getValue();
        Log.i(LOG_TAG, "dataLength: " + data.length);
        Log.i(LOG_TAG, "dataValue: " + Arrays.toString(data));
        switch (charType) {
          case BYTE:
            byteValue = "";
            for (byte i : data) {
              byteValue += i;
            }
            Log.i(LOG_TAG, "byteValue: " + byteValue);
            ByteValueChanged(byteValue);
            break;
          case INT:
            intValue = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, intOffset);
            Log.i(LOG_TAG, "intValue: " + intValue);
            IntValueChanged(intValue);
            break;
          case STRING:
            stringValue = characteristic.getStringValue(strOffset);
            Log.i(LOG_TAG, "stringValue: " + stringValue);
            StringValueChanged(stringValue);
            break;
          case FLOAT:
            if (data.length == 1) {
              floatValue = (float) (data[0] * Math.pow(10, 0));
            } else if (data.length == 2 || data.length == 3) {
              floatValue = characteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_SFLOAT, floatOffset);
            } else {
              floatValue = characteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_FLOAT, floatOffset);
            }
            Log.i(LOG_TAG, "floatValue: " + floatValue);
            FloatValueChanged(floatValue);
        }
        isCharRead = true;
      }

      @Override
      // set value of characteristic
      public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.i(LOG_TAG, "Write Characteristic Successfully.");
        ValueWrite();
      }

      @Override
      public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        descriptorValue = descriptor.getValue();
      }

      @Override
      public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Log.i(LOG_TAG, "Write Descriptor Successfully.");
      }

      @Override
      public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        device_rssi = rssi;
        RssiChanged(device_rssi);
      }
    };

    mAdvertisementScanCallback = new ScanCallback() {
      @Override
      public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);

        if (result == null || result.getDevice() == null || TextUtils.isEmpty(result.getDevice().getName())) {
          return;
        }

        String advertiserAddress = result.getDevice().getAddress();
        String advertiserName = result.getDevice().getName();
        advertiserAddresses.add(advertiserAddress);
        scannedAdvertisers.put(advertiserAddress, result);
        scannedAdvertiserNames.add(advertiserName);
        nameToAddress.put(advertiserName, advertiserAddress);
      }

      @Override
      public void onBatchScanResults(List<ScanResult> results) {
        super.onBatchScanResults(results);
      }

      @Override
      public void onScanFailed(int errorCode) {
        Log.e(LOG_TAG, "Advertisement onScanFailed: " + errorCode);
        ///signalError(); fix me
        super.onScanFailed(errorCode);
      }
    };
  }

  void StartScanning() {
    new BLEAction<Void>("StartScanning") {
      @Override
      public Void action() {
        if (!mLeDevices.isEmpty()) {
          mLeDevices.clear();
          mLeDeviceRssi.clear();
        }

        BluetoothAdapter btAdapter = obtainBluetoothAdapter();

        if (btAdapter != null) {
          if (mBluetoothLeDeviceScanner == null) {
            mBluetoothLeDeviceScanner = btAdapter.getBluetoothLeScanner();
          }

          ScanSettings settings = new ScanSettings.Builder()
              .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
              .build();

          List<ScanFilter> filters = new ArrayList<ScanFilter>();
          ScanFilter filter = new ScanFilter.Builder().build();
          filters.add(filter);

          mBluetoothLeDeviceScanner.startScan(filters, settings, mDeviceScanCallback);
          Log.i(LOG_TAG, "StartScanning successful.");
        }

        return null;
      }
    }.run();
  }

  void StopScanning() {
    new BLEAction<Void>("StopScanning") {
      @Override
      public Void action() {
        if (mBluetoothLeDeviceScanner != null) {
          // After carefully examining the source code for android.bluetooth.le.BluetoothLeScanner, it is clear
          // that the ScanCallback parameter of BluetoothLeScanner.stopScan(ScanCallback) is only used as a key to
          // identify the current scan. It is never fired.
          mBluetoothLeDeviceScanner.stopScan(mDeviceScanCallback);
          Log.i(LOG_TAG, "StopScanning successful.");
        } else {
          signalError("StopScanning", ERROR_NO_DEVICE_SCAN_IN_PROGRESS);
        }
        return null;
      }
    }.run();
  }

  void Connect(final int index) {
    new BLEAction<Void>("Connect") {
      @Override
      public Void action() {
        try {
          if (!mLeDevices.isEmpty()) {
            mBluetoothGatt = mLeDevices.get(index - 1).connectGatt(activity, false, mGattCallback);
            if (mBluetoothGatt != null) {
              //TODO(Will): Delete the puts to this map
              gattMap.put(mLeDevices.get(index - 1).toString(), mBluetoothGatt);
            } else {
              Log.e(LOG_TAG, "Connect failed.");
            }
          } else {
            signalError("Connect", ERROR_DEVICE_LIST_EMPTY);
          }
        } catch (IndexOutOfBoundsException e) {
          signalError("Connect", ERROR_INDEX_OUT_OF_BOUNDS, "Connect", "DeviceList");
        }
        return null;
      }
    }.run();

  }

  void ConnectWithAddress(final String address) {
    new BLEAction<Void>("ConnectWithAddress") {
      @Override
      public Void action() {
        try {
          if (!mLeDevices.isEmpty()) {
            for (BluetoothDevice bluetoothDevice : mLeDevices) {
              if (bluetoothDevice.getAddress().equals(address)) {
                mBluetoothGatt = bluetoothDevice.connectGatt(activity, false, mGattCallback);
                if (mBluetoothGatt != null) {
                  //TODO(Will): Delete the puts to this map
                  gattMap.put(bluetoothDevice.getAddress(), mBluetoothGatt);
                  Log.i(LOG_TAG, "ConnectWithAddress successful.");
                  break;
                } else {
                  Log.e(LOG_TAG, "ConnectWithAddress failed.");
                }
              }
            }
          } else {
            signalError("ConnectWithAddress", ERROR_DEVICE_LIST_EMPTY);
          }
        } catch (IndexOutOfBoundsException e) {
          signalError("ConnectWithAddress", ERROR_INDEX_OUT_OF_BOUNDS, "ConnectWithAddress", "DeviceList");
        }
        return null;
      }
    }.run();
  }

  void Disconnect() {
    new BLEAction<Void>("Disconnect") {
      @Override
      public Void action() {
        if(mBluetoothGatt != null) {
          mBluetoothGatt.disconnect();
        } else {
          signalError("Disconnect", ERROR_NOT_CURRENTLY_CONNECTED);
        }
        return null;
      }
    }.run();
  }

  //TODO(Will): Delete this
  void DisconnectWithAddress(final String address) {
    new BLEAction<Void>("DisconnectWithAddress") {
      @Override
      public Void action() {
        if (gattMap.containsKey(address)) {
          BluetoothGatt addressedGatt = gattMap.get(address);
          addressedGatt.disconnect();
          gattMap.remove(address);
          Log.i(LOG_TAG, "Disconnect successful.");
        } else {
          // signalError here
          Log.e(LOG_TAG, "Disconnect failed. No such address in the list.");
        }
        return null;
      }
    }.run();
  }

  void WriteStringValue(final String service_uuid, final String characteristic_uuid, final String value) {
    new BLEAction<Void>("WriteStringValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "WriteStringValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "WriteStringValue"))
          return null;

        Log.i(LOG_TAG, "stringValue: " + value);

        writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), value);
        return null;
      }
    }.run();
  }

  void WriteIntValue(final String service_uuid, final String characteristic_uuid, final int value, final int offset) {
    new BLEAction<Void>("WriteIntValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "WriteIntValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "WriteIntValue"))
          return null;

        Log.i(LOG_TAG, "intValue: " + value);

        int[] payload = {value, BluetoothGattCharacteristic.FORMAT_SINT32, offset};
        writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), payload);
        return null;
      }
    }.run();
  }

  void WriteFloatValue(final String service_uuid, final String characteristic_uuid, final float value, final int offset) {
    new BLEAction<Void>("WriteFloatValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "WriteFloatValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "WriteFloatValue"))
          return null;

        int floatRep = Float.floatToIntBits(value);
        Log.i(LOG_TAG, "floatRepresentation: " + floatRep);

        int[] payload = {floatRep, BluetoothGattCharacteristic.FORMAT_SINT32, offset};
        writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), payload);
        return null;
      }
    }.run();
  }

  void WriteByteValue(final String service_uuid, final String characteristic_uuid, final String value) {
    new BLEAction<Void>("WriteByteValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "WriteByteValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "WriteByteValue"))
          return null;

        byte[] bytes = value.getBytes();
        Log.i(LOG_TAG, "byteValue: " + Arrays.toString(bytes));

        writeChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid), bytes);
        return null;
      }
    }.run();

  }

  void ReadIntValue(final String service_uuid, final String characteristic_uuid, final int intOffset) {
    new BLEAction<Void>("ReadIntValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "ReadIntValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "ReadIntValue"))
          return null;

        charType = CharType.INT;
        BluetoothLEint.this.intOffset = intOffset;
        readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
        return null;
      }
    }.run();
  }

  void ReadStringValue(final String service_uuid, final String characteristic_uuid, final int strOffset) {
    new BLEAction<Void>("ReadStringValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "ReadStringValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "ReadStringValue"))
          return null;

        charType = CharType.STRING;
        BluetoothLEint.this.strOffset = strOffset;
        readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
        return null;
      }
    }.run();
  }

  void ReadFloatValue(final String service_uuid, final String characteristic_uuid, final int floatOffset) {
    new BLEAction<Void>("ReadFloatValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "ReadFloatValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "ReadFloatValue"))
          return null;

        charType = CharType.FLOAT;
        BluetoothLEint.this.floatOffset = floatOffset;
        readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
        return null;
      }
    }.run();
  }

  void ReadByteValue(final String service_uuid, final String characteristic_uuid) {
    new BLEAction<Void>("ReadByteValue") {
      @Override
      public Void action() {
        if (!validateUUID(service_uuid, "Service", "ReadByteValue")
            || !validateUUID(characteristic_uuid, "Characteristic", "ReadByteValue"))
          return null;

        charType = CharType.BYTE;
        readChar(UUID.fromString(service_uuid), UUID.fromString(characteristic_uuid));
        return null;
      }
    }.run();
  }

  int FoundDeviceRssi(final int index) {
    Integer result = new BLEAction<Integer>("FoundDeviceRssi") {
      @Override
      public Integer action() {
        if (index <= mLeDevices.size()) {
          try {
            if(!mLeDevices.isEmpty()) {
              return mLeDeviceRssi.get(mLeDevices.get(index - 1));
            } else {
              signalError("FoundDeviceRssi", ERROR_DEVICE_LIST_EMPTY);
            }
          } catch(IndexOutOfBoundsException e) {
            signalError("FoundDeviceRssi", ERROR_INDEX_OUT_OF_BOUNDS, "FoundDeviceRssi", "DeviceList");
          }
        }
        return null;
      }
    }.run();

    return (result != null) ? result : -1;
  }

  String FoundDeviceName(int index) {
    if (index <= mLeDevices.size()) {
      Log.i(LOG_TAG, "Device Name is found");
      return mLeDevices.get(index - 1).getName();
    } else {
      Log.e(LOG_TAG, "Device Name isn't found");
      return null;
    }
  }

  String FoundDeviceAddress(int index) {
    if (index <= mLeDevices.size()) {
      Log.i(LOG_TAG, "Device Address is found");
      return mLeDevices.get(index - 1).getAddress();
    } else {
      Log.e(LOG_TAG, "Device Address is found");
      return "";
    }
  }

  void StartAdvertising(final String inData, final String serviceUuid) {
    new BLEAction<Void>("StartAdvertising"){
      @Override
      public Void action() {
        // Ensure that the current Bluetooth Adapter supports Advertising.
        if (!mBluetoothAdapter.isMultipleAdvertisementSupported()) {
          Log.i(LOG_TAG, "Adapter does not support Bluetooth Advertisements.");
          signalError("StartAdvertising", ERROR_ADVERTISEMENTS_NOT_SUPPORTED);
          return null;
        }

        if (!validateUUID(serviceUuid, "Service", "StartAdvertising"))
          return null;

        // Create a scan callback if it does not already exist. If it does, you're already advertising.
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();

        AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
          @Override
          public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            isAdvertising = true;
            super.onStartSuccess(settingsInEffect);
          }

          @Override
          public void onStartFailure(int errorCode) {
            Log.e(LOG_TAG, "Advertising onStartFailure: " + errorCode);
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
          mAdvertiseCallback = advertisingCallback;

          if (mBluetoothLeAdvertiser != null) {
            mBluetoothLeAdvertiser.startAdvertising(advSettings, advData, mAdvertiseCallback);
          }
        }

        Log.i(LOG_TAG, "StartScanningAdvertisements Successfully.");
      }
    }.run();
  }

  void StopAdvertising() {
    new BLEAction<Void>("StopAdvertising"){
      @Override
      public Void action() {
        Log.i(LOG_TAG, "Stopping BLE Advertising");
        if (mBluetoothLeAdvertiser != null) {
          mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
          isAdvertising = false;
          mAdvertiseCallback = null;
        }
        return null;
      }
    }.run();
  }

  // TODO(Will): Merge this timed scanning functionality with the StartScan/StopScan methods above.
  void ScanAdvertisements(final long scanPeriod) {
    new BLEAction<Void>("ScanAdvertisements") {
      @Override
      public Void action() {
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
            StopScanningAdvertisements();
          }
        }, scanPeriod);

        if (mBluetoothAdapter != null) {
          mBluetoothLeAdvertisementScanner = mBluetoothAdapter.getBluetoothLeScanner();

          if (mAdvertisementScanCallback != null) {

            if (mBluetoothLeAdvertisementScanner != null) {
              ScanSettings settings = new ScanSettings.Builder()
                      .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                      .build();

              List<ScanFilter> filters = new ArrayList<ScanFilter>();
              ScanFilter filter = new ScanFilter.Builder()
                      .build();
              // NOTE: removed service uuid from filter:
              // ".setServiceUuid( new ParcelUuid(UUID.fromString( "0000b81d-0000-1000-8000-00805f9b34fb" ) ) )""

              filters.add(filter);

              if (settings != null) {
                mBluetoothLeAdvertisementScanner.startScan(filters, settings, mAdvertisementScanCallback);
              } else {
                Log.i(LOG_TAG, "settings or filters are null.");
              }
            } else {
              Log.i(LOG_TAG, "Bluetooth LE scanner is null.");
            }
          } else {
            Log.i(LOG_TAG, "mAdvertisementScanCallback is null.");
          }
        } else {
          Log.i(LOG_TAG, "No bluetooth adapter found.");
        }
        return null;
      }
    }.run();
  }

  // TODO(Will): Delete this.
  void StopScanningAdvertisements() {
    new BLEAction<Void>("StopScanningAdvertisements"){
      @Override
      public Void action() {
        Log.i(LOG_TAG, "Stopping BLE Advertisement Scan.");
        mBluetoothLeAdvertisementScanner.stopScan(mAdvertisementScanCallback);
        return null;
      }
    }.run();
  }

  // TODO(Will): Delete this.
  String GetAdvertisementData(String deviceAddress, String serviceUuid) {
    if (!validateUUID(serviceUuid, "Service", "GetAdvertisementData"))
      return "";

    return "" + Arrays.toString(scannedAdvertisers.get(deviceAddress).getScanRecord()
        .getServiceData().get(ParcelUuid.fromString(serviceUuid)));
  }

  // TODO(Will): Delete this.
  String GetAdvertiserAddress(String deviceName) {
    return nameToAddress.get(deviceName);
  }

  List<String> GetAdvertiserServiceUuids(String deviceAddress) {
    return BLEUtil.stringifyParcelUuids(scannedAdvertisers.get(deviceAddress)
        .getScanRecord().getServiceUuids());
  }

  String BatteryValue() {
    if (isCharRead) {
      return Integer.toString(battery);
    } else {
      return "Cannot Read Battery Level";
    }
  }

  int TxPower() {
    return txPower;
  }

  boolean IsDeviceConnected() {
    return isConnected;
  }

  String DeviceList() {
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

  String ConnectedDeviceRssi() {
    return Integer.toString(device_rssi);
  }

  long AdvertisementScanPeriod() {
    return SCAN_PERIOD;
  }

  List<String> GetAdvertiserNames() {
    return scannedAdvertiserNames;
  }

  List<String> GetAdvertiserAddresses() {
    return advertiserAddresses;
  }

  boolean IsDeviceAdvertising() {
    return isAdvertising;
  }

  private void Connected() {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "Connected");
      }
    });
  }

  private void Disconnected() {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "Disconnected");
      }
    });
  }

  private void RssiChanged(final int device_rssi) {
    uiThread.postDelayed(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "RssiChanged", device_rssi);
      }
    }, 1000);
  }

  private void DeviceFound() {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "DeviceFound");
      }
    });
  }

  private void ByteValueRead(final String byteValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "ByteValueRead", byteValue);
      }
    });
  }

  private void IntValueRead(final int intValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "IntValueRead", intValue);
      }
    });
  }

  private void StringValueRead(final String stringValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "StringValueRead", stringValue);
      }
    });
  }

  private void FloatValueRead(final float floatValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "FloatValueRead", floatValue);
      }
    });
  }

  private void ByteValueChanged(final String byteValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "ByteValueChanged", byteValue);
      }
    });
  }

  private void IntValueChanged(final int intValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "IntValueChanged", intValue);
      }
    });
  }

  private void FloatValueChanged(final float floatValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "FloatValueChanged", floatValue);
      }
    });
  }

  private void StringValueChanged(final String stringValue) {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "StringValueChanged", stringValue);
      }
    });
  }

  private void ValueWrite() {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(outer, "ValueWrite");
      }
    });
  }

  String GetSupportedServices() {
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

  String GetServiceByIndex(int index) {
    return mGattService.get(index - 1).getUuid().toString();
  }

  String GetSupportedCharacteristics() {
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
    for (BluetoothGattCharacteristic gattChar : gattChars) {
      UUID charUUID = gattChar.getUuid();
      String charName = BluetoothLEGattAttributes.lookup(charUUID, unknownCharString);
      charUUIDList += charUUID + " " + charName + ",";
    }
    return charUUIDList;
  }

  String GetCharacteristicByIndex(int index) {
    return gattChars.get(index - 1).getUuid().toString();
  }

  /**
   * Non-static helper functions. For static helpers, see {@link edu.mit.appinventor.ble.BLEUtil}.
   */

  /*
   * Signal that an error has occurred. Since we are an extension, we don't have access to the normal
   * error handling used by built-in App Inventor components. BluetoothLE errors are shown in a dialog
   * rather than an alert for added clarity.
   */
  private void signalError(final String functionName, final int errorNumber, final Object... messageArgs) {
    final String errorMessage = String.format(errorMessages.get(errorNumber), messageArgs);
    Log.e(LOG_TAG, errorMessage);
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        container.$form().ErrorOccurredDialog(BluetoothLEint.this.outer,
            functionName,
            errorNumber,
            errorMessage,
            "BluetoothLE",
            "Dismiss");
      }
    });
  }

  /*
   * Obtain a new Bluetooth adapter instance. This method will return null
   * if Bluetooth LE is unsupported.
   */
  private BluetoothAdapter getBluetoothAdapter(String functionName, OnBluetoothEnabledListener listener) {
    // Determine whether we are usable on this device
    if (!container.$form().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
      signalError(functionName, ERROR_BLUETOOTH_LE_NOT_SUPPORTED);
      return null;
    }

    if (SdkLevel.getLevel() < SdkLevel.LEVEL_LOLLIPOP) {
      signalError(functionName, ERROR_API_LEVEL_TOO_LOW);
      return null;
    }

    final BluetoothManager bluetoothManager = (BluetoothManager) activity
        .getSystemService(Context.BLUETOOTH_SERVICE);
    BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

    if (bluetoothAdapter != null) {
      if (!bluetoothAdapter.isEnabled()) {
        Log.i(LOG_TAG, "Bluetooth is not enabled, attempting to enable now...");
        // Technically, we have the BLUETOOTH_ADMIN permission and could simply
        // call bluetoothAdapter.enable(), but doing so is intrusive to and
        // possibly unwanted by the user. Instead, we start an activity to enable
        // Bluetooth and listen for the result.
        this.setOnBluetoothEnabledListener(listener);
        activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), requestEnableBT);
      }
    } else {
      signalError(functionName, ERROR_BLUETOOTH_LE_NOT_SUPPORTED);
    }
    return bluetoothAdapter;
  }

  /*
   * Ask the user to enable this device's Bluetooth adapter.
   */
  private void enableBluetoothAdapter(String caller, OnBluetoothEnabledListener callback) {
    Log.i(LOG_TAG, "Bluetooth is not enabled, attempting to enable now...");
    // Technically, we have the BLUETOOTH_ADMIN permission and could simply
    // call BluetoothAdapter#enable(), but doing so is intrusive to and
    // possibly unwanted by the user. Instead, we start an activity to enable it.
    activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), requestEnableBT);
  }

  /*
   * Verify that the BluetoothLE extension is supported on this device.
   */
  private boolean isBLESupported(String caller) {
    if (!container.$form().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
      signalError(caller, ERROR_BLUETOOTH_LE_NOT_SUPPORTED);
      return false;
    } else if (SdkLevel.getLevel() < SdkLevel.LEVEL_LOLLIPOP) {
      signalError(caller, ERROR_API_LEVEL_TOO_LOW);
      return false;
    }
    return true;
  }

  // Validates the given UUID and signals the relevant error when it is invalid.
  private boolean validateUUID(String UUID, String type, String callerBlock) {
    if (BLEUtil.hasValidUUIDFormat(UUID)) {
      return true;
    } else if (BLEUtil.hasInvalidUUIDChars(UUID)) {
      signalError(callerBlock, ERROR_INVALID_UUID_CHARACTERS, type, callerBlock);
      return false;
    } else {
      signalError(callerBlock, ERROR_INVALID_UUID_FORMAT, type, callerBlock);
      return false;
    }
  }

  // Set the OnBluetoothEnabledListener.
  private void setOnBluetoothEnabledListener(OnBluetoothEnabledListener listener) {
    this.mBTEnabledListener = listener;
  }

  // Sort the device list by RSSI from highest to lowest
  private List<BluetoothDevice> sortDeviceList(List<BluetoothDevice> deviceList) {
    Collections.sort(deviceList, new Comparator<BluetoothDevice>() {
      @Override
      public int compare(BluetoothDevice device1, BluetoothDevice device2) {
        return mLeDeviceRssi.get(device2) - mLeDeviceRssi.get(device1);
      }
    });
    return deviceList;
  }

  // Used by mLeDeviceScanCallback to add to the device list
  private void addDevice(BluetoothDevice device, int rssi) {
    if (!mLeDevices.contains(device)) {
      mLeDevices.add(device);
      mLeDeviceRssi.put(device, rssi);
      DeviceFound();
    } else {
      mLeDeviceRssi.put(device, rssi);
    }
    RssiChanged(rssi);
  }

  /*
   * Look-up the BluetoothGattCharacteristic with the given service and
   * characteristic UUIDs.
   */
  private BluetoothGattCharacteristic findMGattChar(UUID ser_uuid, UUID char_uuid) {
    Log.i(LOG_TAG, "isServiceRead: " + isServiceRead);
    Log.i(LOG_TAG, "mGattService.isEmpty(): " + mGattService.isEmpty());

    if (isServiceRead && !mGattService.isEmpty()) {
      for (BluetoothGattService aMGattService : mGattService) {
        if (aMGattService.getUuid().equals(ser_uuid)) {
          return aMGattService.getCharacteristic(char_uuid);
        }
      }
    }

    return null;
  }

  // Read the characteristic with the corresponding UUID
  private void readChar(UUID ser_uuid, UUID char_uuid) {
    mGattChar = findMGattChar(ser_uuid, char_uuid);

    if (mGattChar != null) {
      Log.i(LOG_TAG, "mGattChar initialized to " + mGattChar.getUuid().toString());
      BluetoothGattDescriptor desc = mGattChar
          .getDescriptor(BluetoothLEGattAttributes.CLIENT_CHARACTERISTIC_CONFIGURATION);

      if (desc != null) {
        if ((mGattChar.getProperties() &
            BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
          desc.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
        } else {
          desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        }
        mBluetoothGatt.writeDescriptor(desc);
      }

      mBluetoothGatt.setCharacteristicNotification(mGattChar, true);
      isCharRead = mBluetoothGatt.readCharacteristic(mGattChar);
    } else {
      Log.i(LOG_TAG, "mGattChar is null!");
    }

    if (isCharRead) {
      Log.i(LOG_TAG, "Read Character Successfully.");
    } else {
      Log.e(LOG_TAG, "Read Character Fail.");
    }
  }

  // Write the payload to the characteristic with the corresponding UUID
  private void writeChar(UUID ser_uuid, UUID char_uuid, Object payload) {
    mGattChar = findMGattChar(ser_uuid, char_uuid);
    if (mGattChar != null && payload != null) {
      Log.i(LOG_TAG, "mGattChar initialized to " + mGattChar.getUuid().toString());
      // use the appropriate BluetoothGattCharacteristic#setValue() depending on the payload type
      if (payload instanceof int[]) {
        int[] args = (int[]) payload;
        mGattChar.setValue(args[0], args[1], args[2]);
      } else if (payload instanceof byte[]) {
        mGattChar.setValue((byte[]) payload);
      } else if (payload instanceof String) {
        mGattChar.setValue((String) payload);
      } else {
        throw new IllegalArgumentError("Attempted to write to characteristic with unsupported data type.");
      }

      isCharWritten = mBluetoothGatt.writeCharacteristic(mGattChar);
    } else {
      Log.i(LOG_TAG, "mGattChar is null!");
    }

    if (isCharWritten) {
      Log.i(LOG_TAG, "Write Gatt Characteristic Successfully");
    } else {
      Log.e(LOG_TAG, "Write Gatt Characteristic Fail");
    }
  }
}
