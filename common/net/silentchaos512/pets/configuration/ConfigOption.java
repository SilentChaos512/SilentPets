package net.silentchaos512.pets.configuration;

import net.minecraftforge.common.config.Configuration;

public class ConfigOption<T> {

  public static final String DEFAULT_CATEGORY = "misc";
  public static final String DEFAULT_NAME = "null";

  public String category;
  public String name;
  public String comment;
  public T value;
  public final T defaultValue;
  public T minValue;
  public T maxValue;

  public ConfigOption(T value) {

    this(DEFAULT_CATEGORY, DEFAULT_NAME, value);
  }

  public ConfigOption(String name, T value) {

    this(DEFAULT_CATEGORY, name, value);
  }

  public ConfigOption(String category, String name, T value) {

    this.category = category;
    this.name = name;
    this.defaultValue = value;
    this.comment = "No comment set.";
  }

  public int loadInt(Configuration c) {

    int min = minValue == null ? Integer.MIN_VALUE : (int) minValue;
    int max = maxValue == null ? Integer.MAX_VALUE : (int) maxValue;
    return c.getInt(name, category, (int) defaultValue, min, max, comment);
  }
  
  public float loadFloat(Configuration c) {

    float min = minValue == null ? Float.NEGATIVE_INFINITY : (float) minValue;
    float max = maxValue == null ? Float.POSITIVE_INFINITY : (float) maxValue;
    return c.getFloat(name, category, (float) defaultValue, min, max, comment);
  }
  
  public boolean loadBoolean(Configuration c) {
    
    return c.getBoolean(name, category, (boolean) defaultValue, comment);
  }
}
