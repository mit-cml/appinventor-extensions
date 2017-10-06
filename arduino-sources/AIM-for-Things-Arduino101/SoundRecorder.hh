#if SOUND_RECORDER == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

struct SoundRecorderPins {
  uint8_t recorderPins[DIGITAL_PIN_BYTES];
  uint8_t playbackPins[DIGITAL_PIN_BYTES];
};

struct SoundRecorderMessage {
  int8_t pin;
  int8_t state;
};

SoundRecorderPins recorderPins;

BLEService soundRecorderService("E95D0D00-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic soundRecorderInitialize("E95D0D01-251D-470A-A062-FA1922DFA9A7", BLERead | BLEWrite, sizeof(SoundRecorderPins));
BLECharacteristic soundRecorderRecord("E95D0D02-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(SoundRecorderMessage));
BLECharacteristic soundRecorderPlayback("E95D0D03-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(SoundRecorderMessage));

void soundRecorderInitializeRequested(BLECentral &central, BLECharacteristic &characteristic);
void soundRecorderRecordRequested(BLECentral &central, BLECharacteristic &characteristic);
void soundRecorderPlaybackRequested(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupSoundRecorder() {
#if !ONE_SERVICE
  LOGLN("Configuring Sound Recorder Service...");
  blePeripheral.addAttribute(soundRecorderService);
#endif
  blePeripheral.addAttribute(soundRecorderInitialize);
  blePeripheral.addAttribute(soundRecorderRecord);
  blePeripheral.addAttribute(soundRecorderPlayback);
  soundRecorderInitialize.setEventHandler(BLEWritten, soundRecorderInitializeRequested);
  soundRecorderRecord.setEventHandler(BLEWritten, soundRecorderRecordRequested);
  soundRecorderPlayback.setEventHandler(BLEWritten, soundRecorderPlaybackRequested);
}


void soundRecorderInitializeRequested(BLECentral &central, BLECharacteristic &characteristic) {
  int pin = 0;
  memcpy(&recorderPins, characteristic.value(), sizeof(SoundRecorderPins));
  for (int i = 0; i < DIGITAL_PIN_BYTES; i++) {
    for (int j = 0x80; j != 0; j >>= 1) {
      if ((recorderPins.recorderPins[i] & j) == j || (recorderPins.playbackPins[i] & j) == j) {
        pinMode(pin, OUTPUT);
        digitalWrite(pin, HIGH);
      }
      pin++;
    }
  }
}


void soundRecorderRecordRequested(BLECentral &central, BLECharacteristic &characteristic) {
  SoundRecorderMessage message;
  memcpy(&message, characteristic.value(), sizeof(SoundRecorderMessage));
  digitalWrite(message.pin, message.state ? LOW : HIGH);
}


void soundRecorderPlaybackRequested(BLECentral &central, BLECharacteristic &characteristic) {
  SoundRecorderMessage message;
  memcpy(&message, characteristic.value(), sizeof(SoundRecorderMessage));
  if (message.state) {
    digitalWrite(message.pin, LOW);
    delay(100);
    digitalWrite(message.pin, HIGH);
  }
}

#elif MODE == SETUP

setupSoundRecorder();

#endif
#endif

