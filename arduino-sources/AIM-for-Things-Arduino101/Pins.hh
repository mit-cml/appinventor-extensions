#if PINS == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

struct AnalogPinData {
  uint8_t values[8*ANALOG_PIN_BYTES];
};

struct DigitalPinData {
  uint8_t values[DIGITAL_PIN_BYTES];
};

struct PinModeData {
  uint8_t mode[DIGITAL_PIN_BYTES];
};

struct PinModeData pinModes;

BLEService pinService("a56ada00-ed09-11e5-9c97-0002a5d5c51b");
BLECharacteristic analogPinChar("a56ada02-ed09-11e5-9c97-0002a5d5c51b", BLERead | BLENotify, sizeof(AnalogPinData));
BLECharacteristic digitalPinChar("a56ada03-ed09-11e5-9c97-0002a5d5c51b", BLERead | BLEWrite | BLENotify, sizeof(DigitalPinData));
BLECharacteristic pinModeChar("a56ada04-ed09-11e5-9c97-0002a5d5c51b", BLERead | BLEWrite, sizeof(PinModeData));

void setupPins();
void updateAnalogPins();
void updateDigitalPins();
void digitalPinWritten(BLECentral &central, BLECharacteristic &characteristic);
void pinModeWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupPins() {
#if !ONE_SERVICE
  Serial.println("Configuring Pin Service...");
  blePeripheral.addAttribute(pinService);
#endif
  blePeripheral.addAttribute(analogPinChar);
  blePeripheral.addAttribute(digitalPinChar);
  blePeripheral.addAttribute(pinModeChar);
  digitalPinChar.setEventHandler(BLEWritten, digitalPinWritten);
  pinModeChar.setEventHandler(BLEWritten, pinModeWritten);
}

void updateAnalogPins() {
  struct AnalogPinData analog;
  for (int i=0; i<=5; i++) {
    analog.values[i] = map(analogRead(i), 0, 1023, 0, 255);
  }
  analogPinChar.setValue(reinterpret_cast<byte *>(&analog), sizeof(analog));
}


void updateDigitalPins() {
  int pin = 0;
  struct DigitalPinData digital;
  for (uint8_t i = 0; i < 8; ++i) {
    uint8_t newValue = 0;
    for (uint8_t j = 1; j != 0; j <<= 1, ++pin) {
      if ((pinModes.mode[i] & BITS[j]) == INPUT) {
        if (HIGH == digitalRead(pin)) {
          newValue |= j;
        }
      }
    }
    digital.values[i] = newValue;
  }
  digitalPinChar.setValue(reinterpret_cast<byte *>(&digital), sizeof(digital));
}


void digitalPinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  int pin = 0;
  struct DigitalPinData digital;
  memcpy(&digital, characteristic.value(), sizeof(digital));
  for (int i = 0; i < DIGITAL_PIN_BYTES; ++i) {
    for (int j = 0x80; j != 0; j >>= 1, ++pin) {
      digitalWrite(pin, digital.values[i] & j ? HIGH : LOW);
    }
  }
}


void pinModeWritten(BLECentral &central, BLECharacteristic &characteristic) {
  memcpy(&pinModes, characteristic.value(), sizeof(pinModes));
}


#elif MODE == SETUP

setupPins();

#elif MODE == UPDATE

updateAnalogPins();
updateDigitalPins();

#endif
#endif

