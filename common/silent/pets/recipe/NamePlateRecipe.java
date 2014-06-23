package silent.pets.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import silent.pets.item.NamePlate;


public class NamePlateRecipe implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {

        int numNamePlates = 0;
        int numOtherItems = 0;
        
        ItemStack stack, namePlate = null, otherItem = null;
        
        // Count valid ingredients and look for invalid
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            stack = inventoryCrafting.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() instanceof NamePlate) {
                    ++numNamePlates;
                    namePlate = stack;
                }
                else {
                    ++numOtherItems;
                    otherItem = stack;
                }
            }
        }
        
        return numNamePlates == 1 && numOtherItems == 1;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack stack, namePlate = null, otherItem = null;
        
        // Find ingredients.
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            stack = inventoryCrafting.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() instanceof NamePlate) {
                    namePlate = stack;
                }
                else {
                    otherItem = stack;
                }
            }
        }
        
        if (namePlate == null || otherItem == null || !namePlate.hasDisplayName()) {
            return null;
        }
        
        ItemStack result = otherItem.copy();
        result.stackSize = 1;
        result.setStackDisplayName(namePlate.getDisplayName());
        
        return result;
    }

    @Override
    public int getRecipeSize() {

        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput() {

        // TODO Auto-generated method stub
        return null;
    }

}
