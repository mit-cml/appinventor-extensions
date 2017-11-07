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


import static edu.mit.appinventor.iot.mt7697.Constants.RGBLCD_SERVICE_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.RGBLCD_BACKGROUND_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.RGBLCD_TEXT1_UUID;
import static edu.mit.appinventor.iot.mt7697.Constants.RGBLCD_TEXT2_UUID;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension to interface with the Grove RGB LCD peripheral connected to an MT7697
 * running the App Inventor Companion Sketch.
 *
 * @author jerry73204@gmail.com (Hsiang-Jui Lin)
 */
@DesignerComponent(version = 1,
                   description = "The MT7697 RGB LCD lets users communicate information on a liquid crystal " +
                                 "display (LCD) with optional RGB LED backlight.<br>" +
                                 "<img src='/assets/sensors/Grove-RGBLCD.jpg' width='50%'><br>" +
                                 "<strong>Note:</strong> The RGB LCD display requires 5V power rather than 3.3V. If you are" +
                                 "connecting it with the Grove Shield, you will need to make sure the voltage switch is in " +
                                 "the 5V position otherwise the display may not function correctly.<br>\n\n<strong>More " +
                                 "Links</strong><ul><li>Download a <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/samples/MT7697RgbLcd.aia' " +
                                 "target='_blank'>sample project</a> for the .</li><li>View the <a " +
                                 "href='http://iot.appinventor.mit.edu/assets/howtos/MIT_App_Inventor_IoT_RgbLcd.pdf' " +
                                 "target='_blank'>how to instructions</a> for the RGB LCD.</li></ul>",
                   category = ComponentCategory.EXTENSION,
                   helpUrl = "http://iot.appinventor.mit.edu/#/arduino101/arduinorgblcd",
                   nonVisible = true,
                   iconName = "aiwebres/mt7697.png")
@SimpleObject(external = true)
public class MT7697RgbLcd extends MT7697ExtensionBase {
  private static final int MAX_PACKET_LENGTH = 23;
  private static final String[] UUIDS = {
      RGBLCD_TEXT1_UUID,
      RGBLCD_TEXT2_UUID
  };

  /**
   * First line of the two-line LCD display string.
   *
   * Due to the BLE MTU of 23 bytes we break up the 32-character display into 2 16-byte lines. This
   * is the first line in that string.
   */
  private String line1 = "";

  private final BluetoothLE.BLEResponseHandler<Long> rgbLcdBackgroundHandler =
    new BluetoothLE.BLEResponseHandler<Long>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<Long> values) {
        BackgroundColorReceived(values.get(0).intValue());
      }
    };

  private final BluetoothLE.BLEResponseHandler<String> rgbLcdTextHandler =
    new BluetoothLE.BLEResponseHandler<String>() {
      @Override
      public void onReceive(String serviceUuid, String characteristicUuid, List<String> values) {
        if (RGBLCD_TEXT1_UUID.equalsIgnoreCase(characteristicUuid)) {
          line1 = values.get(0);
        } else if (RGBLCD_TEXT2_UUID.equalsIgnoreCase(characteristicUuid)) {
          TextReceived(line1 + "\n" + values.get(0));
        }
      }
    };

  public MT7697RgbLcd(Form form) {
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
      bleConnection.isCharacteristicPublished(RGBLCD_SERVICE_UUID, RGBLCD_TEXT1_UUID);
  }

  /**
   * Request the current background color from the device. The color will be returned through the
   * <a href='#BackgroundColorReceived"><code>BackgroundColorReceived</code></a> event.
   */
  @SimpleFunction
  public void GetBackgroundColor() {
    if (bleConnection != null) {
      bleConnection.ExReadIntegerValues(RGBLCD_SERVICE_UUID,
                                        RGBLCD_BACKGROUND_UUID,
                                        true,
                                        rgbLcdBackgroundHandler);
    }
  }

  /**
   * Set the background color of the RGB LCD connected to the MT7697. This method can take
   * any of the color blocks or a color composed using the <a
   * href="http://appinventor.mit.edu/explore/ai2/support/blocks/colors.html#make">make a color</a>
   * block. The RGB LCD does not have the same range of colors as a computer screen, so you may not
   * observe changes between similar colors.
   *
   * __Parameters__:
   *
   *     * <code>color</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/colors.html#basic">_color_</a>) &mdash;
   *       The color to change the background color to.
   *
   * @param color The color to change the background color to.
   */
  @SimpleFunction
  public void SetBackgroundColor(int color) {
    if (bleConnection != null) {
      bleConnection.ExWriteIntegerValues(RGBLCD_SERVICE_UUID,
                                         RGBLCD_BACKGROUND_UUID,
                                         true,
                                         color);
    }
  }

  /**
   * Get the text currently shown on the screen. The text will be returned through the <a
   * href="#TextReceived"><code>TextReceived</code></a> event.
   */
  @SimpleFunction
  public void GetText() {
    if (bleConnection != null) {
      bleConnection.ExReadStringValues(RGBLCD_SERVICE_UUID,
                                       RGBLCD_TEXT1_UUID,
                                       false,
                                       rgbLcdTextHandler);
      bleConnection.ExReadStringValues(RGBLCD_SERVICE_UUID,
                                       RGBLCD_TEXT2_UUID,
                                       false,
                                       rgbLcdTextHandler);
    }
  }

  /**
   * Set the text of the display. Text is limited to string with not more than 2 lines of 16
   * characters each. If a line is longer than 16 characters, it will be truncated.
   *
   * __Parameters__:
   *
   *     * <code>text</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The text to show on the LCD. This is limited to two 16-character strings separated by a newline "\n"
   *
   * @param text The text to show on the LCD. This is limited to two 16-character strings separated by a newline "\n"
   */
  @SimpleFunction
  public void SetText(String text) {
    if (bleConnection != null) {
      String[] parts = text.split("\n");
      for (int i = 0; i < 2 && i < parts.length; i++) {
        byte[] utf8 = parts[i].getBytes();
        List<Integer> content = new ArrayList<Integer>();
        for (int j = 0; j < utf8.length && j < MAX_PACKET_LENGTH; j++) {
          content.add(utf8[j] & 0xFF);
        }
        if (content.size() < MAX_PACKET_LENGTH) {
          content.add(0);
        }
        bleConnection.ExWriteByteValues(RGBLCD_SERVICE_UUID, UUIDS[i], false, content);
      }
    }
  }

  /**
   * The <code>BackgroundColorReceived</code> event is run when the background color information is
   * successfully received from the MT7697.
   *
   * __Parameters__:
   *
   *     * <code>color</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/colors.html#basic">_color_</a>) &mdash;
   *       The current color of the background.
   *
   * @param color The current color of the background.
   */
  @SimpleEvent
  public void BackgroundColorReceived(int color) {
    EventDispatcher.dispatchEvent(this, "BackgroundColorReceived", color);
  }

  /**
   * The <code>TextReceived</code> event is run when the text currently displayed on the RGB LCD is
   * received from the MT7697 in response to a call to
   * <a href="#GetText"><code>GetText</code></a>.
   *
   * __Parameters__:
   *
   *     * <code>text</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
   *       The text currently being shown on the RGB LCD. Note that this may not match the
   *       characters being shown due to a limited range of Unicode support for the LCD.
   *
   * @param text The text currently being shown on the RGB LCD. Note that this may not match the
   *             characters being shown due to a limited range of Unicode support for the LCD.
   */
  @SimpleEvent
  public void TextReceived(String text) {
    EventDispatcher.dispatchEvent(this, "TextReceived", text);
  }
}
