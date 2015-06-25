package net.silentchaos512.pets.core.proxy;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelPig;
import net.silentchaos512.pets.client.model.ModelPetCat;
import net.silentchaos512.pets.client.model.ModelPetDog;
import net.silentchaos512.pets.client.model.ModelPetSheep1;
import net.silentchaos512.pets.client.model.ModelPetSheep2;
import net.silentchaos512.pets.client.renderer.entity.RenderPetCat;
import net.silentchaos512.pets.client.renderer.entity.RenderPetChicken;
import net.silentchaos512.pets.client.renderer.entity.RenderPetCow;
import net.silentchaos512.pets.client.renderer.entity.RenderPetDog;
import net.silentchaos512.pets.client.renderer.entity.RenderPetMooshroom;
import net.silentchaos512.pets.client.renderer.entity.RenderPetPig;
import net.silentchaos512.pets.client.renderer.entity.RenderPetSheep;
import net.silentchaos512.pets.entity.PetCat;
import net.silentchaos512.pets.entity.PetChicken;
import net.silentchaos512.pets.entity.PetCow;
import net.silentchaos512.pets.entity.PetDog;
import net.silentchaos512.pets.entity.PetMooshroom;
import net.silentchaos512.pets.entity.PetPig;
import net.silentchaos512.pets.entity.PetSheep;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
    public void registerRenderers() {

        registerRenderersBlocks();
        registerRenderersItems();
        registerRenderersMobs();
        registerRenderersProjectiles();
    }

	private void registerRenderersProjectiles() {
		// TODO Auto-generated method stub
		
	}

	private void registerRenderersMobs() {

	    RenderingRegistry.registerEntityRenderingHandler(PetCat.class, new RenderPetCat(new ModelPetCat()));
	    RenderingRegistry.registerEntityRenderingHandler(PetChicken.class, new RenderPetChicken(new ModelChicken()));
	    RenderingRegistry.registerEntityRenderingHandler(PetCow.class, new RenderPetCow());
	    RenderingRegistry.registerEntityRenderingHandler(PetDog.class, new RenderPetDog(new ModelPetDog(), new ModelPetDog()));
	    RenderingRegistry.registerEntityRenderingHandler(PetMooshroom.class, new RenderPetMooshroom());
	    RenderingRegistry.registerEntityRenderingHandler(PetPig.class, new RenderPetPig(new ModelPig(), new ModelPig(0.5f)));
	    RenderingRegistry.registerEntityRenderingHandler(PetSheep.class, new RenderPetSheep(new ModelPetSheep2(), new ModelPetSheep1()));
	}

	private void registerRenderersItems() {
		// TODO Auto-generated method stub
		
	}

	private void registerRenderersBlocks() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void registerTileEntities() {

        super.registerTileEntities();
	}
	
	@Override
    public void registerKeyHandlers() {

		// TODO
    }
}
