// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0
// author jerry73204@gmail.com (Lin, Hsiang-Jui)
// author az6980522@gmail.com (Yuan, Yu-Yuan)

#include <LBLEPeriphral.h>
#include <LBLE.h>
#include <Servo.h>

#include "constants.hpp"
#include "lble_setup.hpp"
LBLEPinSetup PIN_SETUP;
Servo servo[SERVO_SIZE];

void setup()
{
    pinMode(BTN_PIN, INPUT);
    pinMode(LED_PIN, OUTPUT);
    PIN_SETUP.begin();
}

void loop()
{
    if (digitalRead(BTN_PIN))
    {
        Serial.println("disconnect all!");
        LBLEPeripheral.disconnectAll();
    }
    if (LBLEPeripheral.connected() == 0)
    {
        Serial.println("No connected device");
        for(int pin = 0; pin < SERVO_SIZE; pin++)
            servo[pin].detach();
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
            char info[64];

            if (lble_ref.mode_char->isWritten())
            {
                lble_ref.mode = lble_ref.mode_char->getValue();

                switch (lble_ref.mode) 
                {
                    case MODE_ANALOG_INPUT:
                    {
                        pinMode(pin, INPUT);
                        servo[pin].detach();
                        
                        // int data = analogRead(pin);
                        int data = 123;
                        lble_ref.data_char->setValue(data);

                        sprintf(info, "Pin %d in analog input mode, send data %d", pin, data);
                        Serial.println(info);
                        break;
                    }
                    case MODE_DIGITAL_INPUT:
                    {
                        pinMode(pin, INPUT);
                        servo[pin].detach();

                        // int data = digitalRead(pin);
                        int data = 1;
                        lble_ref.data_char->setValue(data);

                        sprintf(info, "Pin %d in digital input mode, send data %d", pin, data);
                        Serial.println(info);
                        break;
                    }
                    case MODE_ANALOG_OUTPUT:
                        pinMode(pin, OUTPUT);
                        servo[pin].detach();

                        if (lble_ref.data_char->isWritten())
                        {
                            int data = lble_ref.data_char->getValue();
                            // data *= -1;
                            analogWrite(pin, data);
                            sprintf(info, "Pin %d in analog output mode, receive data %d", pin, data);
                            Serial.println(info);
                        }
                        break;
                    case MODE_DIGITAL_OUTPUT:
                        pinMode(pin, OUTPUT);
                        servo[pin].detach();
                        
                        if (lble_ref.data_char->isWritten())
                        {
                            int data = lble_ref.data_char->getValue();
                            // data *= -1;
                            analogWrite(pin, data);
                            sprintf(info, "Pin %d in digital output mode, receive data %d", pin, data);
                            Serial.println(info);
                        }
                        break;
                    case MODE_SERVO:
                        servo[pin].attach(pin);

                        if (lble_ref.data_char->isWritten())
                        {
                            int data = lble_ref.data_char->getValue();
                            // data *= -1;
                            servo[pin].write(data);
                            sprintf(info, "Pin %d in servo mode, receive data %d", pin, data);
                            Serial.println(info);
                        }
                        break;
                    default:
                        sprintf(info, "Pin %d in invalid mode", pin);
                        Serial.println(info);
                        break;
                }
            }

        }
    }
}

