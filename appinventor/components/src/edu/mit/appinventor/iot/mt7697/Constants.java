// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import java.util.HashMap;

class UuidOfPin {
  public final String mServiceUuid;
  public final String mAnalogInputCharUuid;
  public final String mAnalogOutputCharUuid;
  public final String mDigitalInputCharUuid;
  public final String mDigitalOutputCharUuid;

  public UuidOfPin(String serviceUuid,
                   String analogInputUuid,
                   String analogOutputUuid,
                   String digitalInputUuid,
                   String digitalOutputUuid) {
    mServiceUuid = serviceUuid;
    mAnalogInputCharUuid = analogInputUuid;
    mAnalogOutputCharUuid = analogOutputUuid;
    mDigitalInputCharUuid = digitalInputUuid;
    mDigitalOutputCharUuid = digitalOutputUuid;
  }
}

class Constants {
  public static final HashMap<String, UuidOfPin> PIN_UUID_LOOKUP;

  static {
    PIN_UUID_LOOKUP = new HashMap<String, UuidOfPin>();
    PIN_UUID_LOOKUP.put("2", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca02",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca02",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca02",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca02",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca02"));

    PIN_UUID_LOOKUP.put("3", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca03",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca03",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca03",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca03",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca03"));

    PIN_UUID_LOOKUP.put("4", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca04",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca04",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca04",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca04",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca04"));

    PIN_UUID_LOOKUP.put("5", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca05",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca05",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca05",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca05",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca05"));

    PIN_UUID_LOOKUP.put("6", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca06",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca06",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca06",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca06",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca06"));

    PIN_UUID_LOOKUP.put("7", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca07",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca07",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca07",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca07",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca07"));

    PIN_UUID_LOOKUP.put("8", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca08",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca08",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca08",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca08",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca08"));

    PIN_UUID_LOOKUP.put("9", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca09",
                                           "ccb7be01-77bd-4349-86a6-14cc7673ca09",
                                           "ccb7be02-77bd-4349-86a6-14cc7673ca09",
                                           "ccb7be03-77bd-4349-86a6-14cc7673ca09",
                                           "ccb7be04-77bd-4349-86a6-14cc7673ca09"));

    PIN_UUID_LOOKUP.put("10", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca10",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca10",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca10",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca10",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca10"));

    PIN_UUID_LOOKUP.put("11", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca11",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca11",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca11",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca11",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca11"));

    PIN_UUID_LOOKUP.put("12", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca12",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca12",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca12",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca12",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca12"));

    PIN_UUID_LOOKUP.put("13", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca13",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca13",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca13",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca13",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca13"));

    PIN_UUID_LOOKUP.put("14", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca14",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca14",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca14",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca14",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca14"));

    PIN_UUID_LOOKUP.put("15", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca15",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca15",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca15",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca15",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca15"));

    PIN_UUID_LOOKUP.put("16", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca16",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca16",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca16",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca16",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca16"));

    PIN_UUID_LOOKUP.put("17", new UuidOfPin("ccb7be00-77bd-4349-86a6-14cc7673ca17",
                                            "ccb7be01-77bd-4349-86a6-14cc7673ca17",
                                            "ccb7be02-77bd-4349-86a6-14cc7673ca17",
                                            "ccb7be03-77bd-4349-86a6-14cc7673ca17",
                                            "ccb7be04-77bd-4349-86a6-14cc7673ca17"));
  }
}
