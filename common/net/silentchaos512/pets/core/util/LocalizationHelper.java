package net.silentchaos512.pets.core.util;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.silentchaos512.pets.SilentPets;
import net.silentchaos512.pets.entity.EntityPet;

public class LocalizationHelper {

    public final static String BLOCKS_PREFIX = "tile.silentpets:";
    public final static String ENTITY_PREFIX = "entity.SilentPets.";
    public final static String ITEM_PREFIX = "item.silentpets:";
    public final static String MISC_PREFIX = "misc.silentpets:";
    public final static String TALK_PREFIX = "talk.silentpets:";
    
    public final static int MAX_TALK_INDEX = 10;

    public static String getBlockDescription(String blockName, int index) {

        return getLocalizedString(getBlockDescriptionKey(blockName, index));
    }

    public static String getBlockDescriptionKey(String blockName, int index) {

        return BLOCKS_PREFIX + blockName + ".desc" + (index > 0 ? index : "");
    }

    public static String getEntityName(String entityName) {

        return getLocalizedString(ENTITY_PREFIX + entityName + ".name");
    }

    public static String getItemDescription(String itemName, int index) {

        return getLocalizedString(getItemDescriptionKey(itemName, index));
    }

    public static String getItemDescriptionKey(String itemName, int index) {

        return ITEM_PREFIX + itemName + ".desc" + (index > 0 ? index : "");
    }

    public static String getLocalizedString(String key) {

        return StatCollector.translateToLocal(key).trim();
    }

    public static String getMiscText(String key) {

        return getLocalizedString(MISC_PREFIX + key);
    }

    public static String getOtherBlockKey(String blockName, String key) {

        return getLocalizedString(BLOCKS_PREFIX + blockName + "." + key);
    }

    public static String getOtherItemKey(String itemName, String key) {

        return getLocalizedString(ITEM_PREFIX + itemName + "." + key);
    }

    public static String getPetTalk(EntityPet pet, String key) {

        int k = MAX_TALK_INDEX;
        String s1 = TALK_PREFIX + key + SilentPets.instance.random.nextInt(k);
        String s2 = getLocalizedString(s1);
        while (s1.equals(s2) && k > 1) {
            s1 = TALK_PREFIX + key + SilentPets.instance.random.nextInt(--k);
            s2 = getLocalizedString(s1);
        }

        return EnumChatFormatting.ITALIC + "<" + pet.getCustomNameTag() + "> " + s2;
    }

    public static String getPetTalkString(String key) {

        int k = MAX_TALK_INDEX;
        String s1 = TALK_PREFIX + key + SilentPets.instance.random.nextInt(k);
        String s2 = getLocalizedString(s1);
        while (s1.equals(s2) && k > 1) {
            s1 = TALK_PREFIX + key + SilentPets.instance.random.nextInt(--k);
            s2 = getLocalizedString(s1);
        }

        return s2;
    }
}
