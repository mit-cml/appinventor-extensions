// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.arduino101;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.Arrays;
import java.util.List;

/**
 * Extension to interface with a moisture sensor attached ot an Arduino 101 running the
 * App Inventor Companion Sketch.
 *
 * @author ewpatton@mit.edu (Evan W. Patton)
 */
@DesignerComponent(version = 1,
    description = "The Arduino 101 Moisture Sensor lets users receive data from a moisture sensor " +
        "attached to the Arduino, however it can be used for any similar device that provides a " +
        "linear analog signal based on some external phenomenon.<br>\n\n<strong>More Links</strong>" +
        "<ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/Arduino101MoistureSensor.aia' " +
        "target='_blank'>sample project</a> for the Arduino 101 Moisture Sensor.</li><li>View the <a " +
        "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Moisture_Sensor.pdf' " +
        "target='_blank'>how to instructions</a> for the Arduino 101 Moisture Sensor.</li></ul>",
    category = ComponentCategory.EXTENSION,
    helpUrl = "http://iot.appinventor.mit.edu/#/arduino101/arduinomoisture",
    nonVisible = true,
    iconName = "aiwebres/arduino.png")
@SimpleObject(external = true)
public class Arduino101Moisture extends Arduino101ExtensionWithPin<Arduino101Moisture> {
  private static final String MOISTURE_SERVICE_UUID = "E95D0800-251D-470A-A062-FA1922DFA9A7";
  private static final String MOISTURE_PIN_CHARACTERISTIC_UUID = "E95D0801-251D-470A-A062-FA1922DFA9A7";
  private static final String MOISTURE_DATA_CHARACTERISTIC_UUID = "E95D0802-251D-470A-A062-FA1922DFA9A7";

  private final BluetoothLE.BLEResponseHandler<Integer> moistureDataHandler =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
          Log.d("Arduino101Moisture", "Read values = " + Arrays.toString(values.toArray()));
          Log.d("Arduino101Moisture", "pin = " + pin);
          MoistureReceived(values.get(pin));
        }
      };

  public Arduino101Moisture(Form form) {
    super(form, ANALOG);
  }

  /**
   * Obtain the most recent reading from the moisture sensor as reported by the Arduino. On success,
   * the <a href="#MoistureReceived"><code>MoistureReceived</code></a> event will be run.
   */
  @SimpleFunction
  public void ReadMoisture() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(MOISTURE_SERVICE_UUID, MOISTURE_DATA_CHARACTERISTIC_UUID,
          false, moistureDataHandler);
    }
  }

  /**
   * Request notification of updates for the moisture sensor attached to the Arduino 101. The <a
   * href="#MoistureReceived"><code>MoistureReceived</code></a> event will be run as moisture
   * sensor readings are received from the Arduino.
   */
  @SimpleFunction
  public void RequestMoistureUpdates() {
    Log.d("Arduino101Moisture", "RequestMoistureUpdates");
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(MOISTURE_SERVICE_UUID, MOISTURE_DATA_CHARACTERISTIC_UUID,
          false, moistureDataHandler);
    }
  }

  /**
   * Stop listening for notifications of moisture sensor readings from the ARduino. This only has
   * an effect if there was a previous call to <a
   * href="#RequestMoistureUpdates"><code>RequestMoistureUpdates</code></a>. There may be
   * additional pending messages that will be processed after this call.
   */
  @SimpleFunction
  public void StopMoistureUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(MOISTURE_SERVICE_UUID, MOISTURE_DATA_CHARACTERISTIC_UUID,
          moistureDataHandler);
    }
  }

  /**
   * The <code>MoistureReceived</code> event is run when a moisture measurement is received from
   * the moisture sensor attached to the Arduino 101.
   *
   * __Parameters__:
   *
   *     * <code>moisture</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The moisture level measured, between 0 and 100.
   *
   * @param moisture The moisture level measured, between 0 and 100.
   */
  @SimpleEvent
  public void MoistureReceived(final int moisture) {
    EventDispatcher.dispatchEvent(this, "MoistureReceived", moisture);
  }

  // Arduino101ExtensionWithPin implementation

  public String getPinServiceUuid() {
    return MOISTURE_SERVICE_UUID;
  }

  public String getPinCharacteristicUuid() {
    return MOISTURE_PIN_CHARACTERISTIC_UUID;
  }
}
