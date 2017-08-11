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
    description = "The <code>Microbit_Magnetometer</code> provides the ability to " +
        "configure the BBC micro:bit's on-board magnetometer and receive one or more magnetometer " +
        "samples via the appropriate methods."
        /* removed until we have tutorials
        + "<!--<br>\n\nThe magnetometer's report rate, or period, " +
        "determines how frequently data will be sent to App Inventor.<br>\n\n<strong>More links:" +
        "</strong><ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/MicrobitMagnetometer.aia' " +
        "target='_blank'>sample project</a> for the BBC micro:bit Magnetometer.</li><li>View the " +
        "<a href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Microbit_Magnetometer.pdf' " +
        "target='_blank'>how to instructions</a> for the micro:bit Magnetometer.</li></ul>-->"
        */,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbitmagnetometer",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Magnetometer extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String MAGNETOMETER_SERVICE_UUID = "E95DF2D8-251D-470A-A062-FA1922DFA9A8";
  private static final String MAGNETOMETER_DATA_CHARACTERISTIC_UUID = "E95DFB11-251D-470A-A062-FA1922DFA9A8";
  private static final String MAGNETOMETER_PERIOD_CHARACTERISTIC_UUID = "E95D386C-251D-470A-A062-FA1922DFA9A8";
  private static final String MAGNETOMETER_BEARING_CHARACTERISTIC_UUID = "E95D9715-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> magnetometerDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MagnetometerDataReceived(values.get(0), values.get(1), values.get(2));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> magnetometerPeriodHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MagnetometerPeriodReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteMagnetometerPeriod(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> magnetometerBearingHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        MagnetometerBearingReceived(values.get(0));
      }
    };

  public Microbit_Magnetometer(Form form) {
    super(form);
  }

  /**
   * The BluetoothLE component connected to the micro:bit device (setter).
   * @param bluetoothLE the BluetoothLE device
   */
  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COMPONENT +
      ":edu.mit.appinventor.ble.BluetoothLE")
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
   * Read a single sample of magnetometer data from the micro:bit. On successful read, the
   * <a href="#MagnetometerDataReceived"><code>MagnetometerDataReceived</code></a> event will be
   * run.
   */
  @SimpleFunction
  public void ReadMagnetometerData() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_DATA_CHARACTERISTIC_UUID, true, magnetometerDataHandler);
    } else {
      reportNullConnection("ReadMagnetometerData");
    }
  }

  /**
   * Request notifications of changes in the micro:bit's magnetometer. Changes in the magnetometer
   * will be reported at a rate determined by the last period value set by a call to
   * <a href="#WriteMagnetometerPeriod"><code>WriteMagnetometerPeriod</code></a>. Magnetometer
   * data will be reported through the
   * <a href="#MagnetometerDataReceived"><code>MagnetometerDataReceived</code></a> event.
   */
  @SimpleFunction
  public void RequestMagnetometerDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_DATA_CHARACTERISTIC_UUID, true, magnetometerDataHandler);
    } else {
      reportNullConnection("RequestMagnetometerDataUpdates");
    }
  }

  /**
   * Stop receiving updates from the micro:bit's magnetometer. Note that there may be pending
   * messages from the device that will still be reported through the
   * <a href="#MagnetometerDataReceived"><code>MagnetometerDataReceived</code></a> event.
   */
  @SimpleFunction
  public void StopMagnetometerDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_DATA_CHARACTERISTIC_UUID, magnetometerDataHandler);
    } else {
      reportNullConnection("StopMagnetometerDataUpdates");
    }
  }

  /**
   * The <code>MagnetometerDataReceived</code> event is run whenever magnetometer samples are
   * received from the micro:bit. This is usually a result of performing a
   * <a href="#ReadMagnetometerData">read</a> or
   * <a href="#RequestMagnetometerDataUpdates">request</a> operation. The X, Y, and Z values are in
   * milli-teslas, and so should be scaled by 1/1000.
   *
   * __Parameters__:
   *
   *    + <code>Magnetometer_X</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The X value of the magnetometer, in milliT.
   *    + <code>Magnetometer_Y</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The Y value of the magnetometer, in milliT.
   *    + <code>Magnetometer_Z</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The Z value of the magnetometer, in milliT.
   *
   * @param Magnetometer_X The X value of the magnetometer, in milliT.
   * @param Magnetometer_Y The Y value of the magnetometer, in milliT.
   * @param Magnetometer_Z The Z value of the magnetometer, in milliT.
   */
  @SimpleEvent
  public void MagnetometerDataReceived(final int Magnetometer_X, final int Magnetometer_Y, final int Magnetometer_Z) {
    EventDispatcher.dispatchEvent(this, "MagnetometerDataReceived", Magnetometer_X, Magnetometer_Y, Magnetometer_Z);
  }

  /**
   * Read the current report rate for the micro:bit magnetometer. After the period is read, it will
   * be received by the
   * <a href="#MagnetometerPeriodReceived"><code>MagnetometerPeriodReceived</code></a> event.
   */
  @SimpleFunction
  public void ReadMagnetometerPeriod() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_PERIOD_CHARACTERISTIC_UUID, false, magnetometerPeriodHandler);
    } else {
      reportNullConnection("ReadMagnetometerPeriod");
    }
  }

  /**
   * The <code>MagnetometerPeriodReceived</code> event is run after the micro:bit's magnetometer
   * period is read from the device.
   *
   * __Parameters__:
   *
   *     * <code>Magnetometer_Period</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The current reporting interval for the micro:bit's magnetometer, in milliseconds.
   *
   * @param Magnetometer_Period The current reporting interval for the micro:bit's magnetometer, in milliseconds.
   */
  @SimpleEvent
  public void MagnetometerPeriodReceived(final int Magnetometer_Period) {
    EventDispatcher.dispatchEvent(this, "MagnetometerPeriodReceived", Magnetometer_Period);
  }

  /**
   * Use the <code>WriteMagnetometerPeriod</code> method to change how frequently the micro:bit
   * sends magnetometer data to App Inventor. The period is measured in milliseconds. According to
   * the micro:bit specification, valid values are 1, 2, 5, 10, 20, 80, 160 and 640 milliseconds.
   *
   * __Parameters__:
   *
   *     * <code>Magnetometer_Period</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The new magnetometer reporting period, in milliseconds.
   *
   * @param Magnetometer_Period The new magnetometer reporting period, in milliseconds.
   */
  @SimpleFunction
  public void WriteMagnetometerPeriod(final int Magnetometer_Period) {
    if (bleConnection != null) {
      bleConnection.ExWriteShortValuesWithResponse(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_PERIOD_CHARACTERISTIC_UUID, false, Magnetometer_Period, magnetometerPeriodHandler);
    } else {
      reportNullConnection("WriteMagnetometerPeriod");
    }
  }

  /**
   * The <code>WroteMagnetometerPeriod</code> event is run after the micro:bit reports its period
   * as requested by an earlier call to the
   * <a href="#ReadMagnetometerPeriod"><code>ReadAccelerometerPeriod</code></a> method.
   *
   * __Parameters__:
   *
   *     * <code>Magnetometer_Period</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The reporting periodf or the micro:bit's magnetometer, in milliseconds.
   *
   * @param Magnetometer_Period The reporting periodf or the micro:bit's magnetometer, in milliseconds.
   */
  @SimpleEvent
  public void WroteMagnetometerPeriod(final int Magnetometer_Period) {
    EventDispatcher.dispatchEvent(this, "WroteMagnetometerPeriod", Magnetometer_Period);
  }

  /**
   * Read a single bearing sample from the micro:bit. On successful read, the
   * <a href="#MagnetometerBearingReceived"><code>MagnetometerBearingReceived</code></a> event
   * will be run.
   */
  @SimpleFunction
  public void ReadMagnetometerBearing() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_BEARING_CHARACTERISTIC_UUID, false, magnetometerBearingHandler);
    } else {
      reportNullConnection("ReadMagnetometerBearing");
    }
  }

  /**
   * Request notifications of changes in the micro:bit's bearing. Changes in the bearing will be
   * reported at a rate determined by the last period value set by a call to
   * <a href="#WriteMagnetometerPeriod"><code>WriteMagnetometerPeriod</code></a>. Bearing data
   * will be reported through the
   * <a href="#MagnetometerBearingReceived"><code>MagnetometerBearingReceived</code></a> event.
   */
  @SimpleFunction
  public void RequestMagnetometerBearingUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForShortValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_BEARING_CHARACTERISTIC_UUID, false, magnetometerBearingHandler);
    } else {
      reportNullConnection("RequestMagnetometerBearingUpdates");
    }
  }

  /**
   * Stop receiving updates from the micro:bit's magnetometer. Note that there may be pending
   * messages from the device that will still be reported through the
   * <a href="#MagnetometerBearingReceived"><code>MagnetometerBearingReceived</code></a> event.
   */
  @SimpleFunction
  public void StopMagnetometerBearingUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(MAGNETOMETER_SERVICE_UUID, MAGNETOMETER_BEARING_CHARACTERISTIC_UUID, magnetometerBearingHandler);
    } else {
      reportNullConnection("StopMagnetometerBearingUpdates");
    }
  }

  /**
   * The <code>MagnetometerBearingReceived</code> event is run whenever bearing samples are
   * received from the micro:bit. This is usually a result of performing a
   * <a href="#ReadMagnetometerBearing">read</a> or
   * <a href="#RequestMagnetometerBearingUpdates">request</a> operation. The bearing value is
   * reported as degrees relative to North.
   *
   * __Parameters__:
   *
   *     * <code>bearing_value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The bearing from North, in degrees.
   *
   * @param bearing_value The bearing from North, in degrees.
   */
  @SimpleEvent
  public void MagnetometerBearingReceived(final int bearing_value) {
    EventDispatcher.dispatchEvent(this, "MagnetometerBearingReceived", bearing_value);
  }

  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Magnetometer.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
