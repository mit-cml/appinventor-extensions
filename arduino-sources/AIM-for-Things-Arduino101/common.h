#define ENABLED 1
#define DISABLED 0

#define ONE_SERVICE 0
#define ANALOG_PIN_BYTES 2
#define DIGITAL_PIN_BYTES 8
#define DIGITAL_PINS 14
#define MAX_BLE_PACKET 20
#define PWM_PINS 4

#if DEBUGGING == ENABLED
#define LOG(x) Serial.print((x))
#define LOGLN(x) Serial.println((x))
#else
#define LOG(x)
#define LOGLN(x)
#endif

#ifndef INCLUDED_CURIE_BLE_H
#include <CurieBLE.h>
#define INCLUDED_CURIE_BLE_H
#endif

#include "modes.h"

void printArray(const uint8_t values[], int n) {
  for (int i = 0; i < n; i++) {
    Serial.print(values[i]);
    Serial.print(' ');
  }
  Serial.println();
}

void printSignedArray(const int8_t values[], int n) {
  for (int i = 0; i < n; i++) {
    Serial.print(values[i]);
    Serial.print(' ');
  }
  Serial.println();
}


#if ACCELEROMETER == ENABLED || GYROSCOPE == ENABLED

#include <CurieIMU.h>

float convertRawAcceleration(int aRaw);
float convertRawGyro(int gRaw);
void calibrateIMU();
void updateIMUValues();

#endif
