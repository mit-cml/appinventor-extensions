// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2015-2016 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.ble;

import android.os.ParcelUuid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Static helper methods related to the BluetoothLE component.
 *
 * @author William Byrne (will2596@gmail.com)
 */
final class BLEUtil {

  // Regex to ensure that input strings follow the canonical UUID format outlined in RFC4122
  private static final Pattern UUID_FORMAT =
      Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");

  // Regex to detect invalid characters in a potential UUID string
  private static final Pattern INVALID_UUID_CHARS = Pattern.compile("[^0-9a-fA-F-]");
  public static final String BLUETOOTH_BASE_UUID_SUFFIX = "-0000-1000-8000-00805F9B34FB";

  /**
   * Validator for prospective service and characteristic UUID strings used in
   * the BluetoothLE component.
   *
   * @param UUID the UUID to be validated
   * @return true if the UUID is valid, false otherwise
   */
  static boolean hasValidUUIDFormat(String UUID) {
      return UUID_FORMAT.matcher(UUID).find();
  }

  /**
   * Checks the prospective UUID string for invalid characters.
   *
   * @param UUID the UUID to be validated
   * @return true if the UUID has invalid characters, false otherwise
   */
  static boolean hasInvalidUUIDChars(String UUID) {
    return INVALID_UUID_CHARS.matcher(UUID).find();
  }

  /**
   * Function that takes in a List of ParcelUuids and converts it into a
   * List of corresponding Strings.
   *
   * @param serviceUUIDs - a List containing ParcelUuid types
   * @return a List containing String types representing the Uuid's
   */
  static List<String> stringifyParcelUuids(List<ParcelUuid> serviceUUIDs) {
    List<String> deviceServices = new ArrayList<String>();
    for (ParcelUuid serviceUuid : serviceUUIDs) {
      deviceServices.add(serviceUuid.toString());
    }
    return deviceServices;
  }

  /**
   * Converts a string representing a UUID into a UUID object. This method will
   * also convert 16-bit and 32-bit UUIDs from the Bluetooth specification into
   * 128-bit versions.
   *
   * @param uuid the UUID-like string to verify
   * @return a UUID object representing the 128-bit version of {@code uuid}
   * @throws IllegalArgumentException if the {@code uuid} does not represent a
   * valid UUID based on the Bluetooth specification.
   */
  public static UUID bleStringToUuid(String uuid) {
    uuid = uuid.toLowerCase();
    if (uuid.length() == 4) {  // 16 bit short Bluetooth UUID
      uuid = "0000" + uuid + BLUETOOTH_BASE_UUID_SUFFIX;
    } else if (uuid.length() == 8) {  // 32 bit short Bluetooth UUID
      uuid = uuid + BLUETOOTH_BASE_UUID_SUFFIX;
    } else if (uuid.length() == 32) {  // 128 bit Bluetooth UUID without dashes
      uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) +
          "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
    } else if (!hasValidUUIDFormat(uuid)) {
      throw new IllegalArgumentException("Invalid UUID: " + uuid);
    }
    return UUID.fromString(uuid);
  }
}
