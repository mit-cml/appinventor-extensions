// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE.BLEResponseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * An extension to interface with a servo connected to an MT7697 running the App Inventor
 * Companion Sketch.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697 Servo extension lets users remotely control servos connected to" +
                                 "an MT7697.<br>\n\n<strong>More Links</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697Servo.aia' " +
                                 "target='_blank'>sample project</a> for the MT7697 Servo.</li><!--<li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Servo.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 Servo.</li>--></ul>",
                   category = ComponentCategory.EXTENSION,
                   helpUrl = "http://iot.appinventor.mit.edu/#/arduino101/arduinoservo",
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Servo extends MT7697ExtensionBase {
  private static final String SERVO_SERVICE_UUID = "E95D0C00-251D-470A-A062-FA1922DFA9A7";
  private static final String SERVO_POSITION_CHARACTERISTIC_UUID =
    "E95D0C01-251D-470A-A062-FA1922DFA9A7";
  private static final String SERVO_POSITION_MICROS_CHARACTERISTIC_UUID =
    "E95D0C02-251D-470A-A062-FA1922DFA9A7";

  private final BLEResponseHandler<Integer> positionWritten =
    new BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        PositionWritten(values.get(0));
      }
    };

  private int pin;

  public MT7697Servo(Form form) {
    super(form);
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
      bleConnection.isCharacteristicPublished(SERVO_SERVICE_UUID, SERVO_POSITION_CHARACTERISTIC_UUID);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
                    defaultValue = "0")
  @SimpleProperty
  public void Pin(int pin) {
    this.pin = pin;
  }

  @SimpleProperty(description = "The Pin on the Arduino board that the device is wired in to.")
  public int Pin() {
    return pin;
  }

  /**
   * Set the rotation of a positional servo. Valid values are 0-180 degrees.
   *
   * __Parameters__:
   *
   *     * <code>position</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The new position of the servo in degrees. Valid values are 0-180.
   *
   * @param position The new position of the servo in degrees. Valid values are 0-180.
   */
  @SimpleFunction
  public void SetPosition(int position) {
    writeValue(position, SERVO_POSITION_CHARACTERISTIC_UUID);
  }

  /**
   * Set the time per revolution in microseconds of a continuous rotation servo. For a standard
   * servo, 1000 is fully counterclockwise and 2000 is fully clockwise.
   *
   * __Parameters__:
   *
   *     * <code>position</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The new rotational speed for a continuous rotational servo.
   *
   * @param position The new rotational speed for a continuous rotational servo.
   */
  @SimpleFunction
  public void SetPositionMicros(int position) {
    writeValue(position, SERVO_POSITION_MICROS_CHARACTERISTIC_UUID);
  }

  /**
   * The <code>PositionWritten</code> event is run after a successful write to the servo's position.
   * The meaning of the <code>position</code> parameter depends on whether position update was
   * triggered by a <a href="#SetPosition"><code>SetPosition</code></a> call or a
   * <a href="#SetPositionMicros"><code>SetPositionMicros</code></a> call.
   *
   * __Parameters__:
   *
   *     * <code>position</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
   *       The new position of the servo. If the call was positional, this will be in degrees. If the call was rotational, it will be in microseconds.
   *
   * @param position The new position of the servo. If the call was positional, this will be in degrees. If the call was rotational, it will be in microseconds.
   */
  @SimpleEvent
  public void PositionWritten(final int position) {
    EventDispatcher.dispatchEvent(this, "PositionWritten", position);
  }

  private void writeValue(int position, String characteristicUuid) {
    if (bleConnection != null) {
      List<Integer> values = new ArrayList<Integer>(2);
      values.add(pin);
      values.add(position);
      bleConnection.ExWriteShortValuesWithResponse(SERVO_SERVICE_UUID,
                                                   characteristicUuid,
                                                   true,
                                                   values,
                                                   positionWritten);
    }
  }
}
