#ifndef __MT7697_CONSTANTS_HPP__
#define __MT7697_CONSTANTS_HPP__

#define PIN_UUID_PROFILES_SIZE 16
#define BTN_PIN 6
#define LED_PIN 7

// The MT7697 compiler creates seperate static linked libraries
// and link to together.
// We need to "extern" every thing to make the compiler happy.


static const char DEVICE_NAME[] = "MT7697 for AI2";

/* TODO I2C support */
// static const char I2C_SERVICE_UUID[] = "00f81600-1db7-4f22-a187-b25005f98dcd";
// static const char I2C_READ_CHARACTERISTIC_UUID[] = "00f81600-1db7-4f22-a187-b25005f98dcd";
// static const char I2C_WRITE_CHARACTERISTIC_UUID[] = "00f81601-1db7-4f22-a187-b25005f98dcd";

static const char PIN02_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca02";

static const char PIN03_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca03";

static const char PIN04_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca04";

static const char PIN05_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca05";

static const char PIN06_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca06";

static const char PIN07_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca07";

static const char PIN08_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca08";

static const char PIN09_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca09";

static const char PIN10_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca10";

static const char PIN11_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca11";

static const char PIN12_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca12";

static const char PIN13_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca13";

static const char PIN14_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca14";

static const char PIN15_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca15";

static const char PIN16_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca16";

static const char PIN17_SERVICE_UUID[]      = "ccb7be00-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_ANA_INP_CHAR_UUID[] = "ccb7be01-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_ANA_OUT_CHAR_UUID[] = "ccb7be02-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_DIG_INP_CHAR_UUID[] = "ccb7be03-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_DIG_OUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca17";

#endif
