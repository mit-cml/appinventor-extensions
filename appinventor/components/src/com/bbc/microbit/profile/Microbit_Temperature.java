package com.bbc.microbit.profile;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.List;

@DesignerComponent(version = 1,
    description = "The <code>Microbit_Temperature</code> sensor provides App Inventor users with the ability " +
        "to configure the BBC micro:bit's on-board temperature sensor and receive one or more " +
        "temperature samples via the appropriate methods.<br>\n\nThe temperature sensor's report" +
        "rate, or period, determines how frequently data will be sent to App Inventor.<br>\n\n" +
        "<strong>More links:</strong><ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/MicrobitTemperature.aia' " +
        "target='_blank'>sample project</a> for the micro:bit temperature sensor.</li><li>View the " +
        "<a href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_Microbit_Temperature.pdf' " +
        "target='_blank'>how to instructions</a> for the micro:bit temperature sensor.</li></ul>",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbitmicrobittemperature",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Temperature extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String TEMPERATURE_SERVICE_UUID = "E95D6100-251D-470A-A062-FA1922DFA9A8";
  private static final String TEMPERATURE_CHARACTERISTIC_UUID = "E95D9250-251D-470A-A062-FA1922DFA9A8";
  private static final String TEMPERATURE_PERIOD_CHARACTERISTIC_UUID = "E95D1B25-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> temperatureHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        if (values.size() > 0) {
          TemperatureReceived(values.get(0));
        }
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> temperaturePeriodHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        TemperaturePeriodReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteTemperaturePeriod(values.get(0));
      }
    };

  public Microbit_Temperature(Form form) {
    super(form);
  }

  /**
   * The BluetoothLE component connected to the micro:bit device (setter).
   * @param bluetoothLE the BluetoothLE device
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT + ":edu.mit.appinventor.ble.BluetoothLE")
  @SimpleProperty
  public void BluetoothDevice(BluetoothLE bluetoothLE) {
    bleConnection = bluetoothLE;  }

  /**
   * The BluetoothLE component connected to the micro:bit device (getter).
   * @return The BluetoothLE connection used to talk to the micro:bit device.
   */
  @SimpleProperty(description = "The BluetoothLE component connected to the micro:bit device.")
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  /**
   * Read a single sample of temperature data from the micro:bit. On successful read, the
   * <a href="#TemperatureDataReceived"><code>TemperatureDataReceived</code></a> event will be run.
   */
  @SimpleFunction
  public void ReadTemperature() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_CHARACTERISTIC_UUID, true, temperatureHandler);
    } else {
      reportNullConnection("ReadTemperature");
    }
  }

  /**
   * Request notifications of changes in the micro:bit's temperature sensor. Changes in the
   * temperature will be reported at a rate determined by the last period value set by a call to
   * <a href="#WriteTemperaturePeriod"><code>WriteTemperaturePeriod</code></a>. Temperature data
   * will be reported through the
   * <a href="#TemperatureDataReceived"><code>TemperatureDataReceived</code></a> event.
   */
  @SimpleFunction
  public void RequestTemperatureUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_CHARACTERISTIC_UUID, true, temperatureHandler);
    } else {
      reportNullConnection("RequestTemperatureUpdates");
    }
  }

  /**
   * Stop receiving updates from the micro:bit's temperature sensor. Note that there may be
   * pending messages from the device that will still be reported through the
   * <a href="#TemperatureDataReceived"><code>TemperatureDataReceived</code></a> event.
   */
  @SimpleFunction
  public void StopTemperatureUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_CHARACTERISTIC_UUID, temperatureHandler);
    } else {
      reportNullConnection("StopTemperatureUpdates");
    }
  }

  /**
   * The <code>TemperatureReceived</code> event is run whenever temperature samples are received
   * from the micro:bit. This is usually a result of performing a
   * <a href="#ReadTemperature">read</a> or <a href="#RequestTemperatureUpdates">request updates</a>
   * operation. The temperature value is measured in degrees Celsius.
   *
   * __Parameters__:
   *
   *     * <code>temperature_value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The temperature measured by the micro:bit, in degrees Celsius
   *
   * @param temperature_value The temperature measured by the micro:bit, in degrees Celsius
   */
  @SimpleEvent
  public void TemperatureReceived(final int temperature_value) {
    EventDispatcher.dispatchEvent(this, "TemperatureReceived", temperature_value);
  }

  /**
   * Read the current report rate for the micro:bit's temperature sensor. After the period is read,
   * it will be received by the
   * <a href="#TemperaturePeriodReceived"><code>TemperaturePeriodReceived</code></a> event.
   */
  @SimpleFunction
  public void ReadTemperaturePeriod() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(TEMPERATURE_SERVICE_UUID, TEMPERATURE_PERIOD_CHARACTERISTIC_UUID, false, temperaturePeriodHandler);
    } else {
      reportNullConnection("ReadTemperaturePeriod");
    }
  }

  /**
   * Use the <code>WriteTemperaturePeriod</code> method to change how frequently the micro:bit sends
   * temperature data to App Inventor. The period is measured in milliseconds.
   *
   * __Parameters__:
   *
   *     * <code>temperature_period_value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The new reporting period for the micro:bit's temperature sensor.
   *
   * @param temperature_period_value The new reporting period for the micro:bit's temperature sensor.
   */
  @SimpleFunction
  public void WriteTemperaturePeriod(final int temperature_period_value) {
    if (bleConnection != null) {
      bleConnection.ExWriteShortValuesWithResponse(TEMPERATURE_SERVICE_UUID, TEMPERATURE_PERIOD_CHARACTERISTIC_UUID, false, temperature_period_value, temperaturePeriodHandler);
    } else {
      reportNullConnection("WriteTemperaturePeriod");
    }
  }

  /**
   * The <code>TemperaturePeriodReceived</code> event is run after the micro:bit's temperature
   * reporting period is read from the device.
   *
   * __Parameters__:
   *
   *     * <code>temperature_period_value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The current reporting interval for the micro:bit's temperature sensor.
   *
   * @param temperature_period_value The current reporting interval for the micro:bit's temperature sensor.
   */
  @SimpleEvent
  public void TemperaturePeriodReceived(final int temperature_period_value) {
    EventDispatcher.dispatchEvent(this, "TemperaturePeriodReceived", temperature_period_value);
  }

  /**
   * The <code>WroteTemperaturePeriod</code> event is run after the micro:bit's temperature
   * reporting period is written to the device.
   *
   * __Parameters__:
   *
   *     * <code>temperature_period_value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The new reporting interval for the micro:bit's temperature sensor.
   *
   * @param temperature_period_value The new reporting interval for the micro:bit's temperature sensor.
   */
  @SimpleEvent
  public void WroteTemperaturePeriod(final int temperature_period_value) {
    EventDispatcher.dispatchEvent(this, "WroteTemperaturePeriod", temperature_period_value);
  }

  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Temperature.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
