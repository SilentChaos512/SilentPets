package net.silentchaos512.pets.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.pets.core.util.LogHelper;

public class Config {

  /*
   * Misc
   */

  public static double PET_ESSENCE_DROP_RATE;
  private static final double PET_ESSENCE_DROP_RATE_DEFAULT = 0.16;
  private static final String PET_ESSENCE_DROP_RATE_NAME = "PetEssence.DropChance";
  private static final String PET_ESSENCE_DROP_RATE_COMMENT = "The probability of a mob dropping "
      + "Pet Essence on death.";

  public static int PET_REGEN_DELAY;
  private static final int PET_REGEN_DELAY_DEFAULT = 60;
  private static final String PET_REGEN_DELAY_NAME = "RegenDelay";
  private static final String PET_REGEN_DELAY_COMMENT = "The number of ticks pets take the "
      + "regenerate 1 health.";

  public static double PET_SUMMON_RETURN_CHANCE;
  private static final double PET_SUMMON_RETURN_CHANCE_DEFAULT = 0.32;
  private static final String PET_SUMMON_RETURN_CHANCE_NAME = "PetSummon.ReturnChance";
  private static final String PET_SUMMON_RETURN_CHANCE_COMMENT = "The probablity of a pet dropping"
      + " a new summon item on death";

  /*
   * Config handler
   */

  private static Configuration c;

  /*
   * Config categories
   */
  public static final String CATEGORY_ENCHANTMENT = "enchantment";
  public static final String CATEGORY_ITEM = "item";
  public static final String CATEGORY_PET = "pet";

  public static void init(File file) {

    c = new Configuration(file);

    try {
      c.load();

      /*
       * Items
       */
      PET_ESSENCE_DROP_RATE = c.getFloat(PET_ESSENCE_DROP_RATE_NAME, CATEGORY_ITEM,
          (float) PET_ESSENCE_DROP_RATE_DEFAULT, 0.0f, 1.0f, PET_ESSENCE_DROP_RATE_COMMENT);
      PET_SUMMON_RETURN_CHANCE = c.getFloat(PET_SUMMON_RETURN_CHANCE_NAME, CATEGORY_ITEM,
          (float) PET_SUMMON_RETURN_CHANCE_DEFAULT, 0, Float.MAX_VALUE,
          PET_SUMMON_RETURN_CHANCE_COMMENT);

      /*
       * Pets
       */
      PET_REGEN_DELAY = c.getInt(PET_REGEN_DELAY_NAME, CATEGORY_ITEM, PET_REGEN_DELAY_DEFAULT, 0,
          Integer.MAX_VALUE, PET_REGEN_DELAY_COMMENT);
    } catch (Exception e) {
      LogHelper.severe("Oh noes!!! Couldn't load configuration file properly!");
    } finally {
      c.save();
    }
  }

  public static int getEnchantmentId(String name, int default_id) {

    return c.get(CATEGORY_ENCHANTMENT, name, default_id).getInt(default_id);
  }

  public static int getGeneralInt(String category, String name, int default_value, String comment) {

    return c.get(category, name, default_value, comment).getInt(default_value);
  }

  public static void save() {

    c.save();
  }
}
