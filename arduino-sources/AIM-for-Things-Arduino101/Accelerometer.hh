#if ACCELEROMETER == ENABLED
#if MODE == PREAMBLE

#include <CurieIMU.h>

#define ACCELEROMETER_SETUP  setupAccelerometer
#define ACCELEROMETER_UPDATE updateAccelerometer

#elif MODE == DEFINITIONS

struct AccelerometerData {
  float x;
  float y;
  float z;
};

volatile struct AccelerometerData accel;

BLEService accelerometerService("E95D0100-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic accelerometerReadChar("E95D0101-251D-470A-A062-FA1922DFA9A7", BLERead | BLENotify, sizeof(AccelerometerData));

void setupAccelerometer();
void updateAccelerometer();
float convertRawAcceleration(int aRaw);
float convertRawGyro(int gRaw);

#elif MODE == FUNCTIONS

void updateAccelerometer() {
  // accel is computed in updateIMUValues in imu.hh
  accelerometerReadChar.setValue((byte *)&accel, sizeof(accel));
}


void setupAccelerometer() {
  CurieIMU.begin();
  CurieIMU.setAccelerometerRange(4);
  CurieIMU.setGyroRange(250);

  // Configure initial values for characteristics
  LOGLN("Calibrate sensors");
  calibrateIMU();
  updateAccelerometer();
  updateIMUValues();

#if !ONE_SERVICE
  Serial.println("Configuring Accelerometer Service...");
  blePeripheral.addAttribute(accelerometerService);
#endif
  blePeripheral.addAttribute(accelerometerReadChar);

}

#elif MODE == SETUP

setupAccelerometer();

#elif MODE == UPDATE

updateAccelerometer();

#endif
#endif

