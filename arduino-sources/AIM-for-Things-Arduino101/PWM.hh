#if PWM == ENABLED
#if MODE == PREAMBLE

#elif MODE == DEFINITIONS

struct PWMPinMsg {
  uint8_t pin;
  uint8_t power;
};

BLEService pwmService("E95D0F00-251D-470A-A062-FA1922DFA9A7");
BLECharacteristic pwmSetChar("E95D0F01-251D-470A-A062-FA1922DFA9A7", BLEWrite, sizeof(PWMPinMsg));

void setupPWM();
void pwmPinWritten(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupPWM() {
#if !ONE_SERVICE
  LOGLN("Configuring PWM service...");
  blePeripheral.addAttribute(pwmService);
#endif
  blePeripheral.addAttribute(pwmSetChar);
  pwmSetChar.setEventHandler(BLEWritten, pwmPinWritten);
}


void pwmPinWritten(BLECentral &central, BLECharacteristic &characteristic) {
  PWMPinMsg msg;
  memcpy((void *)&msg, characteristic.value(), sizeof(msg));
  LOG("Setting pin ");
  LOG(msg.pin);
  LOG(" to ");
  LOGLN(msg.power);
  analogWrite(msg.pin, (int)(2.55 * msg.power));
}

#elif MODE == SETUP

setupPWM();

#endif
#endif

