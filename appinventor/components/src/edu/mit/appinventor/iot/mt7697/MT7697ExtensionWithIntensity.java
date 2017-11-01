// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.Form;

/**
 * Base class for MT7697 extensions that turn pins on/off with some non-boolean intensity.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@SimpleObject(external = true)
public abstract class MT7697ExtensionWithIntensity extends MT7697ExtensionBase {
  private int pin;
  private int intensity;
  private boolean on = true;

  public MT7697ExtensionWithIntensity(Form form) {
    super(form);
  }

  @SimpleProperty
  public void Pin(int pin) {
    this.pin = pin;
  }

  @SimpleProperty(description = "The Pin on the Arduino board that the device is wired in to.")
  public int Pin() {
    return pin;
  }

  public void Intensity(int intensity) {
    this.intensity = intensity;
    if (on && isConnected()) {
      TurnOn();
    }
  }

  public int Intensity() {
    return intensity;
  }

  @SimpleProperty(description = "Get whether the device attached to the MT7697 is turned on." +
      " This is a best guess approximation given the connected state of the Bluetooth low energy " +
      "component and previous calls to TurnOn and TurnOff.")
  public boolean On() {
    return on;
  }

  /**
   * Turn on the connected device.
   */
  @SimpleFunction
  public void TurnOn() {
    if (writeIntensity(getService(), getCharacteristic(), (int)(2.55 * intensity))) {
      on = true;
    }
  }

  /**
   * Turn off the connected device.
   */
  @SimpleFunction
  public void TurnOff() {
    writeIntensity(getService(), getCharacteristic(), 0);
    on = false;
  }

  private boolean writeIntensity(String service, String characteristic, int intensity) {
    if (bleConnection != null) {
      bleConnection.ExWriteByteValues(service, characteristic, false, toList((byte) pin, (byte) intensity));
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Tests whether the Bluetooth low energy device is broadcasting support for the service. If true,
   * calls to TurnOn and TurnOff should work correctly. Otherwise an error will be reported through
   * the Screen's ErrorOccurred event.
   */
  @Override
  @SimpleFunction
  public boolean IsSupported() {
    return bleConnection != null &&
      bleConnection.isCharacteristicPublished(getService(), getCharacteristic());
  }

  protected abstract String getService();
  protected abstract String getCharacteristic();
}
