package silent.pets.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;
import silent.pets.entity.PetPig;

public class RenderPetPig extends RenderLiving {

    private static final ResourceLocation saddledPigTextures = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");

    public RenderPetPig(ModelBase model1, ModelBase model2) {

        super(model1, 0.7f);
        this.setRenderPassModel(model2);
    }

    protected int shouldRenderPass(PetPig par1EntityPig, int par2, float par3) {

        if (par2 == 0 && par1EntityPig.getSaddled()) {
            this.bindTexture(saddledPigTextures);
            return 1;
        }
        else {
            return -1;
        }
    }
    
    protected ResourceLocation getEntityTexture(PetPig par1EntityPig)
    {
        return pigTextures;
    }
    
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldRenderPass((PetPig)par1EntityLivingBase, par2, par3);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((PetPig)par1Entity);
    }
}
