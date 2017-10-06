#if LED == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

struct LedPinData {
  uint8_t pin;
  uint8_t intensity;
};

BLEService ledService("E95D0700-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic ledStateChar("E95D0701-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(LedPinData));

void setupLED();
void ledPinWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupLED() {
#if !ONE_SERVICE
  LOGLN("Configuring LED Service...");
  blePeripheral.addAttribute(ledService);
#endif
  blePeripheral.addAttribute(ledStateChar);
  ledStateChar.setEventHandler(BLEWritten, ledPinWritten);
}


void ledPinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  const LedPinData *data = reinterpret_cast<const LedPinData *>(characteristic.value());
  switch(data->pin) {
  case 3:
  case 5:
  case 6:
  case 9:
    analogWrite(data->pin, data->intensity);
    break;
  default:
    digitalWrite(data->pin, data->intensity);
  }
}

#elif MODE == SETUP

setupLED();

#endif
#endif

