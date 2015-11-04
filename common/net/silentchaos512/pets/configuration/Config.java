package net.silentchaos512.pets.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.pets.core.util.LogHelper;

public class Config {

  /*
   * Items
   */

  public static ConfigOption<Float> PET_ESSENCE_DROP_CHANCE;
  public static ConfigOption<Float> PET_SUMMON_DROP_CHANCE;
  public static ConfigOption<Integer> PET_SUMMON_LOOT_WEIGHT;
  public static ConfigOption<Integer> NAME_PLATE_LOOT_WEIGHT;

  /*
   * Pets
   */

  public static ConfigOption<Integer> PET_REGEN_DELAY;
  public static ConfigOption<Integer> PET_INITIAL_REGEN_DELAY;
  public static ConfigOption<Boolean> PET_IS_COSMETIC;
  public static ConfigOption<Boolean> PET_FRIENDLY_FIRE;

  /*
   * Config handler
   */

  private static Configuration c;

  public static void init(File file) {

    c = new Configuration(file);

    try {
      c.load();

      final String PREFIX_ITEM = "item" + Configuration.CATEGORY_SPLITTER;
      final String PREFIX_PET = "pet" + Configuration.CATEGORY_SPLITTER;
      String category;

      /*
       * Items
       */

      category = PREFIX_ITEM + "pet_essence";
      PET_ESSENCE_DROP_CHANCE = new ConfigOption<Float>(category, "DropChance", 0.15f);
      PET_ESSENCE_DROP_CHANCE.minValue = 0f;
      PET_ESSENCE_DROP_CHANCE.maxValue = 1f;
      PET_ESSENCE_DROP_CHANCE.comment = "The probability of a mob dropping Pet Essence on death.";
      PET_ESSENCE_DROP_CHANCE.value = PET_ESSENCE_DROP_CHANCE.loadFloat(c);

      category = PREFIX_ITEM + "pet_summon";
      PET_SUMMON_DROP_CHANCE = new ConfigOption<Float>(category, "DropChance", 0.25f);
      PET_SUMMON_DROP_CHANCE.minValue = 0f;
      PET_SUMMON_DROP_CHANCE.maxValue = 1f;
      PET_SUMMON_DROP_CHANCE.comment = "The probablity of a pet dropping a new summon item on death";
      PET_SUMMON_DROP_CHANCE.value = PET_SUMMON_DROP_CHANCE.loadFloat(c);
      
      PET_SUMMON_LOOT_WEIGHT = new ConfigOption<Integer>(category, "LootChestWeight", 7);
      PET_SUMMON_LOOT_WEIGHT.minValue = 0;
      PET_SUMMON_LOOT_WEIGHT.maxValue = Integer.MAX_VALUE;
      PET_SUMMON_LOOT_WEIGHT.comment = "The weight of a pet summoner appearing in certain chests.";
      PET_SUMMON_LOOT_WEIGHT.value = PET_SUMMON_LOOT_WEIGHT.loadInt(c);

      category = PREFIX_ITEM + "name_plate";
      NAME_PLATE_LOOT_WEIGHT = new ConfigOption<Integer>(category, "LootWeight", 5);
      NAME_PLATE_LOOT_WEIGHT.minValue = 0;
      NAME_PLATE_LOOT_WEIGHT.maxValue = Integer.MAX_VALUE;
      NAME_PLATE_LOOT_WEIGHT.comment = "The weight of Name Plates appearing in certain loot chests.";
      NAME_PLATE_LOOT_WEIGHT.value = NAME_PLATE_LOOT_WEIGHT.loadInt(c);

      /*
       * Pets
       */

      category = PREFIX_PET + "regen";
      PET_REGEN_DELAY = new ConfigOption<Integer>(category, "Delay", 60);
      PET_REGEN_DELAY.minValue = 0;
      PET_REGEN_DELAY.maxValue = Integer.MAX_VALUE;
      PET_REGEN_DELAY.comment = "The number of ticks pets take the regenerate 1 health. Set to 0 " 
          + "to disable regeneration.";
      PET_REGEN_DELAY.value = PET_REGEN_DELAY.loadInt(c);
      
      PET_INITIAL_REGEN_DELAY = new ConfigOption<Integer>(category, "InitialDelay", 600);
      PET_INITIAL_REGEN_DELAY.minValue = 0;
      PET_INITIAL_REGEN_DELAY.maxValue = Integer.MAX_VALUE;
      PET_INITIAL_REGEN_DELAY.comment = "After a pet takes damage, it will take this many ticks "
          + "for regeneration to begin again.";
      PET_INITIAL_REGEN_DELAY.value = PET_INITIAL_REGEN_DELAY.loadInt(c);
      
      category = "pet";
      PET_IS_COSMETIC = new ConfigOption<Boolean>(category, "IsCosmetic", false);
      PET_IS_COSMETIC.comment = "If true, pets cannot take or deal damage. In other words, they "
          + "are immortal, but harmless.";
      PET_IS_COSMETIC.value = PET_IS_COSMETIC.loadBoolean(c);
      
      PET_FRIENDLY_FIRE = new ConfigOption<Boolean>(category, "FriendlyFire", false);
      PET_FRIENDLY_FIRE.comment = "If true, pets can be damaged by their owner.";
      PET_FRIENDLY_FIRE.value = PET_FRIENDLY_FIRE.loadBoolean(c);
    } catch (Exception e) {
      LogHelper.severe("Oh noes!!! Couldn't load configuration file properly!");
    } finally {
      c.save();
    }
  }

  public static int getEnchantmentId(String name, int default_id) {

    return c.get("enchantment", name, default_id).getInt(default_id);
  }

  public static void save() {

    c.save();
  }
}
