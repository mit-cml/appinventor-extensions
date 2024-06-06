// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2020 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE.BluetoothConnectionListener;
import java.util.UUID;

@SimpleObject
public class BLEExtension extends AndroidNonvisibleComponent
    implements BluetoothConnectionListener, BLEDevice {

  private UUID uuid;
  private BluetoothLE bleConnection = null;

  /**
   * Creates a new BLEExtension.
   *
   * @param form the container that this component will be placed in
   */
  protected BLEExtension(Form form) {
    super(form);
  }

  /**
   * Creates a new BLEExtension that interacts with the given service.
   *
   * @param form the container that this component will be placed in
   * @param serviceUuid the uuid of the desired service
   */
  protected BLEExtension(Form form, UUID serviceUuid) {
    super(form);
    this.uuid = serviceUuid;
  }

  /**
   * Creates a new BLEExtension that interacts with the given service.
   *
   * @param form the container that this component will be placed in
   * @param serviceUuid the uuid of the desired service
   */
  protected BLEExtension(Form form, String serviceUuid) {
    super(form);
    if (serviceUuid.length() == 4) {
      serviceUuid = "0000" + serviceUuid + "-0000-1000-8000-00805F9B34FB";
    }
    this.uuid = UUID.fromString(serviceUuid.toUpperCase());
  }

  //region BLEExtension Properties
  /**
   * Determines the BluetoothLE component that represents the connection to the remote device.
   *
   * @param bluetoothLE the connection object
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT +
      ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    if (bleConnection != null) {
      bleConnection.removeConnectionListener(this);
    }
    bleConnection = bluetoothLE;
    if (bleConnection != null) {
      bleConnection.addConnectionListener(this);
    }
  }

  @SimpleProperty
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  /**
   * Indicates whether the connected device represented by the BluetoothDevice provides
   * the information required for this %type% to work correctly.
   *
   * @return true if the connected device provides the service UUID needed by the extension
   */
  @SimpleProperty
  public boolean IsSupported() {
    if (isConnected()) {
      return bleConnection.isServicePublished(uuid.toString());
    }
    return false;
  }

  protected boolean isConnected() {
    return bleConnection != null && bleConnection.IsDeviceConnected();
  }
  //endregion

  //region BluetoothConnectionListener implementation
  @Override
  public void onConnected(BluetoothLE bleConnection) {

  }

  @Override
  public void onDisconnected(BluetoothLE bleConnection) {

  }
  //endregion

  //region BLEDevice implementation
  @Override
  public UUID GetBroadcastUUID() {
    return uuid;
  }

  @Override
  public DeviceCallback getDeviceCallback() {
    return null;
  }
  //endregion
}
