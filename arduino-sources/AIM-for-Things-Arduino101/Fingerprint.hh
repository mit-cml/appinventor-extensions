#if FINGERPRINT == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

struct FingerprintPinData {
  struct {
    uint8_t rxPin;
    uint8_t txPin;
  } pins[DIGITAL_PIN_BYTES];
};

struct FingerprintEnrollmentStatus {
  uint8_t pair;
  uint8_t newState;
};

struct FingerprintScanStatus {
  uint8_t pair;
  uint8_t newState;
};

struct FingerprintEnrollmentResult {
  uint8_t pair;
  uint16_t status;
};

struct FingerprintScanResult {
  uint16_t enrolledId;
  uint16_t score;
};

struct FingerprintScanResultMsg {
  uint8_t pair;
  uint8_t reserved;
  struct FingerprintScanResult results[4];
};

BLEService fingerprintService("E95D0400-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic fingerprintPinCharacteristic("E95D0401-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(FingerprintPinData));
BLECharacteristic fingerprintEnrollmentStatus("E95D0402-251D-470A-A062-FA1922DFA9A7", BLERead | BLEWrite, sizeof(FingerprintEnrollmentStatus));
BLECharacteristic fingerprintScanStatus("E95D0403-251D-470A-A062-FA1922DFA9A7", BLERead | BLEWrite, sizeof(FingerprintScanStatus));
BLECharacteristic fingerprintEnrollmentResult("E95D0404-251D-470A-A062-FA1922DFA9A7", BLEIndicate, sizeof(FingerprintEnrollmentResult));
BLECharacteristic fingerprintScanResult("E95D0405-251D-470A-A062-FA1922DFA9A7", BLEIndicate, sizeof(FingerprintScanResultMsg));

void setupFingerprint();
void updateFingerprint();
void fingerprintScanRequested(BLECentral &central, BLECharacteristic &characteristic);
void fingerprintPinRequested(BLECentral &central, BLECharacteristic &characteristic);
void fingerprintScanStatus(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupFingerprint() {
#if !ONE_SERVICE
  Serial.println("Configuring Fingerprint Service...");
  blePeripheral.addAttribute(fingerprintService);
#endif
  blePeripheral.addAttribute(fingerprintPinCharacteristic);
  blePeripheral.addAttribute(fingerprintEnrollmentStatus);
  blePeripheral.addAttribute(fingerprintScanStatus);
  blePeripheral.addAttribute(fingerprintEnrollmentResult);
  blePeripheral.addAttribute(fingerprintScanResult);
  fingerprintPinCharacteristic.setEventHandler(BLEWritten, fingerprintPinWritten);
  fingerprintEnrollmentStatus.setEventHandler(BLEWritten, fingerprintEnrollmentRequested);
  fingerprintScanStatus.setEventHandler(BLEWritten, fingerprintScanRequested);
}


void fingerprintPinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  // TODO: implementation
}


void fingerprintEnrollmentRequested(BLECentral &central, BLECharacteristic &characteristic) {
  // TODO: implementation
}


void fingerprintScanRequested(BLECentral &central, BLECharacteristic &characteristic) {
  // TODO: implementation
}


void updateFingerprint() {

}

#elif MODE == SETUP

setupFingerprint();

#elif MODE == UPDATE

#endif
#endif

