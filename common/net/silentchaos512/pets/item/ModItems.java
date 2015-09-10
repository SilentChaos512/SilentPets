package net.silentchaos512.pets.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.silentchaos512.pets.core.registry.SRegistry;
import net.silentchaos512.pets.item.PetSummon.PetData;
import net.silentchaos512.pets.lib.Names;
import net.silentchaos512.pets.recipe.NamePlateRecipe;

public class ModItems {

  public static PetWand petWand;
  public static MultiItem multiItem;
  public static PetSummon petSummon;
  public static NamePlate namePlate;
  public static PetCarrier petCarrier;

  public static final IRecipe recipeNamePlate = new NamePlateRecipe();

  public static void init() {

    petWand = (PetWand) SRegistry.registerItem(PetWand.class, Names.PET_WAND);
    multiItem = (MultiItem) SRegistry.registerItem(MultiItem.class, Names.MULTI_ITEM);
    petSummon = (PetSummon) SRegistry.registerItem(PetSummon.class, Names.PET_SUMMON);
    namePlate = (NamePlate) SRegistry.registerItem(NamePlate.class, Names.NAME_PLATE);
    petCarrier = (PetCarrier) SRegistry.registerItem(PetCarrier.class, Names.PET_CARRIER);
  }

  public static void initItemRecipes() {

    GameRegistry.addRecipe(recipeNamePlate);
  }
  
  public static final String[] chestGenLocations = new String[] { ChestGenHooks.DUNGEON_CHEST,
      ChestGenHooks.MINESHAFT_CORRIDOR, ChestGenHooks.STRONGHOLD_CORRIDOR,
      ChestGenHooks.STRONGHOLD_LIBRARY };

  public static void addRandomChestGenLoot() {

    // For each chest type above
    for (String chest : chestGenLocations) {
      // Add summoners
      for (int i = 0; i < PetSummon.PETS.size(); ++i) {
        PetData petData = PetSummon.PETS.get(i);
        if (petData != null) {
          ItemStack stack = new ItemStack(petSummon, 1, i);
          WeightedRandomChestContent content = new WeightedRandomChestContent(stack, 1, 1, 15);
          ChestGenHooks.getInfo(chest).addItem(content);
        }
      }
      // Add name plate
      ItemStack stack = new ItemStack(namePlate, 1);
      WeightedRandomChestContent content = new WeightedRandomChestContent(stack, 1, 1, 5);
      ChestGenHooks.getInfo(chest).addItem(content);
    }
  }
}
