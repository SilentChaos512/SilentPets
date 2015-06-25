package net.silentchaos512.pets.item;

import net.minecraft.item.crafting.IRecipe;
import net.silentchaos512.pets.core.registry.SRegistry;
import net.silentchaos512.pets.lib.Names;
import net.silentchaos512.pets.recipe.NamePlateRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

  public static final IRecipe recipeNamePlate = new NamePlateRecipe();
  public static PetWand petWand;
  public static MultiItem multiItem;
  public static PetSummon petSummon;
  public static NamePlate namePlate;

  public static void init() {

    petWand = (PetWand) SRegistry.registerItem(PetWand.class, Names.PET_WAND);
    multiItem = (MultiItem) SRegistry.registerItem(MultiItem.class, Names.MULTI_ITEM);
    petSummon = (PetSummon) SRegistry.registerItem(PetSummon.class, Names.PET_SUMMON);
    namePlate = (NamePlate) SRegistry.registerItem(NamePlate.class, Names.NAME_PLATE);
  }

  public static void initItemRecipes() {

    GameRegistry.addRecipe(recipeNamePlate);
  }

  public static void addRandomChestGenLoot() {

  }
}
