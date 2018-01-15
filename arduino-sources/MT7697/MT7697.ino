#include <LBLEPeriphral.h>
#include <LBLE.h>

#include "constants.hpp"
#include "lble_setup.hpp"
LBLEPinSetup PIN_SETUP;

void setup()
{
    pinMode(BTN_PIN, INPUT);
    pinMode(LED_PIN, OUTPUT);
    PIN_SETUP.begin();
}

void loop()
{
    delay(500);

    if (digitalRead(BTN_PIN))
    {
        Serial.println("disconnect all!");
        LBLEPeripheral.disconnectAll();
    }
    if (LBLEPeripheral.connected() == 0)
    {
        Serial.println("No connected device");
        while(LBLEPeripheral.connected() == 0)
        {
            digitalWrite(LED_PIN, HIGH);
            delay(100);
            digitalWrite(LED_PIN, LOW);
            delay(100);
        }
        Serial.println("Connected, press USR BTN to disconnect");
        digitalWrite(LED_PIN, LOW);
    }
    else
    {
        for (int idx = 0; idx < PIN_UUID_PROFILES_SIZE; idx += 1)
        {
            auto& lble_ref = PIN_SETUP.PIN_LBLE_PROFILES[idx];
            const int pin = lble_ref.pin;


            if (pin != LED_PIN && pin == 14)
            {
                pinMode(pin, INPUT);
                if (pin >= 14 && pin <= 17)
                {
                    int value = analogRead(pin);
                    Serial.print("ana_inp_char ");
                    Serial.print(pin);
                    Serial.print(" write value: ");
                    Serial.println(value);
                    lble_ref.ana_inp_char->setValue(value);
                }
                // int value = digitalRead(pin);
                // Serial.print("dig_inp_char ");
                // Serial.print(pin);
                // Serial.print(" write value: ");
                // Serial.println(value);
                // lble_ref.dig_inp_char->setValue(value);
            }

            // if (pin == BTN_PIN)
            // {
            //     continue;
            // }
            // else
            // {
            //     if (lble_ref.ana_out_char->isWritten())
            //     {
            //         pinMode(pin, OUTPUT);
            //         int value = lble_ref.ana_out_char->getValue();
            //         Serial.print("ana_out_char");
            //         Serial.print(pin);
            //         Serial.print(" get value: ");
            //         Serial.println(value);
            //         analogWrite(pin, value);
            //     }
            //     else if (lble_ref.dig_out_char->isWritten())
            //     {
            //         pinMode(pin, OUTPUT);
            //         int value = lble_ref.dig_out_char->getValue();
            //         Serial.print("dig_out_char ");
            //         Serial.print(pin);
            //         Serial.print(" get value: ");
            //         Serial.println(value);
            //         digitalWrite(pin, value);
            //     }
            // }
        }
    }
}
