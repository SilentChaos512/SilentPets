package net.silentchaos512.pets.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.silentchaos512.pets.lib.PetStats;
import net.silentchaos512.pets.lib.Strings;

public class PetPig extends EntityPet {

  public final static int DATA_SADDLED = 18;

  public PetPig(World world) {

    super(world);

    entityName = "pig";

    this.setSize(0.9F, 0.9F);
    this.getNavigator().setAvoidsWater(true);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, this.aiSit);
    this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
    this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
    this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
    this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
    this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
    this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
  }

  @Override
  protected void applyEntityAttributes() {

    super.applyEntityAttributes();
    PetStats stats = PetStats.pig;
    this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage)
        .setBaseValue(stats.damage);
    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(stats.health);
    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(stats.speed);
  }

  @Override
  public boolean canBeSteered() {

    return true;
  }

  @Override
  protected void dropFewItems(boolean par1, int par2) {

    if (this.getSaddled()) {
      this.dropItem(Items.saddle, 1);
    }
  }

  @Override
  protected void entityInit() {

    super.entityInit();
    this.dataWatcher.addObject(DATA_SADDLED, Byte.valueOf((byte) 0));
  }

  @Override
  public String getHurtSound() {

    return "mob.pig.say";
  }

  public boolean getSaddled() {

    return (this.dataWatcher.getWatchableObjectByte(DATA_SADDLED) & 1) != 0;
  }

  @Override
  public boolean interact(EntityPlayer player) {

    if (super.interact(player)) {
      return true;
    } else if (this.getSaddled() && !this.worldObj.isRemote
        && (this.riddenByEntity == null || this.riddenByEntity == player)) {
      player.mountEntity(this);
      return true;
    } else if (!this.getSaddled() && player.inventory.getCurrentItem() != null
        && player.inventory.getCurrentItem().getItem() == Items.saddle) {
      this.setSaddled(true);
      this.worldObj.playSoundAtEntity(this, "mob.horse.leather", 0.5f, 1.0f);
      --player.inventory.getCurrentItem().stackSize;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void readEntityFromNBT(NBTTagCompound tags) {

    super.readEntityFromNBT(tags);
    this.setSaddled(tags.getBoolean("Saddle"));
  }

  public void setSaddled(boolean value) {

    this.dataWatcher.updateObject(DATA_SADDLED, Byte.valueOf((byte) (value ? 1 : 0)));
  }

  @Override
  public void writeEntityToNBT(NBTTagCompound tags) {

    super.writeEntityToNBT(tags);
    tags.setBoolean(Strings.NBT_PET_SADDLED, this.getSaddled());
  }
}
