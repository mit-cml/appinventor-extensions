#include <LBLEPeriphral.h>
#include <LBLE.h>
#include "constants.hpp"
#include "lble_setup.hpp"

std::vector<struct gpio_lble_profile> GPIO_LBLE_PROFILES;

void setup_lble()
{
    // setup for GPIO
    for (int idx = 0; idx < GPIO_UUID_PROFILES_SIZE; idx += 1)
    {
        // Note that services and characteristics are registered
        // in the ctor of vector elements

        auto *uuid_ptr = &GPIO_UUID_PROFILES[idx];
        GPIO_LBLE_PROFILES.emplace(GPIO_LBLE_PROFILES.end(), uuid_ptr);
    }

    // advertise
    LBLEAdvertisementData advertisement;
    advertisement.configAsConnectableDevice(DEVICE_NAME);
    LBLEPeripheral.setName(DEVICE_NAME);

    LBLEPeripheral.begin();
    LBLEPeripheral.advertise(advertisement);
}
