// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package edu.mit.appinventor.iot.mt7697;

import java.util.HashMap;

class UuidOfPin {
  public final String mModeCharUuid;
  public final String mDataCharUuid;

  public UuidOfPin(String modeCharUuid,
                   String dataCharUuid) {
    mModeCharUuid = modeCharUuid;
    mDataCharUuid = dataCharUuid;
  }
}

/**
 * The class stores UUID constants for BLE connections to MT7697 boards.
 *
 * @author jerry73204@gmail.com (Lin, Hsiang-Jui)
 * @author az6980522@gmail.com (Yuan, Yu-Yuan)
 */
class Constants {
  public static final HashMap<String, UuidOfPin> PIN_UUID_LOOKUP;
  public static final String PIN_SERVICE_UUID = "ccb7be00-77bd-4349-86a6-14cc7673ca00";

  static {
    PIN_UUID_LOOKUP = new HashMap<String, UuidOfPin>();
    PIN_UUID_LOOKUP.put("2", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca02",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca02"));

    PIN_UUID_LOOKUP.put("3", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca03",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca03"));

    PIN_UUID_LOOKUP.put("4", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca04",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca04"));

    PIN_UUID_LOOKUP.put("5", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca05",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca05"));

    PIN_UUID_LOOKUP.put("6", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca06",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca06"));

    PIN_UUID_LOOKUP.put("7", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca07",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca07"));

    PIN_UUID_LOOKUP.put("10", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca10",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca10"));

    PIN_UUID_LOOKUP.put("11", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca11",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca11"));

    PIN_UUID_LOOKUP.put("12", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca12",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca12"));

    PIN_UUID_LOOKUP.put("13", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca13",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca13"));

    PIN_UUID_LOOKUP.put("14", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca14",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca14"));

    PIN_UUID_LOOKUP.put("15", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca15",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca15"));

    PIN_UUID_LOOKUP.put("16", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca16",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca16"));

    PIN_UUID_LOOKUP.put("17", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca17",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca17"));
  }
}
