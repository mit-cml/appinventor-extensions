#if CONSOLE == ENABLED
#if MODE == PREAMBLE

// no premable needed for console

#elif MODE == DEFINITIONS

BLEService consoleService("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
BLECharacteristic consoleTransmitCharacteristic("6E400002-B5A3-F393-E0A9-E50E24DCCA9E", BLEIndicate, 20);
BLECharacteristic consoleReceiveCharacteristic("6E400003-B5A3-F393-E0A9-E50E24DCCA9E", BLEWrite, 20);

void setupConsole();
void updateConsole();
void consoleReceivedBytes(BLECentral &central, BLECharacteristic &characteristic);

#elif MODE == FUNCTIONS

void setupConsole() {
#if !ONE_SERVICE
  LOGLN("Configuring Console Service...");
  blePeripheral.addAttribute(consoleService);
#endif
  blePeripheral.addAttribute(consoleTransmitCharacteristic);
  blePeripheral.addAttribute(consoleReceiveCharacteristic);
  consoleReceiveCharacteristic.setEventHandler(BLEWritten, consoleReceivedBytes);
}


void consoleReceivedBytes(BLECentral &central, BLECharacteristic &characteristic) {
  // TODO: implementation
}


void updateConsole() {

}

#elif MODE == SETUP

setupConsole();

#elif MODE == UPDATE

updateConsole();

#endif
#endif

