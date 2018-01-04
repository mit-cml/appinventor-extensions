## Dev Guide

### Links
* [appinventor-sources on GitHub](https://github.com/mit-cml/appinventor-sources): MIT AppInventor 2 source code.
* [appinventor-extensions on GitHub](https://github.com/mit-cml/appinventor-extensions/tree/extension/bluetoothle):  Extension sources for AI2.

### Rules
* Please push commits to `7697-dev` branch.
* If `master` has new commits (from MIT). We can `git merge` to pull new commits to `7697-dev` branch.
* Before making new commits, `git diff --staged` to check if modifications are proper.
* `grep` is your friend to locate files.

### Road Map

* MT7697 extension for AI2

Sources are located under `appinventor/components/src/edu/mit/appinventor/iot/mt7697/`. It's a clone of Arduino 101 extension with some modifications.

* (Arduino based) MT7697 driving code

We'll create it from scratch under `arduino-sources`. The Arduino 101 code in `arduino-sources/AIM-for-Things-Arduino101` is taken as the reference implementation.

* Device Connectivity

Android phone communicates with MT7697 over GATT protocol. MT7697 behaves as a peripheral device, while the Android phone, the central device, connects to it to retrieve data.

## Milestones

* Communication Protocol

One service and four characters for each pin, responsable to analog input, analog output, digital input and digital output. There's 16 pins in total.

* Pin I/O

So far pin input is tested and is working properly, while pin output is still under testing.

* Documention

Not done yet.

## Build & Test

### Get sources
If it's the first time you download the source code, follow the steps:
1. Install dependencies: JDK >= 1.7, Apache ant, Google AppEngine for Java
2. Clone the repository ([jerry73204/appinventor-extensions](https://github.com/jerry73204/appinventor-extensions)), and checkout to branch "7697-dev".
3. Download submodules.
```sh
git submodule init
git submodule update --recursive --remote
```

### Build and Run Server
1. Compile project by `cd appinventor; ant clean && ant`.
2. Run `dev_appserver.sh --address=0.0.0.0 --port=8888 appengine/build/war` to start AppInventor server. You may modify the address or port.
3. Run `ant RunLocalBuildServer` in another console to enable APK compilation.
4. Go to link `http://localhost:8888/`.

### Build Extensions only
1. Compile extensions by `cd appinventor; ant clean && ant extensions`.
2. The .aix file is located at `appinventor/components/build/extensions/YOUR.EXTENSION.aix`.

### Flash MT7697
1. Clone repository `git clone git@github.com:MediaTek-Labs/mt76x7-uploader.git`.
2. To make sure MT7697 is attached, Run `lsusb` and you should see `Cygnal Integrated Products, Inc. CP2102/CP2109 UART Bridge Controller [CP210x family]`.
3. Refer to README.md in [mt76x7-uploader](https://github.com/MediaTek-Labs/mt76x7-uploader) project for detailed usage.
