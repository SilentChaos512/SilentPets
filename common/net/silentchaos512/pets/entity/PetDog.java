package net.silentchaos512.pets.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.silentchaos512.pets.entity.ai.EntityAIBegPet;
import net.silentchaos512.pets.lib.PetStats;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PetDog extends EntityPet {

    private float field_70926_e;
    private float field_70924_f;
    /** true is the wolf is wet else false */
    private boolean isShaking;
    private boolean field_70928_h;
    /**
     * This time increases while wolf is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    public PetDog(World world) {

        super(world);

        entityName = "wolf";
        this.stats = PetStats.dog;
        this.applyPetStats();

        this.setSize(0.6F, 0.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIBegPet(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
        this.setTamed(false);
    }

    @Override
    protected void applyEntityAttributes() {

        this.stats = PetStats.dog;
        super.applyEntityAttributes();
    }

    @Override
    protected void entityInit() {

        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(DATA_BEG, new Byte((byte) 0));
        this.dataWatcher.addObject(20, new Byte((byte) BlockColored.func_150032_b(1)));
    }

    @Override
    protected void updateAITick() {

        this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tags) {

        super.writeEntityToNBT(tags);
        tags.setBoolean("Angry", this.isAngry());
        tags.setByte("CollarColor", (byte) this.getCollarColor());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tags) {

        super.readEntityFromNBT(tags);
        this.setAngry(tags.getBoolean("Angry"));

        if (tags.hasKey("CollarColor", 99)) {
            this.setCollarColor(tags.getByte("CollarColor"));
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound() {

        return this.isAngry() ? "mob.wolf.growl" : (this.rand.nextInt(3) == 0 ? (this.isTamed()
                && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {

        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.worldObj.setEntityState(this, (byte) 8);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {

        super.onUpdate();
        this.field_70924_f = this.field_70926_e;

        if (this.func_70922_bv()) {
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        }
        else {
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
        }

        if (this.func_70922_bv()) {
            this.numTicksToChaseTarget = 10;
        }

        if (this.isWet()) {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        }
        else if ((this.isShaking || this.field_70928_h) && this.field_70928_h) {
            if (this.timeWolfIsShaking == 0.0F) {
                this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F) {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F) {
                float f = (float) this.boundingBox.minY;
                int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j) {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.worldObj.spawnParticle("splash", this.posX + (double) f1, (double) (f + 0.8F), this.posZ + (double) f2,
                            this.motionX, this.motionY, this.motionZ);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean getWolfShaking() {

        return this.isShaking;
    }

    /**
     * Used when calculating the amount of shading to apply while the wolf is shaking.
     */
    @SideOnly(Side.CLIENT)
    public float getShadingWhileShaking(float p_70915_1_) {

        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
    }

    @SideOnly(Side.CLIENT)
    public float getShakeAngle(float p_70923_1_, float p_70923_2_) {

        float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;

        if (f2 < 0.0F) {
            f2 = 0.0F;
        }
        else if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        return MathHelper.sin(f2 * (float) Math.PI) * MathHelper.sin(f2 * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
    }

    public float getEyeHeight() {

        return this.height * 0.8F;
    }

    @SideOnly(Side.CLIENT)
    public float getInterestedAngle(float p_70917_1_) {

        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * p_70917_1_) * 0.15F * (float) Math.PI;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed() {

        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
    public boolean interact(EntityPlayer player) {

        ItemStack itemstack = player.inventory.getCurrentItem();

        if (this.isTamed()) {
            if (itemstack != null) {
                // if (itemstack.getItem() instanceof ItemFood) {
                // ItemFood itemfood = (ItemFood) itemstack.getItem();
                //
                // if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
                // if (!entity.capabilities.isCreativeMode) {
                // --itemstack.stackSize;
                // }
                //
                // this.heal((float) itemfood.func_150905_g(itemstack));
                //
                // if (itemstack.stackSize <= 0) {
                // entity.inventory.setInventorySlotContents(entity.inventory.currentItem, (ItemStack) null);
                // }
                //
                // return true;
                // }
                // }
                if (itemstack.getItem() == Items.dye) {
                    int i = BlockColored.func_150032_b(itemstack.getItemDamage());

                    if (i != this.getCollarColor()) {
                        this.setCollarColor(i);

                        if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                        }

                        return true;
                    }
                }
            }
        }

        return super.interact(player);
    }

    @SideOnly(Side.CLIENT)
    public float getTailRotation() {

        return this.isAngry() ? 1.5393804F : (this.isTamed() ? (0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F)
                * (float) Math.PI : ((float) Math.PI / 5F));
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    public boolean isAngry() {

        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    /**
     * Sets whether this wolf is angry or not.
     */
    public void setAngry(boolean p_70916_1_) {

        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70916_1_) {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
        }
        else {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
        }
    }

    /**
     * Return this wolf's collar color.
     */
    public int getCollarColor() {

        return this.dataWatcher.getWatchableObjectByte(20) & 15;
    }

    /**
     * Set this wolf's collar color.
     */
    public void setCollarColor(int p_82185_1_) {

        this.dataWatcher.updateObject(20, Byte.valueOf((byte) (p_82185_1_ & 15)));
    }

    public boolean func_70922_bv() {

        return this.dataWatcher.getWatchableObjectByte(DATA_BEG) == 1;
    }
}
