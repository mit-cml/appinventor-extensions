// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.arduino101;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Form;

/**
 * Extension for Arduino 101 that allows the user to manipulate attached motors.
 *
 * @author ewpatton@mit.edu (Evan W. Patton)
 */
@DesignerComponent(version = 1,
    description = "The Arduino 101 PWM Motor extension lets users control external devices that " +
        "respond to pulse width modulation, such as continuous motors or lights. On the Arduino " +
        "101, digital pins 3, 5, 6, and 9 support pulse width modulation.<br>\n\n<strong>More " +
        "Links</strong><ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/Arduino101PWMMotor.aia' " +
        "target='_blank'>sample project</a> for the Arduino 101 Pulse Width Modulation for Motors " +
        "component.</li><li>View the <a " +
        "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_PWM_Motor.pdf' " +
        "target='_blank'>how to instructions</a> for the Arduino 101 PWM Motor.</li></ul>",
    category = ComponentCategory.EXTENSION,
    helpUrl = "http://iot.appinventor.mit.edu/#/arduino101/arduinopwm",
    nonVisible = true,
    iconName = "aiwebres/arduino.png")
@SimpleObject(external = true)
public class Arduino101PWMMotor extends Arduino101ExtensionWithIntensity {
  private static final String PWM_SERVICE_UUID = "E95D0F00-251D-470A-A062-FA1922DFA9A7";
  private static final String PWM_CHARACTERISTIC_UUID = "E95D0F01-251D-470A-A062-FA1922DFA9A7";

  public Arduino101PWMMotor(Form form) {
    super(form);
    super.Pin(3);
    super.Intensity(100);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
      defaultValue = "3")
  public void Pin(int pin) {
    super.Pin(pin);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
      defaultValue = "100")
  @SimpleProperty
  public void Speed(int intensity) {
    super.Intensity(intensity);
  }

  @SimpleProperty(description = "The rate of pulse width modulation as a percentage of time. A " +
      "value of 0 indicates an always-off state, 50 would result in the device being powered for " +
      "half of each time unit, and 100 results in an always-on state. Default: 100.")
  public int Speed() {
    return super.Intensity();
  }

  @Override
  protected String getService() {
    return PWM_SERVICE_UUID;
  }

  @Override
  protected String getCharacteristic() {
    return PWM_CHARACTERISTIC_UUID;
  }
}
