// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE;

import static edu.mit.appinventor.iot.mt7697.Constants.PROXIMITY_SERVICE_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.PROXIMITY_PIN_CHARACTERISTIC_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.PROXIMITY_DATA_CHARACTERISTIC_UUID;

import java.util.List;

/**
 * Extension to interface with a proximity sensor attached to an MT7697 running the
 * App Inventor Companion Sketch.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697 Proximity Sensor lets users receive data from a infrared " +
                                 "proximity sensor. Note that the signal wire on the Grove header may be off by one pin. " +
                                 "If you are not receiving any reading from the sensor, you may need to increase the pin " +
                                 "number by one (A0 becomes A1) to read the correct pin. The Arduino sketch transforms " +
                                 "the raw reading from the sensor into a distance in centimeters based on the spec sheet " +
                                 "published by Sharp.<br><img src='/assets/sensors/Grove-InfraredProxSensor.jpg' " +
                                 "width='50%'><br>\n\n<strong>More Links</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697ProximitySensor.aia' " +
                                 "target='_blank'>sample project</a> for the MT7697 Proximity Sensor.</li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Proximity_Sensor.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 Proximity Sensor.</li><li>" +
                                 "View the <a href='http://iot.appinventor.mit.edu/assets/GP2Y0A21YK.pdf' " +
                                 "target='_blank'>hardware data sheet</a>.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   helpUrl = "http://iot.appinventor.mit.edu/#/arduino101/arduinoproximitysensor",
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697ProximitySensor extends MT7697ExtensionWithPin {
  private final BluetoothLE.BLEResponseHandler<Integer> proximityDataHandler =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characeristicUuid, List<Integer> values) {
          if (values.size() > pin) {
            ProximityReceived(values.get(pin));
          } else {
            throw new IllegalStateException("pin value outside of device range");
          }
        }
      };

  public MT7697ProximitySensor(Form form) {
    super(form, ANALOG);
  }

  /**
   * Obtain the most recent reading from the proximity sensor as reported by the Arduino. On
   * success, the <a href="#ProximityReceived"><code>ProximityReceived</code></a> event will be run.
   */
  @SimpleFunction
  public void ReadProximity() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(PROXIMITY_SERVICE_UUID, PROXIMITY_DATA_CHARACTERISTIC_UUID,
          false, proximityDataHandler);
    }
  }

  /**
   * Request notification of updates for the proximity sensor attached to the MT7697. The <a
   * href="#ProximityReceived"><code>ProximityReceived</code></a> event will be run as proximity
   * sensor readings are received from the Arduino.
   */
  @SimpleFunction
  public void RequestProximityUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(PROXIMITY_SERVICE_UUID, PROXIMITY_DATA_CHARACTERISTIC_UUID,
          false, proximityDataHandler);
    }
  }

  /**
   * Stop listening for notifications of proximity sensor readings from the Arduino. This only has
   * an effect if there was a previous call to <a
   * href="RequestProximityUpdates"><code>RequestProximityUpdates</code></a>. There may be
   * additional pending messages that will be processed after this call.
   */
  @SimpleFunction
  public void StopProximityUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(PROXIMITY_SERVICE_UUID, PROXIMITY_DATA_CHARACTERISTIC_UUID,
          proximityDataHandler);
    }
  }

  /**
   * The <codeProximityReceived</code> event is run when a proximity measurement is received from
   * the proximity sensor attached to the MT7697.
   *
   * __Parameters__:
   *
   *     * <code>proximity</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The proximity of an object from the sensor. Due to physical and mathematical
   *       limitations, this value will range from 6 to 80 cm.
   *
   * @param proximity
   */
  @SimpleEvent
  public void ProximityReceived(final int proximity) {
    EventDispatcher.dispatchEvent(this, "ProximityReceived", proximity);
  }

  // MT7697ExtensionWithPin implementation

  public String getPinServiceUuid() {
    return PROXIMITY_SERVICE_UUID;
  }

  public String getPinCharacteristicUuid() {
    return PROXIMITY_PIN_CHARACTERISTIC_UUID;
  }
}
