#if SERVO == ENABLED
#if MODE == PREAMBLE

#include <Servo.h>

#elif MODE == DEFINITIONS

struct ServoData {
  uint8_t pins[DIGITAL_PINS];
};

Servo servos[14];
ServoData servoPins;

BLEService servoService("E95D0C00-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic servoPosition("E95D0C01-251D-470A-A062-FA1922DFA9A7", BLERead | BLEWrite, sizeof(ServoData));
BLECharacteristic servoPositionMicros("E95D0C02-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(ServoData));

void setupServo();
void servoPositionWritten(BLECentral &central, BLECharacteristic &characteristic);
void servoPositionMicrosWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupServo() {
#if !ONE_SERVICE
  LOGLN("Configuring Servo Service...");
  blePeripheral.addAttribute(servoService);
#endif
  blePeripheral.addAttribute(servoPosition);
  blePeripheral.addAttribute(servoPositionMicros);
  servoPosition.setEventHandler(BLEWritten, servoPositionWritten);
  servoPositionMicros.setEventHandler(BLEWritten, servoPositionMicrosWritten);
  memset(&servoPins, 255, sizeof(ServoData));
  servoPosition.setValue((const uint8_t *)&servoPins, sizeof(servoPins));
}


void servoPositionWritten(BLECentral &central, BLECharacteristic &characteristic) {
  memcpy(&servoPins, characteristic.value(), sizeof(ServoData));
  for (int i = 0; i < DIGITAL_PINS; i++) {
    if (servoPins.pins[i] != 255) {
      if (!servos[i].attached()) {
        servos[i].attach(i);
        Serial.println("Attached servo to pin " + String(i));
      }
      servos[i].write(servoPins.pins[i]);
      Serial.println("Wrote " + String(servoPins.pins[i]) + " to pin " + String(i));
    } else if (servoPins.pins[i] == 255 && servos[i].attached()) {
      servos[i].detach();
      Serial.println("Detached servo from pin " + String(i));
    }
  }
}


void servoPositionMicrosWritten(BLECentral &central, BLECharacteristic &characteristic) {
  struct { int16_t pin, microseconds; } data;
  memcpy(&data, characteristic.value(), 4);
  if (!servos[data.pin].attached()) {
    servos[data.pin].attach(data.pin);
  }
  servos[data.pin].writeMicroseconds(data.microseconds);
  uint8_t value = servos[data.pin].read();
  if (servoPins.pins[data.pin] != value) {
    servoPins.pins[data.pin] = value;
    servoPosition.setValue((const uint8_t *)&servoPins, sizeof(servoPins));
  }
}

#elif MODE == SETUP

setupServo();

#endif
#endif

