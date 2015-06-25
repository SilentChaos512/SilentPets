package net.silentchaos512.pets.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.pets.entity.PetCat;

import org.lwjgl.opengl.GL11;

public class RenderPetCat extends RenderLiving {

    private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
    private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
    private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
    private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");

    public RenderPetCat(ModelBase model) {

        super(model, 0.4f);
    }

    public void doRender(PetCat cat, double x, double y, double z, float f1, float f2) {

        super.doRender((EntityLiving) cat, x, y, z, f1, f2);
    }

    protected ResourceLocation getEntityTexture(PetCat cat) {

        switch (cat.getTameSkin()) {
            case 0:
            default:
                return ocelotTextures;
            case 1:
                return blackOcelotTextures;
            case 2:
                return redOcelotTextures;
            case 3:
                return siameseOcelotTextures;
        }
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(PetCat cat, float f1) {

        super.preRenderCallback(cat, f1);

        if (cat.isTamed()) {
            GL11.glScalef(0.8F, 0.8F, 0.8F);
        }
    }

    public void doRender(EntityLiving entityLiving, double x, double y, double z, float f1, float f2) {

        this.doRender((PetCat) entityLiving, x, y, z, f1, f2);
    }

    protected void preRenderCallback(EntityLivingBase entityLivingBase, float f) {

        this.preRenderCallback((PetCat) entityLivingBase, f);
    }

    public void doRender(EntityLivingBase entityLivingBase, double x, double y, double z, float f1, float f2) {

        this.doRender((PetCat) entityLivingBase, x, y, z, f1, f2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {

        return this.getEntityTexture((PetCat) entity);
    }

    public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {

        this.doRender((PetCat) entity, x, y, z, f1, f2);
    }
}
