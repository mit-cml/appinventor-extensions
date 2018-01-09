#include <LBLE.h>
#include <LBLEPeriphral.h>

LBLEService           pinA0_service     ("ccb7be00-77bd-4349-86a6-14cc7673ca14");
LBLECharacteristicInt pinA0_ana_inp_char("ccb7be01-77bd-4349-86a6-14cc7673ca14", LBLE_READ | LBLE_WRITE);
LBLECharacteristicInt pinA0_ana_out_char("ccb7be02-77bd-4349-86a6-14cc7673ca14", LBLE_READ | LBLE_WRITE);
LBLECharacteristicInt pinA0_dig_inp_char("ccb7be03-77bd-4349-86a6-14cc7673ca14", LBLE_READ | LBLE_WRITE);
LBLECharacteristicInt pinA0_dig_out_char("ccb7be04-77bd-4349-86a6-14cc7673ca14", LBLE_READ | LBLE_WRITE);


void setup() {

    pinMode(LED_BUILTIN, OUTPUT);
    digitalWrite(LED_BUILTIN, LOW);
    Serial.begin(9600);

    pinMode(6, INPUT);
    pinMode(A0, INPUT);

    LBLE.begin();
    while (!LBLE.ready()) {
        delay(100);
    }
    Serial.println("BLE ready");

    Serial.print("Device Address = [");
    Serial.print(LBLE.getDeviceAddress());
    Serial.println("]");

    LBLEAdvertisementData advertisement;
    advertisement.configAsConnectableDevice("BLE Read Test");

    LBLEPeripheral.setName("BLE Read Test");

    pinA0_service.addAttribute(pinA0_ana_inp_char);
    pinA0_service.addAttribute(pinA0_ana_out_char);
    pinA0_service.addAttribute(pinA0_dig_inp_char);
    pinA0_service.addAttribute(pinA0_dig_out_char);

    LBLEPeripheral.addService(pinA0_service);

    LBLEPeripheral.begin();

    LBLEPeripheral.advertise(advertisement);
}

void loop() {

    if(LBLEPeripheral.connected() >= 1)
    {
        Serial.println("Connected");
        digitalWrite(7, LOW);
        while(LBLEPeripheral.connected() >= 1)
        {
            if (digitalRead(6))
            {
                Serial.println("disconnect all!");
                LBLEPeripheral.disconnectAll();
                break;
            }
            else
            {
                pinA0_ana_inp_char.setValue(analogRead(A0));
                delay(500);
            }
        }
    }
    else
    {
        Serial.println("No connected device");
        blink();
    }

}

void blink()
{
    digitalWrite(7, HIGH);
    delay(500);
    digitalWrite(7, LOW);
    delay(500);
}
