#if CAMERA == ENABLED
#if MODE == PREAMBLE

#define CAMERA_SETUP setupCamera();
#define CAMERA_UPDATE updateCamera();

#elif MODE == DEFINITIONS

struct CameraPinData {
  uint8_t rxPins[DIGITAL_PIN_BYTES];
  uint8_t txPins[DIGITAL_PIN_BYTES];
};

struct CameraWriteData {
  uint32_t offset;
};

struct CameraReadData {
  uint8_t frame[MAX_BLE_PACKET];
};

struct CameraPinData cameraPins;
struct CameraWriteData cameraOffset;

BLEService cameraService("E95D0300-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic cameraPinCharacteristic("E95D0301-251D-470A-A062-FA1922DFA9A7", BLEWrite, 16);
BLECharacteristic cameraWriteFrameIndex("E95D0302-251D-470A-A062-FA1922DFA9A7", BLEWrite, 4);
BLECharacteristic cameraReadFrameContent("E95D0303-251D-470A-A062-FA1922DFA9A7", BLERead, 20);

void setupCamera();
void updateCamera();
void cameraPinWritten(BLECentral &central, BLECharacteristic &characteristic);
void cameraFrameIndexWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupCamera() {
#if !ONE_SERVICE
  LOGLN("Configuring Camera Service...");
  blePeripheral.addAttribute(cameraService);
#endif
  blePeripheral.addAttribute(cameraPinCharacteristic);
  blePeripheral.addAttribute(cameraWriteFrameIndex);
  blePeripheral.addAttribute(cameraReadFrameContent);
  cameraPinCharacteristic.setEventHandler(BLEWritten, cameraPinWritten);
  cameraWriteFrameIndex.setEventHandler(BLEWritten, cameraFrameIndexWritten);
}


void cameraPinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  // TODO: implementation
}


void cameraFrameIndexWritten(BLECentral &central, BLECharacteristic &characteristic) {
  // TODO: implementation
}


void updateCamera() {

}

#endif
#endif

