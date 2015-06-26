package net.silentchaos512.pets.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.silentchaos512.pets.lib.PetStats;

public class PetCat extends EntityOcelot {

<<<<<<< HEAD
  public final static int DATA_SKIN = 21;

  public PetCat(World world) {

    super(world);

    // entityName = "cat";
    // this.stats = PetStats.cat;
    // this.applyPetStats();

    this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
  }
=======
    public final static int DATA_SKIN = 21;

    public PetCat(World world) {

        super(world);

        entityName = "cat";
        this.stats = PetStats.cat;
        this.applyPetStats();

        this.setSize(0.6F, 0.8F);
        this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
        
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.tasks.addTask(6, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

    @Override
    protected void entityInit() {

        super.entityInit();
        this.dataWatcher.addObject(DATA_SKIN, Byte.valueOf((byte) 0));
    }
>>>>>>> parent of df2b211... Config updates, prepping for rewrite.

    @Override
    protected void applyEntityAttributes() {

<<<<<<< HEAD
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(PetStats.cat.health);
    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(PetStats.cat.speed);
    this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(PetStats.cat.damage);
  }
=======
        this.stats = PetStats.cat;
        super.applyEntityAttributes();
    }

    @Override
    protected void fall(float p_70069_1_) {

    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {

        Block.SoundType soundtype = p_145780_4_.stepSound;

        if (this.worldObj.getBlock(p_145780_1_, p_145780_2_ + 1, p_145780_3_) == Blocks.snow_layer) {
            soundtype = Blocks.snow_layer.stepSound;
            this.playSound(soundtype.getStepResourcePath(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
        }
        else if (!p_145780_4_.getMaterial().isLiquid()) {
            this.playSound(soundtype.getStepResourcePath(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tags) {

        super.writeEntityToNBT(tags);
        tags.setInteger("CatType", this.getTameSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tags) {

        super.readEntityFromNBT(tags);
        this.setTameSkin(tags.getInteger("CatType"));
    }

    @Override
    protected String getLivingSound() {

        return (this.isInLove() ? "mob.cat.purr" : (this.rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow"));
    }

    @Override
    protected String getHurtSound() {

        return "mob.cat.hitt";
    }

    @Override
    protected String getDeathSound() {

        return "mob.cat.hitt";
    }

    public int getTameSkin() {

        return this.dataWatcher.getWatchableObjectByte(DATA_SKIN);
    }

    public void setTameSkin(int value) {

        this.dataWatcher.updateObject(DATA_SKIN, Byte.valueOf((byte) value));
    }
>>>>>>> parent of df2b211... Config updates, prepping for rewrite.
}
