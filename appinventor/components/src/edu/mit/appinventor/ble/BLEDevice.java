// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2019 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import com.google.appinventor.components.runtime.Component;

import java.util.UUID;

/**
 * Components that implement the BLEDevice interface may be used for the
 * {@link BluetoothLE#ScanForDevice(BLEDevice)} method to scope the scanning
 * to specific sets of devices.
 *
 * @author ewpatton@mit.edu (Evan W. Patton)
 */
public interface BLEDevice extends Component {

  /**
   * Get the UUID of a service that uniquely identifies the type of device.
   *
   * @return the UUID for the device service
   */
  UUID GetBroadcastUUID();
}
