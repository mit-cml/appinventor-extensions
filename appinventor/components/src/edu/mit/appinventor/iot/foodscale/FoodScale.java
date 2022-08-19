package edu.mit.appinventor.iot.foodscale;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import edu.mit.appinventor.ble.BLEExtension;
import edu.mit.appinventor.ble.BLEPacketReader;
import edu.mit.appinventor.ble.BluetoothLE;
import edu.mit.appinventor.iot.foodscale.helpers.FoodScaleUnit;

@DesignerComponent(version = 20220604,
    nonVisible = true,
    category = ComponentCategory.EXTENSION,
    iconName = "aiwebres/scale.png")
@SimpleObject(external = true)
public class FoodScale extends BLEExtension {

  private static final String LOG_TAG = FoodScale.class.getSimpleName();
  private static final double MILK_RATIO = 0.969325153374233;
  private static final double OZ_PER_G = 0.0352;
  private FoodScaleUnit unit = FoodScaleUnit.Unknown;
  private double reading;
  private double mass;
  private boolean lastPacketUnderstood = false;

  private final BluetoothLE.BLEPacketHandler handler = new BluetoothLE.BLEPacketHandler() {
    @Override
    public void onPacketReceived(String serviceUuid, String characteristicUuid,
        BLEPacketReader packet) {
      lastPacketUnderstood = false;
      int header = packet.readUint16(true);
      Log.d(LOG_TAG, "header = " + header);
      double newReading = 0;
      double newMass = 0;
      FoodScaleUnit newUnit = FoodScaleUnit.Unknown;
      if (header == 0xFFA5) {
        // FFA5 00C10044AA0001B0
        newMass = packet.readInt16(true);
        newReading = packet.readInt16(true);
        int temp = packet.readUint8();
        assert(temp == 0xAA);
        packet.readUint8();
        int unitFlag = packet.readUint8();
        switch (unitFlag) {
          case 0:
            newUnit = FoodScaleUnit.Grams;
            break;
          case 1:
            newUnit = FoodScaleUnit.Ounces;
            newReading /= 10;
            break;
          case 2:
            newUnit = FoodScaleUnit.Milliliters;
            break;
          case 3:
            newUnit = FoodScaleUnit.FluidOunces;
            newReading /= 10;
            break;
          default:
            newUnit = FoodScaleUnit.Unknown;
        }
        lastPacketUnderstood = true;
      } else if (header == 0x100C) {
        // 100C 00080A0505 10 C8 00B6C6
        packet.readUint32();
        packet.readUint8();
        int unitFlag = packet.readUint8();
        packet.readUint8();
        newMass = packet.readInt16(true);
        switch (unitFlag) {
          case 1:
            newUnit = FoodScaleUnit.Grams;
            newReading = newMass;
            break;
          case 2:
            newUnit = FoodScaleUnit.Milliliters;
            newReading = newMass;
            break;
          case 4:
            newUnit = FoodScaleUnit.MillilitersOfMilk;
            newReading = MILK_RATIO * newMass;
            break;
          case 8:
            newUnit = FoodScaleUnit.FluidOunces;
            newReading = OZ_PER_G * newMass;
            break;
          case 16:
            newUnit = FoodScaleUnit.Ounces;
            newReading = OZ_PER_G * newMass;
            break;
          default:
            newUnit = FoodScaleUnit.Unknown;
            newReading = newMass;
        }
        lastPacketUnderstood = true;
      }
      if (newUnit != unit || newMass != mass || newReading != reading) {
        mass = newMass;
        reading = newReading;
        unit = newUnit;
        form.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            WeightReport(reading, unit.toUnderlyingValue());
          }
        });
      }
    }
  };

  public FoodScale(ComponentContainer container) {
    super(container.$form(), "FFF0");
  }

  @SimpleProperty(description = "The mass reported by the scale in grams.")
  public double MassInGrams() {
    return mass;
  }

  @SimpleProperty(description = "The reading reported by the scale in the units specified by "
      + "the Unit property.")
  public double Reading() {
    return reading;
  }

  @SimpleProperty(description = "The unit configured on the scale for reading.")
  public String Unit() {
    return unit.toUnderlyingValue();
  }

  @SimpleProperty(description = "Tests whether the extension can understand the scale's messages.")
  public boolean ScaleSupported() {
    return lastPacketUnderstood;
  }

  @SimpleEvent(description = "Runs when the scale reports new data.")
  public void WeightReport(double reading, String unit) {
    EventDispatcher.dispatchEvent(this, "WeightReport", reading, unit);
  }

  @Override
  public void onConnected(BluetoothLE bleConnection) {
    bleConnection.ExRegisterForPackets("FFF0", "FFF1", handler);
  }

  @Override
  public void onDisconnected(BluetoothLE bleConnection) {
    bleConnection.ExUnregisterForPackets("FFF0", "FFF1", handler);
  }
}
