#include <LBLEPeriphral.h>
#include <LBLE.h>

#include "constants.hpp"

// LBLEService pin_service(PIN_SERVICE);
// LBLECharacteristicInt analog_pin_characteristic(ANALOG_PIN_CHARACTERISTIC, LBLE_READ | LBLE_WRITE);
// LBLECharacteristicInt digital_pin_characteristic(DIGITAL_PIN_CHARACTERISTIC, LBLE_READ | LBLE_WRITE);

void setup()
{
    // pin_service.addAttribute(analog_pin_characteristic);
    // pin_service.addAttribute(digital_pin_characteristic);
    // LBLEPeripheral.addService(pin_service);

    LBLEAdvertisementData advertisement;
    advertisement.configAsConnectableDevice(DEVICE_NAME);
    LBLEPeripheral.setName(DEVICE_NAME);

    LBLEPeripheral.begin();
    LBLEPeripheral.advertise(advertisement);
}

void loop()
{
}
