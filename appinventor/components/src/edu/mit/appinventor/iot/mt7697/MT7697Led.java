// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Form;

import static edu.mit.appinventor.iot.mt7697.Constants.LED_SERVICE_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.LED_CHARACTERISTIC_UUID;

/**
 * Extension for MT7697 that allows the user to manipulate attached LEDs.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The Arduino LED component lets users control light-emitting diodes (LEDs) from" +
                                 " their App Inventor projects. If the LED is plugged into a pin supporting pulse width " +
                                 "modulation (PWM), then the LED's brightness can be controlled by varying the Intensity " +
                                 "property. TurnOn and TurnOff methods are used to control the power state of the LED." +
                                 "<br>\n\n<strong>More Links:</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697LED.aia' " +
                                 "target='_blank'>sample project</a> for the MT7697 LED.</li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_LED_Control.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 LED.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Led extends MT7697ExtensionWithIntensity {
  public MT7697Led(Form form) {
    super(form);
    super.Pin(13);
    super.Intensity(100);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
                    defaultValue = "13")
  @SimpleProperty
  public void Pin(int pin) {
    super.Pin(pin);
  }

  @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER,
                    defaultValue = "100")
  @SimpleProperty
  public void Intensity(int intensity) {
    super.Intensity(intensity);
  }

  @SimpleProperty(description = "The Intensity of the LED valued from 1-100. Intensity will only " +
                                "affect the brightness of the LED if it is plugged into a pin supporting pulse width " +
                                "modulation (PWM). For the MT7697, the PWM pins are 3, 5, 6, and 9. For all other " +
                                "pins the LED will either turn on or off with no change in intensity.")
  public int Intensity() {
    return super.Intensity();
  }

  protected String getService() {
    return LED_SERVICE_UUID;
  }

  protected String getCharacteristic() {
    return LED_CHARACTERISTIC_UUID;
  }
}
