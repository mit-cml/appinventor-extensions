#ifndef __MT7697_LBLE_SETUP_HPP__
#define __MT7697_LBLE_SETUP_HPP__

#include <LBLEPeriphral.h>
#include <LBLE.h>
#include <Arduino.h>
#include "constants.hpp"

static LBLEService           PIN02_SERVICE(PIN02_SERVICE_UUID);
static LBLECharacteristicInt PIN02_ANA_INP_CHAR(PIN02_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_ANA_OUT_CHAR(PIN02_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_DIG_INP_CHAR(PIN02_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_DIG_OUT_CHAR(PIN02_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN03_SERVICE(PIN03_SERVICE_UUID);
static LBLECharacteristicInt PIN03_ANA_INP_CHAR(PIN03_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN03_ANA_OUT_CHAR(PIN03_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN03_DIG_INP_CHAR(PIN03_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN03_DIG_OUT_CHAR(PIN03_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN04_SERVICE(PIN04_SERVICE_UUID);
static LBLECharacteristicInt PIN04_ANA_INP_CHAR(PIN04_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN04_ANA_OUT_CHAR(PIN04_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN04_DIG_INP_CHAR(PIN04_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN04_DIG_OUT_CHAR(PIN04_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN05_SERVICE(PIN05_SERVICE_UUID);
static LBLECharacteristicInt PIN05_ANA_INP_CHAR(PIN05_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN05_ANA_OUT_CHAR(PIN05_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN05_DIG_INP_CHAR(PIN05_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN05_DIG_OUT_CHAR(PIN05_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN06_SERVICE(PIN06_SERVICE_UUID);
static LBLECharacteristicInt PIN06_ANA_INP_CHAR(PIN06_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN06_ANA_OUT_CHAR(PIN06_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN06_DIG_INP_CHAR(PIN06_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN06_DIG_OUT_CHAR(PIN06_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN07_SERVICE(PIN07_SERVICE_UUID);
static LBLECharacteristicInt PIN07_ANA_INP_CHAR(PIN07_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN07_ANA_OUT_CHAR(PIN07_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN07_DIG_INP_CHAR(PIN07_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN07_DIG_OUT_CHAR(PIN07_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN08_SERVICE(PIN08_SERVICE_UUID);
static LBLECharacteristicInt PIN08_ANA_INP_CHAR(PIN08_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN08_ANA_OUT_CHAR(PIN08_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN08_DIG_INP_CHAR(PIN08_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN08_DIG_OUT_CHAR(PIN08_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN09_SERVICE(PIN09_SERVICE_UUID);
static LBLECharacteristicInt PIN09_ANA_INP_CHAR(PIN09_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN09_ANA_OUT_CHAR(PIN09_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN09_DIG_INP_CHAR(PIN09_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN09_DIG_OUT_CHAR(PIN09_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN10_SERVICE(PIN10_SERVICE_UUID);
static LBLECharacteristicInt PIN10_ANA_INP_CHAR(PIN10_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN10_ANA_OUT_CHAR(PIN10_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN10_DIG_INP_CHAR(PIN10_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN10_DIG_OUT_CHAR(PIN10_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN11_SERVICE(PIN11_SERVICE_UUID);
static LBLECharacteristicInt PIN11_ANA_INP_CHAR(PIN11_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN11_ANA_OUT_CHAR(PIN11_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN11_DIG_INP_CHAR(PIN11_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN11_DIG_OUT_CHAR(PIN11_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN12_SERVICE(PIN12_SERVICE_UUID);
static LBLECharacteristicInt PIN12_ANA_INP_CHAR(PIN12_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN12_ANA_OUT_CHAR(PIN12_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN12_DIG_INP_CHAR(PIN12_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN12_DIG_OUT_CHAR(PIN12_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN13_SERVICE(PIN13_SERVICE_UUID);
static LBLECharacteristicInt PIN13_ANA_INP_CHAR(PIN13_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN13_ANA_OUT_CHAR(PIN13_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN13_DIG_INP_CHAR(PIN13_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN13_DIG_OUT_CHAR(PIN13_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN14_SERVICE(PIN14_SERVICE_UUID);
static LBLECharacteristicInt PIN14_ANA_INP_CHAR(PIN14_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN14_ANA_OUT_CHAR(PIN14_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN14_DIG_INP_CHAR(PIN14_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN14_DIG_OUT_CHAR(PIN14_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN15_SERVICE(PIN15_SERVICE_UUID);
static LBLECharacteristicInt PIN15_ANA_INP_CHAR(PIN15_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN15_ANA_OUT_CHAR(PIN15_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN15_DIG_INP_CHAR(PIN15_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN15_DIG_OUT_CHAR(PIN15_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN16_SERVICE(PIN16_SERVICE_UUID);
static LBLECharacteristicInt PIN16_ANA_INP_CHAR(PIN16_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN16_ANA_OUT_CHAR(PIN16_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN16_DIG_INP_CHAR(PIN16_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN16_DIG_OUT_CHAR(PIN16_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

static LBLEService           PIN17_SERVICE(PIN17_SERVICE_UUID);
static LBLECharacteristicInt PIN17_ANA_INP_CHAR(PIN17_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN17_ANA_OUT_CHAR(PIN17_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN17_DIG_INP_CHAR(PIN17_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN17_DIG_OUT_CHAR(PIN17_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

// pin profile struct
struct pin_lble_profile
{
    int pin;
    LBLEService *service;
    LBLECharacteristicInt *ana_inp_char;
    LBLECharacteristicInt *ana_out_char;
    LBLECharacteristicInt *dig_inp_char;
    LBLECharacteristicInt *dig_out_char;
};

class LBLEPinSetup
{
public:
    LBLEPinSetup(){}
    pin_lble_profile PIN_LBLE_PROFILES[PIN_UUID_PROFILES_SIZE] = {
        { 2,
          &PIN02_SERVICE,
          &PIN02_ANA_INP_CHAR,
          &PIN02_ANA_OUT_CHAR,
          &PIN02_DIG_INP_CHAR,
          &PIN02_DIG_OUT_CHAR },
        { 3,
          &PIN03_SERVICE,
          &PIN03_ANA_INP_CHAR,
          &PIN03_ANA_OUT_CHAR,
          &PIN03_DIG_INP_CHAR,
          &PIN03_DIG_OUT_CHAR },
        { 4,
          &PIN04_SERVICE,
          &PIN04_ANA_INP_CHAR,
          &PIN04_ANA_OUT_CHAR,
          &PIN04_DIG_INP_CHAR,
          &PIN04_DIG_OUT_CHAR },
        { 5,
          &PIN05_SERVICE,
          &PIN05_ANA_INP_CHAR,
          &PIN05_ANA_OUT_CHAR,
          &PIN05_DIG_INP_CHAR,
          &PIN05_DIG_OUT_CHAR },
        { 6,
          &PIN06_SERVICE,
          &PIN06_ANA_INP_CHAR,
          &PIN06_ANA_OUT_CHAR,
          &PIN06_DIG_INP_CHAR,
          &PIN06_DIG_OUT_CHAR },
        { 7,
          &PIN07_SERVICE,
          &PIN07_ANA_INP_CHAR,
          &PIN07_ANA_OUT_CHAR,
          &PIN07_DIG_INP_CHAR,
          &PIN07_DIG_OUT_CHAR },
        { 8,
          &PIN08_SERVICE,
          &PIN08_ANA_INP_CHAR,
          &PIN08_ANA_OUT_CHAR,
          &PIN08_DIG_INP_CHAR,
          &PIN08_DIG_OUT_CHAR },
        { 9,
          &PIN09_SERVICE,
          &PIN09_ANA_INP_CHAR,
          &PIN09_ANA_OUT_CHAR,
          &PIN09_DIG_INP_CHAR,
          &PIN09_DIG_OUT_CHAR },
        { 10,
          &PIN10_SERVICE,
          &PIN10_ANA_INP_CHAR,
          &PIN10_ANA_OUT_CHAR,
          &PIN10_DIG_INP_CHAR,
          &PIN10_DIG_OUT_CHAR },
        { 11,
          &PIN11_SERVICE,
          &PIN11_ANA_INP_CHAR,
          &PIN11_ANA_OUT_CHAR,
          &PIN11_DIG_INP_CHAR,
          &PIN11_DIG_OUT_CHAR },
        { 12,
          &PIN12_SERVICE,
          &PIN12_ANA_INP_CHAR,
          &PIN12_ANA_OUT_CHAR,
          &PIN12_DIG_INP_CHAR,
          &PIN12_DIG_OUT_CHAR },
        { 13,
          &PIN13_SERVICE,
          &PIN13_ANA_INP_CHAR,
          &PIN13_ANA_OUT_CHAR,
          &PIN13_DIG_INP_CHAR,
          &PIN13_DIG_OUT_CHAR },
        { 14,
          &PIN14_SERVICE,
          &PIN14_ANA_INP_CHAR,
          &PIN14_ANA_OUT_CHAR,
          &PIN14_DIG_INP_CHAR,
          &PIN14_DIG_OUT_CHAR },
        { 15,
          &PIN15_SERVICE,
          &PIN15_ANA_INP_CHAR,
          &PIN15_ANA_OUT_CHAR,
          &PIN15_DIG_INP_CHAR,
          &PIN15_DIG_OUT_CHAR },
        { 16,
          &PIN16_SERVICE,
          &PIN16_ANA_INP_CHAR,
          &PIN16_ANA_OUT_CHAR,
          &PIN16_DIG_INP_CHAR,
          &PIN16_DIG_OUT_CHAR },
        { 17,
          &PIN17_SERVICE,
          &PIN17_ANA_INP_CHAR,
          &PIN17_ANA_OUT_CHAR,
          &PIN17_DIG_INP_CHAR,
          &PIN17_DIG_OUT_CHAR }
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

        // setup services and characteristics
        PIN02_SERVICE.addAttribute(PIN02_ANA_INP_CHAR);
        PIN02_SERVICE.addAttribute(PIN02_ANA_OUT_CHAR);
        PIN02_SERVICE.addAttribute(PIN02_DIG_INP_CHAR);
        PIN02_SERVICE.addAttribute(PIN02_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN02_SERVICE);

        PIN03_SERVICE.addAttribute(PIN03_ANA_INP_CHAR);
        PIN03_SERVICE.addAttribute(PIN03_ANA_OUT_CHAR);
        PIN03_SERVICE.addAttribute(PIN03_DIG_INP_CHAR);
        PIN03_SERVICE.addAttribute(PIN03_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN03_SERVICE);

        PIN04_SERVICE.addAttribute(PIN04_ANA_INP_CHAR);
        PIN04_SERVICE.addAttribute(PIN04_ANA_OUT_CHAR);
        PIN04_SERVICE.addAttribute(PIN04_DIG_INP_CHAR);
        PIN04_SERVICE.addAttribute(PIN04_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN04_SERVICE);

        PIN05_SERVICE.addAttribute(PIN05_ANA_INP_CHAR);
        PIN05_SERVICE.addAttribute(PIN05_ANA_OUT_CHAR);
        PIN05_SERVICE.addAttribute(PIN05_DIG_INP_CHAR);
        PIN05_SERVICE.addAttribute(PIN05_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN05_SERVICE);

        PIN06_SERVICE.addAttribute(PIN06_ANA_INP_CHAR);
        PIN06_SERVICE.addAttribute(PIN06_ANA_OUT_CHAR);
        PIN06_SERVICE.addAttribute(PIN06_DIG_INP_CHAR);
        PIN06_SERVICE.addAttribute(PIN06_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN06_SERVICE);

        PIN07_SERVICE.addAttribute(PIN07_ANA_INP_CHAR);
        PIN07_SERVICE.addAttribute(PIN07_ANA_OUT_CHAR);
        PIN07_SERVICE.addAttribute(PIN07_DIG_INP_CHAR);
        PIN07_SERVICE.addAttribute(PIN07_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN07_SERVICE);

        PIN08_SERVICE.addAttribute(PIN08_ANA_INP_CHAR);
        PIN08_SERVICE.addAttribute(PIN08_ANA_OUT_CHAR);
        PIN08_SERVICE.addAttribute(PIN08_DIG_INP_CHAR);
        PIN08_SERVICE.addAttribute(PIN08_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN08_SERVICE);

        PIN09_SERVICE.addAttribute(PIN09_ANA_INP_CHAR);
        PIN09_SERVICE.addAttribute(PIN09_ANA_OUT_CHAR);
        PIN09_SERVICE.addAttribute(PIN09_DIG_INP_CHAR);
        PIN09_SERVICE.addAttribute(PIN09_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN09_SERVICE);

        PIN10_SERVICE.addAttribute(PIN10_ANA_INP_CHAR);
        PIN10_SERVICE.addAttribute(PIN10_ANA_OUT_CHAR);
        PIN10_SERVICE.addAttribute(PIN10_DIG_INP_CHAR);
        PIN10_SERVICE.addAttribute(PIN10_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN10_SERVICE);

        PIN11_SERVICE.addAttribute(PIN11_ANA_INP_CHAR);
        PIN11_SERVICE.addAttribute(PIN11_ANA_OUT_CHAR);
        PIN11_SERVICE.addAttribute(PIN11_DIG_INP_CHAR);
        PIN11_SERVICE.addAttribute(PIN11_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN11_SERVICE);

        PIN12_SERVICE.addAttribute(PIN12_ANA_INP_CHAR);
        PIN12_SERVICE.addAttribute(PIN12_ANA_OUT_CHAR);
        PIN12_SERVICE.addAttribute(PIN12_DIG_INP_CHAR);
        PIN12_SERVICE.addAttribute(PIN12_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN12_SERVICE);

        PIN13_SERVICE.addAttribute(PIN13_ANA_INP_CHAR);
        PIN13_SERVICE.addAttribute(PIN13_ANA_OUT_CHAR);
        PIN13_SERVICE.addAttribute(PIN13_DIG_INP_CHAR);
        PIN13_SERVICE.addAttribute(PIN13_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN13_SERVICE);

        PIN14_SERVICE.addAttribute(PIN14_ANA_INP_CHAR);
        PIN14_SERVICE.addAttribute(PIN14_ANA_OUT_CHAR);
        PIN14_SERVICE.addAttribute(PIN14_DIG_INP_CHAR);
        PIN14_SERVICE.addAttribute(PIN14_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN14_SERVICE);

        PIN15_SERVICE.addAttribute(PIN15_ANA_INP_CHAR);
        PIN15_SERVICE.addAttribute(PIN15_ANA_OUT_CHAR);
        PIN15_SERVICE.addAttribute(PIN15_DIG_INP_CHAR);
        PIN15_SERVICE.addAttribute(PIN15_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN15_SERVICE);

        PIN16_SERVICE.addAttribute(PIN16_ANA_INP_CHAR);
        PIN16_SERVICE.addAttribute(PIN16_ANA_OUT_CHAR);
        PIN16_SERVICE.addAttribute(PIN16_DIG_INP_CHAR);
        PIN16_SERVICE.addAttribute(PIN16_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN16_SERVICE);

        PIN17_SERVICE.addAttribute(PIN17_ANA_INP_CHAR);
        PIN17_SERVICE.addAttribute(PIN17_ANA_OUT_CHAR);
        PIN17_SERVICE.addAttribute(PIN17_DIG_INP_CHAR);
        PIN17_SERVICE.addAttribute(PIN17_DIG_OUT_CHAR);
        LBLEPeripheral.addService(PIN17_SERVICE);


        LBLEPeripheral.begin();
        LBLEPeripheral.advertise(advertisement);
    }
};
#endif
