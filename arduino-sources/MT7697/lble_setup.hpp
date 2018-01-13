#ifndef __MT7697_LBLE_SETUP_HPP__
#define __MT7697_LBLE_SETUP_HPP__

#include <LBLEPeriphral.h>
#include <LBLE.h>

const int PIN_MODE_ANALOG_READ   = 0x00;
const int PIN_MODE_ANALOG_WRITE  = 0x01;
const int PIN_MODE_DIGITAL_READ  = 0x02;
const int PIN_MODE_DIGITAL_WRITE = 0x03;
const int PIN_MODE_SERVO         = 0x04;
const int PIN_MODE_NONE          = 0xFF;

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

// TODO I2C impl.
/*
LBLEService I2C_SERVICE();
LBLECharacteristicInt I2C_READ_CHARACTERISTIC();
LBLECharacteristicInt I2C_WRITE_CHARACTERISTIC();
*/

static LBLEService           PIN02_SERVICE(PIN02_SERVICE_UUID);
static LBLECharacteristicInt PIN02_ANA_INP_CHAR(PIN02_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_ANA_OUT_CHAR(PIN02_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_DIG_INP_CHAR(PIN02_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_DIG_OUT_CHAR(PIN02_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

// static LBLEService           PIN03_SERVICE(PIN03_SERVICE_UUID);
// static LBLECharacteristicInt PIN03_ANA_INP_CHAR(PIN03_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN03_ANA_OUT_CHAR(PIN03_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN03_DIG_INP_CHAR(PIN03_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN03_DIG_OUT_CHAR(PIN03_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN04_SERVICE(PIN04_SERVICE_UUID);
// static LBLECharacteristicInt PIN04_ANA_INP_CHAR(PIN04_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN04_ANA_OUT_CHAR(PIN04_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN04_DIG_INP_CHAR(PIN04_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN04_DIG_OUT_CHAR(PIN04_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN05_SERVICE(PIN05_SERVICE_UUID);
// static LBLECharacteristicInt PIN05_ANA_INP_CHAR(PIN05_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN05_ANA_OUT_CHAR(PIN05_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN05_DIG_INP_CHAR(PIN05_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN05_DIG_OUT_CHAR(PIN05_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN06_SERVICE(PIN06_SERVICE_UUID);
// static LBLECharacteristicInt PIN06_ANA_INP_CHAR(PIN06_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN06_ANA_OUT_CHAR(PIN06_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN06_DIG_INP_CHAR(PIN06_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN06_DIG_OUT_CHAR(PIN06_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN07_SERVICE(PIN07_SERVICE_UUID);
// static LBLECharacteristicInt PIN07_ANA_INP_CHAR(PIN07_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN07_ANA_OUT_CHAR(PIN07_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN07_DIG_INP_CHAR(PIN07_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN07_DIG_OUT_CHAR(PIN07_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN08_SERVICE(PIN08_SERVICE_UUID);
// static LBLECharacteristicInt PIN08_ANA_INP_CHAR(PIN08_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN08_ANA_OUT_CHAR(PIN08_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN08_DIG_INP_CHAR(PIN08_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN08_DIG_OUT_CHAR(PIN08_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN09_SERVICE(PIN09_SERVICE_UUID);
// static LBLECharacteristicInt PIN09_ANA_INP_CHAR(PIN09_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN09_ANA_OUT_CHAR(PIN09_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN09_DIG_INP_CHAR(PIN09_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN09_DIG_OUT_CHAR(PIN09_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN10_SERVICE(PIN10_SERVICE_UUID);
// static LBLECharacteristicInt PIN10_ANA_INP_CHAR(PIN10_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN10_ANA_OUT_CHAR(PIN10_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN10_DIG_INP_CHAR(PIN10_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN10_DIG_OUT_CHAR(PIN10_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN11_SERVICE(PIN11_SERVICE_UUID);
// static LBLECharacteristicInt PIN11_ANA_INP_CHAR(PIN11_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN11_ANA_OUT_CHAR(PIN11_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN11_DIG_INP_CHAR(PIN11_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN11_DIG_OUT_CHAR(PIN11_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN12_SERVICE(PIN12_SERVICE_UUID);
// static LBLECharacteristicInt PIN12_ANA_INP_CHAR(PIN12_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN12_ANA_OUT_CHAR(PIN12_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN12_DIG_INP_CHAR(PIN12_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN12_DIG_OUT_CHAR(PIN12_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN13_SERVICE(PIN13_SERVICE_UUID);
// static LBLECharacteristicInt PIN13_ANA_INP_CHAR(PIN13_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN13_ANA_OUT_CHAR(PIN13_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN13_DIG_INP_CHAR(PIN13_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN13_DIG_OUT_CHAR(PIN13_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN14_SERVICE(PIN14_SERVICE_UUID);
// static LBLECharacteristicInt PIN14_ANA_INP_CHAR(PIN14_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN14_ANA_OUT_CHAR(PIN14_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN14_DIG_INP_CHAR(PIN14_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN14_DIG_OUT_CHAR(PIN14_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN15_SERVICE(PIN15_SERVICE_UUID);
// static LBLECharacteristicInt PIN15_ANA_INP_CHAR(PIN15_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN15_ANA_OUT_CHAR(PIN15_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN15_DIG_INP_CHAR(PIN15_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN15_DIG_OUT_CHAR(PIN15_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN16_SERVICE(PIN16_SERVICE_UUID);
// static LBLECharacteristicInt PIN16_ANA_INP_CHAR(PIN16_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN16_ANA_OUT_CHAR(PIN16_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN16_DIG_INP_CHAR(PIN16_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN16_DIG_OUT_CHAR(PIN16_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
//
// static LBLEService           PIN17_SERVICE(PIN17_SERVICE_UUID);
// static LBLECharacteristicInt PIN17_ANA_INP_CHAR(PIN17_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN17_ANA_OUT_CHAR(PIN17_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN17_DIG_INP_CHAR(PIN17_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
// static LBLECharacteristicInt PIN17_DIG_OUT_CHAR(PIN17_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);


static pin_lble_profile PIN_LBLE_PROFILES[] = {
    { 2,
      &PIN02_SERVICE, 
      &PIN02_ANA_INP_CHAR,
      &PIN02_ANA_OUT_CHAR, 
      &PIN02_DIG_INP_CHAR, 
      &PIN02_DIG_OUT_CHAR },
    // { 3,
    //   &PIN03_SERVICE,
    //   &PIN03_ANA_INP_CHAR,
    //   &PIN03_ANA_OUT_CHAR,
    //   &PIN03_DIG_INP_CHAR,
    //   &PIN03_DIG_OUT_CHAR },
    // { 4,
    //   &PIN04_SERVICE,
    //   &PIN04_ANA_INP_CHAR,
    //   &PIN04_ANA_OUT_CHAR,
    //   &PIN04_DIG_INP_CHAR,
    //   &PIN04_DIG_OUT_CHAR },
    // { 5,
    //   &PIN05_SERVICE,
    //   &PIN05_ANA_INP_CHAR,
    //   &PIN05_ANA_OUT_CHAR,
    //   &PIN05_DIG_INP_CHAR,
    //   &PIN05_DIG_OUT_CHAR },
    // { 6,
    //   &PIN06_SERVICE,
    //   &PIN06_ANA_INP_CHAR,
    //   &PIN06_ANA_OUT_CHAR,
    //   &PIN06_DIG_INP_CHAR,
    //   &PIN06_DIG_OUT_CHAR },
    // { 7,
    //   &PIN07_SERVICE,
    //   &PIN07_ANA_INP_CHAR,
    //   &PIN07_ANA_OUT_CHAR,
    //   &PIN07_DIG_INP_CHAR,
    //   &PIN07_DIG_OUT_CHAR },
    // { 8,
    //   &PIN08_SERVICE,
    //   &PIN08_ANA_INP_CHAR,
    //   &PIN08_ANA_OUT_CHAR,
    //   &PIN08_DIG_INP_CHAR,
    //   &PIN08_DIG_OUT_CHAR },
    // { 9,
    //   &PIN09_SERVICE,
    //   &PIN09_ANA_INP_CHAR,
    //   &PIN09_ANA_OUT_CHAR,
    //   &PIN09_DIG_INP_CHAR,
    //   &PIN09_DIG_OUT_CHAR },
    // { 10,
    //   &PIN10_SERVICE,
    //   &PIN10_ANA_INP_CHAR,
    //   &PIN10_ANA_OUT_CHAR,
    //   &PIN10_DIG_INP_CHAR,
    //   &PIN10_DIG_OUT_CHAR },
    // { 11,
    //   &PIN11_SERVICE,
    //   &PIN11_ANA_INP_CHAR,
    //   &PIN11_ANA_OUT_CHAR,
    //   &PIN11_DIG_INP_CHAR,
    //   &PIN11_DIG_OUT_CHAR },
    // { 12,
    //   &PIN12_SERVICE,
    //   &PIN12_ANA_INP_CHAR,
    //   &PIN12_ANA_OUT_CHAR,
    //   &PIN12_DIG_INP_CHAR,
    //   &PIN12_DIG_OUT_CHAR },
    // { 13,
    //   &PIN13_SERVICE,
    //   &PIN13_ANA_INP_CHAR,
    //   &PIN13_ANA_OUT_CHAR,
    //   &PIN13_DIG_INP_CHAR,
    //   &PIN13_DIG_OUT_CHAR },
    // { 14,
    //   &PIN14_SERVICE,
    //   &PIN14_ANA_INP_CHAR,
    //   &PIN14_ANA_OUT_CHAR,
    //   &PIN14_DIG_INP_CHAR,
    //   &PIN14_DIG_OUT_CHAR },
    // { 15,
    //   &PIN15_SERVICE,
    //   &PIN15_ANA_INP_CHAR,
    //   &PIN15_ANA_OUT_CHAR,
    //   &PIN15_DIG_INP_CHAR,
    //   &PIN15_DIG_OUT_CHAR },
    // { 16,
    //   &PIN16_SERVICE,
    //   &PIN16_ANA_INP_CHAR,
    //   &PIN16_ANA_OUT_CHAR,
    //   &PIN16_DIG_INP_CHAR,
    //   &PIN16_DIG_OUT_CHAR },
    // { 17,
    //   &PIN17_SERVICE,
    //   &PIN17_ANA_INP_CHAR,
    //   &PIN17_ANA_OUT_CHAR,
    //   &PIN17_DIG_INP_CHAR,
    //   &PIN17_DIG_OUT_CHAR }
};

void setup_lble();

#endif
