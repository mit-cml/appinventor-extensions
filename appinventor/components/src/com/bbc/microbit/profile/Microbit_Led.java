// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017 Massachusetts Institute of Technology, All rights reserved.

package temp;

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
    description = "The <code>Microbit_LED</code> extension provides App Inventor users with the ability to " +
        "programmatically change the BBC micro:bit's 5x5 LED matrix, either by coding custom patterns " +
        "or writing text strings. The scrolling speed of the display may also be customized.<br>" +
        "\n\n<strong>More links:</strong><ul><li>Download a " +
        "<a href='http://iot.appinventor.mit.edu/assets/samples/MicrobitLED.aia' " +
        "target='_blank'>sample projects</a> for the micro:bit LED matrix.</li><li>View the " +
        "<a href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_Microbit_LED.pdf' " +
        "target='_blank'>how to instructions</a> for the micro:bit LED matrix.</li></ul>",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbitled",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Led extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String LED_SERVICE_UUID = "E95DD91D-251D-470A-A062-FA1922DFA9A8";
  private static final String LED_MATRIX_STATE_CHARACTERISTIC_UUID = "E95D7B77-251D-470A-A062-FA1922DFA9A8";
  private static final String LED_TEXT_CHARACTERISTIC_UUID = "E95D93EE-251D-470A-A062-FA1922DFA9A8";
  private static final String SCROLLING_DELAY_CHARACTERISTIC_UUID = "E95D0D2D-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> lEDMatrixStateHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        LEDMatrixStateReceived(values);
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteLEDMatrixState(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> lEDTextHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteLEDText(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> scrollingDelayHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        ScrollingDelayReceived(values.get(0));
      }

      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WroteScrollingDelay(values.get(0));
      }
    };

  public Microbit_Led(Form form) {
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
   * Read the current state of the LED matrix from the micro:bit. The LED matrix state will be
   * reported through the <a href="#LEDMatrixStateReceived"><code>LEDMatrixStateReceived</code></a>
   * event.
   */
  @SimpleFunction
  public void ReadLEDMatrixState() {
    if (bleConnection != null) {
      bleConnection.ExReadByteValues(LED_SERVICE_UUID, LED_MATRIX_STATE_CHARACTERISTIC_UUID, false, lEDMatrixStateHandler);
    } else {
      reportNullConnection("ReadLEDMatrixState");
    }
  }

  /**
   * The <code>LEDMatrixStateReceived</code> event is run when the state of the micro:bit's LED
   * matrix is read from the device. The <code>LED_Matrix_State</code> is a list of 5 values, one
   * for each row of the matrix. Each value is an number from 0-31. From left to right, the LEDs
   * are valued 16, 8, 4, 2, 1.<br>\n\n
   *
   * __Parameters__:
   *
   *    + <code>LED_Matrix_State</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash; A list of 5 values, one for each row, with
   *      each value being between 0-31 to indicate which LEDs in that row are on.
   *
   * @param LED_Matrix_State A list of 5 values, one for each row, with each value being between
   *                         0-31 to indicate which LEDs in that row are on.
   */
  @SimpleEvent
  public void LEDMatrixStateReceived(final List<Integer> LED_Matrix_State) {
    EventDispatcher.dispatchEvent(this, "LEDMatrixStateReceived", LED_Matrix_State);
  }

  /**
   * Write the state of the micro:bit's LED matrix. <code>LED_Matrix_State</code> should be a list
   * of 5 numbers from 0-31 to indicate which LEDs should be turned on in each of the 5 rows of the
   * LED matrix. From left to right, the values of the LEDs are 16, 8, 4, 2, 1. For example, if you
   * want to turn on the LEDs in one row such that power states were 10011, you would send the value
   * (16 + 2 + 1) or 19 for the row. After the write operation completes, the
   * <a href="#WroteLEDMatrixState"><code>WroteLEDMatrixState</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>LED_Matrix_State</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash; A list of 5 numbers, one for each row of the LED matrix, where each
   *       value is the integer value of the bit array for the nth row of the LED matrix.
   *
   * @param LED_Matrix_State A list of 5 numbers, one for each row of the LED matrix, where each
   *                         value is the integer value of the bit array for the nth row of the
   *                         LED matrix.
   */
  @SimpleFunction
  public void WriteLEDMatrixState(final List<Integer> LED_Matrix_State) {
    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(LED_SERVICE_UUID, LED_MATRIX_STATE_CHARACTERISTIC_UUID, false, (Object) LED_Matrix_State, lEDMatrixStateHandler);
    } else {
      reportNullConnection("WriteLEDMatrixState");
    }
  }

  /**
   * The <code>WriteLEDMatrixState</code> event will be run after the micro:bit's LED matrix is
   * written due to a call to <a href="#WriteLEDMatrixState"><code>WriteLEDMatrixState</code></a>.
   * The LED_Matrix_State will be the same as in the call to <code>WriteLEDMatrixState</code> to
   * differentiate the response to potentially many calls to write the LED matrix.
   *
   * __Parameters__:
   *
   *     * <code>LED_Matrix_State</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash; The values written to the LED matrix as specified in the previous call
   *       to {@link #WriteLEDMatrixState(List)} that resulted in this event.
   *
   * @param LED_Matrix_State The values written to the LED matrix as specified in the previous call
   *                         to {@link #WriteLEDMatrixState(List)} that resulted in this event.
   */
  @SimpleEvent
  public void WroteLEDMatrixState(final List<Integer> LED_Matrix_State) {
    EventDispatcher.dispatchEvent(this, "WroteLEDMatrixState", LED_Matrix_State);
  }

  /**
   * Write text to the micro:bit's LED matrix. The rate at which each character appears on the
   * micro:bit's display depends on the last scroll value written by
   * <a href="#WriteScrollDelay"><code>WriteScrollDelay</code></a>. Due to the allowable length of
   * Bluetooth low energy packets, the strings sent are restricted to 20 or more UTF-8 octets.
   * Attempts to write strings longer than 20 octets will result in truncated messages.
   *
   * __Parameters__:
   *
   *    + <code>LED_Text_Value</code> (_string_) &mdash; The text to write. Strings are limited to
   *      a length of 20 UTF-8 octets.
   *
   * @param LED_Text_Value The text to write. Strings are limited to a length of 20 UTF-8 octets.
   */
  @SimpleFunction
  public void WriteLEDText(final String LED_Text_Value) {
    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(LED_SERVICE_UUID, LED_TEXT_CHARACTERISTIC_UUID, false, LED_Text_Value, lEDTextHandler);
    } else {
      reportNullConnection("WriteLEDText");
    }
  }

  /**
   * The <code>WroteLEDText</code> event will be run after text is written to the micro:bit's LED
   * matrix through a call to <a href="#WriteLEDText"><code>WriteLEDText</code></a>. The value of
   * the written text will be given by the <code>LED_Text_Value</code> parameter.
   *
   * __Parameters__:
   *
   *     * <code>LED_Text_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash; The text to write to the LED matrix.
   *
   * @param LED_Text_Value The text written to the LED matrix.
   */
  @SimpleEvent
  public void WroteLEDText(final List<Integer> LED_Text_Value) {
    EventDispatcher.dispatchEvent(this, "WroteLEDText", LED_Text_Value);
  }

  /**
   * Read the current scroll delay for the micro:bit's LED matrix. After a successful read, the
   * <a href="#ScrollingDelayReceived"><code>ScrollingDelayReceived</code></a> event will be run.
   */
  @SimpleFunction
  public void ReadScrollingDelay() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(LED_SERVICE_UUID, SCROLLING_DELAY_CHARACTERISTIC_UUID, false, scrollingDelayHandler);
    } else {
      reportNullConnection("ReadScrollingDelay");
    }
  }

  /**
   * The <code>Scrolling_Delay_Value</code> event will be run after requesting the scrolling delay
   * for the micro:bit's LED matrix through the
   * <a href="#ReadScrollingDelay><code>ReadScrollingDelay</code></a> method.
   *
   * __Parameters__:
   *
   *     * <code>Scrolling_Delay_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The delay between characters on the LED matrix, in milliseconds.
   *
   * @param Scrolling_Delay_Value The number of millisecond for each character to be shown on the
   *                              micro:bit's LED matrix.
   */
  @SimpleEvent
  public void ScrollingDelayReceived(final int Scrolling_Delay_Value) {
    EventDispatcher.dispatchEvent(this, "ScrollingDelayReceived", Scrolling_Delay_Value);
  }
  /**
   * Set the delay between characters displayed on the micro:bit's LED matrix, in milliseconds.
   * After writing the value, the
   * <a href="#WroteScrollingDelay"><code>WriteScrollingDelay</code></a> will be called.
   *
   * __Parameters__:
   *
   *     * <code>Scrolling_Delay_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The delay between characters on the LED matrix, in milliseconds.
   *
   * @param Scrolling_Delay_Value The number of millisecond for each character to be shown on the
   *                              micro:bit's LED matrix.
   */
  @SimpleFunction
  public void WriteScrollingDelay(final int Scrolling_Delay_Value) {
    if (bleConnection != null) {
      bleConnection.ExWriteShortValuesWithResponse(LED_SERVICE_UUID, SCROLLING_DELAY_CHARACTERISTIC_UUID, false, Scrolling_Delay_Value, scrollingDelayHandler);
    } else {
      reportNullConnection("WriteScrollingDelay");
    }
  }
  /**
   * The <code>WroteScrollingDelay</code> event will be run after the micro:bit's scrolling delay
   * is successfully read after a call to the
   * <a href="#WriteScrollingDelay"><code>WriteScrollingDelay</code></a> method.
   *
   * __Parameters__:
   *
   *     * <code>Scrolling_Delay_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash; The delay between characters on the LED matrix, in milliseconds.
   *
   * @param Scrolling_Delay_Value The number of millisecond for each character to be shown on the
   *                              micro:bit's LED matrix.
   */
  @SimpleEvent
  public void WroteScrollingDelay(final int Scrolling_Delay_Value) {
    EventDispatcher.dispatchEvent(this, "WroteScrollingDelay", Scrolling_Delay_Value);
  }
  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Led.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
