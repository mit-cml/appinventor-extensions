#if RGBLCD == ENABLED
#if MODE == PREAMBLE

#include <rgb_lcd.h>

#define RGBLCD_SETUP setupRgbLcd();
#define RGBLCD_UPDATE

#elif MODE == DEFINITIONS

struct RgbLcdBackgroundData {
  uint8_t blue;
  uint8_t green;
  uint8_t red;
  uint8_t alpha;
};

struct RgbLcdMessageData {
  char text[24];
};

rgb_lcd lcd;
bool lcdPrepared = false;
struct RgbLcdBackgroundData rgbLcdBackground;
struct RgbLcdMessageData rgbLcdText1;
struct RgbLcdMessageData rgbLcdText2;

BLEService rgbLcdService("E95D0B00-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic rgbLcdBackgroundChar("E95D0B01-251D-470A-A062-FA1922DFA9A7", BLERead | BLEWrite, sizeof(RgbLcdBackgroundData));
BLECharacteristic rgbLcdTextChar1("E95D0B02-251D-470A-A062-FA1922DFA9A7", BLERead | BLEWrite, sizeof(RgbLcdMessageData));
BLECharacteristic rgbLcdTextChar2("E95D0B03-251D-470A-A062-FA1922DFA9A7", BLERead | BLEWrite, sizeof(RgbLcdMessageData));

void setupRgbLcd();
void rgbLcdBackgroundWritten(BLECentral &central, BLECharacteristic &characteristic);
void rgbLcdText1Written(BLECentral &central, BLECharacteristic &characteristic);
void rgbLcdText2Written(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupRgbLcd() {
#if !ONE_SERVICE
  Serial.println("Configuring RGB LCD Service...");
  blePeripheral.addAttribute(rgbLcdService);
#endif
  blePeripheral.addAttribute(rgbLcdBackgroundChar);
  blePeripheral.addAttribute(rgbLcdTextChar1);
  blePeripheral.addAttribute(rgbLcdTextChar2);
  rgbLcdBackgroundChar.setEventHandler(BLEWritten, rgbLcdBackgroundWritten);
  rgbLcdTextChar1.setEventHandler(BLEWritten, rgbLcdText1Written);
  rgbLcdTextChar2.setEventHandler(BLEWritten, rgbLcdText2Written);
  memset((void *) &rgbLcdBackground, 0, sizeof(rgbLcdBackground));
  memset((void *) &rgbLcdText1, 0, sizeof(rgbLcdText1));
  memset((void *) &rgbLcdText2, 0, sizeof(rgbLcdText2));
  rgbLcdTextChar1.setValue((const unsigned char *) rgbLcdText1.text, sizeof(rgbLcdText1));
  rgbLcdTextChar2.setValue((const unsigned char *) rgbLcdText2.text, sizeof(rgbLcdText2));
  lcd.begin(16, 2);
  lcd.clear();
  lcdPrepared = true;
}


void rgbLcdBackgroundWritten(BLECentral &central, BLECharacteristic &characteristic) {
  Serial.println("rgbLcdBackgroundWritten");
  if (!lcdPrepared) {
    Serial.println("Preparing LCD");
    lcd.begin(16, 2);
    lcdPrepared = true;
  }
  memcpy((void *) &rgbLcdBackground, characteristic.value(), sizeof(rgbLcdBackground));
  Serial.print("  alpha = "); Serial.println(rgbLcdBackground.alpha);
  Serial.print("  red = "); Serial.println(rgbLcdBackground.red);
  Serial.print("  green = "); Serial.println(rgbLcdBackground.green);
  Serial.print("  blue = "); Serial.println(rgbLcdBackground.blue);
  lcd.setRGB(rgbLcdBackground.red, rgbLcdBackground.green, rgbLcdBackground.blue);
}


void rgbLcdText1Written(BLECentral &central, BLECharacteristic &characteristic) {
  Serial.println("rgbLcdText1Written");
  if (!lcdPrepared) {
    Serial.println("Preparing LCD");
    lcd.begin(16, 2);
    lcdPrepared = true;
  }
  memcpy((void *) &rgbLcdText1, characteristic.value(), sizeof(rgbLcdText1));
  lcd.clear();
  rgbLcdText1.text[23] = 0;  // make sure that the string is null terminated
  Serial.print("  text = "); Serial.println(rgbLcdText1.text);
  lcd.print(rgbLcdText1.text);
}

void rgbLcdText2Written(BLECentral &central, BLECharacteristic &characteristic) {
  Serial.println("rgbLcdText2Written");
  if (!lcdPrepared) {
    Serial.println("Preparing LCD");
    lcd.begin(16, 2);
    lcdPrepared = true;
  }
  memcpy((void *) &rgbLcdText2, characteristic.value(), sizeof(rgbLcdText2));
  rgbLcdText2.text[23] = 0;  // make sure that the string is null terminated
  Serial.print("  text = "); Serial.println(rgbLcdText2.text);
  lcd.setCursor(0, 1);  // move to second row
  lcd.print(rgbLcdText2.text);
}

#elif MODE == SETUP

setupRgbLcd();

#endif
#endif

