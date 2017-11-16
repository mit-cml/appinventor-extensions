#ifndef __MT7697_LBLE_CONFIG_HPP__
#define __MT7697_LBLE_CONFIG_HPP__

#include <vector>
#include <LBLEPeriphral.h>
#include <LBLE.h>

const int PIN_MODE_ANALOG_READ   = 0x00;
const int PIN_MODE_ANALOG_WRITE  = 0x01;
const int PIN_MODE_DIGITAL_READ  = 0x02;
const int PIN_MODE_DIGITAL_WRITE = 0x03;
const int PIN_MODE_SERVO         = 0x04;
const int PIN_MODE_NONE          = 0xFF;

struct gpio_lble_profile
{
    const gpio_uuid_profile *uuid_profile;
    int mode = PIN_MODE_NONE;
    LBLEService service;
    LBLECharacteristicInt mode_char;
    LBLECharacteristicBuffer data_char;

    gpio_lble_profile(const gpio_uuid_profile *_uuid_profile)
        : uuid_profile(_uuid_profile)
        , service(_uuid_profile->service_uuid)
        , mode_char(_uuid_profile->mode_char_uuid, LBLE_READ | LBLE_WRITE)
        , data_char(_uuid_profile->data_char_uuid, LBLE_READ | LBLE_WRITE)
    {
        service.addAttribute(mode_char);
        service.addAttribute(data_char);
        LBLEPeripheral.addService(service);
    }
};

extern std::vector<struct gpio_lble_profile> GPIO_LBLE_PROFILES;

void setup_lble();

#endif
