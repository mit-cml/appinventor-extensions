// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2015-2017 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import android.app.Activity;

import android.content.pm.PackageManager;

import android.util.Log;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;

import com.google.appinventor.components.common.ComponentCategory;

import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.lists.FString;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author Andrew McKinney (mckinney@mit.edu)
 * @author Cristhian Ulloa (cristhian2ulloa@gmail.com)
 * @author tiffanyle (le.tiffanya@gmail.com)
 * @author William Byrne (will2596@gmail.com) (minor bugfixes)
 */

@DesignerComponent(version = 20171107,
    description = "Bluetooth Low Energy, also referred to as Bluetooth LE " +
        "or simply BLE, is a new communication protocol similar to classic Bluetooth except " +
        "that it is designed to consume less power while maintaining comparable " +
        "functionality. For this reason, Bluetooth LE is the preferred choice of " +
        "communication with IoT devices that have limited power resources. " +
        "Starting with Android 4.3, Google introduced built-in support for Bluetooth Low Energy. " +
        "The BluetoothLE extension requires Android 5.0 or higher to avoid known " +
        "issues with Google's Bluetooth LE support prior to Android 5.0.",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/bluetoothle/bluetoothleintro",
    iconName = "images/bluetooth.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.BLUETOOTH, " + "android.permission.BLUETOOTH_ADMIN,"
    + "android.permission.ACCESS_COARSE_LOCATION")
public class BluetoothLE extends AndroidNonvisibleComponent implements Component, Deleteable {
  public static final int ERROR_DEVICE_INDEX_OOB = 9101;
  public static final int ERROR_SERVICE_INDEX_OOB = 9102;
  public static final int ERROR_SERVICE_INVALID_UUID = 9103;
  public static final int ERROR_CHARACTERISTIC_INDEX_OOB = 9104;

  /**
   * Basic Variables
   */
  private static final String LOG_TAG = "BluetoothLE";
  private final Activity activity;
  private BluetoothLEint inner;
  Set<BluetoothConnectionListener> connectionListeners = new HashSet<BluetoothConnectionListener>();

  @Override
  public void onDelete() {
    if (inner != null) {
      inner.Disconnect();
      inner = null;
    }
  }

  public static abstract class BLEResponseHandler<T> {
    public void onReceive(String serviceUuid, String characteristicUuid, List<T> values) {
    }
    public void onWrite(String serviceUuid, String characteristicUuid, List<T> values) {
    }
  }

  public interface BluetoothConnectionListener {
    void onConnected(BluetoothLE bleConnection);
    void onDisconnected(BluetoothLE bleConnection);
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

  public Form getForm() {
    return form;
  }

  public void addConnectionListener(BluetoothConnectionListener listener) {
    connectionListeners.add(listener);
  }

  public void removeConnectionListener(BluetoothConnectionListener listener) {
    connectionListeners.remove(listener);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN,
      defaultValue = "false")
  @SimpleProperty
  public void AutoReconnect(boolean autoReconnect) {
    inner.setAutoReconnect(autoReconnect);
  }

  @SimpleProperty(category = PropertyCategory.BEHAVIOR,
      description = "If true, the application will attempt to reestablish a lost connection to a " +
          "device due to link loss (e.g., moving out of range). This will not apply to " +
          "connections that are disconnected by a call to the " +
          "<a href='#Disconnect'><code>Disconnect</code></a> method. Such disconnects will need " +
          "to be reconnected via a call to <a href='#Connect'><code>Connect</code></a> or " +
          "<a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>.")
  public boolean AutoReconnect() {
    return inner.getAutoReconnect();
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
      defaultValue = "10")
  @SimpleProperty
  public void ConnectionTimeout(int timeout) {
    inner.setConnectionTimeout(timeout);
  }

  @SimpleProperty(category = PropertyCategory.BEHAVIOR,
      description = "The amount of time, in seconds, that the BluetoothLE component will wait " +
          "for a connection to be established with a device after a call to " +
          "<a href='#Connect'><code>Connect</code></a> or " +
          "<a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>. If a connection is " +
          "not established in the given amount of time, the attempt will be aborted and the " +
          "<a href='#ConnectionFailed'><code>ConnectionFailed</code></a> event will be run.")
  public int ConnectionTimeout() {
    return inner.getConnectionTimeout();
  }

  /**
   * Starts scanning for Bluetooth low energy devices.
   */
  @SimpleFunction
  public void StartScanning() {
    if (inner != null) {
      inner.StartScanning();
    }
  }

  /**
   * Stops scanning for Bluetooth low energy devices.
   */
  @SimpleFunction
  public void StopScanning() {
    if (inner != null) {
      inner.StopScanning();
    }
  }

  /**
   * The scanning state of the Bluetooth low energy component.
   *
   * @return true if the device is scanning, otherwise false.
   */
  @SimpleProperty(description = "The scanning state of the Bluetooth low energy component.")
  public boolean Scanning() {
    if (inner != null) {
      return inner.isScanning();
    } else {
      return false;
    }
  }

  /**
   * Use the <code>Connect</code> method to connect to a Bluetooth low energy device at the given
   * index in the device list.
   *
   * __Parameters__:
   *
   *     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The index of the target device, which must be between 1 and the length of the list.
   *
   * @param index The index of the target device, which must be between 1 and the length of the list.
   */
  @SimpleFunction
  public void Connect(int index) {
    if (inner != null) {
      try {
        inner.Connect(index);
      } catch(IndexOutOfBoundsException e) {
        form.dispatchErrorOccurredEvent(this, "Connect", ErrorMessages.ERROR_EXTENSION_ERROR,
            ERROR_DEVICE_INDEX_OOB, LOG_TAG, e.getMessage());
      }
    }
  }

  /**
   * Use the <code>ConnectWithAddress</code> method to connect to a specific Bluetooth low energy
   * device if its Media Access Control (MAC) address is known. If none of the devices in the device
   * list matches the given address, the <a href="#ConnectionFailed"><code>ConnectionFailed</code></a>
   * event will be run. Otherwise, if a connection is successful the
   * <a href="#Connected"><code>Connected</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>address</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string" target="_blank">_text_</a>) &mdash;
   *       The MAC address of the target device, of the form "12:34:56:78:90:ab"
   *
   * @param address The MAC address of the target device, of the form "12:34:56:78:90:ab"
   */
  @SimpleFunction
  public void ConnectWithAddress(String address) {
    if (inner != null) {
      inner.ConnectWithAddress(address);
    }
  }

  /**
   * Disconnects from the currently connected BluetoothLE device if a device is connected.
   */
  @SimpleFunction
  public void Disconnect() {
    if(inner != null) {
      inner.Disconnect();
    }
  }

  /**
   * Disconnects from a connected BluetoothLE device with the given address.
   *
   * __Parameters__:
   *
   *     * <code>address</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string" target="_blank">_text_</a>) &mdash;
   *       The Media Access Control (MAC) address of the device to disconnect, of the form "12:34:56:78:90:ab"
   *
   * @param address The Media Access Control (MAC) address of the device to disconnect, of the form "12:34:56:78:90:ab"
   */
  @SimpleFunction
  public void DisconnectWithAddress(String address) {
    if (inner != null) {
      inner.DisconnectWithAddress(address);
    }
  }

  /**
   * Reads one or more 8-bit integer values from a connected BluetoothLE device. Service Unique ID
   * and Characteristic UniqueID are required. The <code>signed</code> parameter indicates whether
   * the bytes should be interpreted as signed values or not when being converted into App Inventor
   * numbers. After the bytes are read, the <a href="#BytesReceived"><code>BytesReceived</code></a>
   * event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the bytes as signed (true) or unsigned (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the bytes as signed (true) or unsigned (false).
   */
  @SimpleFunction
  public void ReadBytes(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.ReadByteValues(serviceUuid, characteristicUuid, signed);
    }
  }

  /**
   * Registers to receive updates when one or more 8-bit integer values from a connected BluetoothLE
   * device are changed. Service Unique ID and Characteristic Unique ID are required. The
   * <code>signed</code> parameter indicates whether the bytes should be interpreted as signed
   * values or not when being converted into App Inventor numbers. Whenever a change is received,
   * the <a href="#BytesReceived"><code>BytesReceived</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the bytes as signed (true) or unsigned (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the bytes as signed (true) or unsigned (false).
   */
  @SimpleFunction
  public void RegisterForBytes(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.RegisterForByteValues(serviceUuid, characteristicUuid, signed);
    }
  }

  /**
   * Writes one or more 8-bit integer values to a connected BluetoothLE device. Service Unique ID
   * and Characteristic Unique ID are required. The values parameter can either be a single numeric
   * value or a list of values. If <code>signed</code> is true, the acceptable values are
   * between -128 and 127. If <code>signed</code> is false, the acceptable values are
   * between 0 and 255.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the bytes as signed (true) or unsigned (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the bytes as signed (true) or unsigned (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteBytes(String serviceUuid, String characteristicUuid, boolean signed,
                         Object values) {
    if (inner != null) {
      inner.WriteByteValues(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, values, 1));
    }
  }

  /**
   * Writes one or more 8-bit integer values to a connected BluetoothLE device and waits for an
   * acknowledgement via the <a href="#BytesWritten"><code>BytesWritten</code></a> event.
   * Service Unique ID and Characteristic Unique ID are required. The values parameter can either
   * be a single numeric value or a list of values. If <code>signed</code> is true, the acceptable
   * values are between -128 and 127. If <code>signed</code> is false, the acceptable values are
   * between 0 and 255.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the bytes as signed (true) or unsigned (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the bytes as signed (true) or unsigned (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteBytesWithResponse(String serviceUuid, String characteristicUuid,
                                     boolean signed, Object values) {
    if (inner != null) {
      inner.WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, values, 1));
    }
  }

  /**
   * Reads one or more 16-bit integer values from a connected BluetoothLE device. Service Unique ID
   * and Characteristic UniqueID are required. The <code>signed</code> parameter indicates whether
   * the shorts should be interpreted as signed values or not when being converted into App Inventor
   * numbers. After the shorts are read, the <a href="#ShortsReceived"><code>ShortsReceived</code></a>
   * event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the shorts as signed (true) or unsigned (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the shorts as signed (true) or unsigned (false).
   */
  @SimpleFunction
  public void ReadShorts(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.ReadShortValues(serviceUuid, characteristicUuid, signed);
    }
  }

  /**
   * Registers to receive updates when one or more 16-bit integer values from a connected BluetoothLE
   * device are changed. Service Unique ID and Characteristic Unique ID are required. The
   * <code>signed</code> parameter indicates whether the shorts should be interpreted as signed
   * values or not when being converted into App Inventor numbers. Whenever a change is received,
   * the <a href="#ShortsReceived"><code>ShortsReceived</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the shorts as signed (true) or unsigned (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the shorts as signed (true) or unsigned (false).
   */
  @SimpleFunction
  public void RegisterForShorts(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.RegisterForShortValues(serviceUuid, characteristicUuid, signed);
    }
  }

  /**
   * Writes one or more 16-bit integer values to a connected BluetoothLE device. Service Unique ID
   * and Characteristic Unique ID are required. The values parameter can either be a single numeric
   * value or a list of values. If <code>signed</code> is true, the acceptable values are
   * between -32768 and 32767. If <code>signed</code> is false, the acceptable values are
   * between 0 and 65535.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the shorts as signed (true) or unsigned (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the shorts as signed (true) or unsigned (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteShorts(String serviceUuid, String characteristicUuid, boolean signed,
                          Object values) {
    if (inner != null) {
      inner.WriteShortValues(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, values, 2));
    }
  }

  /**
   * Writes one or more 16-bit integer values to a connected BluetoothLE device and waits for an
   * acknowledgement via the <a href="#ShortsWritten"><code>ShortsWritten</code></a> event.
   * Service Unique ID and Characteristic Unique ID are required. The values parameter can either
   * be a single numeric value or a list of values. If <code>signed</code> is true, the acceptable
   * values are between -32768 and 32767. If <code>signed</code> is false, the acceptable values
   * are between 0 and 65535.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the shorts as signed (true) or unsigned (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the shorts as signed (true) or unsigned (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteShortsWithResponse(String serviceUuid, String characteristicUuid,
                                      boolean signed, Object values) {
    if (inner != null) {
      inner.WriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, values, 2));
    }
  }

  /**
   * Reads one or more 32-bit integer values from a connected BluetoothLE device. Service Unique ID
   * and Characteristic UniqueID are required. The <code>signed</code> parameter indicates whether
   * the integers should be interpreted as signed values or not when being converted into App Inventor
   * numbers. After the integers are read, the
   * <a href="#IntegersReceived"><code>IntegersReceived</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the integers as signed (true) or unsigned (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the integers as signed (true) or unsigned (false).
   */
  @SimpleFunction
  public void ReadIntegers(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.ReadIntegerValues(serviceUuid, characteristicUuid, signed);
    }
  }

  /**
   * Registers to receive updates when one or more 32-bit integer values from a connected BluetoothLE
   * device are changed. Service Unique ID and Characteristic Unique ID are required. The
   * <code>signed</code> parameter indicates whether the integers should be interpreted as signed
   * values or not when being converted into App Inventor numbers. Whenever a change is received,
   * the <a href="#IntegersReceived"><code>IntegersReceived</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the integers as signed (true) or unsigned (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the integers as signed (true) or unsigned (false).
   */
  @SimpleFunction
  public void RegisterForIntegers(String serviceUuid, String characteristicUuid, boolean signed) {
    if (inner != null) {
      inner.RegisterForIntegerValues(serviceUuid, characteristicUuid, signed);
    }
  }

  /**
   * Writes one or more 32-bit integer values to a connected BluetoothLE device. Service Unique ID
   * and Characteristic Unique ID are required. The values parameter can either be a single numeric
   * value or a list of values. If <code>signed</code> is true, the acceptable values are
   * between -2147483648 and 2147483647. If <code>signed</code> is false, the acceptable values are
   * between 0 and 4294967295.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the integers as signed (true) or unsigned (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the integers as signed (true) or unsigned (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteIntegers(String serviceUuid, String characteristicUuid, boolean signed,
                            Object values) {
    if (inner != null) {
      inner.WriteIntegerValues(serviceUuid, characteristicUuid, signed,
          toList(Long.class, values, 4));
    }
  }

  /**
   * Writes one or more 32-bit integer values to a connected BluetoothLE device and waits for an
   * acknowledgement via the <a href="#IntegersWritten"><code>IntegersWritten</code></a> event.
   * Service Unique ID and Characteristic Unique ID are required. The values parameter can either
   * be a single numeric value or a list of values. If <code>signed</code> is true, the acceptable
   * values are between -2147483648 and 2147483647. If <code>signed</code> is false, the acceptable
   * values are between 0 and 4294967295.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the integers as signed (true) or unsigned (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param signed Interpret the integers as signed (true) or unsigned (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteIntegersWithResponse(String serviceUuid, String characteristicUuid,
                                        boolean signed, Object values) {
    if (inner != null) {
      inner.WriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed,
          toList(Long.class, values, 4));
    }
  }

  /**
   * Reads one or more IEEE 754 floating point numbers from a connected BluetoothLE device. Service Unique ID
   * and Characteristic UniqueID are required. The <code>shortFloat</code> parameter indicates whether
   * the floats are either 16-bit half-precision floating point or 32-bit single precision floating point
   * numbers. After the floats are read, the <a href="#FloatsReceived"><code>FloatsReceived</code></a>
   * event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param shortFloat Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   */
  @SimpleFunction
  public void ReadFloats(String serviceUuid, String characteristicUuid, boolean shortFloat) {
    if (inner != null) {
      inner.ReadFloatValues(serviceUuid, characteristicUuid, shortFloat);
    }
  }

  /**
   * Registers to receive updates when one or more IEEE 754 floating point numbers from a connected
   * BluetoothLE device are changed. Service Unique ID and Characteristic Unique ID are required. The
   * <code>shortFloat</code> parameter indicates whether the floats are either 16-bit half-precision
   * floating point or 32-bit single precision floating point numbers. Whenever a change is received,
   * the <a href="#FloatsReceived"><code>FloatsReceived</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param shortFloat Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   */
  @SimpleFunction
  public void RegisterForFloats(String serviceUuid, String characteristicUuid, boolean shortFloat) {
    if (inner != null) {
      inner.RegisterForFloatValues(serviceUuid, characteristicUuid, shortFloat);
    }
  }

  /**
   * Writes one or more IEEE 754 floating point numbers to a connected BluetoothLE device. Service Unique ID
   * and Characteristic Unique ID are required. The values parameter can either be a single numeric
   * value or a list of values. If <code>shortFloat</code> is true, then each numeric value will be
   * compressed to fit into a 16-bit half-precision floating point value. If <code>shortFloat</code>
   * is false, then each numeric value will be sent as a 32-bit single precision floating point value.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param shortFloat Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteFloats(String serviceUuid, String characteristicUuid, boolean shortFloat,
                          Object values) {
    if (inner != null) {
      inner.WriteFloatValues(serviceUuid, characteristicUuid, shortFloat,
          toList(Float.class, values, shortFloat ? 2 : 4));
    }
  }

  /**
   * Writes one or more IEEE 754 floating point values to a connected BluetoothLE device and waits for an
   * acknowledgement via the <a href="#FloatsWritten"><code>FloatsWritten</code></a> event.
   * Service Unique ID and Characteristic Unique ID are required. The values parameter can either
   * be a single numeric value or a list of values. If <code>shortFloat</code> is false,
   * then each numeric value will be sent as a 32-bit single precision floating point value.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param shortFloat Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteFloatsWithResponse(String serviceUuid, String characteristicUuid,
                                      boolean shortFloat, Object values) {
    if (inner != null) {
      inner.WriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloat,
          toList(Float.class, values, shortFloat ? 2 : 4));
    }
  }

  /**
   * Reads one or more null-terminated strings from a connected BluetoothLE device. Service Unique ID
   * and Characteristic Unique ID are required. The <code>utf16</code> parameter indicates whether
   * the content should be decoded as UTF-16 (true) or UTF-8 (false) code points when converting to
   * App Inventor strings. After the strings are read, the
   * <a href="#StringsReceived"><code>StringsReceived</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the string content as UTF-16 (true) or UTF-8 (false) code points.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param utf16 Interpret the string content as UTF-16 (true) or UTF-8 (false) code points.
   */
  @SimpleFunction
  public void ReadStrings(String serviceUuid, String characteristicUuid, boolean utf16) {
    if (inner != null) {
      inner.ReadStringValues(serviceUuid, characteristicUuid, utf16);
    }
  }

  /**
   * Registers to receive updates when one or more null-terminated strings from a connected
   * BluetoothLE device are changed. Service Unique ID and Characteristic Unique ID are required. The
   * <code>utf16</code> parameter indicates whether the content should be decoded as UTF-16 (true)
   * or UTF-8 (false) code points when converting to App Inventor strings. Whenever a change is
   * received, the <a href="#StringsReceived"><code>StringsReceived</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Interpret the string content as UTF-16 (true) or UTF-8 (false) code points.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param utf16 Interpret the string content as UTF-16 (true) or UTF-8 (false) code points.
   */
  @SimpleFunction
  public void RegisterForStrings(String serviceUuid, String characteristicUuid, boolean utf16) {
    if (inner != null) {
      inner.RegisterForStringValues(serviceUuid, characteristicUuid, utf16);
    }
  }

  /**
   * Writes one or more strings to a connected BluetoothLE device. Service Unique ID and
   * Characteristic Unique ID are required. The values parameter can either be a single string or a
   * list of strings. If <code>utf16</code> is true, the string(s) will be sent using UTF-16 little
   * endian encoding. If <code>utf16</code> is false, the string(s) will be sent using UTF-8
   * encoding.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Send the string encoded as UTF-16 little endian (true) or UTF-8 (false) code points.
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param utf16 Send the string encoded as UTF-16 little endian (true) or UTF-8 (false) code points.
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteStrings(String serviceUuid, String characteristicUuid, boolean utf16,
                           Object values) {
    if (inner != null) {
      inner.WriteStringValues(serviceUuid, characteristicUuid, utf16,
          toList(String.class, values, utf16 ? 2 : 1));
    }
  }

  /**
   * Writes one or more strings to a connected BluetoothLE device and waits for an acknowledgement
   * via the <a href="#StringsWritten"><code>StringsWritten</code></a> event. Service Unique ID and
   * Characteristic Unique ID are required. The values parameter can either be a single string or a
   * list of values. If <code>utf16</code> is true, the string(s) will be sent using UTF-16 little
   * endian encoding. If <code>utf16</code> is false, the string(s) will be sent using UTF-8
   * encoding.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
   *       Send the string encoded as UTF-16 little endian (true) or UTF-8 (false) code points.
   *     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values to write to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param utf16 Send the string encoded as UTF-16 little endian (true) or UTF-8 (false) code points.
   * @param values A list of values to write to the device.
   */
  @SimpleFunction
  public void WriteStringsWithResponse(String serviceUuid, String characteristicUuid,
                                       boolean utf16, Object values) {
    if (inner != null) {
      inner.WriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16,
          toList(String.class, values, utf16 ? 2 : 1));
    }
  }

  /**
   * Unregisters for updates from the given service and characteristic.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *
   * @param service_uuid The unique identifier of the service passed in the read or register call.
   * @param characteristic_uuid The unique identifier of the characteristic in the read or register call.
   */
  @SimpleFunction
  public void UnregisterForValues(String service_uuid, String characteristic_uuid) {
    if (inner != null) {
      inner.UnregisterForValues(service_uuid, characteristic_uuid);
    }
  }

  /**
   * Gets the Received Signal Strength Indicator (RSSI) of the found device at the given index.
   * The returned value will be between -100 and 0 indicating the strength of the connection.
   *
   * __Parameter__:
   *
   *     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The index of the desired device, which must be between 1 and the length of the device list.
   *
   * @param index The index of the desired device, which must be between 1 and the length of the device list.
   * @return the RSSI
   */
  @SimpleFunction
  public int FoundDeviceRssi(int index) {
    if (inner != null) {
      try {
        return inner.FoundDeviceRssi(index);
      } catch(IndexOutOfBoundsException e) {
        form.dispatchErrorOccurredEvent(this, "FoundDeviceRssi",
            ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_DEVICE_INDEX_OOB, LOG_TAG, e.getMessage());
      }
    }
    return 0;
  }

  /**
   * Gets the name of the found device at the given index in the device list.
   *
   * __Parameter__:
   *
   *     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The index of the desired device, which must be between 1 and the length of the device list.
   *
   * @param index The index of the desired device, which must be between 1 and the length of the device list.
   * @return the name of the device, if any.
   */
  @SimpleFunction
  public String FoundDeviceName(int index) {
    if (inner != null) {
      try {
        String result = inner.FoundDeviceName(index);
        return result == null ? "" : result;
      } catch(IllegalArgumentException e) {
        form.dispatchErrorOccurredEvent(this, "FoundDeviceName",
            ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_DEVICE_INDEX_OOB, LOG_TAG, e.getMessage());
      }
    }
    return "";
  }

  /**
   * Gets the Media Access Control (MAC) address of the found device at the given index in the
   * device list. Index specifies the position in the BluetoothLE device list, starting from 1.
   *
   * __Parameter__:
   *
   *     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The index of the desired device, which must be between 1 and the length of the device list.
   *
   * @param index The index of the desired device, which must be between 1 and the length of the device list.
   * @return a string with the MAC address of the device
   */
  @SimpleFunction
  public String FoundDeviceAddress(int index) {
    if (inner != null) {
      try {
        String result = inner.FoundDeviceAddress(index);
        return result == null ? "" : result;
      } catch(IllegalArgumentException e) {
        form.dispatchErrorOccurredEvent(this, "FoundDeviceAddress",
            ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_DEVICE_INDEX_OOB, LOG_TAG, e.getMessage());
      }
    }
    return "";
  }

  /**
   * Creates and publishes a Bluetooth low energy advertisement.
   *
   * __Parameters__:
   *
   *     * <code>inData</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The data to be included in the service advertisement.
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *
   * @param inData The data to be included in the service advertisement.
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   */
  @SimpleFunction
  public void StartAdvertising(String inData, String serviceUuid) {
    if (inner != null) {
      inner.StartAdvertising(inData, serviceUuid);
    }
  }

  /**
   * Stops Bluetooth low energy advertisement from a previous call to
   * <a href="#StartAdvertising"><code>StartAdvertising</code></a>.
   */
  @SimpleFunction
  public void StopAdvertising() {
    if (inner != null) {
      inner.StopAdvertising();
    }
  }

  /**
   * Scans for advertising Bluetooth low energy devices.
   *
   * __Parameter__:
   *
   *     * <code>scanPeriod</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The amount of time to spend scanning, in milliseconds.
   *
   * @param scanPeriod The amount of time to spend scanning, in milliseconds.
   */
  @SimpleFunction
  public void ScanAdvertisements(long scanPeriod) {
    if (inner != null) {
      inner.ScanAdvertisements(scanPeriod);
    }
  }

  /**
   * Stops scanning for Bluetooth low energy advertisements.
   */
  @SimpleFunction
  public void StopScanningAdvertisements() {
    if (inner != null) {
      inner.StopScanningAdvertisements();
    }
  }

  /**
   * Returns the advertisement data associated with the specified device address.
   *
   * __Parameters__:
   *
   *     * <code>deviceAddress</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The Media Access Control (MAC) address of the target Bluetooth low energy device.
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the advertised service.
   *
   * @param deviceAddress The Media Access Control (MAC) address of the target Bluetooth low energy device.
   * @param serviceUuid The unique identifier of the advertised service.
   * @return The advertisement data from the device's service, if any.
   */
  @SimpleFunction
  public String AdvertisementData(String deviceAddress, String serviceUuid) {
    if (inner != null) {
      return inner.GetAdvertisementData(deviceAddress, serviceUuid);
    }
    return "";
  }

  /**
   * Returns the address of the device with the name specified.
   *
   * __Parameters__:
   *
   *     * <code>deviceName</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The advertised name of the target Bluetooth low energy device.
   *
   * @param deviceName The advertised name of the target Bluetooth low energy device.
   * @return the address of the device with the given name, if any.
   */
  @SimpleFunction
  public String AdvertiserAddress(String deviceName) {
    if (inner != null) {
      return inner.GetAdvertiserAddress(deviceName);
    }
    return "";
  }

  /**
   * Returns the list of services available on the advertising device.
   *
   * __Parameters__:
   *
   *     * <code>deviceAddress</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The Media Access Control (MAC) address of the target Bluetooth low energy device.
   *
   * @param deviceAddress The Media Access Control (MAC) address of the target Bluetooth low energy device.
   * @return the list of services the device is advertising, if any.
   */
  @SuppressWarnings("unchecked")
  @SimpleFunction
  public List<String> AdvertiserServiceUuids(String deviceAddress) {
    if (inner != null) {
      return inner.GetAdvertiserServiceUuids(deviceAddress);
    }
    return YailList.makeEmptyList();
  }

  @SimpleProperty(description = "Returns the battery level.", category = PropertyCategory.BEHAVIOR)
  public String BatteryValue() {
    if (inner != null) {
      return inner.BatteryValue();
    }
    return "";
  }

  @SimpleProperty(description = "Returns the transmission power.", category = PropertyCategory.BEHAVIOR)
  public int TxPower() {
    if (inner != null) {
      return inner.TxPower();
    }
    return -1;
  }

  @SimpleProperty(description = "Returns true if a BluetoothLE device is connected; Otherwise, returns false.",
      category = PropertyCategory.BEHAVIOR)
  public boolean IsDeviceConnected() {
    if (inner != null) {
      return inner.IsDeviceConnected();
    }
    return false;
  }

  @SimpleProperty(description = "Returns a sorted list of BluetoothLE devices as a String.",
      category = PropertyCategory.BEHAVIOR)
  public String DeviceList() {
    if (inner != null) {
      return inner.DeviceList();
    }
    return "";
  }

  @SimpleProperty(description = "Returns the RSSI (Received Signal Strength Indicator) of connected device.",
      category = PropertyCategory.BEHAVIOR)
  public int ConnectedDeviceRssi() {
    if (inner != null) {
      return inner.ConnectedDeviceRssi();
    }
    return Integer.MIN_VALUE;
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
    return Collections.emptyList();
  }

  @SimpleProperty(description = "Returns a list of the addresses of devices found during Advertisement scanning.")
  public List<String> AdvertiserAddresses() {
    if (inner != null) {
      return inner.GetAdvertiserAddresses();
    }
    return Collections.emptyList();
  }

  @SimpleProperty(description = "Returns true if the device is currently advertising, false otherwise.")
  public boolean IsDeviceAdvertising() {
    if (inner != null) {
      return inner.IsDeviceAdvertising();
    }
    return false;
  }

  /**
   * The <code>Connected</code> event is run after the application successfully connects to a
   * Bluetooth low energy device. This can be the result of a call to
   * <a href='#Connect'><code>Connect</code></a> or
   * <a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>, or as a result of an
   * automatic reconnect if the <a href='#AutoReconnect'><code>AutoReconnect</code></a> property
   * was true at the time a connection was requested.
   */
  @SimpleEvent
  public void Connected() {
  }

  /**
   * The <code>ConnectionFailed</code> event is run when an attempt to connect to a device does not
   * succeed. If a reason is provided by the Bluetooth low energy stack it will be reported via the
   * <code>reason</code> parameter.
   *
   * __Parameters__:
   *
   *     * <code>reason</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string" target="_blank">_text_</a>) &mdash;
   *       The reason the connection failed, if known.
   *
   * @param reason The reason the connection failed, if known.
   */
  @SimpleEvent
  public void ConnectionFailed(final String reason) {
    form.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        EventDispatcher.dispatchEvent(BluetoothLE.this, "ConnectionFailed", reason);
      }
    });
  }

  /**
   * The <code>Disconnected</code> event is run when a Bluetooth low energy device is disconnected.
   * This can be caused by a call to <a href="#Disconnect"><code>Disconnect</code></a> or
   * <a href="#DisconnectWithAddress"><code>DisconnectWithAddress</code></a>, or after a device
   * is moved away or reset such that a loss of connection occurs.
   */
  @SimpleEvent
  public void Disconnected() {
  }

  /**
   * The <code>RssiChanged</code> event is run when the Received Signal Strength Indicator (RSSI) of
   * a discovered Bluetooth low energy device changes.
   *
   * __Parameters__:
   *
   *     * <code>rssi</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The strength of the received signal, in dBm, from -100 to 0.
   *
   * @param rssi the strength of the received signal, from -100 to 0.
   */
  @SimpleEvent(description = "Trigger event when RSSI (Received Signal Strength Indicator) of found" +
      " BluetoothLE device changes")
  public void RssiChanged(final int rssi) {
  }

  /**
   * The <code>DeviceFound</code> event is run when a new Bluetooth low energy device is found.
   */
  @SimpleEvent
  public void DeviceFound() {
  }

  /**
   * The <code>BytesReceived</code> event is run when one or more byte values are received from a
   * connected Bluetooth device. Depending on the <code>sign</code> parameter of the last call to
   * <a href="#ReadBytes"><code>ReadBytes</code></a> or
   * <a href="#RegisterForBytes"><code>RegisterForBytes</code></a> for the given
   * <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>byteValues</code> list
   * will contain numbers ranging from -128 to 127 (<code>signed = true</code>)
   * or 0 to 255 (<code>signed = false</code>).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>byteValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/listsU.html#makealist">_list_</a>) &mdash;
   *       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param byteValues A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.
   */
  @SimpleEvent
  public void BytesReceived(final String serviceUuid, final String characteristicUuid,
                            final YailList byteValues) {
    EventDispatcher.dispatchEvent(this, "BytesReceived", serviceUuid, characteristicUuid,
        byteValues);
  }

  /**
   * The <code>BytesWritten</code> event is run when one or more byte values are written to a
   * connected Bluetooth device. <code>byteValues</code> will be a list of values actually written
   * to the device. This may be different if the original input was too long to fit into a single
   * transmission unit (typically 23 bytes).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>byteValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values written to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param byteValues A list of values written to the device.
   */
  @SimpleEvent
  public void BytesWritten(final String serviceUuid, final String characteristicUuid,
                           final YailList byteValues) {
    EventDispatcher.dispatchEvent(this, "BytesWritten", serviceUuid, characteristicUuid,
        byteValues);
  }

  /**
   * The <code>ShortsReceived</code> event is run when one or more short integer values are received from a
   * connected Bluetooth device. Depending on the <code>sign</code> parameter of the last call to
   * <a href="#ReadShorts"><code>ReadShorts</code></a> or
   * <a href="#RegisterForShorts"><code>RegisterForShorts</code></a> for the given
   * <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>shortValues</code> list
   * will contain numbers ranging from -32768 to 32767 (<code>signed = true</code>)
   * or 0 to 65535 (<code>signed = false</code>).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>shortValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param shortValues A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.
   */
  @SimpleEvent
  public void ShortsReceived(final String serviceUuid, final String characteristicUuid,
                             final YailList shortValues) {
    EventDispatcher.dispatchEvent(this, "ShortsReceived", serviceUuid, characteristicUuid,
        shortValues);
  }

  /**
   * The <code>ShortsWritten</code> event is run when one or more short integers values are written to a
   * connected Bluetooth device. <code>shortValues</code> will be a list of values actually written
   * to the device. This may be different if the original input was too long to fit into a single
   * transmission unit (typically 11 shorts).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>shortValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values written to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param shortValues A list of values written to the device.
   */
  @SimpleEvent
  public void ShortsWritten(final String serviceUuid, final String characteristicUuid,
                            final YailList shortValues) {
    EventDispatcher.dispatchEvent(this, "ShortsWritten", serviceUuid, characteristicUuid,
        shortValues);
  }

  /**
   * The <code>IntegersReceived</code> event is run when one or more 32-bit integer values are received from a
   * connected Bluetooth device. Depending on the <code>sign</code> parameter of the last call to
   * <a href="#ReadIntegers"><code>ReadIntegers</code></a> or
   * <a href="#RegisterForIntegers"><code>RegisterForIntegers</code></a> for the given
   * <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>intValues</code> list
   * will contain numbers ranging from -2147483648 to 2147483647 (<code>signed = true</code>)
   * or 0 to 4294967296 (<code>signed = false</code>).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>intValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param intValues A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.
   */
  @SimpleEvent
  public void IntegersReceived(final String serviceUuid, final String characteristicUuid,
                               final YailList intValues) {
    EventDispatcher.dispatchEvent(this, "IntegersReceived", serviceUuid, characteristicUuid,
        intValues);
  }

  /**
   * The <code>IntegersWritten</code> event is run when one or more 32-bit integers values are written to a
   * connected Bluetooth device. <code>intValues</code> will be a list of values actually written
   * to the device. This may be different if the original input was too long to fit into a single
   * transmission unit (typically 5 integers).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>intValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values written to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param intValues A list of values written to the device.
   */
  @SimpleEvent
  public void IntegersWritten(final String serviceUuid, final String characteristicUuid,
                              final YailList intValues) {
    EventDispatcher.dispatchEvent(this, "IntegersWritten", serviceUuid, characteristicUuid,
        intValues);
  }

  /**
   * The <code>FloatsReceived</code> event is run when one or more IEEE 754 floating point values are received from a
   * connected Bluetooth device. Depending on the <code>shortFloat</code> parameter of the last call to
   * <a href="#ReadFloats"><code>ReadFloats</code></a> or
   * <a href="#RegisterForFloats"><code>RegisterForFloats</code></a> for the given
   * <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>floatValues</code> list
   * will contain numbers ranging from -65504.0 to 65504.0 (<code>shortFloat = true</code>)
   * or -3.402823466E38 to 3.402823466E38 (<code>shortFloat = false</code>).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>floatValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param floatValues A list of values read from the device. The range of each value will depend on the <code>shortFloat</code> flag previously specified in the call to read or register.
   */
  @SimpleEvent
  public void FloatsReceived(final String serviceUuid, final String characteristicUuid,
                             final YailList floatValues) {
    EventDispatcher.dispatchEvent(this, "FloatsReceived", serviceUuid, characteristicUuid,
        floatValues);
  }

  /**
   * The <code>FloatsWritten</code> event is run when one or more IEEE 754 floating point values are written to a
   * connected Bluetooth device. <code>floatValues</code> will be a list of values actually written
   * to the device. This may be different if the original input was too long to fit into a single
   * transmission unit (typically 11 short floats or 5 regular floats).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>floatValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values written to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param floatValues A list of values written to the device.
   */
  @SimpleEvent
  public void FloatsWritten(final String serviceUuid, final String characteristicUuid,
                            final YailList floatValues) {
    EventDispatcher.dispatchEvent(this, "FloatsWritten", serviceUuid, characteristicUuid,
        floatValues);
  }

  /**
   * The <code>StringsReceived</code> event is run when one or more strings are received from a
   * connected Bluetooth device. Depending on the <code>utf16</code> parameter of the last call to
   * <a href="#ReadStrings"><code>ReadStrings</code></a> or
   * <a href="#RegisterForStrings"><code>RegisterForStrings</code></a> for the given
   * <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>stringValues</code> list
   * will contain either a UTF-16 little endian decoded (<code>utf16 = true</code>) or UTF-8
   * decoded (<code>utf16 = false</code>) strings. The string length is limited by the maximum
   * transmission unit (MTU) of the Bluetooth device, which is typically 23 bytes.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>stringValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values read from the device. The strings will be decoded as UTF-16 or UTF-8 based on the <code>utf16</code> flag previously specified in the call to read or register.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param stringValues A list of values read from the device. The strings will be decoded as UTF-16 or UTF-8 based on the <code>utf16</code> flag previously specified in the call to read or register.
   */
  @SimpleEvent
  public void StringsReceived(final String serviceUuid, final String characteristicUuid,
                              final YailList stringValues) {
    EventDispatcher.dispatchEvent(this, "StringsReceived", serviceUuid, characteristicUuid,
        stringValues);
  }

  /**
   * The <code>StringsWritten</code> event is run when one or more strings are written to a
   * connected Bluetooth device. <code>stringValues</code> will be a list of values actually written
   * to the device. This may be different if the original input was too long to fit into a single
   * transmission unit (typically 22 bytes).
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the characteristic in the read or register call.
   *     * <code>stringValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       A list of values written to the device.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @param characteristicUuid The unique identifier of the characteristic in the read or register call.
   * @param stringValues A list of values written to the device.
   */
  @SimpleEvent
  public void StringsWritten(final String serviceUuid, final String characteristicUuid,
                             final YailList stringValues) {
    EventDispatcher.dispatchEvent(this, "StringsWritten", serviceUuid, characteristicUuid, stringValues);
  }

  /**
   * Returns the list of supported service for the connected device as a string.
   *
   * @return a comma-separated string of supported services.
   */
  @SimpleFunction
  public String SupportedServices() {
    if (inner != null) {
      return inner.GetSupportedServices();
    }
    return "";
  }

  /**
   * A list of pairs indicating a service UUID and its name, if known. The format of the list will
   * be ((uuid1 name1) (uuid2 name2) ...). If no device is connected or Bluetooth low energy is not
   * supported, then an empty list will be returned.
   * @return A list of uuid-name pairs for each service supported by the connected device.
   */
  @SimpleProperty(description = "A list of pairs, one for each advertised service, indicating the " +
      "service's UUID and its name, if known. The format of the list will be ((uuid1 name1) (uuid2 name2) ...). " +
      "If no device is connected or Bluetooth low energy is not supported, then an empty list " +
      "will be returned.")
  public YailList DeviceServices() {
    if (inner != null) {
      return inner.getSupportedServicesList();
    } else {
      return YailList.makeEmptyList();
    }
  }

  /**
   * Returns the Unique ID of the service at the given index in the service list.
   *
   * __Parameters__:
   *
   *     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The index of the desired service, which must be between 1 and the length of the service list.
   *
   * @param index The index of the desired service, which must be between 1 and the length of the service list.
   * @return the Unique ID of the service at the given index in the service list.
   */
  @SimpleFunction
  public String ServiceByIndex(int index) {
    if (inner != null) {
      try {
        String result = inner.GetServiceByIndex(index);
        return result == null ? "" : result;
      } catch(IndexOutOfBoundsException e) {
        form.dispatchErrorOccurredEvent(this, "ServiceByIndex",
            ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_SERVICE_INDEX_OOB, LOG_TAG, e.getMessage());
      }
    }
    return "";
  }

  /**
   * Returns a list of supported characteristic for the connected device as a string.
   * @return a list of supported characteristic for the connected device as a string.
   */
  @SimpleFunction
  public String SupportedCharacteristics() {
    if (inner != null) {
      return inner.GetSupportedCharacteristics();
    }
    return "";
  }

  /**
   * A list of triples indicating a service UUID, a characteristic UUID, and the characteristic's
   * name, if known. The format of the list will be
   * ((service1 characteristic1 name1) (service2 characteristic2 name2) ...). If no device is
   * connected or Bluetooth low energy is not supported, then an empty list will be returned.
   * @return a list of service, characteristic, name triples
   */
  @SimpleProperty(description = "A list of triples, one for each characteristic advertised by the " +
      "connected device, containing the service UUID, characteristic UUID, and the characteristic's " +
      "name, if known. The format of the list will be ((service1 characteristic1 name1) " +
      "(service2 characteristic2 name2) ...). If no device is connected or Bluetooth low energy is " +
      "not supported, then an empty list will be returned.")
  public YailList DeviceCharacteristics() {
    if (inner != null) {
      return inner.getSupportedCharacteristicsList();
    } else {
      return YailList.makeEmptyList();
    }
  }

  /**
   * The advertised name of the connected device. If no device is connected or Bluetooth low energy
   * is not supported, this will return the empty string.
   * @return the advertised name of the connected device.
   */
  @SimpleProperty(description = "The advertised name of the connected device. If no device is " +
      "connected or Bluetooth low energy is not supported, this will return the empty string.")
  public String ConnectedDeviceName() {
    if (inner != null) {
      return inner.ConnectedDeviceName();
    } else {
      return "";
    }
  }

  /**
   * Returns the list of supported characteristics for the given service. The list will contain
   * (UUID, name) pairs for each characteristic provided by the service UUID.
   *
   * __Parameters__:
   *
   *     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The unique identifier of the service passed in the read or register call.
   *
   * @param serviceUuid The unique identifier of the service passed in the read or register call.
   * @return a list of uuid-name pairs for the given service.
   */
  @SimpleFunction
  public YailList GetCharacteristicsForService(String serviceUuid) {
    if (inner != null) {
      try {
        return inner.GetCharacteristicsForService(serviceUuid);
      } catch(RuntimeException e) {
        form.dispatchErrorOccurredEvent(this, "GetCharacteristicsForService",
            ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_SERVICE_INVALID_UUID, LOG_TAG, e.getMessage());
      }
    }
    return YailList.makeEmptyList();
  }

  /**
   * Returns Unique ID of selected characteristic with index. Index specified by list
   * of supported characteristics for a connected device, starting from 1.
   *
   * __Parameters__:
   *
   *     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The index of the desired characteristic, which must be between 1 and the length of the characteristic list.
   *
   * @param index The index of the desired characteristic, which must be between 1 and the length of the characteristic list.
   * @return the UUID of the desired characteristic.
   */
  @SimpleFunction
  public String CharacteristicByIndex(int index) {
    if (inner != null) {
      try {
        String result = inner.GetCharacteristicByIndex(index);
        return result == null ? "" : result;
      } catch(IndexOutOfBoundsException e) {
        form.dispatchErrorOccurredEvent(this, "CharacteristicByIndex",
            ErrorMessages.ERROR_EXTENSION_ERROR, ERROR_CHARACTERISTIC_INDEX_OOB, LOG_TAG,
            e.getMessage());
      }
    }
    return "";
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
      inner.WriteByteValues(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, values, 1));
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
      inner.WriteShortValues(serviceUuid, characteristicUuid, signed,
          toList(Integer.class, values, 2));
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
      inner.WriteIntegerValues(serviceUuid, characteristicUuid, signed,
          toList(Long.class, values, 4));
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
      inner.WriteFloatValues(serviceUuid, characteristicUuid, shortFloats,
          toList(Float.class, values, shortFloats ? 2 : 4));
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
      inner.WriteStringValues(serviceUuid, characteristicUuid, utf16,
          toList(String.class, values, utf16 ? 2 : 1));
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

  public boolean isServicePublished(String serviceUuid) {
    if (inner != null) {
      return inner.isServicePublished(serviceUuid);
    }
    return false;
  }

  public boolean isCharacteristicPublished(String serviceUuid, String characteristicUuid) {
    if (inner != null) {
      return inner.isCharacteristicPublished(serviceUuid, characteristicUuid);
    }
    return false;
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
   * 3. If the value is a FString:
   *    a. Convert it to a Java string (FString is a Collection) and then
   *       process as a String
   * 3. If value is a collection or list:
   *    a. Create a new list checking the type of each entry against T
   * 4. If value is a CharSequence (String)
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
    } else if (Number.class.isAssignableFrom(tClass)) {
      if (value instanceof FString) {
        return toList(tClass, stringToNumber(value.toString()), size);
      } else if (! (value instanceof Collection)) {
        return toList(tClass, Collections.singletonList(value), size);
      } else {
        return listFromIterator(tClass, ((Collection<?>) value).iterator());
      }
    } else if (value instanceof FString) {  // needs to come before List
      // this assumes that the string is being cast to a list of UTF-8 bytes
      return toList(tClass, value.toString(), size);
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
    } else {
      Log.i("BLE", "Is number assignable from " + tClass + "? " + Number.class.isAssignableFrom(tClass));
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
      return (List<T>) toIntList((List<? extends Number>)(List) newArrayList(i));
    } else if (tClass.equals(Long.class)) {
      return (List<T>) toLongList((List<? extends Number>)(List) newArrayList(i));
    } else if (tClass.equals(Float.class)) {
      return (List<T>) toFloatList((List<? extends Number>)(List) newArrayList(i));
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

  private static <T> List<T> newArrayList(Iterator<? extends T> it) {
    List<T> result = new ArrayList<T>();
    while (it.hasNext()) {
      result.add(it.next());
    }
    return result;
  }

  private static Number stringToNumber(String value) {
    return Double.parseDouble(value);
  }
}


