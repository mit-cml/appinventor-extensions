#if LIGHT_SENSOR == ENABLED
#if MODE == PREAMBLE

#define LIGHT_SETUP  setupLightSensor();
#define LIGHT_UPDATE updateLightSensor();

#elif MODE == DEFINITIONS

struct LightSensorPinData {
  uint8_t pins[ANALOG_PIN_BYTES];
};

struct LightSensorData {
  uint8_t reading[8*ANALOG_PIN_BYTES];
};

struct LightSensorPinData lightPins;
struct LightSensorData lightData;

BLEService lightService("E95D0E00-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic lightSensorPinChar("E95D0E01-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(LightSensorPinData));
BLECharacteristic lightSensorDataChar("E95D0E02-251D-470A-A062-FA1922DFA9A7", BLERead | BLENotify, sizeof(LightSensorData));

void setupLightSensor();
void updateLightSensor();
void lightPinWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupLightSensor() {
#if !ONE_SERVICE
  LOGLN("Configuring Light Sensor Service...");
  blePeripheral.addAttribute(lightService);
#endif
  blePeripheral.addAttribute(lightSensorPinChar);
  blePeripheral.addAttribute(lightSensorDataChar);
  lightSensorPinChar.setEventHandler(BLEWritten, lightPinWritten);
}


void lightPinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  memcpy((void *)&lightPins, characteristic.value(), sizeof(lightPins));
  printArray((uint8_t *)characteristic.value(), 8);
}


void updateLightSensor() {
  bool updated = false;
  int pin = 0;
  // read light data from the indicated pins
  for (int i = 0; i < ANALOG_PIN_BYTES; i++) {
    for (int j = 0x80; j != 0; j >>= 1) {
      if ((lightPins.pins[i] & j) == j) {
        // Map [0,1023] to [0,255] because we use 1-byte values in BLE characteristic.
        // Eventually this should match the sensor's hardware specs, but we assume a linear transformation for now.
        lightData.reading[pin] = map(analogRead(pin), 0, 1023, 0, 255);
        Serial.print("Light[");
        Serial.print(pin);
        Serial.print("]: ");
        Serial.println(lightData.reading[pin]);
        updated = true;
      }
      pin++;
    }
  }
  if (updated) {
    // write the updated values to the characteristic
    lightSensorDataChar.setValue(reinterpret_cast<byte *>(&lightData), sizeof(lightData));
  }
}

#elif MODE == SETUP

setupLightSensor();

#elif MODE == UPDATE

updateLightSensor();

#endif
#endif

