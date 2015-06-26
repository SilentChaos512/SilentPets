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
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.silentchaos512.pets.lib.PetStats;

public class PetCat extends EntityOcelot {

  public final static int DATA_SKIN = 21;

  public PetCat(World world) {

    super(world);

    // entityName = "cat";
    // this.stats = PetStats.cat;
    // this.applyPetStats();

    this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
  }

  @Override
  protected void applyEntityAttributes() {

    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(PetStats.cat.health);
    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(PetStats.cat.speed);
    this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(PetStats.cat.damage);
  }
}
