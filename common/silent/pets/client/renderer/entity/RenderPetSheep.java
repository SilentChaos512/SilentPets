package silent.pets.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import silent.pets.entity.PetSheep;

public class RenderPetSheep extends RenderSheep {

    private static final ResourceLocation sheepTextures = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");

    public RenderPetSheep(ModelBase model1, ModelBase model2) {

        super(model1, model2, 0.7f);
        this.setRenderPassModel(model2);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {

        return shearedSheepTextures;
    }

    protected int shouldRenderPass(PetSheep entity, int par2, float par3) {

        if (par2 == 0 && !entity.getSheared()) {
            this.bindTexture(sheepTextures);
            int j = entity.getFleeceColor();
            GL11.glColor3f(EntitySheep.fleeceColorTable[j][0], EntitySheep.fleeceColorTable[j][1], EntitySheep.fleeceColorTable[j][2]);
            return 1;
        }
        else {
            return -1;
        }
    }
    
    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int par2, float par3) {
        
        return this.shouldRenderPass((PetSheep) entity, par2, par3);
    }
}
