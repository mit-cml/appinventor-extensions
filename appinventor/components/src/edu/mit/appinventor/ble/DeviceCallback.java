package edu.mit.appinventor.ble;

public interface DeviceCallback {
  boolean foundDevice(String name, String mac);
}
