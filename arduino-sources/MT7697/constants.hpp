#ifndef __MT7697_CONSTANTS_HPP__
#define __MT7697_CONSTANTS_HPP__

#define GPIO_UUID_PROFILES_SIZE 15

// The MT7697 compiler creates seperate static linked libraries
// and link to together.
// We need to "extern" every thing to make the compiler happy.

// struct gpio_uuid_profile
// {
//     int pin;
//     const char *service_uuid;
//     const char *mode_char_uuid;
//     const char *data_char_uuid;
// };

static const char DEVICE_NAME[] = "MT7697 for AI2";

/* TODO I2C support */
// static const char I2C_SERVICE_UUID[] = "00f81600-1db7-4f22-a187-b25005f98dcd";
// static const char I2C_READ_CHARACTERISTIC_UUID[] = "00f81600-1db7-4f22-a187-b25005f98dcd";
// static const char I2C_WRITE_CHARACTERISTIC_UUID[] = "00f81601-1db7-4f22-a187-b25005f98dcd";

static const char GPIO00_SERVICE_UUID[] = "b8b1b700-6422-4d6d-bf79-b76e47e9ac86";
static const char GPIO00_MODE_CHARACTERISTIC_UUID[] = "b8b1b700-6422-4d6d-bf79-b76e47e9ac86";
static const char GPIO00_DATA_CHARACTERISTIC_UUID[] = "b8b1b701-6422-4d6d-bf79-b76e47e9ac86";

static const char GPIO02_SERVICE_UUID[] = "682e2900-cd85-4578-b0ac-37c89b1c8c3a";
static const char GPIO02_MODE_CHARACTERISTIC_UUID[] = "682e2900-cd85-4578-b0ac-37c89b1c8c3a";
static const char GPIO02_DATA_CHARACTERISTIC_UUID[] = "682e2901-cd85-4578-b0ac-37c89b1c8c3a";

static const char GPIO03_SERVICE_UUID[] = "56b16f00-2193-4cde-8e09-c3ee4c72dc78";
static const char GPIO03_MODE_CHARACTERISTIC_UUID[] = "56b16f00-2193-4cde-8e09-c3ee4c72dc78";
static const char GPIO03_DATA_CHARACTERISTIC_UUID[] = "56b16f01-2193-4cde-8e09-c3ee4c72dc78";

static const char GPIO29_SERVICE_UUID[] = "b053a500-8e23-4bfd-8f88-4af09fd9040b";
static const char GPIO29_MODE_CHARACTERISTIC_UUID[] = "b053a500-8e23-4bfd-8f88-4af09fd9040b";
static const char GPIO29_DATA_CHARACTERISTIC_UUID[] = "b053a501-8e23-4bfd-8f88-4af09fd9040b";

static const char GPIO30_SERVICE_UUID[] = "9aa1fa00-ebc3-4fb6-ba4a-1bb79b8a9d78";
static const char GPIO30_MODE_CHARACTERISTIC_UUID[] = "9aa1fa00-ebc3-4fb6-ba4a-1bb79b8a9d78";
static const char GPIO30_DATA_CHARACTERISTIC_UUID[] = "9aa1fa01-ebc3-4fb6-ba4a-1bb79b8a9d78";

static const char GPIO31_SERVICE_UUID[] = "981a6300-c513-40dc-ad0c-c873e06ff18b";
static const char GPIO31_MODE_CHARACTERISTIC_UUID[] = "981a6300-c513-40dc-ad0c-c873e06ff18b";
static const char GPIO31_DATA_CHARACTERISTIC_UUID[] = "981a6301-c513-40dc-ad0c-c873e06ff18b";

static const char GPIO32_SERVICE_UUID[] = "9126b700-1a1e-48df-82f7-4100a9fb687b";
static const char GPIO32_MODE_CHARACTERISTIC_UUID[] = "9126b700-1a1e-48df-82f7-4100a9fb687b";
static const char GPIO32_DATA_CHARACTERISTIC_UUID[] = "9126b701-1a1e-48df-82f7-4100a9fb687b";

static const char GPIO33_SERVICE_UUID[] = "7ed8d200-6a9f-4d8b-b50f-e8f4b78667b2";
static const char GPIO33_MODE_CHARACTERISTIC_UUID[] = "7ed8d200-6a9f-4d8b-b50f-e8f4b78667b2";
static const char GPIO33_DATA_CHARACTERISTIC_UUID[] = "7ed8d201-6a9f-4d8b-b50f-e8f4b78667b2";

static const char GPIO34_SERVICE_UUID[] = "525d9c00-3995-480c-80d6-98cf3a8420e9";
static const char GPIO34_MODE_CHARACTERISTIC_UUID[] = "525d9c00-3995-480c-80d6-98cf3a8420e9";
static const char GPIO34_DATA_CHARACTERISTIC_UUID[] = "525d9c01-3995-480c-80d6-98cf3a8420e9";

static const char GPIO38_SERVICE_UUID[] = "756bd900-81b9-4565-9ce2-f6f2dfabbbec";
static const char GPIO38_MODE_CHARACTERISTIC_UUID[] = "756bd900-81b9-4565-9ce2-f6f2dfabbbec";
static const char GPIO38_DATA_CHARACTERISTIC_UUID[] = "756bd901-81b9-4565-9ce2-f6f2dfabbbec";

static const char GPIO39_SERVICE_UUID[] = "b6d08900-99e4-4c53-89ef-4039b61ffb19";
static const char GPIO39_MODE_CHARACTERISTIC_UUID[] = "b6d08900-99e4-4c53-89ef-4039b61ffb19";
static const char GPIO39_DATA_CHARACTERISTIC_UUID[] = "b6d08901-99e4-4c53-89ef-4039b61ffb19";

static const char GPIO57_SERVICE_UUID[] = "cd893c00-de11-4cb7-9436-b9f129f5c249";
static const char GPIO57_MODE_CHARACTERISTIC_UUID[] = "cd893c00-de11-4cb7-9436-b9f129f5c249";
static const char GPIO57_DATA_CHARACTERISTIC_UUID[] = "cd893c01-de11-4cb7-9436-b9f129f5c249";

static const char GPIO58_SERVICE_UUID[] = "42b07000-ce29-4feb-9fc1-5ed55cf9f3af";
static const char GPIO58_MODE_CHARACTERISTIC_UUID[] = "42b07000-ce29-4feb-9fc1-5ed55cf9f3af";
static const char GPIO58_DATA_CHARACTERISTIC_UUID[] = "42b07001-ce29-4feb-9fc1-5ed55cf9f3af";

static const char GPIO59_SERVICE_UUID[] = "4433a600-40eb-462a-8a98-22e326f87eca";
static const char GPIO59_MODE_CHARACTERISTIC_UUID[] = "4433a600-40eb-462a-8a98-22e326f87eca";
static const char GPIO59_DATA_CHARACTERISTIC_UUID[] = "4433a601-40eb-462a-8a98-22e326f87eca";

static const char GPIO60_SERVICE_UUID[] = "6dce9900-d064-4c5d-ae39-9380f52712d6";
static const char GPIO60_MODE_CHARACTERISTIC_UUID[] = "6dce9900-d064-4c5d-ae39-9380f52712d6";
static const char GPIO60_DATA_CHARACTERISTIC_UUID[] = "6dce9901-d064-4c5d-ae39-9380f52712d6";

#endif
