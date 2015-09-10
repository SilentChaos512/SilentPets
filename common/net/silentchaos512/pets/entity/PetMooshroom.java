package net.silentchaos512.pets.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.silentchaos512.pets.lib.PetStats;

public class PetMooshroom extends PetCow {

  public PetMooshroom(World world) {

    super(world);

    entityName = "cow";

    this.setSize(0.9F, 1.3F);
  }

  @Override
  protected void applyEntityAttributes() {

    super.applyEntityAttributes();
    PetStats stats = PetStats.mooshroom;
    this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(stats.damage);
    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(stats.health);
    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(stats.speed);
  }

  @Override
  public boolean interact(EntityPlayer player) {

    ItemStack stack = player.inventory.getCurrentItem();

    if (stack != null && stack.getItem() == Items.bowl && this.getGrowingAge() >= 0) {
      if (stack.stackSize == 1) {
        player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(
            Items.mushroom_stew));
        return true;
      }

      if (player.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew))
          && !player.capabilities.isCreativeMode) {
        player.inventory.decrStackSize(player.inventory.currentItem, 1);
        return true;
      }
    }

    return super.interact(player);
  }
}
