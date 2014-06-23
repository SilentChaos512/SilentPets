package silent.pets.core.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryHelper {
    
    public static boolean isItemBlock(Item item, Block block) {
        
        return Block.getIdFromBlock(block) == Item.getIdFromItem(item);
    }
    
    public static boolean isStackBlock(ItemStack stack, Block block) {
        
        return Block.getIdFromBlock(block) == Item.getIdFromItem(stack.getItem());
    }
}
