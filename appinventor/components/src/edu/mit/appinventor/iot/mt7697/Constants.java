// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;
import java.util.HashMap;

class PinBLEProfile {
  public final String pin;
  public final String serviceUUID;
  public final String modeCharUUID;
  public final String dataCharUUID;

  public PinBLEProfile(String _pin, String _serviceUUID, String _modeCharUUID, String _dataCharUUID) {
    this.pin = _pin;
    this.serviceUUID = _serviceUUID;
    this.modeCharUUID = _modeCharUUID;
    this.dataCharUUID = _dataCharUUID;
  }
}

class Constants {
  public static final HashMap<String, PinBLEProfile> PIN_BLE_PROFILES = new HashMap<String, PinBLEProfile>();
  public static final String[] AVAILABLE_PINS = {"0", "1", "2", "3", "4", "5", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};

  static {
    // initialize PIN_BLE_PROFILES
    PIN_BLE_PROFILES.put("0",
                         new PinBLEProfile("0",
                                           "b8b1b700-6422-4d6d-bf79-b76e47e9ac86",
                                           "b8b1b700-6422-4d6d-bf79-b76e47e9ac86",
                                           "b8b1b701-6422-4d6d-bf79-b76e47e9ac86"));

    PIN_BLE_PROFILES.put("1",
                         new PinBLEProfile("1",
                                           "682e2900-cd85-4578-b0ac-37c89b1c8c3a",
                                           "682e2900-cd85-4578-b0ac-37c89b1c8c3a",
                                           "682e2901-cd85-4578-b0ac-37c89b1c8c3a"));

    PIN_BLE_PROFILES.put("2",
                         new PinBLEProfile("2",
                                           "56b16f00-2193-4cde-8e09-c3ee4c72dc78",
                                           "56b16f00-2193-4cde-8e09-c3ee4c72dc78",
                                           "56b16f01-2193-4cde-8e09-c3ee4c72dc78"));

    PIN_BLE_PROFILES.put("3",
                         new PinBLEProfile("3",
                                           "b053a500-8e23-4bfd-8f88-4af09fd9040b",
                                           "b053a500-8e23-4bfd-8f88-4af09fd9040b",
                                           "b053a501-8e23-4bfd-8f88-4af09fd9040b"));

    PIN_BLE_PROFILES.put("4",
                         new PinBLEProfile("4",
                                           "9aa1fa00-ebc3-4fb6-ba4a-1bb79b8a9d78",
                                           "9aa1fa00-ebc3-4fb6-ba4a-1bb79b8a9d78",
                                           "9aa1fa01-ebc3-4fb6-ba4a-1bb79b8a9d78"));

    PIN_BLE_PROFILES.put("5",
                         new PinBLEProfile("5",
                                           "981a6300-c513-40dc-ad0c-c873e06ff18b",
                                           "981a6300-c513-40dc-ad0c-c873e06ff18b",
                                           "981a6301-c513-40dc-ad0c-c873e06ff18b"));

    PIN_BLE_PROFILES.put("8",
                         new PinBLEProfile("8",
                                           "7ed8d200-6a9f-4d8b-b50f-e8f4b78667b2",
                                           "7ed8d200-6a9f-4d8b-b50f-e8f4b78667b2",
                                           "7ed8d201-6a9f-4d8b-b50f-e8f4b78667b2"));

    PIN_BLE_PROFILES.put("9",
                         new PinBLEProfile("9",
                                           "525d9c00-3995-480c-80d6-98cf3a8420e9",
                                           "525d9c00-3995-480c-80d6-98cf3a8420e9",
                                           "525d9c01-3995-480c-80d6-98cf3a8420e9"));

    PIN_BLE_PROFILES.put("10",
                         new PinBLEProfile("10",
                                           "756bd900-81b9-4565-9ce2-f6f2dfabbbec",
                                           "756bd900-81b9-4565-9ce2-f6f2dfabbbec",
                                           "756bd901-81b9-4565-9ce2-f6f2dfabbbec"));

    PIN_BLE_PROFILES.put("11",
                         new PinBLEProfile("11",
                                           "b6d08900-99e4-4c53-89ef-4039b61ffb19",
                                           "b6d08900-99e4-4c53-89ef-4039b61ffb19",
                                           "b6d08901-99e4-4c53-89ef-4039b61ffb19"));

    PIN_BLE_PROFILES.put("12",
                         new PinBLEProfile("12",
                                           "cd893c00-de11-4cb7-9436-b9f129f5c249",
                                           "cd893c00-de11-4cb7-9436-b9f129f5c249",
                                           "cd893c01-de11-4cb7-9436-b9f129f5c249"));

    PIN_BLE_PROFILES.put("13",
                         new PinBLEProfile("13",
                                           "42b07000-ce29-4feb-9fc1-5ed55cf9f3af",
                                           "42b07000-ce29-4feb-9fc1-5ed55cf9f3af",
                                           "42b07001-ce29-4feb-9fc1-5ed55cf9f3af"));

    PIN_BLE_PROFILES.put("14",
                         new PinBLEProfile("14",
                                           "4433a600-40eb-462a-8a98-22e326f87eca",
                                           "4433a600-40eb-462a-8a98-22e326f87eca",
                                           "4433a601-40eb-462a-8a98-22e326f87eca"));

    PIN_BLE_PROFILES.put("15",
                         new PinBLEProfile("15",
                                           "6dce9900-d064-4c5d-ae39-9380f52712d6",
                                           "6dce9900-d064-4c5d-ae39-9380f52712d6",
                                           "6dce9901-d064-4c5d-ae39-9380f52712d6"));

    PIN_BLE_PROFILES.put("16",
                         new PinBLEProfile("16",
                                           "6dce9900-d064-4c5d-ae39-9380f52712d6",
                                           "6dce9900-d064-4c5d-ae39-9380f52712d6",
                                           "6dce9901-d064-4c5d-ae39-9380f52712d6"));

    PIN_BLE_PROFILES.put("17",
                         new PinBLEProfile("17",
                                           "6dce9900-d064-4c5d-ae39-9380f52712d6",
                                           "6dce9900-d064-4c5d-ae39-9380f52712d6",
                                           "6dce9901-d064-4c5d-ae39-9380f52712d6"));

    // sanity check
    assert PIN_BLE_PROFILES.keySet().size() == AVAILABLE_PINS.length;

    for (int ind = 0; ind < AVAILABLE_PINS.length; ind += 1)
    {
      String pin = AVAILABLE_PINS[ind];
      assert PIN_BLE_PROFILES.containsKey(pin);
    }
  }
}
