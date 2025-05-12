package com.galago.ui.input;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ndebruyn
 */
public class Input {

  private static Map<String, Float> inputMaps = new HashMap<>();
  private static Map<String, Float> inputAnalogMaps = new HashMap<>();

  public static void registerInput(String name) {
    inputMaps.put(name, Float.valueOf(0));

  }

  public static void registerAnalogInput(String name) {
    inputAnalogMaps.put(name, Float.valueOf(0));

  }

  public static boolean hasMapping(String name) {
    return inputMaps.containsKey(name);

  }

  public static boolean hasAnalogMapping(String name) {
    return inputAnalogMaps.containsKey(name);

  }

  public static void set(String name, float val) {
    inputMaps.replace(name, val);

  }

  public static void setAnalog(String name, float val) {
    inputAnalogMaps.replace(name, val);

  }

  public static float get(String name) {
    return inputMaps.get(name) == null ? 0 : inputMaps.get(name);
  }

  public static float getAnalog(String name) {
    return inputAnalogMaps.get(name) == null ? 0 : inputAnalogMaps.get(name);
  }

  public static void consume(String name) {
    inputMaps.replace(name, 0f);

  }

  public static void consumeAnalog(String name) {
    inputAnalogMaps.replace(name, 0f);

  }

}
