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
    description = "The <code>Microbit_Button</code> extension provides App Inventor with information about " +
        "the state of the BBC micro:bit's buttons. Developers can use this extension to request " +
        "updates for when a user presses a button or read the current state of the buttons. The " +
        "buttons have three states:<br>\n<ul><li>0 - up</li><li>1 - down</li><li>2 - long " +
        "pressed</li></ul>"
        /* removed until we have tutorials for the button.
        + "<br>\n\n<strong>More links:</strong><ul><li>Download a" +
        "<a href='http://iot.appinventor.mit.edu/assets/samples/MicrobitButton.aia' " +
        "target='_blank'>sample project</a> for the micro:bit buttons.</li><li>View the " +
        "<a href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Microbit_Button.pdf' " +
        "target='_blank'>how to instructions</a> for the micro:bit buttons.</li></ul>"
        */,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbitbutton",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Button extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String BUTTON_SERVICE_UUID = "E95D9882-251D-470A-A062-FA1922DFA9A8";
  private static final String BUTTON_A_STATE_CHARACTERISTIC_UUID = "E95DDA90-251D-470A-A062-FA1922DFA9A8";
  private static final String BUTTON_B_STATE_CHARACTERISTIC_UUID = "E95DDA91-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> buttonAStateHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        ButtonAStateReceived(values.get(0));
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> buttonBStateHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        ButtonBStateReceived(values.get(0));
      }
    };

  public Microbit_Button(Form form) {
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
   * Read the current state of the micro:bit's A button. After a successful read, the
   * <a href="#ButtonAStateReceived"><code>ButtonAStateReceived</code></a> event will be run.
   */
  @SimpleFunction
  public void ReadButtonAState() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(BUTTON_SERVICE_UUID, BUTTON_A_STATE_CHARACTERISTIC_UUID, false, buttonAStateHandler);
    } else {
      reportNullConnection("ReadButtonAState");
    }
  }

  /**
   * Request updates to the state of the micro:bit's A button. After requesting updates, the
   * <a href="#ButtonAStateReceived"><code>ButtonAStateReceived</code></a> will be run whenever
   * the micro:bit reports a button state change to the app. Pressing and holding the button
   * will result in a second event with the value 2 to indicate a long press.
   */
  @SimpleFunction
  public void RequestButtonAStateUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForShortValues(BUTTON_SERVICE_UUID, BUTTON_A_STATE_CHARACTERISTIC_UUID, false, buttonAStateHandler);
    } else {
      reportNullConnection("RequestButtonAStateUpdates");
    }
  }

  /**
   * Stop receiving updates about the state of the micro:bit's A button. Note that there may
   * be pending updates that have not been processed that will result in
   * <a href="#ButtonAStateReceived"><code>ButtonAStateReceived</code></a>
   */
  @SimpleFunction
  public void StopButtonAStateUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(BUTTON_SERVICE_UUID, BUTTON_A_STATE_CHARACTERISTIC_UUID, buttonAStateHandler);
    } else {
      reportNullConnection("StopButtonAStateUpdates");
    }
  }

  /**
   * After performing a <a href="#ReadButtonAState">read</a> or
   * <a href="#RequestButtonAStateUpdates">request for updates</a>, the
   * <code>ButtonAStateReceived</code> event will be run with information about the button state.
   *
   * __Parameters__:
   *
   *   + <code>Button_State_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The state of the micro:bit's A button; 0 for released, 1 for pressed, 2 for long-pressed.
   */
  @SimpleEvent
  public void ButtonAStateReceived(final int Button_State_Value) {
    EventDispatcher.dispatchEvent(this, "ButtonAStateReceived", Button_State_Value);
  }

  /**
   * Read the current state of the micro:bit's B button. After a successful read, the
   * <a href="#ButtonBStateReceived"><code>ButtonBStateReceived</code></a> event will be run.
   */
  @SimpleFunction
  public void ReadButtonBState() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(BUTTON_SERVICE_UUID, BUTTON_B_STATE_CHARACTERISTIC_UUID, false, buttonBStateHandler);
    } else {
      reportNullConnection("REadButtonBState");
    }

  }

  /**
   * Request updates to the state of the micro:bit's B button. After requesting updates, the
   * <a href="#ButtonBStateReceived"><code>ButtonBStateReceived</code></a> will be run whenever
   * the micro:bit reports a button state change to the app. Pressing and holding the button will
   * result in a second event with the value 2 to indicate a long press.
   */
  @SimpleFunction
  public void RequestButtonBStateUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForShortValues(BUTTON_SERVICE_UUID, BUTTON_B_STATE_CHARACTERISTIC_UUID, false, buttonBStateHandler);
    } else {
      reportNullConnection("RequestButtonBStateUpdates");
    }
  }

  /**
   * Stop receiving updates about the state of the micro:bit's B button. Note that there may be
   * pending updates that have not been processed that will result in
   * <a href="#ButtonBStateReceived"><code>ButtonBStateReceived</code></a>.
   */
  @SimpleFunction
  public void StopButtonBStateUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(BUTTON_SERVICE_UUID, BUTTON_B_STATE_CHARACTERISTIC_UUID, buttonBStateHandler);
    } else {
      reportNullConnection("STopButtonBStateUpdates");
    }
  }

  /**
   * After performing a <a href="#ReadButtonBState">read</a> or
   * <a href="#RequestButtonBStateUpdates">request for updates</a>, the
   * <code>ButtonBStateReceived</code> event will be run with information about the button state.
   *
   * __Parameters__:
   *
   *   + <code>Button_State_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The state of the micro:bit's B button; 0 for released, 1 for pressed, 2 for long-pressed.
   */
  @SimpleEvent
  public void ButtonBStateReceived(final int Button_State_Value) {
    EventDispatcher.dispatchEvent(this, "ButtonBStateReceived", Button_State_Value);
  }

  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Button.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
