// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import android.util.Log;
import edu.mit.appinventor.ble.BluetoothLE;

import java.lang.ref.WeakReference;
import java.util.HashSet;

import static edu.mit.appinventor.iot.mt7697.MT7697ExtensionBase.toList;

/**
 * Manages a set of pins for related services. For example, we may have more than one button
 * attached to the Arduino and want to receive events when any button is pressed. The set also
 * implements the {@link BluetoothLE.BluetoothConnectionListener} interface so that it can send
 * the pin configuration to the Arduino once it has successfully connected.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 * @param <E> Concrete implementation of MT7697ExtensionWithPin
 */
public class ActivePinSet<E extends MT7697ExtensionWithPin> extends HashSet<E>
    implements BluetoothLE.BluetoothConnectionListener {

  private static final byte[] MASK = new byte[] {
      127, -65, -33, -17, -9, -5, -3, -2
  };
  private static final byte[] BIT = new byte[] {
     -128, 0x40, 0x20, 0x10, 8, 4, 2, 1
  };
  private final String pinServiceUuid;
  private final String pinCharacteristicUuid;
  private boolean connected = false;
  private byte[] pins;
  private WeakReference<BluetoothLE> client;

  public ActivePinSet(BluetoothLE bleConnection, int pins, E initial) {
    Log.d("ActivePinSet", "<init>");
    this.pins = new byte[pins];
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
          updateConfig();
        }
      });
    }
  }

  @Override
  public synchronized boolean add(E e) {
    Log.d("ActivePinSet", "add");
    int pin = e.Pin();
    int i = pin / 8, j = pin % 8;
    if ((pins[i] & BIT[j]) == BIT[j]) {
      return false;
    } else {
      pins[i] |= BIT[j];
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
    int pin = e.Pin();
    int i = pin / 8, j = pin % 8;
    pins[i] &= MASK[j];
    if (connected) {
      updateConfig();
    }
    if (size() == 0 && client != null) {
      BluetoothLE bleConnection = client.get();
      if (bleConnection != null) {
        bleConnection.removeConnectionListener(this);
      }
      connected = false;
      client = null;
    }
    return true;
  }

  public synchronized boolean update(E e, int old) {
    int pin = e.Pin();
    int oi = old / 8, oj = old % 8, ni = pin / 8, nj = pin % 8;
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
    Log.d("ActivePinSet", "onConnected");
    connected = true;
    client = new WeakReference<BluetoothLE>(bleConnection);
    updateConfig();
  }

  @Override
  public synchronized void onDisconnected(BluetoothLE bleConnection) {
    connected = false;
    client = null;
  }

  synchronized void updateConfig() {
    Log.d("ActivePinSet", "updateConfig()");
    BluetoothLE bleConnection = client.get();
    if (bleConnection == null) {
      client = null;
      connected = false;
      return;
    }
    Log.d("ActivePinSet", "doing update");
    bleConnection.ExWriteByteValues(pinServiceUuid, pinCharacteristicUuid, false, toList(pins));
  }
}
