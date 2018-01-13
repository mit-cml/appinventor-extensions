#include <LBLEPeriphral.h>
#include <LBLE.h>
#include "constants.hpp"
#include "lble_setup.hpp"
#include "Arduino.h"

void setup_lble()
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

    // PIN03_SERVICE.addAttribute(PIN03_ANA_INP_CHAR);
    // PIN03_SERVICE.addAttribute(PIN03_ANA_OUT_CHAR);
    // PIN03_SERVICE.addAttribute(PIN03_DIG_INP_CHAR);
    // PIN03_SERVICE.addAttribute(PIN03_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN03_SERVICE);
    //
    // PIN04_SERVICE.addAttribute(PIN04_ANA_INP_CHAR);
    // PIN04_SERVICE.addAttribute(PIN04_ANA_OUT_CHAR);
    // PIN04_SERVICE.addAttribute(PIN04_DIG_INP_CHAR);
    // PIN04_SERVICE.addAttribute(PIN04_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN04_SERVICE);
    //
    // PIN05_SERVICE.addAttribute(PIN05_ANA_INP_CHAR);
    // PIN05_SERVICE.addAttribute(PIN05_ANA_OUT_CHAR);
    // PIN05_SERVICE.addAttribute(PIN05_DIG_INP_CHAR);
    // PIN05_SERVICE.addAttribute(PIN05_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN05_SERVICE);
    //
    // PIN06_SERVICE.addAttribute(PIN06_ANA_INP_CHAR);
    // PIN06_SERVICE.addAttribute(PIN06_ANA_OUT_CHAR);
    // PIN06_SERVICE.addAttribute(PIN06_DIG_INP_CHAR);
    // PIN06_SERVICE.addAttribute(PIN06_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN06_SERVICE);
    //
    // PIN07_SERVICE.addAttribute(PIN07_ANA_INP_CHAR);
    // PIN07_SERVICE.addAttribute(PIN07_ANA_OUT_CHAR);
    // PIN07_SERVICE.addAttribute(PIN07_DIG_INP_CHAR);
    // PIN07_SERVICE.addAttribute(PIN07_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN07_SERVICE);
    //
    // PIN08_SERVICE.addAttribute(PIN08_ANA_INP_CHAR);
    // PIN08_SERVICE.addAttribute(PIN08_ANA_OUT_CHAR);
    // PIN08_SERVICE.addAttribute(PIN08_DIG_INP_CHAR);
    // PIN08_SERVICE.addAttribute(PIN08_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN08_SERVICE);
    //
    // PIN09_SERVICE.addAttribute(PIN09_ANA_INP_CHAR);
    // PIN09_SERVICE.addAttribute(PIN09_ANA_OUT_CHAR);
    // PIN09_SERVICE.addAttribute(PIN09_DIG_INP_CHAR);
    // PIN09_SERVICE.addAttribute(PIN09_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN09_SERVICE);
    //
    // PIN10_SERVICE.addAttribute(PIN10_ANA_INP_CHAR);
    // PIN10_SERVICE.addAttribute(PIN10_ANA_OUT_CHAR);
    // PIN10_SERVICE.addAttribute(PIN10_DIG_INP_CHAR);
    // PIN10_SERVICE.addAttribute(PIN10_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN10_SERVICE);
    //
    // PIN11_SERVICE.addAttribute(PIN11_ANA_INP_CHAR);
    // PIN11_SERVICE.addAttribute(PIN11_ANA_OUT_CHAR);
    // PIN11_SERVICE.addAttribute(PIN11_DIG_INP_CHAR);
    // PIN11_SERVICE.addAttribute(PIN11_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN11_SERVICE);
    //
    // PIN12_SERVICE.addAttribute(PIN12_ANA_INP_CHAR);
    // PIN12_SERVICE.addAttribute(PIN12_ANA_OUT_CHAR);
    // PIN12_SERVICE.addAttribute(PIN12_DIG_INP_CHAR);
    // PIN12_SERVICE.addAttribute(PIN12_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN12_SERVICE);
    //
    // PIN13_SERVICE.addAttribute(PIN13_ANA_INP_CHAR);
    // PIN13_SERVICE.addAttribute(PIN13_ANA_OUT_CHAR);
    // PIN13_SERVICE.addAttribute(PIN13_DIG_INP_CHAR);
    // PIN13_SERVICE.addAttribute(PIN13_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN13_SERVICE);
    //
    // PIN14_SERVICE.addAttribute(PIN14_ANA_INP_CHAR);
    // PIN14_SERVICE.addAttribute(PIN14_ANA_OUT_CHAR);
    // PIN14_SERVICE.addAttribute(PIN14_DIG_INP_CHAR);
    // PIN14_SERVICE.addAttribute(PIN14_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN14_SERVICE);
    //
    // PIN15_SERVICE.addAttribute(PIN15_ANA_INP_CHAR);
    // PIN15_SERVICE.addAttribute(PIN15_ANA_OUT_CHAR);
    // PIN15_SERVICE.addAttribute(PIN15_DIG_INP_CHAR);
    // PIN15_SERVICE.addAttribute(PIN15_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN15_SERVICE);
    //
    // PIN16_SERVICE.addAttribute(PIN16_ANA_INP_CHAR);
    // PIN16_SERVICE.addAttribute(PIN16_ANA_OUT_CHAR);
    // PIN16_SERVICE.addAttribute(PIN16_DIG_INP_CHAR);
    // PIN16_SERVICE.addAttribute(PIN16_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN16_SERVICE);
    //
    // PIN17_SERVICE.addAttribute(PIN17_ANA_INP_CHAR);
    // PIN17_SERVICE.addAttribute(PIN17_ANA_OUT_CHAR);
    // PIN17_SERVICE.addAttribute(PIN17_DIG_INP_CHAR);
    // PIN17_SERVICE.addAttribute(PIN17_DIG_OUT_CHAR);
    // LBLEPeripheral.addService(PIN17_SERVICE);

    LBLEPeripheral.begin();
    LBLEPeripheral.advertise(advertisement);
}
