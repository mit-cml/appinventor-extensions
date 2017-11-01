// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.List;

/**
 * Extension to access gyroscopic data from the App Inventor Companion Sketch for MT7697.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697Gyroscope component lets users configure the MT7697's " +
                                 "on-board gyroscope and receive one or more gyroscope samples via the appropriate methods." +
                                 "<br>\n\n<strong>More links:</strong><ul><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697Gyroscope.aia' " +
                                 "target='_blank'>sample project</a> for the MT7697 Gyroscope.</li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Gyroscope.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 Gyroscope.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Gyroscope extends MT7697ExtensionBase {
  private static final String GYROSCOPE_SERVICE_UUID = "E95D0500-251D-470A-A062-FA1922DFA9A7";
  private static final String GYROSCOPE_DATA_CHARACTERISTIC_UUID = "E95D0501-251D-470A-A062-FA1922DFA9A7";

  private final BluetoothLE.BLEResponseHandler<Float> gyroscopeDataHandler =
    new BluetoothLE.BLEResponseHandler<Float>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Float> values) {
        GyroscopeDataReceived(values.get(0), values.get(1));
      }
    };

  public MT7697Gyroscope(Form form) {
    super(form);
  }

  /**
   * Check whether the feature is currently available for the device connected via the
   * <a href="#BluetoothDevice"><code>BluetoothDevice</code></a> property. If no device is currently
   * connected, this method will always return false.
   * @return true if the connected device is advertising the service represented by the extension,
   * otherwise false.
   */
  @Override
  @SimpleFunction
  public boolean IsSupported() {
    return bleConnection != null &&
      bleConnection.isCharacteristicPublished(GYROSCOPE_SERVICE_UUID, GYROSCOPE_DATA_CHARACTERISTIC_UUID);
  }

  /**
   * Read a single sample of gyroscope data from the Arduino. On successful read, the
   * <a href="#GyroscopeDataReceived"><code>GyroscopeDataReceived</code></a> event will be run.
   */
  @SimpleFunction
  public void ReadGyroscopeData() {
    if (bleConnection != null) {
      bleConnection.ExReadFloatValues(GYROSCOPE_SERVICE_UUID,
                                      GYROSCOPE_DATA_CHARACTERISTIC_UUID,
                                      false,
                                      gyroscopeDataHandler);
    }
  }

  /**
   * Request notifications of changes in the Arduino's gyroscope. Gyroscope data will be reported
   * through the <a href="#GyroscopeDataReceived"><code>GyroscopeDataReceived</code></a> event.
   */
  @SimpleFunction
  public void RequestGyroscopeDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForFloatValues(GYROSCOPE_SERVICE_UUID,
                                             GYROSCOPE_DATA_CHARACTERISTIC_UUID,
                                             false,
                                             gyroscopeDataHandler);
    }
  }

  /**
   * Stop receiving updates from the Arduino's gyroscope. Note that there may be pending messages
   * from the device that will still be reported through the
   * <a href="#GryoscopeDataReceived"><code>GyroscopeDataReceived</code></a> event. This method
   * has no effect if <a href="#RequestGyroscopeDataUpdates"><code>RequestGyroscopeDataUpdates</code></a>
   * has not been previously called.
   */
  @SimpleFunction
  public void StopGyroscopeDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(GYROSCOPE_SERVICE_UUID,
                                          GYROSCOPE_DATA_CHARACTERISTIC_UUID,
                                          gyroscopeDataHandler);
    }
  }

  /**
   * The <code>GyroscopeDataReceived</code> event is run whenever gyroscope samples are received
   * from the MT7697. This is usually a result of performing a
   * <a href="#ReadGyroscopeData">read</a> or <a href="#RequestGyroscopeDataUpdates">request</a>
   * operation. The X and Y angles are reported in degrees.
   *
   * __Parameters__:
   *
   *     * <code>X_Angle</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The rotation of the device around its X axis, in degrees.
   *     * <code>Y_Angle</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The rotation of the device around its Y axis, in degrees.
   *
   * @param X_Angle The rotation of the device around its X axis, in degrees.
   * @param Y_Angle The rotation of the device around its Y axis, in degrees.
   */
  @SimpleEvent
  public void GyroscopeDataReceived(final float X_Angle, final float Y_Angle) {
    EventDispatcher.dispatchEvent(this, "GyroscopeDataReceived", X_Angle, Y_Angle);
  }
}
