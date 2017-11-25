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
public class MT7697Led extends MT7697ExtensionBase {
  public MT7697Led(Form form) {
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
    // TODO
    return bleConnection != null;//  &&
      // bleConnection.isCharacteristicPublished(getPinServiceUUID(), getPinModeCharUUID()) &&
      // bleConnection.isCharacteristicPublished(getPinServiceUUID(), getPinDataCharUUID());
  }
}
