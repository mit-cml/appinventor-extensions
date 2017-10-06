#if GYROSCOPE == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

struct GyroscopeData {
  float angleX;
  float angleY;
};

volatile struct GyroscopeData gyro;

BLEService gyroService("E95D0500-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic gyroscopeReadChar("E95D0501-251D-470A-A062-FA1922DFA9A7", BLERead | BLENotify, sizeof(GyroscopeData));


#elif MODE == FUNCTIONS

void setupGyroscope() {
#if !ONE_SERVICE
  Serial.println("Configuring Gyroscope Service...");
  blePeripheral.addAttribute(gyroService);
#endif
  blePeripheral.addAttribute(gyroscopeReadChar);
}


void updateGyroscope() {
  // gyro is computed in updateIMUValues in imu.hh
  gyroscopeReadChar.setValue((const unsigned char *)&gyro, sizeof(gyro));
}

#elif MODE == SETUP

setupGyroscope();

#elif MODE == UPDATE

updateGyroscope();

#endif
#endif

