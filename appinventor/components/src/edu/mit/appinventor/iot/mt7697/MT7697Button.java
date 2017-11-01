// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package edu.mit.appinventor.iot.mt7697;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import edu.mit.appinventor.ble.BluetoothLE;

import java.util.List;

/**
 * An extension for the MT7697 to interact with buttons attached to digital pins.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697Button component lets users listen to events triggered by a " +
                                 "button connected to a digital pin on the Arduino. The extension responds to voltage " +
                                 "changes on the specified digital <code>Pin</code> and so can be used to respond to any " +
                                 "hardware component that signals high/low based on some external phenomenon, such as a " +
                                 "motion detector.<br>\n\n<strong>More links:</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697Button.aia' " +
                                 "target='_blank'>sample project</a> for the MT7697 Button.</li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Button.pdf' " +
                                 "target='_blank'>how to instructions</a> for the MT7697 Button.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697Button extends MT7697ExtensionWithPin<MT7697Button> {
  private static final String BUTTON_SERVICE_UUID = "E95D0200-251D-470A-A062-FA1922DFA9A7";
  private static final String BUTTON_PIN_CHARACTERISTIC_UUID = "E95D0201-251D-470A-A062-FA1922DFA9A7";
  private static final String BUTTON_DATA_CHARACTERISTIC_UUID = "E95D0202-251D-470A-A062-FA1922DFA9A7";

  private static final byte[] BITS = new byte[] {
      -128, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01
  };

  private final BluetoothLE.BLEResponseHandler<Integer> buttonDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      private boolean lastKnownState = false;

      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        ButtonStateReceived(values.get(0));
        int pin = Pin();
        int i = pin / 8, j = pin % 8;
        if ((values.get(i).intValue() & BITS[j]) == BITS[j]) {
          if (!lastKnownState) {
            lastKnownState = true;
            Pressed();
          }
        } else if (lastKnownState) {
          lastKnownState = false;
          Released();
        }
      }
    };

  public MT7697Button(Form form) {
    super(form, DIGITAL);
  }

  /**
   * Read the current state of the button as reported by the Arduino. On success, the
   * <a href="#ButtonStateReceived"><code>ButtonStateReceived</code></a> event will be run. If the
   * state of the button has changed, the <a href="#Pressed"><code>Pressed</code></a> or
   * <a href="#Released"><code>Released</code></a> events will be run as well.
   */
  @SimpleFunction
  public void ReadButtonState() {
    bleConnection.ExReadByteValues(BUTTON_SERVICE_UUID,
                                   BUTTON_DATA_CHARACTERISTIC_UUID,
                                   false,
                                   buttonDataHandler);
  }

  /**
   * Request notifications from the Arduino for changes in the button state. The <a
   * href="#ButtonStateReceived"><code>ButtonStateReceived</code></a> event will be run after
   * every sample is received, even if no change occurs. If a change in the state occurs, either
   * the <a href="#Pressed"><code>Pressed</code></a> or the <a
   * href="#Released"><code>Released</code></a> event will be run.
   */
  @SimpleFunction
  public void RequestButtonStateUpdates() {
    bleConnection.ExRegisterForByteValues(BUTTON_SERVICE_UUID,
                                          BUTTON_DATA_CHARACTERISTIC_UUID,
                                          false,
                                          buttonDataHandler);
  }

  /**
   * Stop listening for notifications of button states from the Arduino. This only has an effect
   * if there was a previous call to <a
   * href="#RequestButtonStateUpdates"><code>RequestButtonStateUpdates</code></a>. There may be
   * additional pending messages that will be processed after this call, so if accuracy in the
   * data delivery are important an additional variable should be used to track the state of
   * event processing.
   */
  @SimpleFunction
  public void StopButtonStateUpdates() {
    bleConnection.ExUnregisterForValues(BUTTON_SERVICE_UUID,
                                        BUTTON_DATA_CHARACTERISTIC_UUID,
                                        buttonDataHandler);
  }

  /**
   * The <code>Pressed</code> event is run when a button state is observed to go from a low signal
   * to a high signal, indicating that the button switch has been pressed and the circuit is closed.
   */
  @SimpleEvent
  public void Pressed() {
    EventDispatcher.dispatchEvent(this, "Pressed");
  }

  /**
   * The <code>Released</code> event is run when a button state is observed to go from a high signal
   * to a low signal, indicating that the button switch has been released and the circuit is open.
   */
  @SimpleEvent
  public void Released() {
    EventDispatcher.dispatchEvent(this, "Released");
  }

  /**
   * The <code>ButtonStateReceived</code> event is run when any button state is received by the
   * Bluetooth low energy component from the Arduino. This state value may be the same for
   * extended periods of time if the button switch is left in an open or closed state.
   *
   * __Parameter__:
   *
   *     * <code>Button_State</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The button state: 0 if released, 1 if pressed.
   *
   * @param Button_State The button state: 0 if released, 1 if pressed.
   */
  @SimpleEvent
  public void ButtonStateReceived(int Button_State) {
    EventDispatcher.dispatchEvent(this, "ButtonStateReceived", Button_State);
  }

  // MT7697ExtensionWithPin implementation

  public String getPinServiceUuid() {
    return BUTTON_SERVICE_UUID;
  }

  public String getPinCharacteristicUuid() {
    return BUTTON_PIN_CHARACTERISTIC_UUID;
  }
}
