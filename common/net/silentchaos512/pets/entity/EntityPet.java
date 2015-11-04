package net.silentchaos512.pets.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.silentchaos512.pets.configuration.Config;
import net.silentchaos512.pets.core.util.InventoryHelper;
import net.silentchaos512.pets.core.util.LocalizationHelper;
import net.silentchaos512.pets.core.util.LogHelper;
import net.silentchaos512.pets.core.util.PlayerHelper;
import net.silentchaos512.pets.item.ModItems;
import net.silentchaos512.pets.item.PetWand;
import net.silentchaos512.pets.lib.Names;
import net.silentchaos512.pets.lib.PetStats;

public class EntityPet extends EntityTameable {

  public final static int DATA_BEG = 30;
  public final static int DATA_TALK = 31;

  protected String entityName = "null";
  // public PetStats stats = PetStats.generic;

  protected int timerHealthRegen = Config.PET_REGEN_DELAY.value;

  public EntityPet(World world) {

    super(world);
  }

  // protected void applyPetStats() {
  //
  // this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.stats.health);
  // this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.stats.speed);
  // }

  @Override
  public boolean attackEntityAsMob(Entity entity) {

    if (!Config.PET_IS_COSMETIC.value) {
      return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this
          .getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
    }
    return false;
  }

  @Override
  public boolean attackEntityFrom(DamageSource source, float amount) {

    if (isEntityInvulnerable() || Config.PET_IS_COSMETIC.value) {
      return false;
    } else {
      // Friendly fire disabled?
      if (!Config.PET_FRIENDLY_FIRE.value && source.getEntity() instanceof EntityPlayer) {
        if (getOwner() == source.getEntity()) {
          return false;
        }
      }
      // reset regen timer
      timerHealthRegen = Config.PET_INITIAL_REGEN_DELAY.value;

      // stop sitting
      Entity entity = source.getEntity();
      this.aiSit.setSitting(false);

      if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
        amount = (amount + 1.0f) / 2.0f;
      }

      return super.attackEntityFrom(source, amount);
    }
  }

  @Override
  public EntityAgeable createChild(EntityAgeable var1) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void entityInit() {

    super.entityInit();
    this.dataWatcher.addObject(DATA_TALK, Byte.valueOf((byte) 1));
  }

  public void func_70918_i(boolean value) {

    if (value) {
      this.dataWatcher.updateObject(DATA_BEG, Byte.valueOf((byte) 1));
    } else {
      this.dataWatcher.updateObject(DATA_BEG, Byte.valueOf((byte) 0));
    }
  }

  @Override
  protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_,
      Block p_145780_4_) {

    this.playSound("mob." + entityName + ".step", 0.15F, 1.0F);
  }

  @Override
  protected String getDeathSound() {

    return "mob." + entityName + ".hurt";
  }

  @Override
  protected String getHurtSound() {

    return "mob." + entityName + ".hurt";
  }

  @Override
  protected String getLivingSound() {

    return "mob." + entityName + ".say";
  }

  @Override
  protected float getSoundVolume() {

    return this.dataWatcher.getWatchableObjectByte(DATA_TALK) == 0 ? 0.0f : 0.4f;
  }

  public boolean getAllowTalk() {

    return this.dataWatcher.getWatchableObjectByte(DATA_TALK) != 0;
  }

  public void setAllowTalk(boolean talkAllowed) {

    this.dataWatcher.updateObject(DATA_TALK, Byte.valueOf((byte) (talkAllowed ? 1 : 0)));
  }

  @Override
  public int getTotalArmorValue() {

    ItemStack stack = this.getEquipmentInSlot(1);
    if (stack != null) {
      ItemArmor helmet = (ItemArmor) stack.getItem();
      int k = 0;
      for (int i = 0; i < 4; ++i) {
        k += helmet.getArmorMaterial().getDamageReductionAmount(i);
      }
      return k;
    } else {
      return 0;
    }
  }

  @Override
  public boolean interact(EntityPlayer player) {

    ItemStack stack = player.inventory.getCurrentItem();

    if (this.isTamed()) {
      if (stack != null) {
        // Pet wand?
        if (stack.getItem() instanceof PetWand) {
          handlePetWand(player, stack);
          return true;
        }
        // Heal with food?
        // Dye?
        // Armor?
        if (stack.getItem() instanceof ItemArmor && this.getEquipmentInSlot(1) == null) {
          ItemArmor armor = (ItemArmor) stack.getItem();
          if (armor.armorType == 0) {
            this.setCurrentItemOrArmor(1, stack);
            // Display newly equipped armor!
            if (this.worldObj.isRemote) {
              String armorName = StatCollector
                  .translateToLocal(stack.getItem().getUnlocalizedName(stack) + ".name");
              String s = LocalizationHelper.getOtherItemKey(Names.PET_WAND,
                  "state.armor.isWearing");
              s = String.format(s, this.getPetName(), armorName, this.getTotalArmorValue());
              PlayerHelper.addChatMessage(player, s);
            }
            --stack.stackSize;
            return true;
          }
        }
      }

      if (this.func_152114_e(player) && !this.worldObj.isRemote && !this.isBreedingItem(stack)) {
        this.aiSit.setSitting(!this.isSitting());
        if (this.isSitting()) {
          PlayerHelper.addChatMessage(player, LocalizationHelper.getPetTalk(this, "following"));
        } else {
          PlayerHelper.addChatMessage(player, LocalizationHelper.getPetTalk(this, "sitting"));
        }
        this.isJumping = false;
        this.setPathToEntity((PathEntity) null);
        this.setTarget((Entity) null);
        this.setAttackTarget((EntityLivingBase) null);
      }
    }

    return super.interact(player);
  }

  private void handlePetWand(EntityPlayer player, ItemStack stack) {

    PetWand.State state = PetWand.getState(stack);

    if (state == PetWand.State.ARMOR) {
      ItemStack helmet = this.getEquipmentInSlot(1);
      if (!player.worldObj.isRemote && player.isSneaking()) {
        // Remove armor
        if (helmet != null) {
          helmet.stackSize = 1;
          PlayerHelper.addItemToInventoryOrDrop(player, helmet);
          this.setCurrentItemOrArmor(1, (ItemStack) null);
        }
      } else if (player.worldObj.isRemote && !player.isSneaking()) {
        // Display armor, if any
        if (helmet != null) {
          String armorName = StatCollector
              .translateToLocal(helmet.getItem().getUnlocalizedName(helmet) + ".name");
          String s = LocalizationHelper.getOtherItemKey(Names.PET_WAND, "state.armor.isWearing");
          s = String.format(s, this.getPetName(), armorName, this.getTotalArmorValue());
          PlayerHelper.addChatMessage(player, s);
        } else {
          String s = LocalizationHelper.getOtherItemKey(Names.PET_WAND, "state.armor.noArmor");
          s = String.format(s, this.getPetName());
          PlayerHelper.addChatMessage(player, s);
        }
      }
    } else if (state == PetWand.State.CARRY) {
      // Put pet in a carrier
      // FIXME
      if (!player.worldObj.isRemote) {
        ItemStack carrier = ModItems.petCarrier.create(this);
        PlayerHelper.addItemToInventoryOrDrop(player, carrier);
        this.setDead();
      }
    } else if (state == PetWand.State.TALK) {
      // Toggle talk mode.
      if (!player.worldObj.isRemote) {
        boolean allowed = !this.getAllowTalk();
        this.setAllowTalk(allowed);
        if (allowed) {
          PlayerHelper.addChatMessage(player, LocalizationHelper.getPetTalk(this, "talk.on"));
        } else {
          PlayerHelper.addChatMessage(player, LocalizationHelper.getPetTalk(this, "talk.off"));
        }
      }
    } else if (state == PetWand.State.NONE) {
      // Do nothing.
    } else {
      LogHelper.warning("Failed to handle an unknown Pet Wand state: " + state.name());
    }
  }

  @Override
  public boolean isAIEnabled() {

    return true;
  }

  @Override
  public boolean isBreedingItem(ItemStack stack) {

    return false;
  }

  @Override
  public void onLivingUpdate() {

    super.onLivingUpdate();

    // Regenerate health
    if (Config.PET_REGEN_DELAY.value > 0 && --timerHealthRegen <= 0) {
      timerHealthRegen = Config.PET_REGEN_DELAY.value;
      this.heal(1);
    }
  }

  public String getPetName() {

    if (this.hasCustomNameTag()) {
      return this.getCustomNameTag();
    } else {
      return LocalizationHelper.getMiscText("Pet.NoName");
    }
  }

  @Override
  public boolean func_142018_a(EntityLivingBase target, EntityLivingBase owner) {

    if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
      if (target instanceof EntityTameable) {
        EntityTameable entityTameable = (EntityTameable) target;

        if (entityTameable.isTamed() && entityTameable.getOwner() == owner) {
          return false;
        }
      }

      return target instanceof EntityPlayer && owner instanceof EntityPlayer
          && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target) ? false
              : !(target instanceof EntityHorse) || !((EntityHorse) target).isTame();
    } else {
      return false;
    }
  }

  @Override
  public void readEntityFromNBT(NBTTagCompound tags) {

    super.readEntityFromNBT(tags);
    this.setAllowTalk(tags.getBoolean("TalkAllowed"));
  }

  @Override
  public void writeEntityToNBT(NBTTagCompound tags) {

    super.writeEntityToNBT(tags);
    tags.setBoolean("TalkAllowed", this.getAllowTalk());
  }
}
