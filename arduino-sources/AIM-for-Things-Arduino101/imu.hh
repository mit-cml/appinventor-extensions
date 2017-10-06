#ifndef APPINVENTOR_IMU_HH_
#define APPINVENTOR_IMU_HH_

float convertRawAcceleration(int aRaw) {
  return (float) aRaw / 8912.0;
}


float convertRawGyro(int gRaw) {
  return gRaw * 250.0 / 32768.0;
}


void calibrateIMU() {
  CurieIMU.autoCalibrateGyroOffset();
  CurieIMU.autoCalibrateAccelerometerOffset(X_AXIS, 0);
  CurieIMU.autoCalibrateAccelerometerOffset(Y_AXIS, 0);
  CurieIMU.autoCalibrateAccelerometerOffset(Z_AXIS, 1);
}


void updateIMUValues() {
  int ax, ay, az, gx, gy, gz;
  float roll, pitch;

  CurieIMU.readMotionSensor(ax, ay, az, gx, gy, gz);
  dt = (double)(micros() - (nextIMURead - IMU_READ_FREQ)) / 1000000;
  nextIMURead = micros() + IMU_READ_FREQ;

#if ACCELEROMETER == ENABLED
  accel.x = ax * IMU_FILTER_ALPHA + accel.x * (1.0 - IMU_FILTER_ALPHA);
  accel.y = ay * IMU_FILTER_ALPHA + accel.y * (1.0 - IMU_FILTER_ALPHA);
  accel.z = az * IMU_FILTER_ALPHA + accel.z * (1.0 - IMU_FILTER_ALPHA);
#endif

#if GYROSCOPE == ENABLED
  roll = atan2(convertRawAcceleration(ay), convertRawAcceleration(az)) * RAD_TO_DEG;
  pitch = atan2(-convertRawAcceleration(ax), convertRawAcceleration(az)) * RAD_TO_DEG;

  gyro.angleX = 0.93 * (gyro.angleX + convertRawGyro(gx) * dt) + 0.07 * roll;
  gyro.angleY = 0.93 * (gyro.angleY + convertRawGyro(gy) * dt) + 0.07 * pitch;

  if (gyro.angleX > 180) {
    gyro.angleX -= 360;
  } else if (gyro.angleX < -180) {
    gyro.angleX += 360;
  }
#endif
}

#endif

