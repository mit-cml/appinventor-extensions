#include <cassert>
#include <LBLEPeriphral.h>
#include <LBLE.h>

#include "constants.hpp"
// #include "lble_setup.hpp"

static LBLEService           PIN02_SERVICE(PIN02_SERVICE_UUID);
static LBLECharacteristicInt PIN02_ANA_INP_CHAR(PIN02_ANA_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_ANA_OUT_CHAR(PIN02_ANA_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_DIG_INP_CHAR(PIN02_DIG_INP_CHAR_UUID, LBLE_READ | LBLE_WRITE);
static LBLECharacteristicInt PIN02_DIG_OUT_CHAR(PIN02_DIG_OUT_CHAR_UUID, LBLE_READ | LBLE_WRITE);

struct pin_lble_profile
{
    int pin;
    LBLEService *service;
    LBLECharacteristicInt *ana_inp_char;
    LBLECharacteristicInt *ana_out_char;
    LBLECharacteristicInt *dig_inp_char;
    LBLECharacteristicInt *dig_out_char;
};

static pin_lble_profile PIN_LBLE_PROFILES[] = {
    { 2,
      &PIN02_SERVICE, 
      &PIN02_ANA_INP_CHAR,
      &PIN02_ANA_OUT_CHAR, 
      &PIN02_DIG_INP_CHAR, 
      &PIN02_DIG_OUT_CHAR }
};

void setup()
{
    // setup_lble();
    pinMode(6, INPUT);
    pinMode(7, OUTPUT);

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

    LBLEPeripheral.begin();
    LBLEPeripheral.advertise(advertisement);
}

void loop()
{
    if(digitalRead(6))
    {
        Serial.println("disconnect all!");
        LBLEPeripheral.disconnectAll();
    }
    if(LBLEPeripheral.connected() == 0)
    {
        Serial.println("No connected device");
        while(LBLEPeripheral.connected() == 0)
        {
            digitalWrite(7, HIGH);
            delay(100);
            digitalWrite(7, LOW);
            delay(100);
        }
        Serial.println("Connected, press USR BTN to disconnect");
        digitalWrite(7, LOW);
    }
    else
    {
        // if (PIN02_ANA_INP_CHAR.isWritten())
        // {
        //     Serial.println("ana_inp written!!!");
        //     Serial.println(PIN02_ANA_INP_CHAR.getValue());
        // }
        // auto& lble_ref = PIN_LBLE_PROFILES[5];
        // const int pin = lble_ref.pin;
        // Serial.print("Pin: ");
        // Serial.println(lble_ref.pin);
        for (int idx = 0; idx < PIN_UUID_PROFILES_SIZE; idx += 1)
        {
            auto& lble_ref = PIN_LBLE_PROFILES[idx];
            const int pin = lble_ref.pin;
            if (lble_ref.ana_inp_char->isWritten())
                Serial.println("ana_inp written!!!");
            if (lble_ref.ana_out_char->isWritten())
                Serial.println("ana_out written!!!");
            if (lble_ref.dig_inp_char->isWritten())
                Serial.println("dig_inp written!!!");
            if (lble_ref.dig_out_char->isWritten())
                Serial.println("dig_out written!!!");
        }
        // for (int idx = 0; idx < PIN_UUID_PROFILES_SIZE; idx += 1)
        // {
        //     auto& lble_ref = PIN_LBLE_PROFILES[idx];
        //     const int pin = lble_ref.pin;
        //
        //     // set pin mode if requested
            // {
                // const int value = lble_ref.dig_out_char->getValue();
                //
                // Serial.print("Pin: ");
                // Serial.print(pin);
                // Serial.print(" is written with value:  ");
                // Serial.println(value);

                // lble_ref.mode = value;
                //
                // switch (value)
                // {
                //     case PIN_MODE_NONE:
                //         break;
                //     case PIN_MODE_ANALOG_READ:
                //     case PIN_MODE_DIGITAL_READ:
                //         {
                //             pinMode(pin, INPUT);
                //             break;
                //         }
                //     case PIN_MODE_ANALOG_WRITE:
                //     case PIN_MODE_DIGITAL_WRITE:
                //         {
                //             pinMode(pin, OUTPUT);
                //             break;
                //         }
                //     case PIN_MODE_SERVO:
                //         {
                //             // TODO
                //             break;
                //         }
                //     default:
                //         assert(0);
                // }
            // }

            // execute I/O according to its mode
            // const int mode = lble_ref.mode;
            // switch (mode)
            // {
            //     case PIN_MODE_ANALOG_READ:
            //         {
            //             // TODO
            //             break;
            //         }
            //     case PIN_MODE_ANALOG_WRITE:
            //         {
            //             // TODO
            //             break;
            //         }
            //     case PIN_MODE_DIGITAL_READ:
            //         {
            //             // TODO
            //             break;
            //         }
            //     case PIN_MODE_DIGITAL_WRITE:
            //         {
            //             // TODO
            //             break;
            //         }
            //     case PIN_MODE_SERVO:
            //         {
            //             // TODO
            //             break;
            //         }
            //     default:
            //         assert(mode == PIN_MODE_NONE);
            // }
        // }
    }
}
