#include <LBLE.h>
#include <LBLEPeriphral.h>

LBLEService           pin7_service("ccb7be00-77bd-4349-86a6-14cc7673ca07");
LBLECharacteristicInt pin7_ana_inp_char("ccb7be01-77bd-4349-86a6-14cc7673ca07", LBLE_READ | LBLE_WRITE);
LBLECharacteristicInt pin7_ana_out_char("ccb7be02-77bd-4349-86a6-14cc7673ca07", LBLE_READ | LBLE_WRITE);
LBLECharacteristicInt pin7_dig_inp_char("ccb7be03-77bd-4349-86a6-14cc7673ca07", LBLE_READ | LBLE_WRITE);
LBLECharacteristicInt pin7_dig_out_char("ccb7be04-77bd-4349-86a6-14cc7673ca07", LBLE_READ | LBLE_WRITE);

void setup() {

    pinMode(LED_BUILTIN, OUTPUT);
    digitalWrite(LED_BUILTIN, LOW);
    Serial.begin(9600);

    pinMode(6, INPUT);

    LBLE.begin();
    while (!LBLE.ready()) {
        delay(100);
    }
    Serial.println("BLE ready");

    Serial.print("Device Address = [");
    Serial.print(LBLE.getDeviceAddress());
    Serial.println("]");

    LBLEAdvertisementData advertisement;
    advertisement.configAsConnectableDevice("BLE LED");

    LBLEPeripheral.setName("BLE LED");

    pin7_service.addAttribute(pin7_ana_inp_char);
    pin7_service.addAttribute(pin7_ana_out_char);
    pin7_service.addAttribute(pin7_dig_inp_char);
    pin7_service.addAttribute(pin7_dig_out_char);

    LBLEPeripheral.addService(pin7_service);

    LBLEPeripheral.begin();

    LBLEPeripheral.advertise(advertisement);
}

void loop() {

    if(LBLEPeripheral.connected() >= 1)
    {
        Serial.println("Conneted");
        digitalWrite(7, LOW);
        while(LBLEPeripheral.connected() >= 1)
        {
            if (digitalRead(6))
            {
                Serial.println("disconnect all!");
                LBLEPeripheral.disconnectAll();
                break;
            }
            if (pin7_dig_out_char.isWritten()) 
            {
                const int value = pin7_dig_out_char.getValue();
                Serial.print("value: ");
                Serial.println(value);
                switch (value) {
                    case 1:
                        digitalWrite(LED_BUILTIN, HIGH);
                        break;
                    case 0:
                        digitalWrite(LED_BUILTIN, LOW);
                        break;
                    default:
                        Serial.println("Unknown value written");
                        break;
                }
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
