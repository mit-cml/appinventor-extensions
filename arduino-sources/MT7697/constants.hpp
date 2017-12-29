#ifndef __MT7697_CONSTANTS_HPP__
#define __MT7697_CONSTANTS_HPP__

#define PIN_UUID_PROFILES_SIZE 16

// The MT7697 compiler creates seperate static linked libraries
// and link to together.
// We need to "extern" every thing to make the compiler happy.


static const char DEVICE_NAME[] = "MT7697 for AI2";

/* TODO I2C support */
// static const char I2C_SERVICE_UUID[] = "00f81600-1db7-4f22-a187-b25005f98dcd";
// static const char I2C_READ_CHARACTERISTIC_UUID[] = "00f81600-1db7-4f22-a187-b25005f98dcd";
// static const char I2C_WRITE_CHARACTERISTIC_UUID[] = "00f81601-1db7-4f22-a187-b25005f98dcd";

static const char PIN02_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca02";
static const char PIN02_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca02";

static const char PIN03_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca03";
static const char PIN03_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca03";

static const char PIN04_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca04";
static const char PIN04_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca04";

static const char PIN05_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca05";
static const char PIN05_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca05";

static const char PIN06_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca06";
static const char PIN06_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca06";

static const char PIN07_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca07";
static const char PIN07_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca07";

static const char PIN08_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca08";
static const char PIN08_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca08";

static const char PIN09_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca09";
static const char PIN09_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca09";

static const char PIN10_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca10";
static const char PIN10_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca10";

static const char PIN11_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca11";
static const char PIN11_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca11";

static const char PIN12_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca12";
static const char PIN12_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca12";

static const char PIN13_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca13";
static const char PIN13_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca13";

static const char PIN14_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca14";
static const char PIN14_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca14";

static const char PIN15_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca15";
static const char PIN15_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca15";

static const char PIN16_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca16";
static const char PIN16_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca16";

static const char PIN17_SERVICE_UUID[]             = "ccb7be00-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_ANALOG_INPUT_CHAR_UUID[]   = "ccb7be01-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_ANALOG_OUTPUT_CHAR_UUID[]  = "ccb7be02-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_DIGITAL_INPUT_CHAR_UUID[]  = "ccb7be03-77bd-4349-86a6-14cc7673ca17";
static const char PIN17_DIGITAL_OUTPUT_CHAR_UUID[] = "ccb7be04-77bd-4349-86a6-14cc7673ca17";

#endif
