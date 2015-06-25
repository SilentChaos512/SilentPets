package net.silentchaos512.pets.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.pets.core.util.LogHelper;

public class Config {

    /*
     * Misc
     */

    public static ConfigOptionDouble PET_ESSENCE_DROP_CHANCE = new ConfigOptionDouble("PetEssence.DropChance", 0.15);
    public static ConfigOptionInt PET_REGEN_DELAY = new ConfigOptionInt("Pet.RegenDelay", 60);
    public static ConfigOptionDouble PET_SUMMON_DROP_CHANCE = new ConfigOptionDouble("PetSummon.DropChance", 0.25);

    /*
     * Config handler
     */

    private static Configuration c;

    /*
     * Config categories
     */
    public static final String CATEGORY_KEYBIND = "keybindings";
    public static final String CATEGORY_GRAPHICS = "graphics";
    public static final String CATEGORY_AUDIO = "audio";
    public static final String CATEGORY_ENCHANTMENT = "enchantment";
    public static final String CATEGORY_WORLD = "world";
    public static final String CATEGORY_WORLD_GEN = CATEGORY_WORLD + Configuration.CATEGORY_SPLITTER + "generation";
    public static final String CATEGORY_WORLD_STRUCTURE = CATEGORY_WORLD + Configuration.CATEGORY_SPLITTER + "structure";
    public static final String CATEGORY_BLOCK_PROPERTIES = "block" + Configuration.CATEGORY_SPLITTER + "properties";
    public static final String CATEGORY_ITEM_PROPERTIES = "item" + Configuration.CATEGORY_SPLITTER + "properties";
    public static final String CATEGORY_DURABILITY = "item" + Configuration.CATEGORY_SPLITTER + "durability";
    public static final String CATEGORY_PET = "pet";

    public static void init(File file) {

        c = new Configuration(file);

        try {
            c.load();

            /*
             * Misc
             */
            PET_ESSENCE_DROP_CHANCE.loadValue(c, CATEGORY_ITEM_PROPERTIES, "The probability of a mob dropping Pet Essence on death.");
            PET_REGEN_DELAY.loadValue(c, CATEGORY_PET, "The number of ticks pets take the regenerate 1 health.");
            PET_SUMMON_DROP_CHANCE.loadValue(c, CATEGORY_ITEM_PROPERTIES, "The probablity of a pet dropping a new summon item on death");
        }
        catch (Exception e) {
            LogHelper.severe("Oh noes!!! Couldn't load configuration file properly!");
        }
        finally {
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
