package net.silentchaos512.pets.entity;

import net.minecraft.entity.Entity;
import net.silentchaos512.pets.SilentPets;
import net.silentchaos512.pets.item.PetSummon;
import cpw.mods.fml.common.registry.EntityRegistry;


public class ModEntities {

    public static void init() {
        
        int k = 0;
        String s = "pet.";
        registerEntity(PetCow.class, s + "cow", ++k);
        registerEntity(PetSheep.class, s + "sheep", ++k);
        registerEntity(PetChicken.class, s + "chicken", ++k);
        registerEntity(PetPig.class, s + "pig", ++k);
        registerEntity(PetMooshroom.class, s + "mooshroom", ++k);
        registerEntity(PetDog.class, s + "dog", ++k);
        registerEntity(PetCat.class, s + "cat", ++k);
    }
    
    private static void registerEntity(Class<? extends EntityPet> entity, String name, int id) {
        
        EntityRegistry.registerModEntity(entity, name, id, SilentPets.instance, 80, 3, true);
        PetSummon.addPet(entity, name, id);
    }
}
