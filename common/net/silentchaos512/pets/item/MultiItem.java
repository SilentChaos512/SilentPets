package net.silentchaos512.pets.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.pets.core.registry.SRegistry;
import net.silentchaos512.pets.core.util.InventoryHelper;
import net.silentchaos512.pets.core.util.LocalizationHelper;
import net.silentchaos512.pets.core.util.RecipeHelper;
import net.silentchaos512.pets.lib.Names;
import net.silentchaos512.pets.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class MultiItem extends ItemSG {

    public final static String[] names = { Names.PET_ESSENCE_RAW, Names.PET_ESSENCE, Names.PIG_LEATHER, Names.MOSSY_ESSENCE };

    public MultiItem() {

        super();

        icons = new IIcon[names.length];
        setMaxStackSize(64);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(Names.MULTI_ITEM);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {

        // list.add(EnumChatFormatting.DARK_GRAY + LocalizationHelper.getItemDescription(Names.MULTI_ITEM, 0));
        list.add(EnumChatFormatting.ITALIC + LocalizationHelper.getItemDescription(names[stack.getItemDamage()], 0));
    }

    @Override
    public void addRecipes() {

        RecipeHelper.addSurround(getStack(Names.PET_ESSENCE), new ItemStack(Items.gold_ingot), getStack(Names.PET_ESSENCE_RAW));
        GameRegistry.addSmelting(getStack(Names.PIG_LEATHER), new ItemStack(Items.leather), 0.2f);
        RecipeHelper.addSurround(getStack(Names.MOSSY_ESSENCE), getStack(Names.PET_ESSENCE), Blocks.mossy_cobblestone);
        // Saddle
        GameRegistry.addShapedRecipe(new ItemStack(Items.saddle), "l  ", "lll", "iml", 'l', Items.leather, 'i', Items.iron_ingot, 'm',
                getStack(Names.MOSSY_ESSENCE));
    }

    @Override
    public void addOreDict() {

        if (!InventoryHelper.oreDictContainsKey(Strings.ORE_DICT_LEATHER)) {
            OreDictionary.registerOre(Strings.ORE_DICT_LEATHER, Items.leather);
        }
        OreDictionary.registerOre(Strings.ORE_DICT_LEATHER, getStack(Names.PIG_LEATHER));
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
