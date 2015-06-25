package net.silentchaos512.pets.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.silentchaos512.pets.core.util.LocalizationHelper;
import net.silentchaos512.pets.lib.Names;
import net.silentchaos512.pets.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;


public class PetWand extends ItemSG {
    
    public static enum State {
        
        NONE,
        ARMOR,
        TALK,
        CARRY;
    }

    public PetWand() {
        
        setMaxStackSize(1);
        setUnlocalizedName(Names.PET_WAND);
        icons = new IIcon[State.values().length];
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
        
        GameRegistry.addShapedRecipe(new ItemStack(this), "lpl", " g ", " g ", 'l', new ItemStack(Items.dye, 1, 4), 'p', MultiItem.getStack(Names.PET_ESSENCE_RAW), 'g', Items.gold_ingot);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        
        String s = super.getUnlocalizedName(stack);
        if (stack.getItemDamage() < State.values().length && stack.getItemDamage() != 0) {
            s += ".state." + State.values()[stack.getItemDamage()].name().toLowerCase();
        }
        return s;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        
        if (player.isSneaking()) {
            int k = stack.getItemDamage();
            if (++k >= State.values().length) {
                k = 1;
            }
            setState(stack, State.values()[k]);
        }
        
        return stack;
    }
    
    @Override
    public IIcon getIconFromDamage(int meta) {
        
        if (meta < icons.length) {
            return icons[meta];
        }
        else {
            return icons[0];
        }
    }
    
    @Override
    public void registerIcons(IIconRegister reg) {
        
        for (int i = 0; i < icons.length; ++i) {
            icons[i] = reg.registerIcon(Strings.RESOURCE_PREFIX + itemName + "_" + State.values()[i].name());
        }
    }
    
    public static State getState(ItemStack stack) {
        
        if (stack != null && stack.getItemDamage() < State.values().length) {
            return State.values()[stack.getItemDamage()];
        }
        else {
            return State.NONE;
        }
    }
    
    public static void setState(ItemStack stack, State state) {
        
        if (stack != null && stack.getItem() instanceof PetWand) {
            stack.setItemDamage(state.ordinal());
        }
    }
}
