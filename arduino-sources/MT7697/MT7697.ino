#include <cassert>
#include <LBLEPeriphral.h>
#include <LBLE.h>

#include "constants.hpp"
#include "lble_setup.hpp"

void setup()
{
    setup_lble();
}

void loop()
{
    for (int idx = 0; idx < GPIO_UUID_PROFILES_SIZE; idx += 1)
    {
        auto& lble_ref = GPIO_LBLE_PROFILES[idx];
        const int pin = lble_ref.pin;

        // set pin mode if requested
        if (lble_ref.mode_char->isWritten())
        {
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
