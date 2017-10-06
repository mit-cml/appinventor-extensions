#if PROXIMITY == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

// ProximitySensor BLE
struct ProximityPinData {
  uint8_t pins[ANALOG_PIN_BYTES];
};

struct ProximityData {
  uint8_t reading[8*ANALOG_PIN_BYTES];
};

struct ProximityPinData proximityPins;
struct ProximityData proximityData;

BLEService proximityService("E95D0A00-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic proximityPinChar("E95D0A01-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(ProximityPinData));
BLECharacteristic proximityReadChar("E95D0A02-251D-470A-A062-FA1922DFA9A7", BLERead | BLENotify, sizeof(ProximityData));

void setupProximity();
void updateProximity();
void proximityPinWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupProximity() {
#if !ONE_SERVICE
  Serial.println("Configuring Proximity Service...");
  blePeripheral.addAttribute(proximityService);
#endif
  blePeripheral.addAttribute(proximityPinChar);
  blePeripheral.addAttribute(proximityReadChar);
  proximityPinChar.setEventHandler(BLEWritten, proximityPinWritten);
}

void proximityPinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  memcpy((void *)&proximityPins, characteristic.value(), sizeof(proximityPins));
}

void updateProximity() {
  int pin = 0;
  // read proximity data from the indicated pins
  for (int i = 0; i < ANALOG_PIN_BYTES; i++) {
    for (int j = 0x80; j != 0; j >>= 1) {
      if ((proximityPins.pins[i] & j) == j) {
        float sensor_value, sum = 0.0;
        for (int k = 0; k < 20; k++) {
          sum += analogRead(pin);
        }
        sensor_value = sum / 20.0 * 5.0 / 1024.0;
        if (sensor_value >= 3.15) {
          proximityData.reading[pin] = 6;
        } else if (sensor_value >= 2.98) {
          proximityData.reading[pin] = 6 + lround((3.15 - sensor_value) / (3.15 - 2.98));
        } else if (sensor_value >= 2.75) {
          proximityData.reading[pin] = 7 + lround((2.98 - sensor_value) / (2.98 - 2.75));
        } else if (sensor_value >= 2.31) {
          proximityData.reading[pin] = 8 + lround(2 * (2.75 - sensor_value) / (2.75 - 2.31));
        } else if (sensor_value >= 1.65) {
          proximityData.reading[pin] = 10 + lround(5 * (2.31 - sensor_value) / (2.31 - 1.65));
        } else if (sensor_value >= 1.3) {
          proximityData.reading[pin] = 15 + lround(5 * (1.65 - sensor_value) / (1.65 - 1.3));
        } else if (sensor_value >= 1.08) {
          proximityData.reading[pin] = 20 + lround(5 * (1.3 - sensor_value) / (1.3 - 1.08));
        } else if (sensor_value >= 0.925) {
          proximityData.reading[pin] = 25 + lround(5 * (1.08 - sensor_value) / (1.08 - 0.925));
        } else if (sensor_value >= 0.75) {
          proximityData.reading[pin] = 30 + lround(10 * (0.925 - sensor_value) / (0.925 - 0.75));
        } else if (sensor_value >= 0.61) {
          proximityData.reading[pin] = 40 + lround(10 * (0.75 - sensor_value) / (0.75 - 0.61));
        } else if (sensor_value >= 0.525) {
          proximityData.reading[pin] = 50 + lround(10 * (0.61 - sensor_value) / (0.61 - 0.525));
        } else if (sensor_value >= 0.45) {
          proximityData.reading[pin] = 60 + lround(10 * (0.525 - sensor_value) / (0.61 - 0.45));
        } else if (sensor_value >= 0.4) {
          proximityData.reading[pin] = 70 + lround(10 * (0.45 - sensor_value) / (0.45 - 0.4));
        } else {
          proximityData.reading[pin] = 80;
        }
      }
      pin++;
    }
  }
  // write the updated values to the characteristic
  proximityReadChar.setValue(reinterpret_cast<byte *>(&proximityData), sizeof(proximityData));
}

#elif MODE == SETUP

setupProximity();

#elif MODE == UPDATE

updateProximity();

#endif
#endif

