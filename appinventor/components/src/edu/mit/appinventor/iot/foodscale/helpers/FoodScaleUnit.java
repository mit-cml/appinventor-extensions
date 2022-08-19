package edu.mit.appinventor.iot.foodscale.helpers;

import com.google.appinventor.components.common.OptionList;
import java.util.HashMap;
import java.util.Map;

public enum FoodScaleUnit /*implements OptionList<String>*/ {
  Grams("g"),
  Ounces("oz"),
  Milliliters("ml"),
  FluidOunces("fl oz"),
  MillilitersOfMilk("ml milk"),
  Unknown("units");

  private static final Map<String, FoodScaleUnit> LOOKUP = new HashMap<>();

  static {
    for (FoodScaleUnit unit : values()) {
      LOOKUP.put(unit.unit, unit);
    }
  }

  private final String unit;

  FoodScaleUnit(String unit) {
    this.unit = unit;
  }

  public String toUnderlyingValue() {
    return unit;
  }

  public static FoodScaleUnit fromUnderlyingValue(String unit) {
    return LOOKUP.get(unit);
  }
}
