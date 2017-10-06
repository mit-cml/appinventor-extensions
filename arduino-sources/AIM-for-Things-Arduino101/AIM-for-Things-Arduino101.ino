#define NAME             "App Inventor"     // no more than 11 characters
#define DEBUGGING        DISABLED

#define ACCELEROMETER    DISABLED
#define BUTTON           DISABLED
#define CAMERA           DISABLED
#define CONSOLE          DISABLED
#define FINGERPRINT      DISABLED
#define GYROSCOPE        DISABLED
#define LED              DISABLED
#define LIGHT_SENSOR     DISABLED
#define MOISTURE_SENSOR  DISABLED
#define PINS             DISABLED
#define PROXIMITY        DISABLED
#define PWM              DISABLED
#define RGBLCD           DISABLED
#define SERVO            DISABLED
#define SOUND_RECORDER   DISABLED
#define TEMPERATURE      DISABLED

// frequency to read sensor values in µs
const unsigned long SENSOR_UPDATE_FREQ = 50000;
const unsigned long IMU_READ_FREQ = 5000;
const double IMU_FILTER_ALPHA = 0.5; //Alpha for accelerometer low pass filter

unsigned long nextSensorUpdate;
unsigned long nextIMURead;
double dt;

const uint8_t BITS[8] = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80 };
const uint8_t MASK[8] = { 0xFE, 0xFD, 0xFB, 0xF7, 0xEF, 0xDF, 0xBF, 0x7F };

#include "common.h"

bool connected = false;
BLEPeripheral blePeripheral;
BLEService appinventorService("E95DFFFF-251D-470A-A062-FA1922DFA9A7");

#define MODE PREAMBLE
#include "sensors.h"
#undef MODE
#define MODE DEFINITIONS
#include "sensors.h"
#undef MODE
#define MODE FUNCTIONS
#include "sensors.h"
#undef MODE

void setup() {
  Serial.begin(9600);
#if DEBUGGING == ENABLED
  while (!Serial);
#endif
  LOGLN("Starting App Inventor...");

  blePeripheral.setDeviceName(NAME);
  blePeripheral.setLocalName(NAME);
  blePeripheral.setAppearance(128);
  blePeripheral.setAdvertisedServiceUuid(appinventorService.uuid());

  blePeripheral.addAttribute(appinventorService);

#define MODE SETUP
#include "sensors.h"
#undef MODE

  LOGLN("Setting connection interval");
  blePeripheral.setConnectionInterval(0x0006, 0x0010);
  LOGLN("Begin peripheral");
  blePeripheral.begin();

  Serial.println("App Inventor started.");
}

void loop() {
  BLECentral bleCentral = blePeripheral.central();
  if (bleCentral && !connected) {
    Serial.print("Connected to ");
    Serial.println(bleCentral.address());
    connected = true;
  } else if (connected && !bleCentral) {
    Serial.println("Disconnected");
    connected = false;
  }

  if (connected) {
    if ((long)(micros() - nextSensorUpdate) >= 0) {
      updateSensorValues();
      nextSensorUpdate += SENSOR_UPDATE_FREQ;
    }
#if ACCELEROMETER == ENABLED || GYROSCOPE == ENABLED
    if ((long)(micros() - nextIMURead) >= 0) {
      updateIMUValues();
    }
#endif
  }
}

void updateSensorValues() {
#define MODE UPDATE
#include "sensors.h"
#undef MODE
}

#if ACCELEROMETER == ENABLED || GYROSCOPE == ENABLED
#include "imu.hh"
#endif
