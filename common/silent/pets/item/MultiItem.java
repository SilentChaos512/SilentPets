package silent.pets.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import silent.pets.SilentPets;
import silent.pets.core.registry.SRegistry;
import silent.pets.core.util.LocalizationHelper;
import silent.pets.core.util.RecipeHelper;
import silent.pets.lib.Names;
import silent.pets.lib.Strings;


public class MultiItem extends ItemSG {

    public final static String[] names = { Names.PET_ESSENCE_RAW, Names.PET_ESSENCE };
    
    public MultiItem() {
        
        super();
        
        icons = new IIcon[names.length];
        setMaxStackSize(64);
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabMaterials);
        setUnlocalizedName(Names.MULTI_ITEM);
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {

        //list.add(EnumChatFormatting.DARK_GRAY + LocalizationHelper.getItemDescription(Names.MULTI_ITEM, 0));
        list.add(EnumChatFormatting.ITALIC + LocalizationHelper.getItemDescription(names[stack.getItemDamage()], 0));
    }
    
    @Override
    public void addRecipes() {
        
        RecipeHelper.addSurround(getStack(Names.PET_ESSENCE), new ItemStack(Items.gold_ingot), getStack(Names.PET_ESSENCE_RAW));
    }
    
    public static ItemStack getStack(String name) {

        for (int i = 0; i < names.length; ++i) {
            if (names[i].equals(name)) {
                return new ItemStack(SRegistry.getItem(Names.MULTI_ITEM), 1, i);
            }
        }

        return null;
    }

    public static ItemStack getStack(String name, int count) {

        for (int i = 0; i < names.length; ++i) {
            if (names[i].equals(name)) {
                return new ItemStack(SRegistry.getItem(Names.MULTI_ITEM), count, i);
            }
        }

        return null;
    }
    
    public static int getMetaFor(String name) {
        
        for (int i = 0; i < names.length; ++i) {
            if (name.equals(names[i])) {
                return i;
            }
        }
        
        return -1;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {

        if (stack.getItemDamage() < names.length) {
            return getUnlocalizedName(names[stack.getItemDamage()]);
        }
        else {
            return getUnlocalizedName(Names.MULTI_ITEM + stack.getItemDamage());
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {

        for (int i = 0; i < names.length; ++i) {
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + names[i]);
        }
    }
}
