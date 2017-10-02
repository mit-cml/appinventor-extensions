// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

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
 * Extension to read humidity and temperature data from a DHT sensor attached to an MT7697
 * running the App Inventor Companion Sketch.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
    description = "The MT7697Humidity component lets users gather humidity and temperature " +
        "data from a <a href='http://wiki.seeed.cc/Grove-TemperatureAndHumidity_Sensor/' " +
        "target='_blank'>Grove DHT11 sensor</a>. Temperature data are reported in degrees Celsius " +
        "and the relative humidity is reported in percent.<br><img class='centered' " +
        "alt='Image of a Grove temperature and humidity sensor'" +
        "src='/assets/sensors/Grove-TempAndHumiSensor.jpg'><br>\n\n<strong>More links:</strong>" +
        "<ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/MT7697Humidity.aia' " +
        "target='_blank'>sample project</a> for the MT7697 Humidity sensor.</li>" +
        "<li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/MT7697TemperatureSensor.aia' " +
        "target='_blank'>sample project</a> for the MT7697 Temperature sensor.</li>" +
        "<li>View the <a " +
        "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Humidity_Sensor.pdf' " +
        "target='_blank'>how to instructions</a> for the MT7697 Humidity " +
        "sensor.</li><li>View the <a " +
        "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Temperature_Sensor.pdf' " +
        "target='_blank'>how to instructions</a> for the MT7697 Temperature sensor.</li></ul>",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Humidity extends MT7697ExtensionWithPin<MT7697Humidity> {
  private static final String HUMIDITY_SERVICE_UUID = "E95D0600-251D-470A-A062-FA1922DFA9A7";
  private static final String HUMIDITY_PIN_CHARACTERISTIC_UUID = "E95D0601-251D-470A-A062-FA1922DFA9A7";
  private static final String HUMIDITY_DATA_CHARACTERISTIC_UUID = "E95D0602-251D-470A-A062-FA1922DFA9A7";
  private static final String TEMPERATURE_DATA_CHARACTERISTIC_UUID = "E95D0603-251D-470A-A062-FA1922DFA9A7";

  private final BluetoothLE.BLEResponseHandler<Integer> humidityDataHandler =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
          Log.d("MT7697Humidity", Arrays.toString(values.toArray()));
          HumidityReceived(values.get(pin));
        }
      };

  private final BluetoothLE.BLEResponseHandler<Integer> temperatureDataHandler =
      new BluetoothLE.BLEResponseHandler<Integer>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
          Log.d("MT7697Temperature", Arrays.toString(values.toArray()));
          TemperatureReceived(values.get(pin));
        }
      };

  public MT7697Humidity(Form form) {
    super(form, DIGITAL);
  }

  /**
   * Read the current humidity from the attached sensor. On success, the <a
   * href="#HumidityReceived"><code>HumidityReceived</code></a> event will be run with the
   * received humidity measurement.
   */
  @SimpleFunction
  public void ReadHumidity() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(HUMIDITY_SERVICE_UUID, HUMIDITY_DATA_CHARACTERISTIC_UUID,
          false, humidityDataHandler);
    }
  }

  /**
   * Request notifications from the Arduino for changes in the humidity sensor reading. The <a
   * href="#HumidityReceived"><code>HumidityReceived</code></a> event will be run as samples
   * are received from the Arduino.
   */
  @SimpleFunction
  public void RequestHumidityUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(HUMIDITY_SERVICE_UUID, HUMIDITY_DATA_CHARACTERISTIC_UUID,
          false, humidityDataHandler);
    }
  }

  /**
   * Stop listening for notifications of changes in the humidity sensor reading. This only has an
   * effect if there was a previous call to <a
   * href="#RequestHumidityUpdates"><code>RequestHumidityUpdates</code></a>. There may be additional
   * pending messages that will be processed by running <a
   * href="#HumidityReceived"><code>HumidityReceived</code></a>.
   */
  @SimpleFunction
  public void StopHumidityUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(HUMIDITY_SERVICE_UUID, HUMIDITY_DATA_CHARACTERISTIC_UUID,
          humidityDataHandler);
    }
  }

  /**
   * Read the current temperature from the attached sensor. On success, the <a
   * href="#TemperatureReceived"><code>TemperatureReceived</code></a> event will be run with the
   * received temperature measurement.
   */
  @SimpleFunction
  public void ReadTemperature() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(HUMIDITY_SERVICE_UUID, TEMPERATURE_DATA_CHARACTERISTIC_UUID,
          true, temperatureDataHandler);
    }
  }

  /**
   * Request notifications from the Arduino for changes in the temperature sensor reading. The <a
   * href="#TemperatureReceived"><code>TemperatureReceived</code></a> event will be run as samples
   * are received from the Arduino.
   */
  @SimpleFunction
  public void RequestTemperatureUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(HUMIDITY_SERVICE_UUID, TEMPERATURE_DATA_CHARACTERISTIC_UUID,
          true, temperatureDataHandler);
    }
  }

  /**
   * Stop listening for notifications of changes in the temperature sensor reading. This only has
   * an effect if there was a previous call to <a
   * href="#RequestTemperatureUpdates"><code>RequestTemperatureUpdates</code></a>. There may be
   * additional pending messages that will be processed by running <a
   * href="#TemperatureReceived"><code>TemperatureReceived</code></a>.
   */
  @SimpleFunction
  public void StopTemperatureUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(HUMIDITY_SERVICE_UUID, TEMPERATURE_DATA_CHARACTERISTIC_UUID,
          temperatureDataHandler);
    }
  }

  /**
   * The <code>HumidityReceived</code> event is run whenever humidity samples are received from the
   * Arduino. This is usually a result of performing a <a href="#ReadHumidity">read</a> or
   * <a href="#RequestHumidityUpdates">request</a> operation. The humidity value is a relative
   * humidity between 0-100%.
   *
   * __Parameters__:
   *
   *     * <code>humidity</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The relative humidity, in percent.
   *
   * @param humidity The relative humidity, in percent.
   */
  @SimpleEvent
  public void HumidityReceived(final float humidity) {
    EventDispatcher.dispatchEvent(this, "HumidityReceived", humidity);
  }

  /**
   * The <code>TemperatureReceived</code> event is run whenever temperature samples are received
   * from the Arduino. This is usually a result of performing a <a href="#ReadTemperature>read</a>
   * or <a href="#RequestTemperatureUpdates">request</a> operation. The temperature value is
   * measured in degrees Celsius.
   *
   * __Parameters__:
   *
   *     * <code>temperature</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The temperature, in degrees Celsius.
   *
   * @param temperature The temperature, in degrees Celsius.
   */
  @SimpleEvent
  public void TemperatureReceived(final float temperature) {
    EventDispatcher.dispatchEvent(this, "TemperatureReceived", temperature);
  }

  // MT7697ExtensionWithPin implementation

  public String getPinServiceUuid() {
    return HUMIDITY_SERVICE_UUID;
  }

  public String getPinCharacteristicUuid() {
    return HUMIDITY_PIN_CHARACTERISTIC_UUID;
  }
}
