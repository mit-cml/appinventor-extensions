---
layout: documentation
title: Extension
---

[&laquo; Back to index](index.html)
# Extension

Table of Contents:

* [BluetoothLE](#BluetoothLE)

## BluetoothLE  {#BluetoothLE}

Bluetooth Low Energy, also referred to as Bluetooth LE or simply BLE, is a new communication protocol similar to classic Bluetooth except that it is designed to consume less power while maintaining comparable functionality. For this reason, Bluetooth LE is the preferred choice of communication with IoT devices that have limited power resources. Starting with Android 4.3, Google introduced built-in support for Bluetooth Low Energy. The BluetoothLE extension requires Android 5.0 or higher to avoid known issues with Google's Bluetooth LE support prior to Android 5.0.



### Properties  {#BluetoothLE-Properties}

{:.properties}

{:id="BluetoothLE.AdvertisementScanPeriod" .number .ro .bo} *AdvertisementScanPeriod*
: Returns the value of ScanPeriod.

{:id="BluetoothLE.AdvertiserAddresses" .list .ro .bo} *AdvertiserAddresses*
: Returns a list of the addresses of devices found during Advertisement scanning.

{:id="BluetoothLE.AdvertiserNames" .list .ro .bo} *AdvertiserNames*
: Returns a list of the names of the devices found during Advertisment scanning.

{:id="BluetoothLE.AutoReconnect" .boolean} *AutoReconnect*
: If true, the application will attempt to reestablish a lost connection to a device due to link loss (e.g., moving out of range). This will not apply to connections that are disconnected by a call to the <a href='#Disconnect'><code>Disconnect</code></a> method. Such disconnects will need to be reconnected via a call to <a href='#Connect'><code>Connect</code></a> or <a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>.

{:id="BluetoothLE.BatteryValue" .text .ro .bo} *BatteryValue*
: Returns the battery level.

{:id="BluetoothLE.ConnectedDeviceName" .text .ro .bo} *ConnectedDeviceName*
: The advertised name of the connected device. If no device is connected or Bluetooth low energy
 is not supported, this will return the empty string.

{:id="BluetoothLE.ConnectedDeviceRssi" .number .ro .bo} *ConnectedDeviceRssi*
: Returns the RSSI (Received Signal Strength Indicator) of connected device.

{:id="BluetoothLE.ConnectionTimeout" .number} *ConnectionTimeout*
: The amount of time, in seconds, that the BluetoothLE component will wait for a connection to be established with a device after a call to <a href='#Connect'><code>Connect</code></a> or <a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>. If a connection is not established in the given amount of time, the attempt will be aborted and the <a href='#ConnectionFailed'><code>ConnectionFailed</code></a> event will be run.

{:id="BluetoothLE.DeviceCharacteristics" .list .ro .bo} *DeviceCharacteristics*
: A list of triples indicating a service UUID, a characteristic UUID, and the characteristic's
 name, if known. The format of the list will be
 ((service1 characteristic1 name1) (service2 characteristic2 name2) ...). If no device is
 connected or Bluetooth low energy is not supported, then an empty list will be returned.

{:id="BluetoothLE.DeviceList" .text .ro .bo} *DeviceList*
: Returns a sorted list of BluetoothLE devices as a String.

{:id="BluetoothLE.DeviceServices" .list .ro .bo} *DeviceServices*
: A list of pairs indicating a service UUID and its name, if known. The format of the list will
 be ((uuid1 name1) (uuid2 name2) ...). If no device is connected or Bluetooth low energy is not
 supported, then an empty list will be returned.

{:id="BluetoothLE.IsDeviceAdvertising" .boolean .ro .bo} *IsDeviceAdvertising*
: Returns true if the device is currently advertising, false otherwise.

{:id="BluetoothLE.IsDeviceConnected" .boolean .ro .bo} *IsDeviceConnected*
: Returns true if a BluetoothLE device is connected; Otherwise, returns false.

{:id="BluetoothLE.NoLocationNeeded" .boolean .wo .do} *NoLocationNeeded*
: Enable this option to assert that your app does not use Bluetooth to derive location data.
 If enabled, ACCESS_FINE_LOCATION permission will not be needed by the compiled app nor will
 the extension attempt to request permission.

{:id="BluetoothLE.NullTerminateStrings" .boolean} *NullTerminateStrings*
: Instructs the BluetoothLE component to terminate strings with a null byte (true) or not (false)
 when sending string data to a connected device.

{:id="BluetoothLE.Scanning" .boolean .ro .bo} *Scanning*
: The scanning state of the Bluetooth low energy component.

{:id="BluetoothLE.TxPower" .number .ro .bo} *TxPower*
: Returns the transmission power.

### Events  {#BluetoothLE-Events}

{:.events}

{:id="BluetoothLE.BytesReceived"} BytesReceived(*serviceUuid*{:.text},*characteristicUuid*{:.text},*byteValues*{:.list})
: The <code>BytesReceived</code> event is run when one or more byte values are received from a
 connected Bluetooth device. Depending on the <code>sign</code> parameter of the last call to
 <a href="#ReadBytes"><code>ReadBytes</code></a> or
 <a href="#RegisterForBytes"><code>RegisterForBytes</code></a> for the given
 <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>byteValues</code> list
 will contain numbers ranging from -128 to 127 (<code>signed = true</code>)
 or 0 to 255 (<code>signed = false</code>).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>byteValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/listsU.html#makealist">_list_</a>) &mdash;
       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.

{:id="BluetoothLE.BytesWritten"} BytesWritten(*serviceUuid*{:.text},*characteristicUuid*{:.text},*byteValues*{:.list})
: The <code>BytesWritten</code> event is run when one or more byte values are written to a
 connected Bluetooth device. <code>byteValues</code> will be a list of values actually written
 to the device. This may be different if the original input was too long to fit into a single
 transmission unit (typically 23 bytes).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>byteValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values written to the device.

{:id="BluetoothLE.Connected"} Connected()
: The <code>Connected</code> event is run after the application successfully connects to a
 Bluetooth low energy device. This can be the result of a call to
 <a href='#Connect'><code>Connect</code></a> or
 <a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>, or as a result of an
 automatic reconnect if the <a href='#AutoReconnect'><code>AutoReconnect</code></a> property
 was true at the time a connection was requested.

{:id="BluetoothLE.ConnectionFailed"} ConnectionFailed(*reason*{:.text})
: The <code>ConnectionFailed</code> event is run when an attempt to connect to a device does not
 succeed. If a reason is provided by the Bluetooth low energy stack it will be reported via the
 <code>reason</code> parameter.

 __Parameters__:

     * <code>reason</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string" target="_blank">_text_</a>) &mdash;
       The reason the connection failed, if known.

{:id="BluetoothLE.DeviceFound"} DeviceFound()
: The <code>DeviceFound</code> event is run when a new Bluetooth low energy device is found.

{:id="BluetoothLE.Disconnected"} Disconnected()
: The <code>Disconnected</code> event is run when a Bluetooth low energy device is disconnected.
 This can be caused by a call to <a href="#Disconnect"><code>Disconnect</code></a> or
 <a href="#DisconnectWithAddress"><code>DisconnectWithAddress</code></a>, or after a device
 is moved away or reset such that a loss of connection occurs.

{:id="BluetoothLE.FloatsReceived"} FloatsReceived(*serviceUuid*{:.text},*characteristicUuid*{:.text},*floatValues*{:.list})
: The <code>FloatsReceived</code> event is run when one or more IEEE 754 floating point values are received from a
 connected Bluetooth device. Depending on the <code>shortFloat</code> parameter of the last call to
 <a href="#ReadFloats"><code>ReadFloats</code></a> or
 <a href="#RegisterForFloats"><code>RegisterForFloats</code></a> for the given
 <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>floatValues</code> list
 will contain numbers ranging from -65504.0 to 65504.0 (<code>shortFloat = true</code>)
 or -3.402823466E38 to 3.402823466E38 (<code>shortFloat = false</code>).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>floatValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.

{:id="BluetoothLE.FloatsWritten"} FloatsWritten(*serviceUuid*{:.text},*characteristicUuid*{:.text},*floatValues*{:.list})
: The <code>FloatsWritten</code> event is run when one or more IEEE 754 floating point values are written to a
 connected Bluetooth device. <code>floatValues</code> will be a list of values actually written
 to the device. This may be different if the original input was too long to fit into a single
 transmission unit (typically 11 short floats or 5 regular floats).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>floatValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values written to the device.

{:id="BluetoothLE.IntegersReceived"} IntegersReceived(*serviceUuid*{:.text},*characteristicUuid*{:.text},*intValues*{:.list})
: The <code>IntegersReceived</code> event is run when one or more 32-bit integer values are received from a
 connected Bluetooth device. Depending on the <code>sign</code> parameter of the last call to
 <a href="#ReadIntegers"><code>ReadIntegers</code></a> or
 <a href="#RegisterForIntegers"><code>RegisterForIntegers</code></a> for the given
 <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>intValues</code> list
 will contain numbers ranging from -2147483648 to 2147483647 (<code>signed = true</code>)
 or 0 to 4294967296 (<code>signed = false</code>).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>intValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.

{:id="BluetoothLE.IntegersWritten"} IntegersWritten(*serviceUuid*{:.text},*characteristicUuid*{:.text},*intValues*{:.list})
: The <code>IntegersWritten</code> event is run when one or more 32-bit integers values are written to a
 connected Bluetooth device. <code>intValues</code> will be a list of values actually written
 to the device. This may be different if the original input was too long to fit into a single
 transmission unit (typically 5 integers).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>intValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values written to the device.

{:id="BluetoothLE.MTUChanged"} MTUChanged(*bytes*{:.number})
: The MTUChanged event is run when the two BluetoothLE devices have successfully changed their
 maximum transmission unit (MTU) to a different value. This event will only run in response
 to a call to the method block RequestMTU.

 __Parameters__:

   * <code>bytes</code> (<a href="">_number_</a>) &mdash;
     The new size, in bytes, of the new MTU.

{:id="BluetoothLE.RssiChanged"} RssiChanged(*rssi*{:.number})
: The <code>RssiChanged</code> event is run when the Received Signal Strength Indicator (RSSI) of
 a discovered Bluetooth low energy device changes.

 __Parameters__:

     * <code>rssi</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The strength of the received signal, in dBm, from -100 to 0.

{:id="BluetoothLE.ShortsReceived"} ShortsReceived(*serviceUuid*{:.text},*characteristicUuid*{:.text},*shortValues*{:.list})
: The <code>ShortsReceived</code> event is run when one or more short integer values are received from a
 connected Bluetooth device. Depending on the <code>sign</code> parameter of the last call to
 <a href="#ReadShorts"><code>ReadShorts</code></a> or
 <a href="#RegisterForShorts"><code>RegisterForShorts</code></a> for the given
 <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>shortValues</code> list
 will contain numbers ranging from -32768 to 32767 (<code>signed = true</code>)
 or 0 to 65535 (<code>signed = false</code>).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>shortValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values read from the device. The range of each value will depend on the <code>sign</code> flag previously specified in the call to read or register.

{:id="BluetoothLE.ShortsWritten"} ShortsWritten(*serviceUuid*{:.text},*characteristicUuid*{:.text},*shortValues*{:.list})
: The <code>ShortsWritten</code> event is run when one or more short integers values are written to a
 connected Bluetooth device. <code>shortValues</code> will be a list of values actually written
 to the device. This may be different if the original input was too long to fit into a single
 transmission unit (typically 11 shorts).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>shortValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values written to the device.

{:id="BluetoothLE.StringsReceived"} StringsReceived(*serviceUuid*{:.text},*characteristicUuid*{:.text},*stringValues*{:.list})
: The <code>StringsReceived</code> event is run when one or more strings are received from a
 connected Bluetooth device. Depending on the <code>utf16</code> parameter of the last call to
 <a href="#ReadStrings"><code>ReadStrings</code></a> or
 <a href="#RegisterForStrings"><code>RegisterForStrings</code></a> for the given
 <code>serviceUuid</code> and <code>characteristicUuid</code>, the <code>stringValues</code> list
 will contain either a UTF-16 little endian decoded (<code>utf16 = true</code>) or UTF-8
 decoded (<code>utf16 = false</code>) strings. The string length is limited by the maximum
 transmission unit (MTU) of the Bluetooth device, which is typically 23 bytes.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>stringValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values read from the device. The strings will be decoded as UTF-16 or UTF-8 based on the <code>utf16</code> flag previously specified in the call to read or register.

{:id="BluetoothLE.StringsWritten"} StringsWritten(*serviceUuid*{:.text},*characteristicUuid*{:.text},*stringValues*{:.list})
: The <code>StringsWritten</code> event is run when one or more strings are written to a
 connected Bluetooth device. <code>stringValues</code> will be a list of values actually written
 to the device. This may be different if the original input was too long to fit into a single
 transmission unit (typically 22 bytes).

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>stringValues</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values written to the device.

### Methods  {#BluetoothLE-Methods}

{:.methods}

{:id="BluetoothLE.AdvertisementData" class="method returns text"} <i/> AdvertisementData(*deviceAddress*{:.text},*serviceUuid*{:.text})
: Returns the advertisement data associated with the specified device address.

 __Parameters__:

     * <code>deviceAddress</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The Media Access Control (MAC) address of the target Bluetooth low energy device.
     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the advertised service.

{:id="BluetoothLE.AdvertiserAddress" class="method returns text"} <i/> AdvertiserAddress(*deviceName*{:.text})
: Returns the address of the device with the name specified.

 __Parameters__:

     * <code>deviceName</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The advertised name of the target Bluetooth low energy device.

{:id="BluetoothLE.AdvertiserServiceUuids" class="method returns list"} <i/> AdvertiserServiceUuids(*deviceAddress*{:.text})
: Returns the list of services available on the advertising device.

 __Parameters__:

     * <code>deviceAddress</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The Media Access Control (MAC) address of the target Bluetooth low energy device.

{:id="BluetoothLE.CanReadCharacteristic" class="method returns boolean"} <i/> CanReadCharacteristic(*serviceUuid*{:.text},*characteristicUuid*{:.text})
: Tests whether the given characteristic can be read.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service to test for read capability.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic to test for read capability.

{:id="BluetoothLE.CanRegisterForCharacteristic" class="method returns boolean"} <i/> CanRegisterForCharacteristic(*serviceUuid*{:.text},*characteristicUuid*{:.text})
: Tests whether the given characteristic can be used to register for notifications.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service to test for notification capability.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic to test for notification capability.

{:id="BluetoothLE.CanWriteCharacteristic" class="method returns boolean"} <i/> CanWriteCharacteristic(*serviceUuid*{:.text},*characteristicUuid*{:.text})
: Tests whether the given characteristic can be written.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service to test for write capability.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic to test for write capability.

{:id="BluetoothLE.CanWriteCharacteristicWithResponse" class="method returns boolean"} <i/> CanWriteCharacteristicWithResponse(*serviceUuid*{:.text},*characteristicUuid*{:.text})
: Tests whether the given characteristic can be written with an acknowledgement by the device.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service to test for write with response capability.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic to test for write with response capability.

{:id="BluetoothLE.CharacteristicByIndex" class="method returns text"} <i/> CharacteristicByIndex(*index*{:.number})
: Returns Unique ID of selected characteristic with index. Index specified by list
 of supported characteristics for a connected device, starting from 1.

 __Parameters__:

     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The index of the desired characteristic, which must be between 1 and the length of the characteristic list.

{:id="BluetoothLE.Connect" class="method"} <i/> Connect(*index*{:.number})
: Use the <code>Connect</code> method to connect to a Bluetooth low energy device at the given
 index in the device list.

 __Parameters__:

     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The index of the target device, which must be between 1 and the length of the list.

{:id="BluetoothLE.ConnectMatchingName" class="method"} <i/> ConnectMatchingName(*name*{:.text})
: Method for ConnectMatchingName

{:id="BluetoothLE.ConnectToDeviceType" class="method"} <i/> ConnectToDeviceType(*device*{:.component},*name*{:.text})
: Connects to the first device found advertising with the given
 <code>name</code> and the service UUID associated with <code>device</code>.

 __Parameters__:

   * <code>device</code> (<a href="">_component_</a>) &mdash;
     A component block that represents a Bluetooth Low Energy device
   * <code>name</code> (a href="">_name_</a>) &mdash;
     The name advertised by the desired device,

{:id="BluetoothLE.ConnectToDeviceWithServiceAndName" class="method"} <i/> ConnectToDeviceWithServiceAndName(*serviceUuid*{:.text},*name*{:.text})
: Connects to the first device found advertising with the given
 <code>name</code> and the service UUID associated with <code>device</code>.

 __Parameters__:

   * <code>serviceUuid</code> (<a href="">_text_</a>) &mdash;
     The unique identifier of the service being broadcast by the device(s)
     of interest.
   * <code>name</code> (a href="">_name_</a>) &mdash;
     The name advertised by the desired device,

{:id="BluetoothLE.ConnectWithAddress" class="method"} <i/> ConnectWithAddress(*address*{:.text})
: Use the <code>ConnectWithAddress</code> method to connect to a specific Bluetooth low energy
 device if its Media Access Control (MAC) address is known. If none of the devices in the device
 list matches the given address, the <a href="#ConnectionFailed"><code>ConnectionFailed</code></a>
 event will be run. Otherwise, if a connection is successful the
 <a href="#Connected"><code>Connected</code></a> event will be run.

 __Parameters__:

     * <code>address</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string" target="_blank">_text_</a>) &mdash;
       The MAC address of the target device, of the form "12:34:56:78:90:ab"

{:id="BluetoothLE.Disconnect" class="method"} <i/> Disconnect()
: Disconnects from the currently connected BluetoothLE device if a device is connected.

{:id="BluetoothLE.DisconnectWithAddress" class="method"} <i/> DisconnectWithAddress(*address*{:.text})
: Disconnects from a connected BluetoothLE device with the given address.

 __Parameters__:

     * <code>address</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string" target="_blank">_text_</a>) &mdash;
       The Media Access Control (MAC) address of the device to disconnect, of the form "12:34:56:78:90:ab"

{:id="BluetoothLE.FoundDeviceAddress" class="method returns text"} <i/> FoundDeviceAddress(*index*{:.number})
: Gets the Media Access Control (MAC) address of the found device at the given index in the
 device list. Index specifies the position in the BluetoothLE device list, starting from 1.

 __Parameter__:

     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The index of the desired device, which must be between 1 and the length of the device list.

{:id="BluetoothLE.FoundDeviceName" class="method returns text"} <i/> FoundDeviceName(*index*{:.number})
: Gets the name of the found device at the given index in the device list.

 __Parameter__:

     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The index of the desired device, which must be between 1 and the length of the device list.

{:id="BluetoothLE.FoundDeviceRssi" class="method returns number"} <i/> FoundDeviceRssi(*index*{:.number})
: Gets the Received Signal Strength Indicator (RSSI) of the found device at the given index.
 The returned value will be between -100 and 0 indicating the strength of the connection.

 __Parameter__:

     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The index of the desired device, which must be between 1 and the length of the device list.

{:id="BluetoothLE.GetCharacteristicsForService" class="method returns list"} <i/> GetCharacteristicsForService(*serviceUuid*{:.text})
: Returns the list of supported characteristics for the given service. The list will contain
 (UUID, name) pairs for each characteristic provided by the service UUID.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.

{:id="BluetoothLE.OpenDevicePicker" class="method"} <i/> OpenDevicePicker()
: Method for OpenDevicePicker

{:id="BluetoothLE.ReadBytes" class="method"} <i/> ReadBytes(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean})
: Reads one or more 8-bit integer values from a connected BluetoothLE device. Service Unique ID
 and Characteristic UniqueID are required. The <code>signed</code> parameter indicates whether
 the bytes should be interpreted as signed values or not when being converted into App Inventor
 numbers. After the bytes are read, the <a href="#BytesReceived"><code>BytesReceived</code></a>
 event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the bytes as signed (true) or unsigned (false).

{:id="BluetoothLE.ReadConnectedRssi" class="method"} <i/> ReadConnectedRssi()
: Initiates a read of the connected device's Received Signal Strength Indicator (RSSI). The
 resulting value will be reported via the RssiChanged event.

{:id="BluetoothLE.ReadFloats" class="method"} <i/> ReadFloats(*serviceUuid*{:.text},*characteristicUuid*{:.text},*shortFloat*{:.boolean})
: Reads one or more IEEE 754 floating point numbers from a connected BluetoothLE device. Service Unique ID
 and Characteristic UniqueID are required. The <code>shortFloat</code> parameter indicates whether
 the floats are either 16-bit half-precision floating point or 32-bit single precision floating point
 numbers. After the floats are read, the <a href="#FloatsReceived"><code>FloatsReceived</code></a>
 event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).

{:id="BluetoothLE.ReadIntegers" class="method"} <i/> ReadIntegers(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean})
: Reads one or more 32-bit integer values from a connected BluetoothLE device. Service Unique ID
 and Characteristic UniqueID are required. The <code>signed</code> parameter indicates whether
 the integers should be interpreted as signed values or not when being converted into App Inventor
 numbers. After the integers are read, the
 <a href="#IntegersReceived"><code>IntegersReceived</code></a> event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the integers as signed (true) or unsigned (false).

{:id="BluetoothLE.ReadShorts" class="method"} <i/> ReadShorts(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean})
: Reads one or more 16-bit integer values from a connected BluetoothLE device. Service Unique ID
 and Characteristic UniqueID are required. The <code>signed</code> parameter indicates whether
 the shorts should be interpreted as signed values or not when being converted into App Inventor
 numbers. After the shorts are read, the <a href="#ShortsReceived"><code>ShortsReceived</code></a>
 event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the shorts as signed (true) or unsigned (false).

{:id="BluetoothLE.ReadStrings" class="method"} <i/> ReadStrings(*serviceUuid*{:.text},*characteristicUuid*{:.text},*utf16*{:.boolean})
: Reads one or more null-terminated strings from a connected BluetoothLE device. Service Unique ID
 and Characteristic Unique ID are required. The <code>utf16</code> parameter indicates whether
 the content should be decoded as UTF-16 (true) or UTF-8 (false) code points when converting to
 App Inventor strings. After the strings are read, the
 <a href="#StringsReceived"><code>StringsReceived</code></a> event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the string content as UTF-16 (true) or UTF-8 (false) code points.

{:id="BluetoothLE.RegisterForBytes" class="method"} <i/> RegisterForBytes(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean})
: Registers to receive updates when one or more 8-bit integer values from a connected BluetoothLE
 device are changed. Service Unique ID and Characteristic Unique ID are required. The
 <code>signed</code> parameter indicates whether the bytes should be interpreted as signed
 values or not when being converted into App Inventor numbers. Whenever a change is received,
 the <a href="#BytesReceived"><code>BytesReceived</code></a> event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the bytes as signed (true) or unsigned (false).

{:id="BluetoothLE.RegisterForFloats" class="method"} <i/> RegisterForFloats(*serviceUuid*{:.text},*characteristicUuid*{:.text},*shortFloat*{:.boolean})
: Registers to receive updates when one or more IEEE 754 floating point numbers from a connected
 BluetoothLE device are changed. Service Unique ID and Characteristic Unique ID are required. The
 <code>shortFloat</code> parameter indicates whether the floats are either 16-bit half-precision
 floating point or 32-bit single precision floating point numbers. Whenever a change is received,
 the <a href="#FloatsReceived"><code>FloatsReceived</code></a> event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).

{:id="BluetoothLE.RegisterForIntegers" class="method"} <i/> RegisterForIntegers(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean})
: Registers to receive updates when one or more 32-bit integer values from a connected BluetoothLE
 device are changed. Service Unique ID and Characteristic Unique ID are required. The
 <code>signed</code> parameter indicates whether the integers should be interpreted as signed
 values or not when being converted into App Inventor numbers. Whenever a change is received,
 the <a href="#IntegersReceived"><code>IntegersReceived</code></a> event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the integers as signed (true) or unsigned (false).

{:id="BluetoothLE.RegisterForShorts" class="method"} <i/> RegisterForShorts(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean})
: Registers to receive updates when one or more 16-bit integer values from a connected BluetoothLE
 device are changed. Service Unique ID and Characteristic Unique ID are required. The
 <code>signed</code> parameter indicates whether the shorts should be interpreted as signed
 values or not when being converted into App Inventor numbers. Whenever a change is received,
 the <a href="#ShortsReceived"><code>ShortsReceived</code></a> event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the shorts as signed (true) or unsigned (false).

{:id="BluetoothLE.RegisterForStrings" class="method"} <i/> RegisterForStrings(*serviceUuid*{:.text},*characteristicUuid*{:.text},*utf16*{:.boolean})
: Registers to receive updates when one or more null-terminated strings from a connected
 BluetoothLE device are changed. Service Unique ID and Characteristic Unique ID are required. The
 <code>utf16</code> parameter indicates whether the content should be decoded as UTF-16 (true)
 or UTF-8 (false) code points when converting to App Inventor strings. Whenever a change is
 received, the <a href="#StringsReceived"><code>StringsReceived</code></a> event will be run.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the string content as UTF-16 (true) or UTF-8 (false) code points.

{:id="BluetoothLE.RequestMTU" class="method"} <i/> RequestMTU(*bytes*{:.number})
: Requests a new minimum transmission unit (MUT) for the BluetoothLE connection. This feature
 is only supported when both devices support Bluetooth 4.2 or higher. If the MTU is changed
 successfully, the MTUChanged event will be run. The default MTU is 20.

 This block is intended for advanced apps that need to change the size of the messages sent
 between the BLE devices. Most developers will not need to adjust this value.

 __Parameters__:

   * <code>bytes</code> (<a href="">_number_</a>) &mdash;
     The desired MTU size.

{:id="BluetoothLE.ScanAdvertisements" class="method"} <i/> ScanAdvertisements(*scanPeriod*{:.number})
: Scans for advertising Bluetooth low energy devices.

 __Parameter__:

     * <code>scanPeriod</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The amount of time to spend scanning, in milliseconds.

{:id="BluetoothLE.ScanForDevice" class="method"} <i/> ScanForDevice(*device*{:.component})
: Scans for a particular type of device. The device component must implement
 the BLEDevice interface in order for this method to work.

 __Parameters__:

   * <code>param</code> (<a href="">_component_</a>) &mdash;
     A component block that expects a particular service.

{:id="BluetoothLE.ScanForService" class="method"} <i/> ScanForService(*serviceUuid*{:.text})
: Scans for devices advertising a particular Bluetooth low energy service
 by UUID.

 __Parameters__:

   * <code>serviceUuid</code> (<a href="">_text_</a>) &mdash;
     The unique identifier of the service being broadcast by the device(s)
     of interest.

{:id="BluetoothLE.ServiceByIndex" class="method returns text"} <i/> ServiceByIndex(*index*{:.number})
: Returns the Unique ID of the service at the given index in the service list.

 __Parameters__:

     * <code>index</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/math.html#number">_number_</a>) &mdash;
       The index of the desired service, which must be between 1 and the length of the service list.

{:id="BluetoothLE.StartAdvertising" class="method"} <i/> StartAdvertising(*inData*{:.text},*serviceUuid*{:.text})
: Creates and publishes a Bluetooth low energy advertisement.

 __Parameters__:

     * <code>inData</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The data to be included in the service advertisement.
     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.

{:id="BluetoothLE.StartScanning" class="method"} <i/> StartScanning()
: Starts scanning for Bluetooth low energy devices.

{:id="BluetoothLE.StopAdvertising" class="method"} <i/> StopAdvertising()
: Stops Bluetooth low energy advertisement from a previous call to
 <a href="#StartAdvertising"><code>StartAdvertising</code></a>.

{:id="BluetoothLE.StopScanning" class="method"} <i/> StopScanning()
: Stops scanning for Bluetooth low energy devices.

{:id="BluetoothLE.StopScanningAdvertisements" class="method"} <i/> StopScanningAdvertisements()
: Stops scanning for Bluetooth low energy advertisements.

{:id="BluetoothLE.SupportedCharacteristics" class="method returns text"} <i/> SupportedCharacteristics()
: Returns a list of supported characteristic for the connected device as a string.

{:id="BluetoothLE.SupportedServices" class="method returns text"} <i/> SupportedServices()
: Returns the list of supported service for the connected device as a string.

{:id="BluetoothLE.UnregisterForValues" class="method"} <i/> UnregisterForValues(*service_uuid*{:.text},*characteristic_uuid*{:.text})
: Unregisters for updates from the given service and characteristic.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.

{:id="BluetoothLE.WriteBytes" class="method"} <i/> WriteBytes(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean},*values*{:.any})
: Writes one or more 8-bit integer values to a connected BluetoothLE device. Service Unique ID
 and Characteristic Unique ID are required. The values parameter can either be a single numeric
 value or a list of values. If <code>signed</code> is true, the acceptable values are
 between -128 and 127. If <code>signed</code> is false, the acceptable values are
 between 0 and 255.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the bytes as signed (true) or unsigned (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteBytesWithResponse" class="method"} <i/> WriteBytesWithResponse(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean},*values*{:.any})
: Writes one or more 8-bit integer values to a connected BluetoothLE device and waits for an
 acknowledgement via the <a href="#BytesWritten"><code>BytesWritten</code></a> event.
 Service Unique ID and Characteristic Unique ID are required. The values parameter can either
 be a single numeric value or a list of values. If <code>signed</code> is true, the acceptable
 values are between -128 and 127. If <code>signed</code> is false, the acceptable values are
 between 0 and 255.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the bytes as signed (true) or unsigned (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteFloats" class="method"} <i/> WriteFloats(*serviceUuid*{:.text},*characteristicUuid*{:.text},*shortFloat*{:.boolean},*values*{:.any})
: Writes one or more IEEE 754 floating point numbers to a connected BluetoothLE device. Service Unique ID
 and Characteristic Unique ID are required. The values parameter can either be a single numeric
 value or a list of values. If <code>shortFloat</code> is true, then each numeric value will be
 compressed to fit into a 16-bit half-precision floating point value. If <code>shortFloat</code>
 is false, then each numeric value will be sent as a 32-bit single precision floating point value.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteFloatsWithResponse" class="method"} <i/> WriteFloatsWithResponse(*serviceUuid*{:.text},*characteristicUuid*{:.text},*shortFloat*{:.boolean},*values*{:.any})
: Writes one or more IEEE 754 floating point values to a connected BluetoothLE device and waits for an
 acknowledgement via the <a href="#FloatsWritten"><code>FloatsWritten</code></a> event.
 Service Unique ID and Characteristic Unique ID are required. The values parameter can either
 be a single numeric value or a list of values. If <code>shortFloat</code> is false,
 then each numeric value will be sent as a 32-bit single precision floating point value.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>shortFloat</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the floats as 16-bit half-precision (true) or 32-bit single-precision (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteIntegers" class="method"} <i/> WriteIntegers(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean},*values*{:.any})
: Writes one or more 32-bit integer values to a connected BluetoothLE device. Service Unique ID
 and Characteristic Unique ID are required. The values parameter can either be a single numeric
 value or a list of values. If <code>signed</code> is true, the acceptable values are
 between -2147483648 and 2147483647. If <code>signed</code> is false, the acceptable values are
 between 0 and 4294967295.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the integers as signed (true) or unsigned (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteIntegersWithResponse" class="method"} <i/> WriteIntegersWithResponse(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean},*values*{:.any})
: Writes one or more 32-bit integer values to a connected BluetoothLE device and waits for an
 acknowledgement via the <a href="#IntegersWritten"><code>IntegersWritten</code></a> event.
 Service Unique ID and Characteristic Unique ID are required. The values parameter can either
 be a single numeric value or a list of values. If <code>signed</code> is true, the acceptable
 values are between -2147483648 and 2147483647. If <code>signed</code> is false, the acceptable
 values are between 0 and 4294967295.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the integers as signed (true) or unsigned (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteShorts" class="method"} <i/> WriteShorts(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean},*values*{:.any})
: Writes one or more 16-bit integer values to a connected BluetoothLE device. Service Unique ID
 and Characteristic Unique ID are required. The values parameter can either be a single numeric
 value or a list of values. If <code>signed</code> is true, the acceptable values are
 between -32768 and 32767. If <code>signed</code> is false, the acceptable values are
 between 0 and 65535.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the shorts as signed (true) or unsigned (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteShortsWithResponse" class="method"} <i/> WriteShortsWithResponse(*serviceUuid*{:.text},*characteristicUuid*{:.text},*signed*{:.boolean},*values*{:.any})
: Writes one or more 16-bit integer values to a connected BluetoothLE device and waits for an
 acknowledgement via the <a href="#ShortsWritten"><code>ShortsWritten</code></a> event.
 Service Unique ID and Characteristic Unique ID are required. The values parameter can either
 be a single numeric value or a list of values. If <code>signed</code> is true, the acceptable
 values are between -32768 and 32767. If <code>signed</code> is false, the acceptable values
 are between 0 and 65535.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>signed</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Interpret the shorts as signed (true) or unsigned (false).
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteStrings" class="method"} <i/> WriteStrings(*serviceUuid*{:.text},*characteristicUuid*{:.text},*utf16*{:.boolean},*values*{:.any})
: Writes one or more strings to a connected BluetoothLE device. Service Unique ID and
 Characteristic Unique ID are required. The values parameter can either be a single string or a
 list of strings. If <code>utf16</code> is true, the string(s) will be sent using UTF-16 little
 endian encoding. If <code>utf16</code> is false, the string(s) will be sent using UTF-8
 encoding.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Send the string encoded as UTF-16 little endian (true) or UTF-8 (false) code points.
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.

{:id="BluetoothLE.WriteStringsWithResponse" class="method"} <i/> WriteStringsWithResponse(*serviceUuid*{:.text},*characteristicUuid*{:.text},*utf16*{:.boolean},*values*{:.any})
: Writes one or more strings to a connected BluetoothLE device and waits for an acknowledgement
 via the <a href="#StringsWritten"><code>StringsWritten</code></a> event. Service Unique ID and
 Characteristic Unique ID are required. The values parameter can either be a single string or a
 list of values. If <code>utf16</code> is true, the string(s) will be sent using UTF-16 little
 endian encoding. If <code>utf16</code> is false, the string(s) will be sent using UTF-8
 encoding.

 __Parameters__:

     * <code>serviceUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the service passed in the read or register call.
     * <code>characteristicUuid</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/text.html#string">_text_</a>) &mdash;
       The unique identifier of the characteristic in the read or register call.
     * <code>utf16</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/logic.html#true" target="_blank">_boolean_</a>)
       Send the string encoded as UTF-16 little endian (true) or UTF-8 (false) code points.
     * <code>values</code> (<a href="http://appinventor.mit.edu/explore/ai2/support/blocks/lists.html#makealist">_list_</a>) &mdash;
       A list of values to write to the device.
