// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

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
    description = "The <code>Microbit_Accelerometer</code> component lets users configure the BBC " +
        "micro:bit's on-board accelerometer and receive one or more accelerometer samples via the " +
        "appropriate methods.<br>\n\nThe accelerometer's report rate, or period, determines how " +
        "frequently data will be sent to App Inventor."
        /* removed until we have tutorials
        + "<!--<br>\n\n<strong>More links:</strong>" +
        "<ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/MicrobitAccelerometer.aia'" +
        "target='_blank'>sample project</a> for the micro:bit accelerometer.</li><li>View the " +
        "<a href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Microbit_Accelerometer.pdf' " +
        "target='_blank'>how to instructions</a> for the micro:bit accelerometer.</li></ul>-->"
        */,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbitaccelerometer",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Accelerometer extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String ACCELEROMETER_SERVICE_UUID = "E95D0753-251D-470A-A062-FA1922DFA9A8";
  private static final String ACCELEROMETER_DATA_CHARACTERISTIC_UUID = "E95DCA4B-251D-470A-A062-FA1922DFA9A8";
  private static final String ACCELEROMETER_PERIOD_CHARACTERISTIC_UUID = "E95DFB24-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> accelerometerDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        AccelerometerDataReceived(values.get(0), values.get(1), values.get(2));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> accelerometerPeriodWriteHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        AccelerometerPeriodReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
         WroteAccelerometerPeriod(values.get(0));
      }
    };

  public Microbit_Accelerometer(Form form) {
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
    bleConnection = bluetoothLE;
  }

  /**
   * The BluetoothLE component connected to the micro:bit device (getter).
   * @return The BluetoothLE connection used to talk to the micro:bit device.
   */
  @SimpleProperty(description = "The BluetoothLE component connected to the micro:bit device.")
  public BluetoothLE BluetoothDevice() {
    return bleConnection;
  }

  /**
   * Read a single sample of accelerometer data from the micro:bit. On successful read, the
   * <a href="#AccelerometerDataReceived"><code>AccelerometerDataReceived</code></a>
   * event will be run.
   */
  @SimpleFunction
  public void ReadAccelerometerData() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_DATA_CHARACTERISTIC_UUID, true, accelerometerDataHandler);
    } else {
      reportNullConnection("ReadAccelerometerData");
    }
  }

  /**
   * Request notifications of changes in the micro:bit's accelerometer. Changes in the accelerometer
   * will be reported at a rate determined by the last period value set by a call to
   * <a href="#WriteAccelerometerPeriod"><code>WriteAccelerometerPeriod</code></a>. Accelerometer
   * data will be reported through the
   * <a href="#AccelerometerDataReceived"><code>AccelerometerDataReceived</code></a> event.
   */
  @SimpleFunction
  public void RequestAccelerometerDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForShortValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_DATA_CHARACTERISTIC_UUID, true, accelerometerDataHandler);
    } else {
      reportNullConnection("RequestAccelerometerDataUpdates");
    }
  }

  /**
   * Stop receiving updates from the micro:bit's accelerometer. Note that there may be pending
   * messages from the device that will still be reported through the
   * <a href="#AccelerometerDataReceived"><code>AccelerometerDataReceived</code></a> event.
   */
  @SimpleFunction
  public void StopAccelerometerDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_DATA_CHARACTERISTIC_UUID, accelerometerDataHandler);
    } else {
      reportNullConnection("StopAccelerometerDataUpdates");
    }
  }

  /**
   * The <code>AccelerometerDataReceived</code> event is run whenever accelerometer samples are
   * received from the micro:bit. This is usually a result of performing a
   * <a href="#ReadAccelerometerData">read</a> or
   * <a href="#RequestAccelerometerDataUpdates">request</a> operation.
   * The X, Y, and Z values are in thousands of 1 Earth gravity (G=-9.8 m/s<sup>2</sup>), and so
   * should be scaled by 1/1000.
   *
   * __Parameters__:
   *
   *    + <code>Accelerometer_X</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The X value of the accelerometer, in milliG.
   *    + <code>Accelerometer_Y</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The Y value of the accelerometer, in milliG.
   *    + <code>Accelerometer_Z</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The Z value of the accelerometer, in milliG.
   *
   * @param Accelerometer_X The X value of the accelerometer, in milliG.
   * @param Accelerometer_Y The Y value of the accelerometer, in milliG.
   * @param Accelerometer_Z The Z value of the accelerometer, in milliG.
   */
  @SimpleEvent
  public void AccelerometerDataReceived(final int Accelerometer_X, final int Accelerometer_Y, final int Accelerometer_Z) {
    EventDispatcher.dispatchEvent(this, "AccelerometerDataReceived", Accelerometer_X, Accelerometer_Y, Accelerometer_Z);
  }

  /**
   * Read the current report rate for the micro:bit accelerometer. After the period is read, it will
   * be received by the
   * <a href="#AccelerometerPeriodReceived"><code>AccelerometerPeriodReceived</code></a> event.
   */
  @SimpleFunction
  public void ReadAccelerometerPeriod() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_PERIOD_CHARACTERISTIC_UUID, false, accelerometerPeriodWriteHandler);
    } else {
      reportNullConnection("ReadAccelerometerPeriod");
    }
  }

  /**
   * The <code>AccelerometerPeriodReceived</code> event is run after the micro:bit's accelerometer
   * period is read from the device.
   *
   * __Parameters__:
   *
   *    + <code>Accelerometer_Period</code> (_number_) &mdash; The current reporting interval for the micro:bit's accelerometer, in milliseconds.
   *
   * @param Accelerometer_Period The current reporting interval for the micro:bit's accelerometer, in milliseconds.
   */
  @SimpleEvent
  public void AccelerometerPeriodReceived(final int Accelerometer_Period) {
    EventDispatcher.dispatchEvent(this, "AccelerometerPeriodReceived", Accelerometer_Period);
  }

  /**
   * Use the <code>WriteAccelerometerPeriod</code> method to change how frequently the micro:bit
   * sends accelerometer data to App Inventor. The period is measured in milliseconds. According to
   * the micro:bit specification, valid values are 1, 2, 5, 10, 20, 80, 160, and 640 milliseconds.
   *
   * __Parameters__:
   *
   *    + <code>period</code> (_number_) &mdash; The new accelerometer reporting period, in milliseconds.
   *
   * @param period The new accelerometer reporting period, in milliseconds.
   */
  @SimpleFunction
  public void WriteAccelerometerPeriod(final short period) {
    if (bleConnection != null) {
      bleConnection.ExWriteShortValuesWithResponse(ACCELEROMETER_SERVICE_UUID, ACCELEROMETER_PERIOD_CHARACTERISTIC_UUID, false, period, accelerometerPeriodWriteHandler);
    } else {
      reportNullConnection("WriteAccelerometerPeriod");
    }
  }

  /**
   * The <code>WroteAccelerometerPeriod</code> event is run after the micro:bit reports its period
   * as requested by an earlier call to the
   * <a href="#ReadAccelerometerPeriod"><code>ReadAccelerometerPeriod</code></a> method.
   *
   * __Parameters__:
   *
   *    + <code>Accelerometer_Period</code> (_number_) &mdash; The reporting period for the micro:bit's accelerometer, in milliseconds.
   *
   * @param Accelerometer_Period The reporting period for the micro:bit's accelerometer, in milliseconds.
   */
  @SimpleEvent
  public void WroteAccelerometerPeriod(final int Accelerometer_Period) {
    EventDispatcher.dispatchEvent(this, "WroteAccelerometerPeriod", Accelerometer_Period);
  }

  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Accelerometer.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
