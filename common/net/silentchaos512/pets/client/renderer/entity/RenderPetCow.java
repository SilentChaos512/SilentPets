package net.silentchaos512.pets.client.renderer.entity;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;


public class RenderPetCow extends RenderCow {

    private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
    
    public RenderPetCow() {

        super(new ModelCow(), 0.7F);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        
        return cowTextures;
    }
}
