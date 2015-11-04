package net.silentchaos512.pets.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.silentchaos512.pets.SilentPets;
import net.silentchaos512.pets.configuration.Config;
import net.silentchaos512.pets.lib.Names;
import cpw.mods.fml.common.registry.GameRegistry;

public class NamePlate extends ItemSG {

    public NamePlate() {

        setMaxStackSize(1);
        setMaxDamage(Config.NAME_PLATE_MAX_DAMAGE.value);
        setUnlocalizedName(Names.NAME_PLATE);
    }

    @Override
    public void addRecipes() {

        GameRegistry.addShapedRecipe(new ItemStack(this), "iii", "pdp", "iii", 'i', Items.iron_ingot, 'p',
                MultiItem.getStack(Names.PET_ESSENCE_RAW), 'd', Items.diamond);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {

        if (!stack.hasDisplayName()) {
            return false;
        }
        else if (entity instanceof EntityLiving) {
            // Name plate rename mob
            EntityLiving entityLiving = (EntityLiving) entity;
            if (entityLiving.hasCustomNameTag() && entityLiving.getCustomNameTag().equals(stack.getDisplayName())) {
                return false;
            }
            entityLiving.setCustomNameTag(stack.getDisplayName());
            entityLiving.func_110163_bv();
            stack.attemptDamageItem(1, SilentPets.instance.random);
            return true;
        }
        else {
            return super.itemInteractionForEntity(stack, player, entity);
        }
    }
}
