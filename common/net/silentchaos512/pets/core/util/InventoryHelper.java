package net.silentchaos512.pets.core.util;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryHelper {

    public static final String[] dyeNames = new String[] {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"};
    
    public static int getDyeMetaFromOreDict(ItemStack stack) {

        if (stack != null) {
            if (stack.getItem() == Items.dye) {
                return stack.getItemDamage();
            }
            else {
                String oreDictName = "";
                
                for (int i : OreDictionary.getOreIDs(stack)) {
                    if (OreDictionary.getOreName(i).substring(0, 3).equals("dye")) {
                        oreDictName = OreDictionary.getOreName(i);
                        break;
                    }
                }
                
                for (int i = 0; i < dyeNames.length; ++i) {
                    if (oreDictName.equals("dye" + dyeNames[i])) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    public static boolean isDye(ItemStack stack) {

        if (stack != null) {
            if (stack.getItem() == Items.dye) {
                return true;
            }

            int[] ids = OreDictionary.getOreIDs(stack);
            for (int i : ids) {
                if (OreDictionary.getOreName(i).substring(0, 3).equals("dye")) {
                    LogHelper.derp();
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isItemBlock(Item item, Block block) {

        return Block.getIdFromBlock(block) == Item.getIdFromItem(item);
    }

    public static boolean isStackBlock(ItemStack stack, Block block) {

        return Block.getIdFromBlock(block) == Item.getIdFromItem(stack.getItem());
    }
    
    public static boolean oreDictContainsKey(String key) {
        
        for (String s : OreDictionary.getOreNames()) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
