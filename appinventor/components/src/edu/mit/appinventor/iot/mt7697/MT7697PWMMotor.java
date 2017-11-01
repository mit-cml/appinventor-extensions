// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Form;

import static edu.mit.appinventor.iot.mt7697.Constants.PWM_SERVICE_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.PWM_CHARACTERISTIC_UUID;

/**
 * Extension for MT7697 that allows the user to manipulate attached motors.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697 PWM Motor extension lets users control external devices that " +
                                 "respond to pulse width modulation, such as continuous motors or lights. On the Arduino " +
                                 "101, digital pins 3, 5, 6, and 9 support pulse width modulation.<br>\n\n<strong>More " +
                                 "Links</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697PWMMotor.aia' " +
                                 "target='_blank'>sample project</a> for the MT7697 Pulse Width Modulation for Motors " +
                                 "component.</li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_PWM_Motor.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 PWM Motor.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   helpUrl = "http://iot.appinventor.mit.edu/#/arduino101/arduinopwm",
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697PWMMotor extends MT7697ExtensionWithIntensity {
  public MT7697PWMMotor(Form form) {
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
