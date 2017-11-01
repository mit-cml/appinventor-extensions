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
import edu.mit.appinventor.ble.BluetoothLE.BLEResponseHandler;

import static edu.mit.appinventor.iot.mt7697.Constants.LIGHT_SENSOR_SERVICE_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.LIGHT_SENSOR_PIN_CHARACTERISTIC_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.LIGHT_SENSOR_DATA_CHARACTERISTIC_UUID;

import java.util.List;

/**
 * Extension to interpret light sensor data from the MT7697.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697 Light Sensor component lets users receive data from a light " +
                                 "sensor attached to the Arduino, however it can be used for any similar device that " +
                                 "provides a linear analog signal based on some external phenomenon.<br>\n\n<strong>" +
                                 "More Links</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697LightSensor.aia' " +
                                 "target='_blank'>sample project</a></li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Light_Sensor.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 Light Sensor.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697LightSensor extends MT7697ExtensionWithPin<MT7697LightSensor> {
  private final BluetoothLE.BLEResponseHandler<Integer> lightSensorDataHandler =
      new BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
          LightSensorDataReceived(values.get(pin));
        }
      };

  public MT7697LightSensor(Form form) {
    super(form, ANALOG);
  }

  /**
   * Obtain the most recent reading from the light sensor as reported by the Arduino. On success,
   * the <a href="#LightSensorDataReceived"><code>LightSensorDataReceived</code></a> event will be
   * run.
   */
  @SimpleFunction
  public void ReadLightSensor() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(LIGHT_SENSOR_SERVICE_UUID,
                                     LIGHT_SENSOR_DATA_CHARACTERISTIC_UUID,
                                     false,
                                     lightSensorDataHandler);
    }
  }

  /**
   * Request notification of updates for the light sensor attached to the MT7697. The <a
   * href="#LightSensorDataReceived"><code>LightSensorDataReceived</code></a> event will be run as
   * light sensor readings are received from the Arduino.
   */
  @SimpleFunction
  public void RequestLightSensorUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(LIGHT_SENSOR_SERVICE_UUID,
                                            LIGHT_SENSOR_DATA_CHARACTERISTIC_UUID,
                                            false,
                                            lightSensorDataHandler);
    }
  }

  /**
   * Stop listening for notifications of light sensor readings from the Arduino. This only has an
   * effect if there was a previous call to <a
   * href="#RequestLightSensorUpdates"><code>RequestLightSensorUpdates</code></a>. There may be
   * additional pending messages that will be processed after this call.
   */
  @SimpleFunction
  public void StopLightSensorUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(LIGHT_SENSOR_SERVICE_UUID,
                                          LIGHT_SENSOR_DATA_CHARACTERISTIC_UUID,
                                          lightSensorDataHandler);
    }
  }

  /**
   * The <code>LightSensorDataReceived</code> event is run when a sample is received from the light
   * sensor attached to the MT7697.
   *
   * __Parameters__:
   *
   *     * <code>intensity</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The intensity of the light received from the sensor, linear in the voltage provided by the light sensor.
   *
   * @param intensity The intensity of the light received from the sensor, linear in the voltage provided by the light sensor.
   */
  @SimpleEvent
  public void LightSensorDataReceived(final int intensity) {
    EventDispatcher.dispatchEvent(this, "LightSensorDataReceived", intensity);
  }

  // MT7697ExtensionWithPin implementation

  @Override
  public String getPinServiceUuid() {
    return LIGHT_SENSOR_SERVICE_UUID;
  }

  @Override
  public String getPinCharacteristicUuid() {
    return LIGHT_SENSOR_PIN_CHARACTERISTIC_UUID;
  }
}
