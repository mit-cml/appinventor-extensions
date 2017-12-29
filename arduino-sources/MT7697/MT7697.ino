#include <cassert>
#include <LBLEPeriphral.h>
#include <LBLE.h>

#include "constants.hpp"
#include "lble_setup.hpp"

void setup()
{
    setup_lble();
    pinMode(6, INPUT);
    pinMode(7, OUTPUT);
}

void loop()
{
    if (digitalRead(6))
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
        digitalWrite(7, HIGH);
    }
    else
    {
        for (int idx = 0; idx < PIN_UUID_PROFILES_SIZE; idx += 1)
        {
            auto& lble_ref = PIN_LBLE_PROFILES[idx];
            const int pin = lble_ref.pin;

            // set pin mode if requested
            if (lble_ref.mode_char->isWritten())
            {
                Serial.println("Written!!!");
                const int value = lble_ref.mode_char->getValue();
                lble_ref.mode = value;

                switch (value)
                {
                    case PIN_MODE_NONE:
                        break;
                    case PIN_MODE_ANALOG_READ:
                    case PIN_MODE_DIGITAL_READ:
                        {
                            pinMode(pin, INPUT);
                            break;
                        }
                    case PIN_MODE_ANALOG_WRITE:
                    case PIN_MODE_DIGITAL_WRITE:
                        {
                            pinMode(pin, OUTPUT);
                            break;
                        }
                    case PIN_MODE_SERVO:
                        {
                            // TODO
                            break;
                        }
                    default:
                        assert(0);
                }
            }

            // execute I/O according to its mode
            const int mode = lble_ref.mode;
            switch (mode)
            {
                case PIN_MODE_ANALOG_READ:
                    {
                        // TODO
                        break;
                    }
                case PIN_MODE_ANALOG_WRITE:
                    {
                        // TODO
                        break;
                    }
                case PIN_MODE_DIGITAL_READ:
                    {
                        // TODO
                        break;
                    }
                case PIN_MODE_DIGITAL_WRITE:
                    {
                        // TODO
                        break;
                    }
                case PIN_MODE_SERVO:
                    {
                        // TODO
                        break;
                    }
                default:
                    assert(mode == PIN_MODE_NONE);
            }
        }
    }
}
