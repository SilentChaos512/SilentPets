package silent.pets.item;

import net.minecraft.item.crafting.IRecipe;
import silent.pets.core.registry.SRegistry;
import silent.pets.lib.Names;
import silent.pets.recipe.NamePlateRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    
    public final static IRecipe recipeNamePlate = new NamePlateRecipe();

	public static void init() {
		
	    SRegistry.registerItem(PetWand.class, Names.PET_WAND);
	    SRegistry.registerItem(MultiItem.class, Names.MULTI_ITEM);
	    SRegistry.registerItem(PetSummon.class, Names.PET_SUMMON);
	    SRegistry.registerItem(NamePlate.class, Names.NAME_PLATE);
	}
	
    public static void initItemRecipes() {

        GameRegistry.addRecipe(recipeNamePlate);
    }

    public static void addRandomChestGenLoot() {

        // TODO Auto-generated method stub
        
    }

}
