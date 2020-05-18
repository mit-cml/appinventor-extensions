// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2017-2020 Massachusetts Institute of Technology, All rights reserved.

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
import edu.mit.appinventor.ble.BluetoothLE.BluetoothConnectionListener;

import java.util.List;

@DesignerComponent(version = 20200518,
    description = "The <code>Microbit_Uart_Simple</code> sensor provides the ability " +
        "to read from and write strings to the BBC micro:bit's serial UART port.",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "http://iot.appinventor.mit.edu/#/microbit/microbituart",
    iconName = "aiwebres/microbit.png")
@SimpleObject(external = true)
public class Microbit_Uart_Simple extends AndroidNonvisibleComponent {
  private BluetoothLE bleConnection = null;

  private static final String UART_SERVICE_UUID = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E";
  private static final String TX_CHARACTERISTIC_CHARACTERISTIC_UUID =
      "6E400002-B5A3-F393-E0A9-E50E24DCCA9E";
  private static final String RX_CHARACTERISTIC_CHARACTERISTIC_UUID =
      "6E400003-B5A3-F393-E0A9-E50E24DCCA9E";

  private final BluetoothLE.BLEResponseHandler<String> txCharacteristicHandler =
      new BluetoothLE.BLEResponseHandler<String>() {
        @Override
        public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
          String message = "";
          if (values != null && values.size() > 0) {
            message = values.get(0);
          }
          MessageReceived(message);
        }
      };

  private final BluetoothLE.BLEResponseHandler<String> rXCharacteristicWriteHandler =
      new BluetoothLE.BLEResponseHandler<String>() {
        @Override
        public void onWrite(String serviceUuid, String characteristicUuid, List<String> values) {
          String message = "";
          if (values != null && values.size() > 0) {
            message = values.get(0);
          }
          MessageSent(message);
        }
      };

  private final BluetoothLE.BluetoothConnectionListener listener =
      new BluetoothConnectionListener() {
        @Override
        public void onConnected(BluetoothLE bleConnection) {
          bleConnection.ExRegisterForStringValues(UART_SERVICE_UUID,
              TX_CHARACTERISTIC_CHARACTERISTIC_UUID, false, txCharacteristicHandler);
        }

        @Override
        public void onDisconnected(BluetoothLE bleConnection) {
          bleConnection.ExUnregisterForValues(UART_SERVICE_UUID,
              TX_CHARACTERISTIC_CHARACTERISTIC_UUID, txCharacteristicHandler);
        }
      };

  public Microbit_Uart_Simple(Form form) {
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
    if (bleConnection != null) {
      bleConnection.removeConnectionListener(listener);
    }
    bleConnection = bluetoothLE;
    if (bleConnection != null) {
      bleConnection.addConnectionListener(listener);
    }
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
   * The <code>TXCharacteristicReceived</code> event is run whenever messages are received over the
   * micro:bit's serial UART protocol.
   *
   * __Parameters__:
   *
   *     * <code>message</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/listsU.html#makealist">_list_</a>) &mdash;
   *       A list of unsigned byte values read from the device.
   *
   * @param message the message received from the micro:bit
   */
  @SimpleEvent
  public void MessageReceived(final String message) {
    EventDispatcher.dispatchEvent(this, "MessageReceived", message);
  }

  /**
   * Writes the given value <code>UART_TX</code> to the micro:bit. The size of the message will
   * depend on the value of <code>UART_TX</code>. Numbers will be converted to integers and sent
   * as 32-bit integer values. Strings will be encoded using UTF-8. Lists of objects will be
   * sent as a sequence of bytes using the aforementioned rules for numbers and strings.
   *
   * __Parameters__:
   *
   *     * <code>UART_TX</code> (_any_) &mdash;
   *       The value to transmit to the RX "pin" of the micro:bit. Strings will be encoded as UTF-8,
   *       numbers will be sent as 32-bit integers. Lists of values will be converted into be
   *       converted into a sequence of bytes depending on the type of each value in the list.
   *
   * @param message The value to transmit to the RX "pin" of the micro:bit. Strings will be encoded
   *       as UTF-8, numbers will be sent as 32-bit integers. Lists of values will be converted into
   *       be converted into a sequence of bytes depending on the type of each value in the list.
   */
  @SimpleFunction
  public void SendMessage(String message) {
    if (bleConnection != null) {
      bleConnection.ExWriteStringValuesWithResponse(UART_SERVICE_UUID,
          RX_CHARACTERISTIC_CHARACTERISTIC_UUID, false, message, rXCharacteristicWriteHandler);
    } else {
      reportNullConnection("SendMessage");
    }
  }

  /**
   * The <code>WroteRXCharacteristic</code> event is run after a message is written to the micro:bit
   * via its serial UART.
   *
   * __Parameters__:
   *
   *     * <code>UART_TX_FIELD</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/listsU.html#makealist">_list_</a>) &mdash;
   *       A list of unsigned byte values written to the device.
   *
   * @param message A list of unsigned byte values written to the device.
   */
  @SimpleEvent
  public void MessageSent(final String message) {
    EventDispatcher.dispatchEvent(this, "MessageSent", message);
  }

  @SuppressWarnings("SameParameterValue")
  private void reportNullConnection(String functionName) {
    form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EXTENSION_ERROR,
        1, Microbit_Uart_Simple.class.getSimpleName(), "BluetoothDevice is not set");
  }
}
