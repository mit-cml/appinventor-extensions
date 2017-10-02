// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import edu.mit.appinventor.ble.BluetoothLE;

import java.lang.ref.WeakReference;
import java.util.HashSet;

import static edu.mit.appinventor.iot.mt7697.MT7697ExtensionBase.toList;

/**
 * A set of pins where the components involved require two pins in order to communicate.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
public class ActiveTTLPinSet<E extends MT7697ExtensionWithSerialTTL> extends HashSet<E>
    implements BluetoothLE.BluetoothConnectionListener {

  private static final byte[] MASK = new byte[] {
      127, -65, -33, -17, -9, -5, -3, -2
  };
  private static final byte[] BIT = new byte[] {
      -128, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01
  };
  private final String pinServiceUuid;
  private final String pinCharacteristicUuid;
  private boolean connected = false;
  private byte[] pins = new byte[16];
  private WeakReference<BluetoothLE> client;

  public ActiveTTLPinSet(BluetoothLE bleConnection, E initial) {
    pinServiceUuid = initial.getPinServiceUuid();
    pinCharacteristicUuid = initial.getPinCharacteristicUuid();
    client = new WeakReference<BluetoothLE>(bleConnection);
    add(initial);
    bleConnection.addConnectionListener(this);
    connected = bleConnection.IsDeviceConnected();
    if (connected) {
      bleConnection.getForm().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          if (connected) {
            updateConfig();
          }
        }
      });
    }
  }

  @Override
  public synchronized boolean add(E e) {
    int pin1 = e.getPin1(), pin2 = e.getPin2();
    int i1 = pin1 / 8, j1 = pin1 % 8, i2 = 8 + pin2 / 8, j2 = pin2 % 8;
    if ((pins[i1] & BIT[j1]) == BIT[j1] || (pins[i2] & BIT[j2]) == BIT[j2]) {
      return false;
    } else {
      pins[i1] |= BIT[j1];
      pins[i2] |= BIT[j2];
      boolean result = super.add(e);
      if (connected) {
        updateConfig();
      }
      return result;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public synchronized boolean remove(Object key) {
    if (!super.remove(key)) {
      return false;
    }
    E e = (E) key;
    int pin1 = e.getPin1(), pin2 = e.getPin2();
    int i1 = pin1 / 8, j1 = pin1 % 8, i2 = 8 + pin2 / 8, j2 = pin2 % 8;
    pins[i1] &= MASK[j1];
    pins[i2] &= MASK[j2];
    if (connected) {
      updateConfig();
    }
    if (size() == 0) {
      BluetoothLE bleConnection = client.get();
      if (bleConnection != null) {
        bleConnection.removeConnectionListener(this);
      }
      connected = false;
      client = null;
    }
    return true;
  }

  public synchronized boolean updatePin1(E e, int old) {
    return updatePin(e, old, e.getPin1(), 0);
  }

  public synchronized boolean updatePin2(E e, int old) {
    return updatePin(e, old, e.getPin2(), 8);
  }

  private boolean updatePin(E e, int old, int pin, int offset) {
    int oi = offset + old / 8, oj = old % 8, ni = offset + pin / 8, nj = pin % 8;
    if ((pins[oi] & BIT[oj]) == 0) {
      // e was not in the set
      return add(e);
    }
    if ((pins[ni] & BIT[nj]) == BIT[nj]) {
      // pin already in use
      return false;
    }
    pins[ni] |= BIT[nj];
    pins[oi] &= MASK[oj];
    if (connected) {
      updateConfig();
    }
    return true;
  }

  @Override
  public synchronized void onConnected(BluetoothLE bleConnection) {
    connected = true;
    client = new WeakReference<BluetoothLE>(bleConnection);
    updateConfig();
  }

  @Override
  public synchronized void onDisconnected(BluetoothLE bleConnection) {
    connected = false;
    client = null;
  }

  private synchronized void updateConfig() {
    BluetoothLE bleConnection = client.get();
    if (bleConnection == null) {
      client = null;
      connected = false;
      return;
    }
    bleConnection.ExWriteByteValues(pinServiceUuid, pinCharacteristicUuid, false, toList(pins));
  }
}
