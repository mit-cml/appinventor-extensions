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
    description = "The <code>Microbit_Io_Pin</code> component lets users configure the BBC micro:bit's " +
        "analog pins for input and output, and to read, write, and request notifications for the " +
        "I/O pin states."
        /* removed until we have tutorials
        + "<!--<br>\n\n<strong>More links:</strong><ul><li>Download a <a " +
        "href='http://iot.appinventor.mit.edu/assets/samples/MicrobitIoPins.aia' " +
        "target='_blank'>sample project</> for the micro:bit IO pins.</li><li>View the <a " +
        "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_Microbit_IO_Pins.pdf' " +
        "target='_blank'>how to instructions</a> for the micro:bit IO pins.</li></ul>-->"
        */,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbitiopin",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Io_Pin extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String IO_PIN_SERVICE_UUID = "E95D127B-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_DATA_CHARACTERISTIC_UUID = "E95D8D00-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID = "E95D5899-251D-470A-A062-FA1922DFA9A8";
  private static final String PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID = "E95DB9FE-251D-470A-A062-FA1922DFA9A8";
  private static final String PWM_CONTROL_CHARACTERISTIC_UUID = "E95DD822-251D-470A-A062-FA1922DFA9A8";

  private final BluetoothLE.BLEResponseHandler<Integer> pinDataHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        PinDataReceived(values);
      }
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePinData(values);
      }
    };

  private final BluetoothLE.BLEResponseHandler<Integer> pinADConfigurationHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        PinADConfigurationReceived(values);
      }
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePinADConfiguration(values);
      } 
    }; 

  private final BluetoothLE.BLEResponseHandler<Integer> pinIOConfigurationHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Integer> values) {
        PinIOConfigurationReceived(values);
      }
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePinIOConfiguration(values);
      } 
    }; 

  private final BluetoothLE.BLEResponseHandler<Integer> pWMControlHandler =
    new BluetoothLE.BLEResponseHandler<Integer>() {
      @Override
      public void onWrite(String serviceUuid, String characteristicUuid, List<Integer> values) {
        WrotePWMControl(values);
      } 
    }; 

  public Microbit_Io_Pin(Form form) {
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
   * Read a single sample of the pin states from the micro:bit. On successful read, the
   * <a href="#PinDataReceived"><code>PinDataReceived</code></a> event will be run. Prior to reading
   * the pins, one should configure the pins for input using the <a
   * href="#WritePinIOConfiguration"><code>WritePinIOConfiguration</code></a> method.
   */
  @SimpleFunction
  public void ReadPinData() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, pinDataHandler);
    } else {
      reportNullConnection("ReadPinData");
    }
  }

  /**
   * The <code>PinDataReceived</code> event will be run when pin data are successfully received
   * from the micro:bit, typically as a result of calling
   * <a href="#ReadPinData"><code>ReadPinData</code></a> or
   * <a href="#RequestPinDataUpdates"><code>RequestPinDataUpdates</code></a>.
   *
   * __Parameters__:
   *
   *     * <code>IO\_Pin\_Data</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash; A list of pin values ranging from 0-255 from the micro:bit.
   *
   * @param IO_Pin_Data A list of pin values ranging from 0-255.
   */
  @SimpleEvent
  public void PinDataReceived(final List<Integer> IO_Pin_Data) {
    EventDispatcher.dispatchEvent(this, "PinDataReceived", IO_Pin_Data);
  }

  /**
   * The <code>WritePinData</code> is used to set the output values of the micro:bit's pins if
   * configured as output pins using the
   * <a href="#WritePinIOConfiguration"><code>WritePinIOConfiguration</code></a> method. After the
   * pins are written, the <a href="#WrotePinData"><code>WrotePinData</code></a> event will be run.
   *
   * __Parameters__:
   *
   *     * <code>IO\_Pin\_Data</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash; A list of up to 19 values from 0-255 to be output to the micro:bit's pins.
   *
   * @param IO_Pin_Data A list of up to 19 values from 0-255 to be output to the micro:bit's pins.
   */
  @SimpleFunction
  public void WritePinData(List<Integer> IO_Pin_Data) {
    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, IO_Pin_Data, pinDataHandler);
    } else {
      reportNullConnection("WritePinData");
    }
  }

  /**
   * The <code>WrotePinData</code> event will be run after the micro:bit's output pins are
   * successfully written by a call to the <a href="#WritePinData"><code>WritePinData</code></a>
   * method.
   *
   * __Parameters__:
   *
   *     * <code>IO\_Pin\_Data</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash; A list of up to 19 values from 0-255 that were written to the micro:bit's
   *       pins from the last call to <a href="#WritePinData"><code>WritePinData</code></a>.
   *
   * @param IO_Pin_Data A list of up to 19 values from 0-255 that were written to the micro:bit's
   *                    pins from the last call to
   *                    <a href="#WritePinData"><code>WritePinData</code></a>.
   */
  @SimpleEvent
  public void WrotePinData(final List<Integer> IO_Pin_Data) {
    EventDispatcher.dispatchEvent(this, "WrotePinData", IO_Pin_Data);
  }

  /**
   * Request updates to any micro:bit pins configured as input pins. After requesting updates, the
   * <a href="#PinDataReceived"><code>PinDataReceived</code></a> will be run whenever the micro:bit
   * reports changes to its input pins.
   */
  @SimpleFunction
  public void RequestPinDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExRegisterForByteValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, false, pinDataHandler);
    } else {
      reportNullConnection("RequestPinDataUpdates");
    }
  }

  /**
   * Stop receiving updates about the state of the micro:bit's I/O pins. Note that there may be
   * pending updates that have not been processed that will result in additional
   * <a href="#PinDataReceived"><code>PinDataReceived</code></a> events.
   */
  @SimpleFunction
  public void StopPinDataUpdates() {
    if (bleConnection != null) {
      bleConnection.ExUnregisterForValues(IO_PIN_SERVICE_UUID, PIN_DATA_CHARACTERISTIC_UUID, pinDataHandler);
    } else {
      reportNullConnection("StopPinDataUpdates");
    }
  }

  /**
   * The <code>PinDataReceived</code> event will be run after the micro:bit sends the status of its
   * input pins. This will typically follow calls to
   * <a href="#ReadPinData"><code>ReadPinData</code></a> or
   * <a href="#RequestPinData"><code>RequestPinData</code></a>.
   *
   * __Parameters__:
   *
   *     * <code>IO_Pin_Data</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
   *       The state of the micro:bit's input pins. Pins not configured for input will have 0 value.
   *
   * @param IO_Pin_Data The state of the micro:bit's input pins. Pins not configured for input will
   *                    have 0 value.
   */
  @SimpleEvent
  public void PinDataReceived(final int IO_Pin_Data) {
    EventDispatcher.dispatchEvent(this, "PinDataReceived", IO_Pin_Data);
  }

  /**
   * Read the state of the micro:bit's pin configuration, specifically whether each pin is
   * configured as digital (0 or 1) or analog (0 to 255). After a successful read, the
   * <a href="#PinADConfigurationReceived"><code>PinADConfigurationReceived</code></a> event will
   * be run.
   */
  @SimpleFunction
  public void ReadPinADConfiguration() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(IO_PIN_SERVICE_UUID, PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID, false, pinADConfigurationHandler);
    } else {
      reportNullConnection("ReadPinADConfiguration");
    }
  }

  /**
   * The <code>PinADConfigurationReceived</code> event is run after a successful call to the
   * <a href="#ReadPinADConfiguration"><code>ReadPinADConfiguration</code></a> method is returned
   * by the micro:bit.
   *
   * __Parameters__:
   *
   *     * <code>Pin\_AD\_Config\_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       A list of analog/digital pin states configured on the micro:bit.
   *
   * @param Pin_AD_Config_Value A list of analog/digital pin states configured on the micro:bit.
   *
   */
  @SimpleEvent
  public void PinADConfigurationReceived(final List<Integer> Pin_AD_Config_Value) {
    EventDispatcher.dispatchEvent(this, "PinADConfigurationReceived", Pin_AD_Config_Value);
  }

  /**
   * The <code>WritePinADConfiguration</code> is used to configure whether pins on the micro:bit
   * are analog or digital. The <code>Pin\_AD\_Config\_Value</code> is composed of three bytes that
   * represent 19 bits, one for each pin on the micro:bit. A 0 bit indicates a digital pin and a
   * 1 bit indicates an analog pin. The
   * <a href="#WrotePinADConfiguration"><code>WrotePinADConfiguration</code></a> event will be run
   * after successfully writing a new pin configuration.
   *
   * __Parameters__:
   *
   *     * <code>Pin\_AD\_Config\_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   *
   * @param Pin_AD_Config_Value A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   */
  @SimpleFunction
  public void WritePinADConfiguration(final List<Integer> Pin_AD_Config_Value) {
    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_AD_CONFIGURATION_CHARACTERISTIC_UUID, false, Pin_AD_Config_Value, pinADConfigurationHandler);
    } else {
      reportNullConnection("WritePinADConfiguration");
    }
  }

  /**
   * The <code>WrotePinADConfiguration</code> event is run after a successful write to the
   * micro:bit's configuration server for analog and digital pins. <code>Pin\_AD\_Config\_Value</code>
   * will be the list of bytes that were written as part of the last call to the service.
   *
   * __Parameters__:
   *
   *     * <code>Pin\_AD\_Config\_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   *
   * @param Pin_AD_Config_Value A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   */
  @SimpleEvent
  public void WrotePinADConfiguration(final List<Integer> Pin_AD_Config_Value) {
    EventDispatcher.dispatchEvent(this, "WrotePinADConfiguration", Pin_AD_Config_Value);
  }

  /**
   * Read the current input/output configuration of the micro:bit's pins. After a successful read,
   * the <a href="#PinIOConfigurationReceived"><code>PinIOConfigurationReceived</code></a> event
   * will be run.
   */
  @SimpleFunction
  public void ReadPinIOConfiguration() {
    if (bleConnection != null) {
      bleConnection.ExReadShortValues(IO_PIN_SERVICE_UUID, PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID, false, pinIOConfigurationHandler);
    } else {
      reportNullConnection("ReadPinIOConfiguration");
    }
  }

  /**
   * The <code>PinIOConfigurationReceived</code> event will be run after the micro:bit's pins'
   * input/output configuration is read. <code>Pin\_IO\_Config\_Value</code> will be a list of three
   * bytes valued from 0-255 composing a 19-bit mask indicating the input or output state of each
   * of the 19 pins provided by the micro:bit. A 0 bit indicates output and a 1 bit indicates input.
   *
   * __Parameters__:
   *
   *     * <code>Pin\_IO\_Config\_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   *
   * @param Pin_IO_Config_Value A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   */
  @SimpleEvent
  public void PinIOConfigurationReceived(final List<Integer> Pin_IO_Config_Value) {
    EventDispatcher.dispatchEvent(this, "PinIOConfigurationReceived", Pin_IO_Config_Value);
  }

  /**
   * The <code>WritePinIOConfiguration</code> method is used to configure the micro:bit's pins for
   * input or output. The <code>Pin\_IO\_Config\_Value</code> parameter should be a 3-element list
   * where each element is a number from 0-255 composing a 19-bit bitmask. A 0-bit indicates an
   * output and a 1-bit indicates an input.
   *
   * __Parameters__:
   *
   *     * <code>Pin_IO_Config_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   *
   * @param Pin_IO_Config_Value A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   */
  @SimpleFunction
  public void WritePinIOConfiguration(final List<Integer> Pin_IO_Config_Value) {
    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PIN_IO_CONFIGURATION_CHARACTERISTIC_UUID, false, Pin_IO_Config_Value, pinIOConfigurationHandler);
    } else {
      reportNullConnection("WritePinIOConfiguration");
    }
  }

  /**
   * The <code>WrotePinIOConfiguration</code> event will run after a successful update of the
   * micro:bit's input/output pin configuration. <code>Pin\_IO\_Config\_Value</code> that was sent to
   * the device will be passed as a parameter to the event.
   *
   * __Parameters__:
   *
   *     * <code>Pin\_IO\_Config\_Value</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   *
   * @param Pin_IO_Config_Value A list of 8-bit values that are composed into a 19-bit mask, one bit per pin on the micro:bit.
   */
  @SimpleEvent
  public void WrotePinIOConfiguration(final List<Integer> Pin_IO_Config_Value) {
    EventDispatcher.dispatchEvent(this, "WrotePinIOConfiguration", Pin_IO_Config_Value);
  }

  /**
   * Writes the PWM control field on the micro:bit. The PWM control field is a variable length
   * array of one or two instances of a data structure containing a 1-byte pin field (range 0-19),
   * a 2-byte value field (range 0-1024), and a 4-byte period field (in milliseconds).
   *
   * __Parameters__:
   *
   *     * <code>PWM\_Control\_Field</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       The PWM control field data as defined in the WritePWMControl method description.
   *
   * @param PWM_Control_Field The PWM control field data as defined in the WritePWMControl method description.
   */
  @SimpleFunction
  public void WritePWMControl(final List<Integer> PWM_Control_Field) {
    if (bleConnection != null) {
      bleConnection.ExWriteByteValuesWithResponse(IO_PIN_SERVICE_UUID, PWM_CONTROL_CHARACTERISTIC_UUID, false, PWM_Control_Field, pWMControlHandler);
    } else {
      reportNullConnection("WritePWMControl");
    }
  }

  /**
   * The <code>WrotePWMControl</code> event is run after a successful write to the micro:bit's
   * pulse-width modulation control service. The value of the control field that was written will
   * be passed as the <code>PWM\_Control\_Field</code>.
   *
   * __Parameters__:
   *
   *     * <code>PWM\_Control\_Field</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#emptylist">_list_</a>) &mdash;
   *       The control information written to the micro:bit.
   *
   * @param PWM_Control_Field The control information written to the micro:bit.
   */
  @SimpleEvent
  public void WrotePWMControl(final List<Integer> PWM_Control_Field) {
    EventDispatcher.dispatchEvent(this, "WrotePWMControl", PWM_Control_Field);
  }

  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Io_Pin.class.getSimpleName(), "BluetoothDevice is not set");
  }
}

