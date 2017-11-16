#include <LBLEPeriphral.h>
#include <LBLE.h>
#include "constants.hpp"
#include "lble_setup.hpp"
#include "Arduino.h"

std::vector<struct gpio_lble_profile> GPIO_LBLE_PROFILES;

void setup_lble()
{
    // setup for GPIO
    GPIO_LBLE_PROFILES.reserve(GPIO_UUID_PROFILES_SIZE);
    for (int idx = 0; idx < GPIO_UUID_PROFILES_SIZE; ++idx)
    {
        // Note that services and characteristics are registered
        // in the ctor of vector elements
        GPIO_LBLE_PROFILES.push_back(&GPIO_UUID_PROFILES[idx]);
    }

    Serial.begin(9600);

    LBLE.begin();
    while(!LBLE.ready())
        delay(100);
    Serial.println("BLE ready.");

    Serial.print("MAC address: ");
    Serial.println(LBLE.getDeviceAddress());


    // advertise
    LBLEAdvertisementData advertisement;
    Serial.println("1");
    advertisement.configAsConnectableDevice(DEVICE_NAME);
    Serial.println("2");
    LBLEPeripheral.setName(DEVICE_NAME);
    Serial.println("3");

    LBLEPeripheral.begin();
    Serial.println("4");
    LBLEPeripheral.advertise(advertisement);
    Serial.println("5");
}
