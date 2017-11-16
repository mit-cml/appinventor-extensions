#ifndef __MT7697_CONSTANTS_HPP__
#define __MT7697_CONSTANTS_HPP__

#define GPIO_UUID_PROFILES_SIZE 15

// The MT7697 compiler creates seperate static linked libraries
// and link to together.
// We need to "extern" every thing to make the compiler happy.

struct gpio_uuid_profile
{
    int pin;
    const char *service_uuid;
    const char *mode_char_uuid;
    const char *data_char_uuid;
};

extern const char *DEVICE_NAME;

extern const char *I2C_SERVICE;
extern const char *I2C_READ_CHARACTERISTIC;
extern const char *I2C_WRITE_CHARACTERISTIC;

extern const char *GPIO00_SERVICE;
extern const char *GPIO00_MODE_CHARACTERISTIC;
extern const char *GPIO00_DATA_CHARACTERISTIC;

extern const char *GPIO02_SERVICE;
extern const char *GPIO02_MODE_CHARACTERISTIC;
extern const char *GPIO02_DATA_CHARACTERISTIC;

extern const char *GPIO03_SERVICE;
extern const char *GPIO03_MODE_CHARACTERISTIC;
extern const char *GPIO03_DATA_CHARACTERISTIC;

extern const char *GPIO29_SERVICE;
extern const char *GPIO29_MODE_CHARACTERISTIC;
extern const char *GPIO29_DATA_CHARACTERISTIC;

extern const char *GPIO30_SERVICE;
extern const char *GPIO30_MODE_CHARACTERISTIC;
extern const char *GPIO30_DATA_CHARACTERISTIC;

extern const char *GPIO31_SERVICE;
extern const char *GPIO31_MODE_CHARACTERISTIC;
extern const char *GPIO31_DATA_CHARACTERISTIC;

extern const char *GPIO32_SERVICE;
extern const char *GPIO32_MODE_CHARACTERISTIC;
extern const char *GPIO32_DATA_CHARACTERISTIC;

extern const char *GPIO33_SERVICE;
extern const char *GPIO33_MODE_CHARACTERISTIC;
extern const char *GPIO33_DATA_CHARACTERISTIC;

extern const char *GPIO34_SERVICE;
extern const char *GPIO34_MODE_CHARACTERISTIC;
extern const char *GPIO34_DATA_CHARACTERISTIC;

extern const char *GPIO38_SERVICE;
extern const char *GPIO38_MODE_CHARACTERISTIC;
extern const char *GPIO38_DATA_CHARACTERISTIC;

extern const char *GPIO39_SERVICE;
extern const char *GPIO39_MODE_CHARACTERISTIC;
extern const char *GPIO39_DATA_CHARACTERISTIC;

extern const char *GPIO57_SERVICE;
extern const char *GPIO57_MODE_CHARACTERISTIC;
extern const char *GPIO57_DATA_CHARACTERISTIC;

extern const char *GPIO58_SERVICE;
extern const char *GPIO58_MODE_CHARACTERISTIC;
extern const char *GPIO58_DATA_CHARACTERISTIC;

extern const char *GPIO59_SERVICE;
extern const char *GPIO59_MODE_CHARACTERISTIC;
extern const char *GPIO59_DATA_CHARACTERISTIC;

extern const char *GPIO60_SERVICE;
extern const char *GPIO60_MODE_CHARACTERISTIC;
extern const char *GPIO60_DATA_CHARACTERISTIC;

extern struct gpio_uuid_profile GPIO_UUID_PROFILES[GPIO_UUID_PROFILES_SIZE];
// extern struct gpio_uuid_profile GPIO_UUID_PROFILES[15];

#endif
