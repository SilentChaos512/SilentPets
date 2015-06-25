package net.silentchaos512.pets.client.renderer.entity;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.silentchaos512.pets.core.util.LogHelper;
import net.silentchaos512.pets.entity.PetSheep;

import org.lwjgl.opengl.GL11;

public class RenderPetSheep extends RenderSheep {

    private static final ResourceLocation sheepTextures = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
    private final ModelBase model;

    public RenderPetSheep(ModelBase model1, ModelBase model2) {

        super(model1, model2, 0.7f);
        this.setRenderPassModel(model2);
        this.model = model1;
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
    
//    @Override
//    public void doRender(EntityLiving entity, double x, double y, double z, float par8, float par9) {
//        
//        super.doRender(entity, x, y, z, par8, par9);
//        renderEquippedItems(entity, par9);
//    }
//
//    protected void renderEquippedItems(EntityLiving entity, float par2) {
//
//        GL11.glColor3f(1.0F, 1.0F, 1.0F);
//        super.renderEquippedItems(entity, par2);
//        ItemStack itemstack = entity.getHeldItem();
//        ItemStack itemstack1 = entity.func_130225_q(3);
//        Item item;
//        float f1;
//
//        if (itemstack1 != null) {
//            GL11.glPushMatrix();
//            //this.model.boxList..postRender(0.0625F);
//            item = itemstack1.getItem();
//
//            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, EQUIPPED);
//            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack1, BLOCK_3D));
//
//            if (item instanceof ItemBlock) {
//                if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType())) {
//                    f1 = 0.625F;
//                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
//                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
//                    GL11.glScalef(f1, -f1, -f1);
//                }
//
//                this.renderManager.itemRenderer.renderItem(entity, itemstack1, 0);
//            }
//            else if (item == Items.skull) {
//                f1 = 1.0625F;
//                GL11.glScalef(f1, -f1, -f1);
//                String s = "";
//
//                if (itemstack1.hasTagCompound() && itemstack1.getTagCompound().hasKey("SkullOwner", 8)) {
//                    s = itemstack1.getTagCompound().getString("SkullOwner");
//                }
//
//                TileEntitySkullRenderer.field_147536_b.func_147530_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack1.getItemDamage(), s);
//            }
//
//            GL11.glPopMatrix();
//        }
//
//        if (itemstack != null && itemstack.getItem() != null) {
//            item = itemstack.getItem();
//            GL11.glPushMatrix();
//
//            if (this.mainModel.isChild) {
//                f1 = 0.5F;
//                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
//                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
//                GL11.glScalef(f1, f1, f1);
//            }
//
////            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
//            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
//
//            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, EQUIPPED);
//            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack, BLOCK_3D));
//
//            if (item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
//                f1 = 0.5F;
//                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
//                f1 *= 0.75F;
//                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
//                GL11.glScalef(-f1, -f1, f1);
//            }
//            else if (item == Items.bow) {
//                f1 = 0.625F;
//                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
//                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
//                GL11.glScalef(f1, -f1, f1);
//                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
//            }
//            else if (item.isFull3D()) {
//                f1 = 0.625F;
//
//                if (item.shouldRotateAroundWhenRendering()) {
//                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
//                }
//
//                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
//                GL11.glScalef(f1, -f1, f1);
//                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
//            }
//            else {
//                f1 = 0.375F;
//                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
//                GL11.glScalef(f1, f1, f1);
//                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
//                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
//            }
//
//            float f2;
//            float f3;
//            int i;
//
//            if (itemstack.getItem().requiresMultipleRenderPasses()) {
//                for (i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i) {
//                    int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
//                    f2 = (float) (j >> 16 & 255) / 255.0F;
//                    f3 = (float) (j >> 8 & 255) / 255.0F;
//                    float f4 = (float) (j & 255) / 255.0F;
//                    GL11.glColor4f(f2, f3, f4, 1.0F);
//                    this.renderManager.itemRenderer.renderItem(entity, itemstack, i);
//                }
//            }
//            else {
//                i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
//                float f5 = (float) (i >> 16 & 255) / 255.0F;
//                f2 = (float) (i >> 8 & 255) / 255.0F;
//                f3 = (float) (i & 255) / 255.0F;
//                GL11.glColor4f(f5, f2, f3, 1.0F);
//                this.renderManager.itemRenderer.renderItem(entity, itemstack, 0);
//            }
//
//            GL11.glPopMatrix();
//        }
//    }
}
