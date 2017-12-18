// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import java.util.HashMap;

class UuidOfPin {
  public final String mServiceUuid;
  public final String mAnalogCharUuid;
  public final String mDigitalCharUuid;

  public UuidOfPin(String serviceUuid, String analogUuid, String digitalUuid) {
    mServiceUuid = serviceUuid;
    mAnalogCharUuid = analogUuid;
    mDigitalCharUuid = digitalUuid;
  }
}

class Constants {
  public static final HashMap<String, UuidOfPin> PIN_UUID_LOOKUP;

  static {
    PIN_UUID_LOOKUP = new HashMap<String, UuidOfPin>();
    PIN_UUID_LOOKUP.put("0", new UuidOfPin("a8955300-e41f-11e7-a628-5b151cab795e",   // service UUID
                                           "a8955300-e41f-11e7-a628-5b151cab795e",   // analog char UUID
                                           "a8955301-e41f-11e7-a628-5b151cab795e")); // digital char UUID

    PIN_UUID_LOOKUP.put("1", new UuidOfPin("a895c600-e41f-11e7-9d52-930bcb63ca0b",
                                            "a895c600-e41f-11e7-9d52-930bcb63ca0b",
                                            "a895c601-e41f-11e7-9d52-930bcb63ca0b"));

    PIN_UUID_LOOKUP.put("2", new UuidOfPin("a8963600-e41f-11e7-b0f0-eb7c7fb9f900",
                                            "a8963600-e41f-11e7-b0f0-eb7c7fb9f900",
                                            "a8963601-e41f-11e7-b0f0-eb7c7fb9f900"));

    PIN_UUID_LOOKUP.put("3", new UuidOfPin("a896a600-e41f-11e7-be23-93f0f7b5adf4",
                                            "a896a600-e41f-11e7-be23-93f0f7b5adf4",
                                            "a896a601-e41f-11e7-be23-93f0f7b5adf4"));

    PIN_UUID_LOOKUP.put("4", new UuidOfPin("a8970f00-e41f-11e7-9968-773e1195eeda",
                                            "a8970f00-e41f-11e7-9968-773e1195eeda",
                                            "a8970f01-e41f-11e7-9968-773e1195eeda"));

    PIN_UUID_LOOKUP.put("5", new UuidOfPin("a8976f00-e41f-11e7-b762-e7fa342d395d",
                                            "a8976f00-e41f-11e7-b762-e7fa342d395d",
                                            "a8976f01-e41f-11e7-b762-e7fa342d395d"));

    PIN_UUID_LOOKUP.put("8", new UuidOfPin("a897df00-e41f-11e7-b600-3fe1e4807023",
                                            "a897df00-e41f-11e7-b600-3fe1e4807023",
                                            "a897df01-e41f-11e7-b600-3fe1e4807023"));

    PIN_UUID_LOOKUP.put("9", new UuidOfPin("a8984d00-e41f-11e7-9a0b-2b333f2270db",
                                            "a8984d00-e41f-11e7-9a0b-2b333f2270db",
                                            "a8984d01-e41f-11e7-9a0b-2b333f2270db"));

    PIN_UUID_LOOKUP.put("10", new UuidOfPin("a898b800-e41f-11e7-9e45-dfdad5b74952",
                                            "a898b800-e41f-11e7-9e45-dfdad5b74952",
                                            "a898b801-e41f-11e7-9e45-dfdad5b74952"));

    PIN_UUID_LOOKUP.put("11", new UuidOfPin("a8992d00-e41f-11e7-beab-ff857ace7d35",
                                            "a8992d00-e41f-11e7-beab-ff857ace7d35",
                                            "a8992d01-e41f-11e7-beab-ff857ace7d35"));

    PIN_UUID_LOOKUP.put("12", new UuidOfPin("a899a900-e41f-11e7-b769-232489afcfe3",
                                            "a899a900-e41f-11e7-b769-232489afcfe3",
                                            "a899a901-e41f-11e7-b769-232489afcfe3"));

    PIN_UUID_LOOKUP.put("13", new UuidOfPin("a89a1900-e41f-11e7-833c-c71c3564ef5b",
                                            "a89a1900-e41f-11e7-833c-c71c3564ef5b",
                                            "a89a1901-e41f-11e7-833c-c71c3564ef5b"));

    PIN_UUID_LOOKUP.put("14", new UuidOfPin("a89a8800-e41f-11e7-99a0-e7768702f6da",
                                            "a89a8800-e41f-11e7-99a0-e7768702f6da",
                                            "a89a8801-e41f-11e7-99a0-e7768702f6da"));

    PIN_UUID_LOOKUP.put("15", new UuidOfPin("a89af600-e41f-11e7-b3b8-5fedfc2d5a63",
                                            "a89af600-e41f-11e7-b3b8-5fedfc2d5a63",
                                            "a89af601-e41f-11e7-b3b8-5fedfc2d5a63"));

    PIN_UUID_LOOKUP.put("16", new UuidOfPin("a89b6500-e41f-11e7-8e93-8b2408337887",
                                            "a89b6500-e41f-11e7-8e93-8b2408337887",
                                            "a89b6501-e41f-11e7-8e93-8b2408337887"));

    PIN_UUID_LOOKUP.put("17", new UuidOfPin("9c38cc00-43d5-4967-acf7-e3754df8c439",
                                            "9c38cc00-43d5-4967-acf7-e3754df8c439",
                                            "9c38cc01-43d5-4967-acf7-e3754df8c439"));
  }
}
