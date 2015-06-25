package net.silentchaos512.pets.client.renderer.entity;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.pets.entity.PetChicken;
import net.silentchaos512.pets.entity.PetMooshroom;

import org.lwjgl.opengl.GL11;

public class RenderPetMooshroom extends RenderPetCow {

    private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");

    public RenderPetMooshroom() {

        super();
    }

    public void doRender(PetMooshroom par1Entity, double par2, double par4, double par6, float par8, float par9) {

        super.doRender((EntityLiving) par1Entity, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {

        this.doRender((PetMooshroom) par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9) {

        this.doRender((PetMooshroom) par1Entity, par2, par4, par6, par8, par9);
    }
    
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {

        this.doRender((PetMooshroom) par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float par2) {

        super.renderEquippedItems(entity, par2);

        if (!entity.isChild()) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glPushMatrix();
            GL11.glScalef(1.0F, -1.0F, 1.0F);
            GL11.glTranslatef(0.2F, 0.4F, 0.5F);
            GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
            GL11.glTranslatef(0.1F, 0.0F, -0.6F);
            GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            ((ModelQuadruped) this.mainModel).head.postRender(0.0625F);
            GL11.glScalef(1.0F, -1.0F, 1.0F);
            GL11.glTranslatef(0.0F, 0.75F, -0.2F);
            GL11.glRotatef(12.0F, 0.0F, 1.0F, 0.0F);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {

        return cowTextures;
    }
}
