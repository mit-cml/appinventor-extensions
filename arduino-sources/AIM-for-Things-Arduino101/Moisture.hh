#if MOISTURE_SENSOR == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

struct MoisturePinData {
  uint8_t pins[ANALOG_PIN_BYTES];
};

struct MoistureData {
  uint8_t reading[8*ANALOG_PIN_BYTES];
};

struct MoisturePinData moisturePins;
struct MoistureData moistureData;

// moistureBLE
BLEService moistureService("E95D0800-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic moisturePinChar("E95D0801-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(MoisturePinData));
BLECharacteristic moistureReadChar("E95D0802-251D-470A-A062-FA1922DFA9A7", BLERead | BLENotify, sizeof(MoistureData));

void setupMoisture();
void updateMoistureData();
void moisturePinWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupMoisture() {
#if !ONE_SERVICE
  Serial.println("Configuring Moisture Service...");
  blePeripheral.addAttribute(moistureService);
#endif
  blePeripheral.addAttribute(moisturePinChar);
  blePeripheral.addAttribute(moistureReadChar);
  moisturePinChar.setEventHandler(BLEWritten, moisturePinWritten);
}

void moisturePinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  Serial.println("Updating moisture pins...");
  memcpy((void *)&moisturePins, characteristic.value(), sizeof(moisturePins));
  Serial.print("moisture pins: ");
  printArray(moisturePins.pins, ANALOG_PIN_BYTES);
}

void updateMoistureData() {
  int pin = 0;
  bool updated = false;
  // read moisture data from the indicated pins
  for (int i = 0; i < ANALOG_PIN_BYTES; i++) {
    for (int j = 0x80; j != 0; j >>= 1) {
      if ((moisturePins.pins[i] & j) == j) {
        // Map [0,1023] to [0,255] because we use 1-byte values in BLE characteristic.
        // Eventually this should match the sensor's hardware specs, but we assume a linear transformation for now.
        moistureData.reading[pin] = map(analogRead(pin), 0, 1023, 0, 255);
        Serial.print("Moisture[");
        Serial.print(pin);
        Serial.print("]: ");
        Serial.println(moistureData.reading[pin]);
        updated = true;
      }
      pin++;
    }
  }
  if (updated) {
    // write the updated values to the characteristic
    moistureReadChar.setValue(reinterpret_cast<byte *>(&moistureData), sizeof(moistureData));
  }
}

#elif MODE == SETUP

setupMoisture();

#elif MODE == UPDATE

updateMoistureData();

#endif
#endif

