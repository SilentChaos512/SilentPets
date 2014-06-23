package silent.pets.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import silent.pets.core.util.LocalizationHelper;
import silent.pets.lib.Names;
import cpw.mods.fml.common.registry.GameRegistry;


public class PetWand extends ItemSG {

    public PetWand() {
        
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabTools);
        setUnlocalizedName(Names.PET_WAND);
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        
        // TODO Add details about state
        
        if (stack.stackTagCompound == null) {
            list.add(EnumChatFormatting.ITALIC + LocalizationHelper.getItemDescription(itemName, 0));
        }
    }
    
    @Override
    public void addRecipes() {
        
        GameRegistry.addShapedRecipe(new ItemStack(this), "p", "g", "g", 'p', MultiItem.getStack(Names.PET_ESSENCE), 'g', Items.gold_ingot);
    }
}
