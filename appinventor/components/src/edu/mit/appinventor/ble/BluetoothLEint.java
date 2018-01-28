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
import android.os.Looper;
import android.os.ParcelUuid;

import android.text.TextUtils;

import android.util.Log;

import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import edu.mit.appinventor.ble.BluetoothLE.BluetoothConnectionListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import java.util.*;

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
   * BLEOperation provides an abstraction of specific BLE operations that are supported in MIT App
   * Inventor.
   * @author Evan W. Patton (ewpatton@mit.edu)
   */
  public abstract class BLEOperation extends BluetoothGattCallback implements Runnable {
    /**
     * Constant for identifying Bluetooth utf8s data type.
     * @see BluetoothGattCharacteristic
     */
    static final int FORMAT_UTF8S = 1;

    /**
     * Constant for identifying Bluetooth utf16s data type.
     */
    static final int FORMAT_UTF16S = 2;

    /**
     * The characteristic that will be read or provide notifications as part of the operation.
     */
    protected final BluetoothGattCharacteristic characteristic;

    /**
     * The type of the characteristic. See the FORMAT_* constants on
     * {@link BluetoothGattCharacteristic}.
     */
    protected final int type;

    /**
     * Size of the data type within the Bluetooth packet. For example, a byte will have size 1.
     */
    protected final int size;

    /**
     * Flag indicating that the operation still needs to be removed from the
     * {@link #pendingOperations} queue.
     */
    protected boolean needsRemoval = false;

    BLEOperation(BluetoothGattCharacteristic characteristic, int type) {
      this.characteristic = characteristic;
      this.type = type;
      this.size = sizeofT(type);
    }

    /**
     * @return true if the operation is a read (or notify) operation, otherwise false.
     */
    public abstract boolean isRead();

    /**
     * @return true if the operation is a notify, otherwise false.
     */
    public abstract boolean isNotify();

    /**
     * @return true if the operation is a write, otherwise false.
     */
    public abstract boolean isWrite();

    /**
     * Read the attribute encapsulated by the BLEOperation using the specified GATT object.
     * @param gatt Bluetooth General Attribute (GATT) profile target
     */
    public void read(BluetoothGatt gatt) {}

    /**
     * Unsubscribe from the attribute encapsulated by the BLEOperation previously subscribed to
     * using the specified GATT object.
     * @param gatt Bluetooth General Attribute (GATT) profile target
     */
    public void unsubscribe(BluetoothGatt gatt) {}

    /**
     * Determine the size of the value based on the specified Bluetooth format.
     * @return the size, in bytes, of the format. If the format is a string, then -1 will be
     *   returned.
     */
    protected final int sizeofT(int type) {
      switch (type) {
        case BluetoothGattCharacteristic.FORMAT_UINT8:
        case BluetoothGattCharacteristic.FORMAT_SINT8:
          return 1;
        case BluetoothGattCharacteristic.FORMAT_SINT16:
        case BluetoothGattCharacteristic.FORMAT_UINT16:
        case BluetoothGattCharacteristic.FORMAT_SFLOAT:
          return 2;
        case BluetoothGattCharacteristic.FORMAT_SINT32:
        case BluetoothGattCharacteristic.FORMAT_UINT32:
        case BluetoothGattCharacteristic.FORMAT_FLOAT:
          return 4;
        default:
          return -1;  // uitf8s or utf16s
      }
    }

    /**
     * Register the operation on the queue of pending operations.
     */
    protected final void registerPendingOperation() {
      synchronized (pendingOperationsByUuid) {
        final UUID uuid = characteristic.getUuid();
        if (!pendingOperationsByUuid.containsKey(uuid)) {
          pendingOperationsByUuid.put(uuid, new ArrayList<BLEOperation>());
        }
        if (!pendingOperationsByUuid.get(uuid).contains(this)) {
          pendingOperationsByUuid.get(uuid).add(this);
          needsRemoval = true;
        }
      }
    }

  }

  /**
   * Bluetooth read and notify operations extend BLEReadOperation to specify the return type of an
   * operation. For operations that return primitive values, boxed types must be used.
   * @param <T> The return type of a read operation. Typically this is Integer, Float, or String.
   */
  private abstract class BLEReadOperation<T> extends BLEOperation {

    /**
     * Class of the return value's data type. Note that this may not be the same as the data type.
     * For example, byte and short values will be returned as int.
     */
    private final Class<T> mClass;

    /**
     * Flag to indicate that the operation is a notify.
     */
    private boolean notify = false;

    /**
     * Handler to call after reading values.
     */
    private final BluetoothLE.BLEResponseHandler<T> handler;

    /**
     * Delay in milliseconds in the event the operation fails. The delay is backed off as a power
     * of 2 until 10 attempts have failed.
     */
    private int delay = 1;

    BLEReadOperation(Class<T> aClass, BluetoothGattCharacteristic characteristic, int type,
                     BluetoothLE.BLEResponseHandler<T> handler, boolean notify) {
      super(characteristic, type);
      this.mClass = aClass;
      this.handler = handler;
      this.notify = notify;
    }

    @Override
    public boolean isRead() {
      return true;
    }

    @Override
    public boolean isNotify() {
      return notify;
    }

    @Override
    public boolean isWrite() {
      return false;
    }

    @Override
    public void run() {
      if (isNotify()) {
        subscribe(mBluetoothGatt);
      } else {
        read(mBluetoothGatt);
      }
    }

    @Override
    public void read(final BluetoothGatt gatt) {
      if (gatt.readCharacteristic(this.characteristic)) {
        registerPendingOperation();
      } else if (delay > 2000) {
        Log.i(LOG_TAG, "Took too long to schedule read. Treating this as failure.");
      } else {
        Log.d(LOG_TAG, "Unable to read characteristic " + characteristic.getUuid() +
            ". Deferring operation by " + delay + "ms.");
        mHandler.postDelayed(new Runnable() {
          @Override
          public void run() {
            read(gatt);
          }
        }, delay);
        delay *= 2;
      }
    }

    /**
     * Subscribe to the attribute encapsulated by the BLEOperation using the specified GATT object.
     * @param gatt Bluetooth General Attribute (GATT) profile target
     */
    public void subscribe(final BluetoothGatt gatt) {
      notify = true;
      registerPendingOperation();
      BluetoothGattDescriptor desc = characteristic
          .getDescriptor(BluetoothLEGattAttributes.CLIENT_CHARACTERISTIC_CONFIGURATION);
      boolean wroteDescriptor = false;

      if (desc != null) {
        // Prefer notification to indication for characteristics that support both
        if ((characteristic.getProperties() &
            BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
          desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
          desc.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
        }
        gatt.writeDescriptor(desc);
        wroteDescriptor = true;
      }

      if (!gatt.setCharacteristicNotification(characteristic, true)) {
        if (delay > 2000) {
          Log.i(LOG_TAG, "Took too long to subscribe. Treating this as failure.");
          return;
        }
        Log.d(LOG_TAG, "Unable to set characteristic notification for " + characteristic.getUuid()
            + ". Deferring operation by " + delay + "ms.");
        mHandler.postDelayed(new Runnable() {
          @Override
          public void run() {
            subscribe(gatt);
          }
        }, delay);
        delay *= 2;
      } else {
        if (!wroteDescriptor) {
          // no change will happen due to lack of descriptor change
          // so force the next operation.
          if (runPendingOperation(this)) {
            needsRemoval = false;
          }
        }
        Log.d(LOG_TAG, "Subscribed for UUID: " + characteristic.getUuid());
      }
    }

    @Override
    public void unsubscribe(final BluetoothGatt gatt) {
      synchronized (pendingOperationsByUuid) {
        if (gatt.setCharacteristicNotification(characteristic, false)) {
          pendingOperationsByUuid.get(characteristic.getUuid()).remove(this);
          notify = false;
        } else if (delay > 2000) {
          Log.i(LOG_TAG, "Took too long to unsubscribe. Treating this as failure.");
        } else {
          Log.d(LOG_TAG, "setCharacteristicNotification returned false. Deferring operation by " +
              delay + "ms.");
          mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
              unsubscribe(gatt);
            }
          }, delay);
          delay *= 2;
        }
      }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt,
                                        final BluetoothGattCharacteristic characteristic) {
      if (this.characteristic == characteristic) {
        final List<T> data = readCharacteristic();
        onReceive(data);
        Log.d(LOG_TAG, "handler = " + handler);
        if (handler != null) {
          Log.d(LOG_TAG, "Posting handler's onReceive to UI thread");
          mHandler.post(new Runnable() {
            @Override
            public void run() {
              handler.onReceive(characteristic.getService().getUuid().toString(),
                  characteristic.getUuid().toString(), data);
            }
          });
        }
        if (needsRemoval && runPendingOperation(this)) {
          needsRemoval = false;
        }
      } else {
        Log.d(LOG_TAG, "Characteristic did not match");
      }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt,
                                     final BluetoothGattCharacteristic characteristic,
                                     int status) {
      if (this.characteristic == characteristic) {
        try {
          switch (status) {
            case BluetoothGatt.GATT_SUCCESS:
              final List<T> data = readCharacteristic();
              onReceive(data);
              Log.d(LOG_TAG, "handler = " + handler);
              if (handler != null) {
                Log.d(LOG_TAG, "Posting handler's onReceive to UI thread");
                mHandler.post(new Runnable() {
                  @Override
                  public void run() {
                    handler.onReceive(characteristic.getService().getUuid().toString(),
                        characteristic.getUuid().toString(), data);
                  }
                });
              }
              break;
            default:
              // TODO(ewpatton): handle error conditions
              Log.e(LOG_TAG, "Error code " + status + " from characteristic " +
                  characteristic.getUuid());
          }
        } finally {
          pendingOperationsByUuid.get(characteristic.getUuid()).remove(this);
          if (needsRemoval && runPendingOperation(this)) {
            needsRemoval = false;
          }
          Log.d(LOG_TAG, "pendingOperations.size() = " + pendingOperations.size());
          Log.d(LOG_TAG, "pendingOperationsByUuid.size() = " + pendingOperationsByUuid.size());
        }
      }
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, final BluetoothGattDescriptor descriptor,
                                 int status) {
      Log.d(LOG_TAG, "onDescriptorRead: " + descriptor.getCharacteristic().getUuid());
      if (needsRemoval && runPendingOperation(this)) {
        needsRemoval = false;
      }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, final BluetoothGattDescriptor descriptor,
                                    int status) {
      Log.d(LOG_TAG, "onDescriptorWrite: " + descriptor.getCharacteristic().getUuid());
      if (needsRemoval && runPendingOperation(this)) {
        needsRemoval = false;
      }
    }

    /**
     * Read one or more values from the characteristic.
     * @return A list of values read from the characteristic
     */
    private List<T> readCharacteristic() {
      Log.d(LOG_TAG, "Received bytes = " + Arrays.toString(characteristic.getValue()));
      if (type == FORMAT_UTF8S || type == FORMAT_UTF16S) {
        Reader reader = null;
        List<T> result = new ArrayList<T>();
        try {
          reader = new InputStreamReader(new ByteArrayInputStream(characteristic.getValue()),
                                         type == FORMAT_UTF8S ? "UTF-8" : "UTF-16LE");
          StringBuilder sb = new StringBuilder();
          int c;
          while ((c = reader.read()) >= 0) {
            if (c != 0) {
              sb.append(Character.toChars(c));
            } else {
              if (sb.length() > 0) {
                result.add(mClass.cast(sb.toString()));
              }
              sb.setLength(0);
            }
          }
          if (sb.length() > 0) {
            result.add(mClass.cast(sb.toString()));
          }
        } catch(IOException e) {
          Log.e(LOG_TAG, "Unable to read UTF-8 string from byte array.");
        } finally {
          if (reader != null) {
            try {
              reader.close();
            } catch(IOException e) {
              Log.wtf(LOG_TAG, "Unable to close stream after IOException.");
            }
          }
        }
        return result;
      } else if(type == BluetoothGattCharacteristic.FORMAT_FLOAT ||
          type == BluetoothGattCharacteristic.FORMAT_SFLOAT) {
        int elements = characteristic.getValue().length / size;
        List<T> values = new ArrayList<T>(elements);
        if (size == 4) {
          // workaround for the fact that Android BLE API doesn't use IEEE 754 floating points
          ByteBuffer buffer = ByteBuffer.wrap(characteristic.getValue())
              .order(ByteOrder.LITTLE_ENDIAN);
          for (int i = 0; i < elements; i++) {
            values.add(mClass.cast(buffer.getFloat(size * i)));
          }
        } else {
          for (int i = 0; i < elements; i++) {
            Float value = characteristic.getFloatValue(type, i * size);
            if (value != null) {
              values.add(mClass.cast(value));
            } else {
              values.add(mClass.cast(Float.NaN));
            }
          }
        }
        return values;
      } else {
        int elements = characteristic.getValue().length / size;
        Log.d(LOG_TAG, "Reading " + elements + " elements of size " + size);
        List<T> values = new ArrayList<T>(elements);
        for (int i = 0; i < elements; i++) {
          Integer value = characteristic.getIntValue(type, i * size);
          if (value != null) {
            values.add(mClass.cast(value));
          } else {
            values.add(mClass.cast(0));
          }
        }
        return values;
      }
    }

    /**
     * Callback for subclasses to implement that is called when the Bluetooth characteristic
     * is read or changes.
     * @param values The list of values extracted from the Bluetooth message.
     */
    protected abstract void onReceive(List<T> values);
  }

  /**
   * Bluetooth write operations extend BLEWriteOperation to specify the value type of an
   * operation. For operations that write primitive values, boxed types must be used.
   * @param <T> The value type of a write operation. Typically this is Integer, Float, or String.
   */
  private abstract class BLEWriteOperation<T> extends BLEOperation {
    /**
     * The class of the value type.
     */
    private final Class<T> mClass;

    /**
     * The data to be written for pending operations.
     */
    private List<T> data;

    /**
     * Handler, if any, that will be called after the write operation completes.
     */
    private BluetoothLE.BLEResponseHandler<T> handler;

    /**
     * The write tyoe of the characteristic. See {@link BluetoothGattCharacteristic}.
     */
    private final int writeType;

    BLEWriteOperation(Class<T> aClass, BluetoothGattCharacteristic characteristic, int type, List<T> data, int writeType) {
      super(characteristic, type);
      this.mClass = aClass;
      this.data = data;
      this.writeType = writeType;
    }

    @Override
    public boolean isRead() {
      return false;
    }

    @Override
    public boolean isNotify() {
      return false;
    }

    @Override
    public boolean isWrite() {
      return true;
    }

    @Override
    public void run() {
      write(mBluetoothGatt, writeType);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt,
                                      final BluetoothGattCharacteristic characteristic,
                                      int status) {
      if (this.characteristic == characteristic) {
        try {
          switch (status) {
            case BluetoothGatt.GATT_SUCCESS:
              onWrite(data);
              if (handler != null) {
                mHandler.post(new Runnable() {
                  @Override
                  public void run() {
                    handler.onWrite(characteristic.getService().getUuid().toString(),
                        characteristic.getUuid().toString(), data);
                  }
                });
              }
              break;
            default:
              // TODO(ewpatton): Handle error condition
              Log.e(LOG_TAG, "Error code " + status + " from characteristic " +
                  characteristic.getUuid());
          }
        } finally {
          pendingOperationsByUuid.get(characteristic.getUuid()).remove(this);
          if (needsRemoval && runPendingOperation(this)) {
            needsRemoval = false;
          }
          Log.d(LOG_TAG, "pendingOperations.size() = " + pendingOperations.size());
          Log.d(LOG_TAG, "pendingOperationsByUuid.size() = " + pendingOperationsByUuid.size());
        }
      }
    }

    @SuppressWarnings("unchecked")
    private void write(BluetoothGatt gatt, int writeType) {
      registerPendingOperation();
      characteristic.setWriteType(writeType);
      // TODO(ewpatton): Refactor this so that each write type knows only its own serialization
      if (mClass == String.class) {
        byte[] str = ((String) data.get(0)).getBytes();
        final int len = str.length > 22 ? 23 : str.length + 1;
        byte[] buffer = new byte[len];
        for (int i = 0; i < len - 1; i++) {
          buffer[i] = str[i];
        }
        buffer[len - 1] = 0;
        characteristic.setValue(buffer);
      } else if (mClass == Float.class) {
        byte[] contents = new byte[size * data.size()];
        int value, i = 0;
        if (size == 4) {
          for (Float f : (List<Float>) data) {
            value = Float.floatToIntBits(f);
            // values are sent LSB first
            contents[i++] = (byte)(value & 0xFF);
            contents[i++] = (byte)((value >> 8) & 0xFF);
            contents[i++] = (byte)((value >> 16) & 0xFF);
            contents[i++] = (byte)((value >> 24) & 0xFF);
          }
        } else {
          for (Float f : (List<Float>) data) {
            value = Float.floatToIntBits(f);
            // convert the 32-bit float into 16-bit half-float
            value = ((value & 0x80000000) >> 16)  // sign bit
                | (((((value & 0x7F800000) >> 23) + 127 - 15) & 0x1F) << 11)  // exponent
                | ((value & 0x007FF000) >> 13);   // mantissa
            contents[i++] = (byte)(value & 0xFF);
            contents[i++] = (byte)((value >> 8) & 0xFF);
          }
        }
        characteristic.setValue(contents);
      } else {
        byte[] contents = new byte[size * data.size()];
        long value;
        int i = 0;
        for (Number n : (List<? extends Number>) data) {
          value = n.longValue();
          for (int j = 0; j < size; j++) {
            contents[i++] = (byte)(value & 0xFF);
            value >>= 8;
          }
        }
        characteristic.setValue(contents);
      }
      gatt.writeCharacteristic(characteristic);
    }

    /**
     * Write the data encapsulated by the operation to the characteristic using the supplied GATT
     * connection.
     * @param gatt Connection to the peripheral device.
     */
    public void write(BluetoothGatt gatt) {
      write(gatt, BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
    }

    /**
     * Write the data encapsulated by the operation to the characteristic using the supplied GATT
     * connection. The handler will be called on the UI thread once the operation completes.
     * @param gatt Connection to the peripheral device.
     * @param handler Callback for when the operation completes.
     */
    public void writeWithResponse(BluetoothGatt gatt, BluetoothLE.BLEResponseHandler<T> handler) {
      this.handler = handler;
      write(gatt, BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
    }

    protected abstract void onWrite(List<T> values);
  }

  /**
   * A Bluetooth operation to read or register for byte values.
   */
  class BLEReadByteOperation extends BLEReadOperation<Integer> {

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If signed is true,
     * the values are interpreted as signed 8-bit values. If signed is false, the values are
     * interpreted as unsigned 8-bit values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param signed true if the values are signed.
     * @param handler A callback object to handle the received data.
     */
    BLEReadByteOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                         BluetoothLE.BLEResponseHandler<Integer> handler) {
      this(characteristic, signed, handler, false);
    }

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If signed is true,
     * the values are interpreted as signed 8-bit values. If signed is false, the values are
     * interpreted as unsigned 8-bit values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param signed true if the values are signed.
     * @param handler A callback object to handle the received data.
     * @param notify true if the operation is a subscription for notifications/indications,
     *               otherwise false.
     */
    BLEReadByteOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                         BluetoothLE.BLEResponseHandler<Integer> handler, boolean notify) {
      super(Integer.class, characteristic, signed? BluetoothGattCharacteristic.FORMAT_SINT8 :
          BluetoothGattCharacteristic.FORMAT_UINT8, handler, notify);
    }

    @Override
    protected void onReceive(List<Integer> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.BytesReceived(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to read or register for short integer (16-bit) values.
   */
  class BLEReadShortOperation extends BLEReadOperation<Integer> {

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If signed is true,
     * the values are interpreted as signed 16-bit values. If signed is false, the values are
     * interpreted as unsigned 16-bit values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param signed true if the values are signed.
     * @param handler A callback object to handle the received data.
     */
    BLEReadShortOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                          BluetoothLE.BLEResponseHandler<Integer> handler) {
      this(characteristic, signed, handler, false);
    }

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If signed is true,
     * the values are interpreted as signed 16-bit values. If signed is false, the values are
     * interpreted as unsigned 16-bit values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param signed true if the values are signed.
     * @param handler A callback object to handle the received data.
     * @param notify true if the operation is a subscription for notifications/indications,
     *               otherwise false.
     */
    BLEReadShortOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                          BluetoothLE.BLEResponseHandler<Integer> handler, boolean notify) {
      super(Integer.class, characteristic, signed? BluetoothGattCharacteristic.FORMAT_SINT16 :
          BluetoothGattCharacteristic.FORMAT_UINT16, handler, notify);
    }

    @Override
    protected void onReceive(List<Integer> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.ShortsReceived(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A BLE read operation for 32-bit integer values. Since Bluetooth allows for both signed and
   * unsigned values, this uses the Long boxing type to handle unsigned 32-bit integers (which can
   * only be represented in Java by the long data type because ints are always signed).
   */
  class BLEReadIntegerOperation extends BLEReadOperation<Long> {

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If signed is true,
     * the values are interpreted as signed 32-bit values. If signed is false, the values are
     * interpreted as unsigned 32-bit values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param signed true if the values are signed.
     * @param handler A callback object to handle the received data.
     */
    BLEReadIntegerOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                            BluetoothLE.BLEResponseHandler<Long> handler) {
      this(characteristic, signed, handler, false);
    }

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If signed is true,
     * the values are interpreted as signed 32-bit values. If signed is false, the values are
     * interpreted as unsigned 32-bit values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param signed true if the values are signed.
     * @param handler A callback object to handle the received data.
     * @param notify true if the operation is a subscription for notifications/indications,
     *               otherwise false.
     */
    BLEReadIntegerOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                            BluetoothLE.BLEResponseHandler<Long> handler, boolean notify) {
      super(Long.class, characteristic, signed? BluetoothGattCharacteristic.FORMAT_SINT32 :
          BluetoothGattCharacteristic.FORMAT_UINT32, handler, notify);
    }

    @Override
    protected void onReceive(List<Long> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.IntegersReceived(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to read or register for floating-point values.
   */
  class BLEReadFloatOperation extends BLEReadOperation<Float> {

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If shortFloat is true,
     * the values are interpreted as 16-bit floating point values (sfloat type in the Bluetooth
     * specification). Otherwise, the values are interpreted as 32-bit IEEE floating point values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param shortFloat true if the values are short floats.
     * @param handler A callback object to handle the received data.
     */
    BLEReadFloatOperation(BluetoothGattCharacteristic characteristic, boolean shortFloat,
                          BluetoothLE.BLEResponseHandler<Float> handler) {
      this(characteristic, shortFloat, handler, false);
    }

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If shortFloat is true,
     * the values are interpreted as 16-bit floating point values (sfloat type in the Bluetooth
     * specification). Otherwise, the values are interpreted as 32-bit IEEE floating point values.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param shortFloat true if the values are short floats.
     * @param handler A callback object to handle the received data.
     * @param notify true if the operation is a subscription for notifications/indications,
     *               otherwise false.
     */
    BLEReadFloatOperation(BluetoothGattCharacteristic characteristic, boolean shortFloat,
                          BluetoothLE.BLEResponseHandler<Float> handler, boolean notify) {
      super(Float.class, characteristic, shortFloat? BluetoothGattCharacteristic.FORMAT_SFLOAT :
          BluetoothGattCharacteristic.FORMAT_FLOAT, handler, notify);
    }

    @Override
    public void onReceive(List<Float> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.FloatsReceived(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to read or register for strings.
   */
  class BLEReadStringOperation extends BLEReadOperation<String> {

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If utf16 is true,
     * the values are interpreted as UTF-16 strings. Otherwise, UTF-8 is used as the encoding.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param utf16 true if the values should be decoded using UTF-16.
     * @param handler A callback object to handle the received data.
     */
    BLEReadStringOperation(BluetoothGattCharacteristic characteristic, boolean utf16,
                           BluetoothLE.BLEResponseHandler<String> handler) {
      this(characteristic, utf16, handler, false);
    }

    /**
     * Construct a new Bluetooth read operation for the given characteristic. If utf16 is true,
     * the values are interpreted as UTF-16 strings. Otherwise, UTF-8 is used as the encoding.
     * @param characteristic The Bluetooth GATT characteristic to read or register for values.
     * @param utf16 true if the values should be decoded using UTF-16.
     * @param handler A callback object to handle the received data.
     * @param notify true if the operation is a subscription for notifications/indications,
     *               otherwise false.
     */
    BLEReadStringOperation(BluetoothGattCharacteristic characteristic, boolean utf16,
                           BluetoothLE.BLEResponseHandler<String> handler, boolean notify) {
      super(String.class, characteristic, utf16? FORMAT_UTF16S : FORMAT_UTF8S, handler, notify);
    }

    @Override
    public void onReceive(List<String> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.StringsReceived(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to write one or more bytes to a characteristic.
   */
  class BLEWriteBytesOperation extends BLEWriteOperation<Integer> {
    /**
     * Construct a new Blueooth write operation for the given characteristic. If signed is true,
     * the values are interpreted as signed bytes. Otherwise, unsigned bytes are sent. The
     * writeType should indicate whether or not a response from the Bluetooth client is required.
     * @param characteristic The Bluetooth GATT characteristic to write values to.
     * @param signed true if the values should be signed bytes, otherwise false.
     * @param values The list of integer values (will be truncated) to send to the client.
     * @param writeType Either {@link BluetoothGattCharacteristic#WRITE_TYPE_DEFAULT} or
     *                  {@link BluetoothGattCharacteristic#WRITE_TYPE_NO_RESPONSE}.
     */
    BLEWriteBytesOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                           List<Integer> values, int writeType) {
      super(Integer.class, characteristic, signed? BluetoothGattCharacteristic.FORMAT_SINT8 :
          BluetoothGattCharacteristic.FORMAT_UINT8, values, writeType);
    }

    @Override
    public void onWrite(List<Integer> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.BytesWritten(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to write one or more shorts to a characteristic.
   */
  class BLEWriteShortsOperation extends BLEWriteOperation<Integer> {
    /**
     * Construct a new Bluetooth write operation for the given characteristic. If signed is true,
     * the values are interpreted as signed shorts. Otherwise, unsigned shorts are sent. The
     * writeType should indicate whether or not a response from the Bluetooth client is required.
     * @param characteristic The Bluetooth GATT characteristic to write values to.
     * @param signed true if the values should be signed shorts, otherwise false.
     * @param values The list of integer values (will be truncated) to send to the client.
     * @param writeType Either {@link BluetoothGattCharacteristic#WRITE_TYPE_DEFAULT} or
     *                  {@link BluetoothGattCharacteristic#WRITE_TYPE_NO_RESPONSE}.
     */
    BLEWriteShortsOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                            List<Integer> values, int writeType) {
      super(Integer.class, characteristic, signed? BluetoothGattCharacteristic.FORMAT_SINT16 :
          BluetoothGattCharacteristic.FORMAT_UINT16, values, writeType);
    }

    @Override
    public void onWrite(List<Integer> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.ShortsWritten(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to write one or more integers to a characteristic.
   */
  class BLEWriteIntegersOperation extends BLEWriteOperation<Long> {
    /**
     * Construct a new Bluetooth write operation for the given characteristic. If signed is true,
     * the values are interpreted as signed ints. Otherwise, unsigned ints are sent. The
     * writeType should indicate whether or not a response from the Bluetooth client is required.
     * @param characteristic The Bluetooth GATT characteristic to write values to.
     * @param signed true if the values should be signed ints, otherwise false.
     * @param values The list of long values (will be truncated) to send to the client.
     * @param writeType Either {@link BluetoothGattCharacteristic#WRITE_TYPE_DEFAULT} or
     *                  {@link BluetoothGattCharacteristic#WRITE_TYPE_NO_RESPONSE}.
     */
    BLEWriteIntegersOperation(BluetoothGattCharacteristic characteristic, boolean signed,
                              List<Long> values, int writeType) {
      super(Long.class, characteristic, signed? BluetoothGattCharacteristic.FORMAT_SINT32 :
          BluetoothGattCharacteristic.FORMAT_UINT32, values, writeType);
    }

    @Override
    public void onWrite(List<Long> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.IntegersWritten(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to write one or more floats to a characteristic.
   */
  class BLEWriteFloatsOperation extends BLEWriteOperation<Float> {
    /**
     * Construct a new Bluetooth write operation for the given characteristic. If shortFloats is
     * true, the values are interpreted as half-precision (!6-bit) floats. Otherwise, single-
     * precision (32-bit) floats are sent. The writeType should indicate whether or not a
     * response from the Bluetooth client is required.
     * @param characteristic The Bluetooth GATT characteristic to write values to.
     * @param shortFloats true if the values should be written as 16-bit floats, otherwise false.
     * @param values The list of float values (may be truncated) to send to the client.
     * @param writeType Either {@link BluetoothGattCharacteristic#WRITE_TYPE_DEFAULT} or
     *                  {@link BluetoothGattCharacteristic#WRITE_TYPE_NO_RESPONSE}.
     */
    BLEWriteFloatsOperation(BluetoothGattCharacteristic characteristic, boolean shortFloats,
                            List<Float> values, int writeType) {
      super(Float.class, characteristic, shortFloats? BluetoothGattCharacteristic.FORMAT_SFLOAT :
          BluetoothGattCharacteristic.FORMAT_FLOAT, values, writeType);
    }

    @Override
    public void onWrite(List<Float> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.FloatsWritten(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
    }
  }

  /**
   * A Bluetooth operation to write one or more strings to a characteristic.
   */
  class BLEWriteStringsOperation extends BLEWriteOperation<String> {
    /**
     * Construct a new Bluetooth write operation for the given characteristic. If utf16 is true,
     * the values are sent as UTF-16 in little endian format. Otherwise, UTF-8 is used. The
     * writeType should indicate whether or not a response from the Bluetooth client is required.
     * @param characteristic The Bluetooth GATT characteristic to write values to.
     * @param utf16 true if the values should be written as UTF-16 strings, otherwise false.
     * @param values The list of strings to send to the client.
     * @param writeType Either {@link BluetoothGattCharacteristic#WRITE_TYPE_DEFAULT} or
     *                  {@link BluetoothGattCharacteristic#WRITE_TYPE_NO_RESPONSE}.
     */
    BLEWriteStringsOperation(BluetoothGattCharacteristic characteristic, boolean utf16,
                             List<String> values, int writeType) {
      super(String.class, characteristic, utf16? FORMAT_UTF16S : FORMAT_UTF8S, values, writeType);
    }

    @Override
    public void onWrite(List<String> values) {
      final YailList yailList = YailList.makeList(values);
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          outer.StringsWritten(characteristic.getService().getUuid().toString(),
              characteristic.getUuid().toString(), yailList);
        }
      });
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
  private static final int ERROR_UNSUPPORTED_CHARACTERISTIC = 9011;
  private static final String BLUETOOTH_BASE_UUID_SUFFIX = "-0000-1000-8000-00805F9B34FB";
  private static final String UNKNOWN_SERVICE_NAME = "Unknown Service";
  private static final String UNKNOWN_CHAR_NAME = "Unknown Characteristic";
  private static final int GATT_LINK_LOSS = 8;  // Not documented in Android SDK

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
  private BluetoothLeScanner mBluetoothLeDeviceScanner = null;
  private BluetoothGatt mBluetoothGatt;
  private int device_rssi = 0;
  private final Handler uiThread;
  private volatile int connectionTimeout = 10;
  private boolean autoReconnect = false;

  /**
   * pendingOperationsByUuid stores a list of pending BLE operations per characteristic.
   */
  private final Map<UUID, List<BLEOperation>> pendingOperationsByUuid =
      new HashMap<UUID, List<BLEOperation>>();
  private final Queue<BLEOperation> pendingOperations = new LinkedList<BLEOperation>();

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
  private volatile boolean isConnected = false;
  private volatile boolean isUserDisconnect = false;
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
            isScanning = false;
            Log.e(LOG_TAG, "Device Scan failed due to an internal error. Error Code: " + errorCode);
        }
        super.onScanFailed(errorCode);
      }
    };

    this.mGattCallback = new BluetoothGattCallback() {
      @Override
      public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        if (gatt != mBluetoothGatt) {
          // Connection change targeting a previous connection (due to calling Connect multiple times)
          return;
        }
        if (newState == BluetoothProfile.STATE_CONNECTED) {
          gatt.discoverServices();
          gatt.readRemoteRssi();
          Log.i(LOG_TAG, "Connect successful.");
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
          isConnected = false;
          if (!autoReconnect || isUserDisconnect) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
            isUserDisconnect = false;
          } else {
            mBluetoothGatt.connect();
          }
          Disconnected();
          Log.i(LOG_TAG, "Disconnect successful.");
        }

        if (status != BluetoothGatt.GATT_SUCCESS && !isConnected) {
          if (status != GATT_LINK_LOSS || !autoReconnect) {
            BluetoothLEint.this.outer.ConnectionFailed("Connection status was set to OS code " + status);
          }
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
          isConnected = true;
          // Now that we've connected to a device, clear any pending operations for the previous
          // connection and fire the Connected event.
          pendingOperationsByUuid.clear();
          pendingOperations.clear();
          Connected();
        } else {
          BluetoothLEint.this.outer.ConnectionFailed("Service discovery failed with OS code " + status);
        }
        Log.i(LOG_TAG, "onServicesDiscovered fired with status: " + status);
      }

      @Override
      // Result of a characteristic read operation
      public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
          // We have a pending operation for the characteristic that should fire.
          Log.d(LOG_TAG, "onCharacteristicRead: " + characteristic.getUuid());
          List<BLEOperation> operations = new ArrayList<BLEOperation>(pendingOperationsByUuid.get(characteristic.getUuid()));
          for (BLEOperation operation : operations) {
            operation.onCharacteristicRead(gatt, characteristic, status);
          }
        }
      }

      @Override
      // Result of a characteristic read operation is changed
      public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        if (pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
          // We have a pending operation for the characteristic that should fire.
          Log.d(LOG_TAG, "onCharacteristicRead");
          List<BLEOperation> operations = new ArrayList<BLEOperation>(pendingOperationsByUuid.get(characteristic.getUuid()));
          for (BLEOperation operation : operations) {
            operation.onCharacteristicChanged(gatt, characteristic);
          }
        }
      }

      @Override
      // set value of characteristic
      public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
          List<BLEOperation> operations = new ArrayList<BLEOperation>(pendingOperationsByUuid.get(characteristic.getUuid()));
          for (BLEOperation operation : operations) {
            operation.onCharacteristicWrite(gatt, characteristic, status);
          }
        }
      }

      @Override
      public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        descriptorValue = descriptor.getValue();
        final BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
        if (pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
          // We have a pending operation for the characteristic that should fire.
          Log.d(LOG_TAG, "onDescriptorRead");
          List<BLEOperation> operations = new ArrayList<BLEOperation>(pendingOperationsByUuid.get(characteristic.getUuid()));
          for (BLEOperation operation : operations) {
            operation.onDescriptorRead(gatt, descriptor, status);
          }
          return;
        }
      }

      @Override
      public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Log.i(LOG_TAG, "Write Descriptor Successfully.");
        final BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
        if (pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
          // We have a pending operation for the characteristic that should fire.
          Log.d(LOG_TAG, "onDescriptorWrite");
          List<BLEOperation> operations = new ArrayList<BLEOperation>(pendingOperationsByUuid.get(characteristic.getUuid()));
          for (BLEOperation operation : operations) {
            operation.onDescriptorWrite(gatt, descriptor, status);
          }
          return;
        }
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

  boolean isScanning() {
    return isScanning;
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
          mBluetoothLeDeviceScanner = btAdapter.getBluetoothLeScanner();

          if (mBluetoothLeDeviceScanner != null) {
          
            ScanSettings settings = new ScanSettings.Builder()
              .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
              .build();

            List<ScanFilter> filters = new ArrayList<ScanFilter>();
            ScanFilter filter = new ScanFilter.Builder().build();
            filters.add(filter);

            mBluetoothLeDeviceScanner.startScan(filters, settings, mDeviceScanCallback);
            Log.i(LOG_TAG, "StartScanning successful.");
          }
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
          isScanning = false;
          Log.i(LOG_TAG, "StopScanning successful.");
        } else {
          signalError("StopScanning", ERROR_NO_DEVICE_SCAN_IN_PROGRESS);
        }
        return null;
      }
    }.run();
  }

  void Connect(final int index) {
    if (index < 1 || index > mLeDevices.size()) {
      outer.ConnectionFailed("Invalid index provided to Connect");
      throw new IndexOutOfBoundsException("Expected device index between 1 and " + mLeDevices.size());
    }
    new BLEAction<Void>("Connect") {
      @Override
      public Void action() {
        try {
          if (mBluetoothLeAdvertisementScanner != null) {
            mBluetoothLeAdvertisementScanner.stopScan(mDeviceScanCallback);
            isScanning = false;
          }
          if (!mLeDevices.isEmpty()) {
            forceDisconnect();
            mBluetoothGatt = mLeDevices.get(index - 1)
                .connectGatt(activity, autoReconnect, mGattCallback);
            if (mBluetoothGatt != null) {
              //TODO(Will): Delete the puts to this map
              gattMap.put(mLeDevices.get(index - 1).toString(), mBluetoothGatt);
              scheduleConnectionTimeoutMessage();
            } else {
              outer.ConnectionFailed("Connect failed to return a BluetoothGatt object");
              Log.e(LOG_TAG, "Connect failed.");
            }
          } else {
            outer.ConnectionFailed("Device list was empty");
            signalError("Connect", ERROR_DEVICE_LIST_EMPTY);
          }
        } catch (IndexOutOfBoundsException e) {
          outer.ConnectionFailed(e.getMessage());
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
          if (mBluetoothLeAdvertisementScanner != null) {
            mBluetoothLeAdvertisementScanner.stopScan(mDeviceScanCallback);
            isScanning = false;
          }
          if (!mLeDevices.isEmpty()) {
            for (BluetoothDevice bluetoothDevice : mLeDevices) {
              if (bluetoothDevice.getAddress().equals(address)) {
                forceDisconnect();
                mBluetoothGatt = bluetoothDevice.connectGatt(activity, autoReconnect, mGattCallback);
                if (mBluetoothGatt != null) {
                  //TODO(Will): Delete the puts to this map
                  gattMap.put(bluetoothDevice.getAddress(), mBluetoothGatt);
                  scheduleConnectionTimeoutMessage();
                  Log.i(LOG_TAG, "ConnectWithAddress successful.");
                  return null;
                } else {
                  outer.ConnectionFailed("Connect failed to return a BluetoothGatt object");
                  Log.e(LOG_TAG, "ConnectWithAddress failed.");
                }
              }
            }
            outer.ConnectionFailed("No device found with address " + address);
          } else {
            outer.ConnectionFailed("Device list was empty");
            signalError("ConnectWithAddress", ERROR_DEVICE_LIST_EMPTY);
          }
        } catch (IndexOutOfBoundsException e) {
          outer.ConnectionFailed(e.getMessage());
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
          isUserDisconnect = true;
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

  /**
   * Schedule an operation to read one or more byte values from the given service/characteristic
   * pair. Bytes will be interpreted as signed or unsigned depending on the truth value of the
   * <code>signed</code> parameter.
   *
   * NB: Packets cannot have mixed values. All bytes will be interpreted as the same signedness.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param signed true if the bytes should be read as signed.
   */
  void ReadByteValues(final String serviceUuid, final String characteristicUuid,
                      final boolean signed) {
    ReadByteValues(serviceUuid, characteristicUuid, signed, null);
  }

  void ReadByteValues(final String serviceUuid, final String characteristicUuid,
                      final boolean signed,
                      final BluetoothLE.BLEResponseHandler<Integer> callback) {
    final String METHOD = "ReadByteValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadByteOperation(characteristic, signed, callback));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to register for notifications for byte values for the given
   * service/characteristic pair. Bytes will be interpreted as signed or unsigned depending on the
   * truth value of the <code>signed</code> parameter.
   *
   * NB: Packets cannot have mixed values. All bytes will be interpreted as the same signedness.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param signed true if the bytes should be read as signed.
   */
  void RegisterForByteValues(final String serviceUuid, final String characteristicUuid,
                             final boolean signed) {
    RegisterForByteValues(serviceUuid, characteristicUuid, signed, null);
  }

  void RegisterForByteValues(final String serviceUuid, final String characteristicUuid,
                             final boolean signed,
                             final BluetoothLE.BLEResponseHandler<Integer> callback) {
    final String METHOD = "RegisterForByteValues";
    mHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        new BLEAction<Void>(METHOD) {
          @Override
          public Void action() {
            BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
                bleStringToUuid(characteristicUuid));
            schedulePendingOperation(new BLEReadByteOperation(characteristic, signed, callback, true));
            return null;
          }
        }.run();
      }
    }, 1);
  }

  void WriteByteValues(final String serviceUuid, final String characteristicUuid,
                       final boolean signed, final List<Integer> values) {
    final String METHOD = "WriteByteValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteBytesOperation(characteristic, signed, values,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE));
        return null;
      }
    }.run();
  }

  void WriteByteValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                   final boolean signed, final List<Integer> values) {
    WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed, values, null);
  }

  void WriteByteValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                   final boolean signed, final List<Integer> values,
                                   final BluetoothLE.BLEResponseHandler<Integer> callback) {
    final String METHOD = "WriteByteValuesWithResponse";
    Log.d(LOG_TAG, "WriteByteValuesWithResponse: " + values);
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteBytesOperation(characteristic, signed, values, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to read one or more short values from the given service/characteristic
   * pair. Shorts will be interpreted as signed or unsigned depending on the truth value of the
   * <code>signed</code> parameter.
   *
   * NB: Packets cannot have mixed values. All shorts will be interpreted as the same signedness.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param signed true if the shorts should be read as signed.
   */
  void ReadShortValues(final String serviceUuid, final String characteristicUuid, final boolean signed) {
    ReadShortValues(serviceUuid, characteristicUuid, signed, null);
  }

  void ReadShortValues(final String serviceUuid, final String characteristicUuid,
                       final boolean signed,
                       final BluetoothLE.BLEResponseHandler<Integer> callback) {
    final String METHOD = "ReadShortValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadShortOperation(characteristic, signed, callback));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to register for notifications for short values for the given
   * service/characteristic pair. Shorts will be interpreted as signed or unsigned depending on the
   * truth value of the <code>signed</code> parameter.
   *
   * NB: Packets cannot have mixed values. All shorts will be interpreted as the same signedness.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param signed true if the shorts should be read as signed.
   */
  void RegisterForShortValues(final String serviceUuid, final String characteristicUuid,
                              final boolean signed) {
    RegisterForShortValues(serviceUuid, characteristicUuid, signed, null);
  }

  void RegisterForShortValues(final String serviceUuid, final String characteristicUuid,
                              final boolean signed,
                              final BluetoothLE.BLEResponseHandler<Integer> callback) {
    final String METHOD = "RegisterForShortValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadShortOperation(characteristic, signed, callback, true));
        return null;
      }
    }.run();
  }

  void WriteShortValues(final String serviceUuid, final String characteristicUuid,
                        final boolean signed, final List<Integer> values) {
    final String METHOD = "WriteShortValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteShortsOperation(characteristic, signed, values,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE));
        return null;
      }
    }.run();
  }

  void WriteShortValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                    final boolean signed, final List<Integer> values) {
    WriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed, values, null);
  }

  void WriteShortValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                    final boolean signed, final List<Integer> values,
                                    final BluetoothLE.BLEResponseHandler<Integer> handler) {
    final String METHOD = "WriteShortValuesWithResponse";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteShortsOperation(characteristic, signed, values,
            BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to read one or more integer values from the given service/characteristic
   * pair. Integers will be interpreted as signed or unsigned depending on the truth value of the
   * <code>signed</code> parameter.
   *
   * NB: Packets cannot have mixed values. All integers will be interpreted as the same signedness.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param signed true if the integers should be read as signed.
   */
  void ReadIntegerValues(final String serviceUuid, final String characteristicUuid,
                         final boolean signed) {
    ReadIntegerValues(serviceUuid, characteristicUuid, signed, null);
  }

  void ReadIntegerValues(final String serviceUuid, final String characteristicUuid,
                         final boolean signed, final BluetoothLE.BLEResponseHandler<Long> handler) {
    final String METHOD = "ReadIntegerValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadIntegerOperation(characteristic, signed, handler));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to register for notifications for integer values for the given
   * service/characteristic pair. Integers will be interpreted as signed or unsigned depending on
   * the truth value of the <code>signed</code> parameter.
   *
   * NB: Packets cannot have mixed values. All integers will be interpreted as the same signedness.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param signed true if the integers should be read as signed.
   */
  void RegisterForIntegerValues(final String serviceUuid, final String characteristicUuid, final boolean signed) {
    RegisterForIntegerValues(serviceUuid, characteristicUuid, signed, null);
  }

  void RegisterForIntegerValues(final String serviceUuid, final String characteristicUuid,
                                final boolean signed,
                                final BluetoothLE.BLEResponseHandler<Long> handler) {
    final String METHOD = "RegisterForIntegerValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadIntegerOperation(characteristic, signed, handler, true));
        return null;
      }
    }.run();
  }

  void WriteIntegerValues(final String serviceUuid, final String characteristicUuid,
                          final boolean signed, final List<Long> values) {
    final String METHOD = "WriteIntegerValuesWithResponse";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteIntegersOperation(characteristic, signed, values,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE));
        return null;
      }
    }.run();
  }

  void WriteIntegerValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                      final boolean signed, final List<Long> values) {
    WriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed, values, null);
  }

  void WriteIntegerValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                      final boolean signed, final List<Long> values,
                                      final BluetoothLE.BLEResponseHandler<Long> handler) {
    final String METHOD = "WriteIntegerValuesWithResponse";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteIntegersOperation(characteristic, signed, values,
            BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to read one or more floating point values from the given
   * service/characteristic pair. Floats will be interpreted as 16- or 32-bit depending on the
   * truth value of the <code>shortFloat</code> parameter.
   *
   * NB: Packets cannot have mixed values. All floats will be interpreted as either 16-bit floats or
   * 32-bit floats.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param shortFloat true if the values are 16-bit floats instead of 32-bit floats.
   */
  void ReadFloatValues(final String serviceUuid, final String characteristicUuid, final boolean shortFloat) {
    ReadFloatValues(serviceUuid, characteristicUuid, shortFloat, null);
  }

  void ReadFloatValues(final String serviceUuid, final String characteristicUuid,
                       final boolean shortFloat,
                       final BluetoothLE.BLEResponseHandler<Float> callback) {
    final String METHOD = "ReadFloatValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadFloatOperation(characteristic, shortFloat, callback));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to register for notifications for floating point values for the given
   * service/characteristic pair. Floats will be interpreted as 16- or 32-bit depending on the
   * truth value of the <code>shortFloat</code> parameter.
   *
   * NB: Packets cannot have mixed values. All floats will be interpreted as either 16-bit floats or
   * 32-bit floats.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param shortFloat true if the values are 16-bit floats instead of 32-bit floats.
   */
  void RegisterForFloatValues(final String serviceUuid, final String characteristicUuid,
                              final boolean shortFloat) {
    RegisterForFloatValues(serviceUuid, characteristicUuid, shortFloat, null);
  }

  void RegisterForFloatValues(final String serviceUuid, final String characteristicUuid,
                              final boolean shortFloat,
                              final BluetoothLE.BLEResponseHandler<Float> callback) {
    final String METHOD = "RegisterForFloatValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadFloatOperation(characteristic, shortFloat, callback, true));
        return null;
      }
    }.run();
  }

  void WriteFloatValues(final String serviceUuid, final String characteristicUuid,
                        final boolean shortFloat, final List<Float> values) {
    final String METHOD = "WriteFloatValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteFloatsOperation(characteristic, shortFloat, values,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE));
        return null;
      }
    }.run();
  }

  void WriteFloatValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                    final boolean shortFloat, final List<Float> values) {
    WriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloat, values, null);
  }

  void WriteFloatValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                    final boolean shortFloat, final List<Float> values,
                                    final BluetoothLE.BLEResponseHandler<Float> callback) {
    final String METHOD = "WriteFloatValuesWithResponse";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteFloatsOperation(characteristic, shortFloat, values,
            BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT));
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to read one or more zero-terminated strings from the given
   * service/characteristic pair. Strings will be interpreted as UTF-8 or UTF-16 based on the
   * truth value of the <code>utf16</code> parameter.
   *
   * NB: Packets cannot have mixed values. All strings will be interpreted either as UTF-8 or
   * UTF-16.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param utf16 true if the values are 16-bit UTF code points.
   */
  void ReadStringValues(final String serviceUuid, final String characteristicUuid,
                        final boolean utf16) {
    ReadStringValues(serviceUuid, characteristicUuid, utf16, null);
  }

  void ReadStringValues(final String serviceUuid, final String characteristicUuid,
                        final boolean utf16, final BluetoothLE.BLEResponseHandler<String> handler) {
    final String METHOD = "ReadStringValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        try {
          BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
              bleStringToUuid(characteristicUuid));
          schedulePendingOperation(new BLEReadStringOperation(characteristic, utf16, handler));
        } catch(IllegalStateException e) {
          container.$form().dispatchErrorOccurredEvent(outer, "ReadString",
              ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_UNSUPPORTED_CHARACTERISTIC,
              "BluetoothLE", UNKNOWN_CHAR_NAME);
        }
        return null;
      }
    }.run();
  }

  /**
   * Schedule an operation to register for notifications for zero-terminated strings from the given
   * service/characteristic pair. Strings will be interpreted as UTF-8 or UTF-16 based on the
   * truth value of the <code>utf16</code> parameter.
   *
   * NB: Packets cannot have mixed values. All strings will be interpreted either as UTF-8 or
   * UTF-16.
   *
   * @param serviceUuid UUID for the Bluetooth service.
   * @param characteristicUuid UUID for the Bluetooth characteristic.
   * @param utf16 true if the values are 16-bit UTF code points.
   */
  void RegisterForStringValues(final String serviceUuid, final String characteristicUuid,
                               final boolean utf16) {
    RegisterForStringValues(serviceUuid, characteristicUuid, utf16, null);
  }

  void RegisterForStringValues(final String serviceUuid, final String characteristicUuid,
                               final boolean utf16,
                               final BluetoothLE.BLEResponseHandler<String> handler) {
    final String METHOD = "RegisterForStringValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEReadStringOperation(characteristic, utf16, handler, true));
        return null;
      }
    }.run();
  }

  void WriteStringValues(final String serviceUuid, final String characteristicUuid,
                         final boolean utf16, final List<String> values) {
    final String METHOD = "WriteStringValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteStringsOperation(characteristic, utf16, values,
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE));
        return null;
      }
    }.run();
  }

  void WriteStringValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                     final boolean utf16, final List<String> values) {
    WriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16, values, null);
  }

  void WriteStringValuesWithResponse(final String serviceUuid, final String characteristicUuid,
                                     final boolean utf16, final List<String> values,
                                     final BluetoothLE.BLEResponseHandler<String> handler) {
    final String METHOD = "WriteStringValuesWithResponse";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        BluetoothGattCharacteristic characteristic = findMGattChar(bleStringToUuid(serviceUuid),
            bleStringToUuid(characteristicUuid));
        schedulePendingOperation(new BLEWriteStringsOperation(characteristic, utf16, values,
            BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT));
        return null;
      }
    }.run();
  }

  /**
   * Unsubscribe from notification previously subscribed for the service/characteristic combination.
   *
   * @param serviceUuid UUID of the Bluetooth service.
   * @param characteristicUuid UUID of the Bluetooth characteristic.
   */
  void UnregisterForValues(final String serviceUuid, final String characteristicUuid) {
    UnregisterForValues(serviceUuid, characteristicUuid, null);
  }

  void UnregisterForValues(final String serviceUuid, final String characteristicUuid,
                           final BluetoothLE.BLEResponseHandler<?> handler) {
    final String METHOD = "UnsubscribeForValues";
    new BLEAction<Void>(METHOD) {
      @Override
      public Void action() {
        UUID characteristic = bleStringToUuid(characteristicUuid);
        List<BLEOperation> operations = pendingOperationsByUuid.get(characteristic);
        if (operations != null) {
          // Copy the list to prevent ConcurrentModificationException
          List<BLEOperation> readOnlyList = new ArrayList<BLEOperation>(operations);
          for (BLEOperation operation : readOnlyList) {
            if (operation.isNotify()) {
              operation.unsubscribe(mBluetoothGatt);
            }
          }
        }
        return null;
      }
    }.run();
  }

  int FoundDeviceRssi(final int index) {
    if (index < 1 || index > mLeDevices.size()) {
      throw new IllegalArgumentException("Expected device index between 1 and " + mLeDevices.size());
    }
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
    if (index < 1 || index > mLeDevices.size()) {
      throw new IllegalArgumentException("Expected device index between 1 and " + mLeDevices.size());
    }
    if (index <= mLeDevices.size()) {
      Log.i(LOG_TAG, "Device Name is found");
      return mLeDevices.get(index - 1).getName();
    } else {
      Log.e(LOG_TAG, "Device Name isn't found");
      return null;
    }
  }

  String FoundDeviceAddress(int index) {
    if (index < 1 || index > mLeDevices.size()) {
      throw new IllegalArgumentException("Expected device index between 1 and " + mLeDevices.size());
    }
    if (index <= mLeDevices.size()) {
      Log.i(LOG_TAG, "Device Address is found");
      return mLeDevices.get(index - 1).getAddress();
    } else {
      Log.e(LOG_TAG, "Device Address is found");
      return "";
    }
  }

  String ConnectedDeviceName() {
    if (isConnected && mBluetoothGatt != null) {
      BluetoothDevice device = mBluetoothGatt.getDevice();
      if (device != null) {
        return device.getName();
      }
    }
    return "";
  }

  void StartAdvertising(final String inData, final String serviceUuid) {
    new BLEAction<Void>("StartAdvertising"){
      @Override
      public Void action() {
        // Obtain an instance of the Bluetooth Adapter
        BluetoothAdapter btAdapter = obtainBluetoothAdapter();

        // Ensure that the current Bluetooth Adapter supports Advertising.
        if (!btAdapter.isMultipleAdvertisementSupported()) {
          Log.i(LOG_TAG, "Adapter does not support Bluetooth Advertisements.");
          signalError("StartAdvertising", ERROR_ADVERTISEMENTS_NOT_SUPPORTED);
          return null;
        }

        if (!validateUUID(serviceUuid, "Service", "StartAdvertising"))
          return null;

        // Create a scan callback if it does not already exist. If it does, you're already advertising.
        mBluetoothLeAdvertiser = btAdapter.getBluetoothLeAdvertiser();

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
        return null;
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

        // Obtain an instance of the Bluetooth Adapter
        BluetoothAdapter btAdapter = obtainBluetoothAdapter();

        if (btAdapter != null) {
          mBluetoothLeAdvertisementScanner = btAdapter.getBluetoothLeScanner();

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

  int ConnectedDeviceRssi() {
    return device_rssi;
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
        Set<BluetoothConnectionListener> listeners =
            new HashSet<BluetoothConnectionListener>(outer.connectionListeners);
        for (BluetoothConnectionListener listener : listeners) {
          listener.onConnected(outer);
        }
      }
    });
  }

  private void Disconnected() {
    uiThread.post(new Runnable() {
      @Override
      public void run() {
        try {
          Set<BluetoothConnectionListener> listeners =
              new HashSet<BluetoothConnectionListener>(outer.connectionListeners);
          for (BluetoothConnectionListener listener : listeners) {
            listener.onDisconnected(outer);
          }
        } finally {
          EventDispatcher.dispatchEvent(outer, "Disconnected");
        }
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

  YailList getSupportedServicesList() {
    if (mGattService == null) return YailList.makeEmptyList();
    YailList result = YailList.makeEmptyList();
    for (BluetoothGattService service : mGattService) {
      UUID serviceUuid = service.getUuid();
      YailList pair = YailList.makeList(new Object[] {
          serviceUuid.toString(),
          BluetoothLEGattAttributes.lookup(serviceUuid, UNKNOWN_SERVICE_NAME)
          });
      result.add(pair);
    }
    return result;
  }

  String GetServiceByIndex(int index) {
    if (index < 1 || index > mGattService.size()) {
      throw new IndexOutOfBoundsException("Expected service index between 1 and " + mGattService.size());
    }
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

  YailList getSupportedCharacteristicsList() {
    if (mGattService == null) return YailList.makeEmptyList();
    YailList result = YailList.makeEmptyList();
    for (BluetoothGattService service : mGattService) {
      for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
        UUID serviceUuid = service.getUuid();
        UUID characteristicUuid = characteristic.getUuid();
        YailList triple = YailList.makeList(new Object[] {
            serviceUuid.toString(),
            characteristicUuid.toString(),
            BluetoothLEGattAttributes.lookup(characteristicUuid, UNKNOWN_CHAR_NAME)
        });
        result.add(triple);
      }
    }
    return result;
  }

  YailList GetCharacteristicsForService(String serviceUuid) {
    if (mGattService == null) {
      return YailList.makeEmptyList();
    }
    List<YailList> result = new ArrayList<YailList>();
    final String unknownCharString = "Unknown Characteristic";
    try {
      BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString(serviceUuid));
      if (service == null) {
        throw new IllegalArgumentException("Device does not advertise service " + serviceUuid);
      }
      for (BluetoothGattCharacteristic c : service.getCharacteristics()) {
        result.add(YailList.makeList(new Object[] {c.getUuid().toString().toUpperCase(),
            BluetoothLEGattAttributes.lookup(c.getUuid(), unknownCharString)}));
      }
    } catch(Exception e) {
      Log.e(LOG_TAG, "Exception while looking up BluetoothLE service", e);
      throw new RuntimeException("Exception while looking up BluetoothLE service: " +
          e.getMessage(), e);
    }
    return YailList.makeList(result);
  }

  String GetCharacteristicByIndex(int index) {
    if (index < 1 || index > gattChars.size()) {
      throw new IndexOutOfBoundsException("Expected characteristic index between 1 and " +
          gattChars.size());
    }
    return gattChars.get(index - 1).getUuid().toString();
  }

  boolean isServicePublished(String serviceUuid) {
    UUID service = bleStringToUuid(serviceUuid);
    return mBluetoothGatt != null && mBluetoothGatt.getService(service) != null;
  }

  boolean isCharacteristicPublished(String serviceUuid, String characteristicUuid) {
    UUID service = bleStringToUuid(serviceUuid);
    UUID characteristic = bleStringToUuid(characteristicUuid);
    if (mBluetoothGatt != null) {
      BluetoothGattService gattService = mBluetoothGatt.getService(service);
      if (gattService != null) {
        return gattService.getCharacteristic(characteristic) != null;
      }
    }
    return false;
  }

  void setConnectionTimeout(int timeout) {
    this.connectionTimeout = timeout;
  }

  int getConnectionTimeout() {
    return this.connectionTimeout;
  }

  void setAutoReconnect(boolean autoReconnect) {
    this.autoReconnect = autoReconnect;
  }

  boolean getAutoReconnect() {
    return this.autoReconnect;
  }

  /*
   * Non-static helper functions. For static helpers, see {@link edu.mit.appinventor.ble.BLEUtil}.
   */

  /**
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
        container.$form().dispatchErrorOccurredEvent(BluetoothLEint.this.outer, functionName,
            ErrorMessages.ERROR_EXTENSION_ERROR, errorNumber, "BluetoothLE", errorMessage);
      }
    });
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

    if (!isServiceRead) {
      throw new IllegalStateException("Not connected to a Bluetooth low energy device.");
    } else if (!mGattService.isEmpty()) {
      for (BluetoothGattService aMGattService : mGattService) {
        if (aMGattService.getUuid().equals(ser_uuid)) {
          return aMGattService.getCharacteristic(char_uuid);
        }
      }
    }

    throw new IllegalStateException("Service " + ser_uuid + ", characteristic " + char_uuid + " are not published by the connected device.");
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

  private void schedulePendingOperation(BLEOperation operation) {
    synchronized (pendingOperations) {
      pendingOperations.add(operation);
      if (pendingOperations.size() == 1) {
        mHandler.post(operation);
      }
    }
  }

  private boolean runPendingOperation(BLEOperation after) {
    synchronized (pendingOperations) {
      boolean removed = false;
      BLEOperation operation = pendingOperations.peek();
      if (operation == after) {
        pendingOperations.poll();
        removed = true;
        operation = pendingOperations.peek();
        Log.d(LOG_TAG, "running next operation " + operation);
        if (operation != null) {
          mHandler.post(operation);
        }
      } else {
        Log.d(LOG_TAG, "after operation = " + after);
        Log.d(LOG_TAG, "pending operation = " + operation);
      }
      return removed;
    }
  }

  private void scheduleConnectionTimeoutMessage() {
    uiThread.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (!isConnected && mBluetoothGatt != null) {
          mBluetoothGatt.disconnect();
          outer.ConnectionFailed("Connection timeout reached");
        }
      }
    }, connectionTimeout * 1000);
  }

  private void forceDisconnect() {
    if (mBluetoothGatt != null) {
      if (isConnected) {
        mBluetoothGatt.disconnect();
        mBluetoothGatt.close();
        mBluetoothGatt = null;
      } else if (autoReconnect) {
        mBluetoothGatt.disconnect();
        mBluetoothGatt.close();
        mBluetoothGatt = null;
      }
      isConnected = false;
      isUserDisconnect = true;
    }
    mBluetoothGatt = null;
  }

  private static UUID bleStringToUuid(String uuid) {
    uuid = uuid.toLowerCase();
    if (uuid.length() == 4) {  // 16 bit short Bluetooth UUID
      uuid = "0000" + uuid + BLUETOOTH_BASE_UUID_SUFFIX;
    } else if (uuid.length() == 8) {  // 32 bit short Bluetooth UUID
      uuid = uuid + BLUETOOTH_BASE_UUID_SUFFIX;
    } else if (uuid.length() == 32) {  // 128 bit Bluetooth UUID without dashes
      uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) +
          "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
    } else if (!BLEUtil.hasValidUUIDFormat(uuid)) {
      throw new IllegalArgumentException("Invalid UUID: " + uuid);
    }
    return UUID.fromString(uuid);
  }
}
