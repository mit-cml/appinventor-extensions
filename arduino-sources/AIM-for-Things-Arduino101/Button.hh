#if BUTTON == ENABLED
#if MODE == PREAMBLE

#define BUTTON_SETUP setupButton();
#define BUTTON_UPDATE updateButton();

#elif MODE == DEFINITIONS

struct ButtonPinData {
  /**
   * A sequence of 64 bits indicating which pins have buttons attached.
   */
  uint8_t buttons[DIGITAL_PIN_BYTES];
};

struct ButtonStateData {
  /**
   * A sequence of 64 bits where each bit indicates the state of a button.
   */
  uint8_t buttons[DIGITAL_PIN_BYTES];
};

struct ButtonPinData buttonPins;
struct ButtonStateData buttonStates;

BLEService buttonService("E95D0200-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic buttonPinCharacteristic("E95D0201-251D-470A-A062-FA1922DFA9A7", BLEWrite, 8);
BLECharacteristic buttonReadCharacteristic("E95D0202-251D-470A-A062-FA1922DFA9A7", BLERead | BLENotify, 8);

void setupButton();
void updateButton();
void buttonPinsWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupButton() {
#if !ONE_SERVICE
  LOGLN("Configuring Button Service...");
  blePeripheral.addAttribute(buttonService);
#endif
  blePeripheral.addAttribute(buttonPinCharacteristic);
  blePeripheral.addAttribute(buttonReadCharacteristic);
  buttonPinCharacteristic.setEventHandler(BLEWritten, buttonPinsWritten);
}


void buttonPinsWritten(BLECentral &central, BLECharacteristic &characteristic) {
  const byte *value = characteristic.value();
  memcpy(&buttonPins, value, sizeof(buttonPins));
}


void updateButtons() {
  int pin = 0;
  for (uint8_t i = 0; i < 8; ++i) {
    uint8_t newValue = 0;
    for (uint8_t j = 0x80; j != 0; j >>= 1, ++pin) {
      if ((buttonPins.buttons[i] & j) == j) {
        if (HIGH == digitalRead(pin)) {
          newValue |= j;
        }
      }
    }
    buttonStates.buttons[i] = newValue;
  }
  buttonReadCharacteristic.setValue(reinterpret_cast<byte *>(&buttonStates), sizeof(buttonStates));
}

#elif MODE == SETUP

setupButton();

#elif MODE == UPDATE

updateButtons();

#endif
#endif

