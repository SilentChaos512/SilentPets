package net.silentchaos512.pets.item;

import java.lang.reflect.Constructor;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.silentchaos512.pets.core.util.LogHelper;
import net.silentchaos512.pets.entity.EntityPet;
import net.silentchaos512.pets.item.PetSummon.PetData;
import net.silentchaos512.pets.lib.Names;

public class PetCarrier extends ItemSG {

  public PetCarrier() {

    this.setMaxStackSize(1);
    this.setUnlocalizedName(Names.PET_CARRIER);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    super.addInformation(stack, player, list, advanced);
    // TODO
  }

  public ItemStack create(EntityPet pet) {

    ItemStack stack = new ItemStack(this);
    stack.stackTagCompound = new NBTTagCompound();

    String entityId = EntityList.getEntityString(pet);
    stack.stackTagCompound.setString("id", entityId);
    pet.writeEntityToNBT(stack.stackTagCompound);
    if (pet.hasCustomNameTag()) {
      stack.setStackDisplayName(pet.getCustomNameTag());
    }

    return stack;
  }

  @Override
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
      int side, float hitX, float hitY, float hitZ) {

    if (stack == null || stack.stackTagCompound == null) {
      return false;
    }

    String id = stack.stackTagCompound.getString("id");

    if (!world.isRemote) {
      if (!player.capabilities.isCreativeMode && stack.stackSize > 0) {
        --stack.stackSize;
      }

      Block block = world.getBlock(x, y, z);
      x += Facing.offsetsXForSide[side];
      y += Facing.offsetsYForSide[side];
      z += Facing.offsetsZForSide[side];
      double d = 0.0;

      if (side == 1 && block.getRenderType() == 11) {
        d = 0.5;
      }

      // Create pet entity.
      try {
        // PetData data = PetSummon.pets[id];
        // Constructor<?> constructor = data.getPetClass().getDeclaredConstructor(World.class);
        // EntityPet pet = (EntityPet) constructor.newInstance(world);
        EntityPet pet = (EntityPet) EntityList.createEntityFromNBT(stack.stackTagCompound, world);

        // Load NBT from carrier.
        pet.readFromNBT(stack.stackTagCompound);
        // Set position, spawn in world.
        pet.setPosition(x + 0.5, y + pet.height / 2.0f, z + 0.5);
        world.spawnEntityInWorld(pet);

        // Make it tame and set master.
        // pet.setTamed(true);
        // pet.func_152115_b(player.getUniqueID().toString());
        // world.setEntityState(pet, (byte) 7);

        // Custom name?
        if (stack.hasDisplayName()) {
          pet.setCustomNameTag(stack.getDisplayName());
        }

        // Remove empty stack?
        if (stack.stackSize <= 0) {
          stack = null;
        }
      } catch (Exception ex) {
        LogHelper.severe("Failed to create a pet.");
        ex.printStackTrace();
      }
    }

    return true;
  }
}
