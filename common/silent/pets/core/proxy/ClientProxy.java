package silent.pets.core.proxy;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelPig;
import silent.pets.client.model.ModelPetDog;
import silent.pets.client.model.ModelPetSheep1;
import silent.pets.client.model.ModelPetSheep2;
import silent.pets.client.renderer.entity.RenderPetChicken;
import silent.pets.client.renderer.entity.RenderPetCow;
import silent.pets.client.renderer.entity.RenderPetDog;
import silent.pets.client.renderer.entity.RenderPetMooshroom;
import silent.pets.client.renderer.entity.RenderPetPig;
import silent.pets.client.renderer.entity.RenderPetSheep;
import silent.pets.entity.PetChicken;
import silent.pets.entity.PetCow;
import silent.pets.entity.PetDog;
import silent.pets.entity.PetMooshroom;
import silent.pets.entity.PetPig;
import silent.pets.entity.PetSheep;
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
