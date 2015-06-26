package net.silentchaos512.pets.lib;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.silentchaos512.pets.configuration.Config;
import net.silentchaos512.pets.core.util.LocalizationHelper;
import net.silentchaos512.pets.core.util.PlayerHelper;
import net.silentchaos512.pets.item.PetWand;

public class PetData {

  public static final int DATA_TALK = 31;

  protected int timerHealthRegen = Config.PET_REGEN_DELAY;
  protected EntityPlayer owner = null;
  protected EntityAISit aiSit = null;
  
  public PetData(EntityLiving pet, EntityPlayer player) {
    
    this.owner = player;
    if (pet instanceof EntityTameable) {
      ;
    } else {
      aiSit = new EntityAISit(pet);
    }
  }

  public void resetRegenTimer() {

    this.timerHealthRegen = Config.PET_REGEN_DELAY;
  }

  public boolean getAllowTalk(EntityLiving pet) {

    return pet.getDataWatcher().getWatchableObjectByte(DATA_TALK) != 0;
  }

  public void setAllowTalk(EntityTameable entity, boolean talkAllowed) {

    entity.getDataWatcher().updateObject(DATA_TALK, Byte.valueOf((byte) (talkAllowed ? 1 : 0)));
  }
  
  public EntityPlayer getOwner() {
    
    return this.owner;
  }
  
  public String getPetName(EntityLiving pet) {
    
    if (pet.hasCustomNameTag()) {
      return pet.getCustomNameTag();
    } else {
      return LocalizationHelper.getMiscText("Pet.NoName");
    }
  }

  public int getTotalArmorValue(EntityLiving pet) {

    ItemStack stack = pet.getEquipmentInSlot(1);
    if (stack != null) {
      ItemArmor helmet = (ItemArmor) stack.getItem();
      int armorValue = 0;
      for (int i = 0; i < 4; ++i) {
        armorValue += helmet.getArmorMaterial().getDamageReductionAmount(i);
      }
      return armorValue;
    } else {
      return 0;
    }
  }

  public boolean interact(EntityLiving pet, EntityPlayer player) {

    ItemStack heldStack = player.inventory.getCurrentItem();

    if (heldStack != null) {
      Item heldItem = heldStack.getItem();
      // Pet wand?
      if (heldItem instanceof PetWand) {
        return this.handlePetWand(pet, player, heldStack);
      }

      // TODO: Heal with food? Dye?

      // Armor
      if (heldItem instanceof ItemArmor && pet.getEquipmentInSlot(1) == null) {
        ItemArmor armor = (ItemArmor) heldItem;
        if (armor.armorType == 0) {
          pet.setCurrentItemOrArmor(1, heldStack);
          // Display newly equipped armor!
          String armorName = StatCollector.translateToLocal(heldItem.getUnlocalizedName(heldStack)
              + ".name");
          String s = LocalizationHelper.getOtherItemKey(Names.PET_WAND, "state.armor.isWearing");
          s = String.format(s, this.getPetName(pet), armorName, this.getTotalArmorValue(pet));
          PlayerHelper.addChatMessage(player, s);
        }
      }
    }
    
    if (player == this.getOwner() && !pet.worldObj.isRemote) {
      pet.getAi
    }
  }

  private boolean handlePetWand(EntityLiving pet, EntityPlayer player, ItemStack heldItem) {

    // TODO Auto-generated method stub

  }
}
