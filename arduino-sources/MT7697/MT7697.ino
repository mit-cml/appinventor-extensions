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
            if (lble_ref.mode_char->isWritten())
            {
                lble_ref.mode = lble_ref.mode_char->getValue();
                switch (lble_ref.mode) {
                    case MODE_ANALOG_INPUT:
                    case MODE_DIGITAL_INPUT:
                        pinMode(pin, INPUT);
                        break;
                    case MODE_ANALOG_OUTPUT:
                    case MODE_DIGITAL_OUTPUT:
                        pinMode(pin, OUTPUT);
                        break;
                    case MODE_UNSET:
                        Serial.println("Mode unset");
                        break;
                    default:
                        Serial.println("Invalid mode!!!");
                        break;
                }
            }

            switch (lble_ref.mode) {
                case MODE_ANALOG_INPUT:
                {
                    int data = analogRead(pin);
                    lble_ref.data_char->setValue(data);
                    Serial.print("Send analog data: ");
                    Serial.println(data);
                    break;
                }
                case MODE_DIGITAL_INPUT:
                {
                    int data = digitalRead(pin);
                    lble_ref.data_char->setValue(data);
                    Serial.print("Send digital data: ");
                    Serial.println(data);
                    break;
                }
                case MODE_ANALOG_OUTPUT:
                    if (lble_ref.data_char->isWritten())
                    {
                        int data = lble_ref.data_char->getValue();
                        data *= -1;
                        analogWrite(pin, data);
                        Serial.print("Receive analog data: ");
                        Serial.println(data);
                    }
                    break;
                case MODE_DIGITAL_OUTPUT:
                    if (lble_ref.data_char->isWritten())
                    {
                        int data = lble_ref.data_char->getValue();
                        data *= -1;
                        analogWrite(pin, data);
                        Serial.print("Receive digital data: ");
                        Serial.println(data);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
