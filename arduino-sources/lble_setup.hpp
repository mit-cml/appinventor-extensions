#ifndef __MT7697_LBLE_SETUP_HPP__
#define __MT7697_LBLE_SETUP_HPP__

#include <LBLEPeriphral.h>
#include <LBLE.h>
#include <Arduino.h>
#include "constants.hpp"

static LBLEService PIN_SERVICE(PIN_SERVICE_UUID);

static LBLECharacteristicInt PIN02_MODE_CHAR(PIN02_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_DATA_CHAR(PIN02_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN03_MODE_CHAR(PIN03_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN03_DATA_CHAR(PIN03_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN04_MODE_CHAR(PIN04_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN04_DATA_CHAR(PIN04_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN05_MODE_CHAR(PIN05_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN05_DATA_CHAR(PIN05_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN06_MODE_CHAR(PIN06_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN06_DATA_CHAR(PIN06_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN07_MODE_CHAR(PIN07_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN07_DATA_CHAR(PIN07_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

// reserve for i2c
//
// static LBLECharacteristicInt PIN08_MODE_CHAR(PIN08_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN08_DATA_CHAR(PIN08_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLECharacteristicInt PIN09_MODE_CHAR(PIN09_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN09_DATA_CHAR(PIN09_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN10_MODE_CHAR(PIN10_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN10_DATA_CHAR(PIN10_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN11_MODE_CHAR(PIN11_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN11_DATA_CHAR(PIN11_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN12_MODE_CHAR(PIN12_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN12_DATA_CHAR(PIN12_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN13_MODE_CHAR(PIN13_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN13_DATA_CHAR(PIN13_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN14_MODE_CHAR(PIN14_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN14_DATA_CHAR(PIN14_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN15_MODE_CHAR(PIN15_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN15_DATA_CHAR(PIN15_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN16_MODE_CHAR(PIN16_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN16_DATA_CHAR(PIN16_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLECharacteristicInt PIN17_MODE_CHAR(PIN17_MODE_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN17_DATA_CHAR(PIN17_DATA_CHAR_UUID, LBLE_READ | LBLE_WRITE);

// pin profile struct
struct pin_lble_profile
{
    int pin;
    int mode; 
    LBLECharacteristicInt *mode_char;
    LBLECharacteristicInt *data_char;
};

class LBLEPinSetup
{
public:
    LBLEPinSetup(){}
    pin_lble_profile PIN_LBLE_PROFILES[PIN_UUID_PROFILES_SIZE] = {
        { 2,
          MODE_UNSET,
          &PIN02_MODE_CHAR,
          &PIN02_DATA_CHAR },
        { 3,
          MODE_UNSET,
          &PIN03_MODE_CHAR,
          &PIN03_DATA_CHAR },
        { 4,
          MODE_UNSET,
          &PIN04_MODE_CHAR,
          &PIN04_DATA_CHAR },
        { 5,
          MODE_UNSET,
          &PIN05_MODE_CHAR,
          &PIN05_DATA_CHAR },
        { 6,
          MODE_UNSET,
          &PIN06_MODE_CHAR,
          &PIN06_DATA_CHAR },
        { 7,
          MODE_UNSET,
          &PIN07_MODE_CHAR,
          &PIN07_DATA_CHAR },
        // reserve for i2c
        //
        // { 8,
        //   MODE_UNSET,
        //   &PIN08_MODE_CHAR,
        //   &PIN08_DATA_CHAR },
        // { 9,
        //   MODE_UNSET,
        //   &PIN09_MODE_CHAR,
        //   &PIN09_DATA_CHAR },
        { 10,
          MODE_UNSET,
          &PIN10_MODE_CHAR,
          &PIN10_DATA_CHAR },
        { 11,
          MODE_UNSET,
          &PIN11_MODE_CHAR,
          &PIN11_DATA_CHAR },
        { 12,
          MODE_UNSET,
          &PIN12_MODE_CHAR,
          &PIN12_DATA_CHAR },
        { 13,
          MODE_UNSET,
          &PIN13_MODE_CHAR,
          &PIN13_DATA_CHAR },
        { 14,
          MODE_UNSET,
          &PIN14_MODE_CHAR,
          &PIN14_DATA_CHAR },
        { 15,
          MODE_UNSET,
          &PIN15_MODE_CHAR,
          &PIN15_DATA_CHAR },
        { 16,
          MODE_UNSET,
          &PIN16_MODE_CHAR,
          &PIN16_DATA_CHAR },
        { 17,
          MODE_UNSET,
          &PIN17_MODE_CHAR,
          &PIN17_DATA_CHAR }
    };

    void begin()
    {
        Serial.begin(9600);

        LBLE.begin();
        while(!LBLE.ready())
            delay(100);
        Serial.println("BLE ready.");

        Serial.print("MAC address: ");
        Serial.println(LBLE.getDeviceAddress());

        // advertise
        LBLEAdvertisementData advertisement;
        advertisement.configAsConnectableDevice(DEVICE_NAME);
        LBLEPeripheral.setName(DEVICE_NAME);


        PIN_SERVICE.addAttribute(PIN02_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN02_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN03_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN03_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN04_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN04_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN05_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN05_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN06_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN06_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN07_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN07_DATA_CHAR);

        // reserve for i2c
        //
        // PIN_SERVICE.addAttribute(PIN08_MODE_CHAR);
        // PIN_SERVICE.addAttribute(PIN08_DATA_CHAR);
        //
        // PIN_SERVICE.addAttribute(PIN09_MODE_CHAR);
        // PIN_SERVICE.addAttribute(PIN09_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN10_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN10_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN11_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN11_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN12_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN12_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN13_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN13_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN14_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN14_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN15_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN15_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN16_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN16_DATA_CHAR);

        PIN_SERVICE.addAttribute(PIN17_MODE_CHAR);
        PIN_SERVICE.addAttribute(PIN17_DATA_CHAR);

        LBLEPeripheral.addService(PIN_SERVICE);

        LBLEPeripheral.begin();
        LBLEPeripheral.advertise(advertisement);
    }
};
#endif
