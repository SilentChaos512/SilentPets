package net.silentchaos512.pets.core.handler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.silentchaos512.pets.SilentPets;
import net.silentchaos512.pets.configuration.Config;
import net.silentchaos512.pets.core.util.LocalizationHelper;
import net.silentchaos512.pets.core.util.LogHelper;
import net.silentchaos512.pets.core.util.PlayerHelper;
import net.silentchaos512.pets.entity.EntityPet;
import net.silentchaos512.pets.item.ModItems;
import net.silentchaos512.pets.item.MultiItem;
import net.silentchaos512.pets.item.NamePlate;
import net.silentchaos512.pets.item.PetSummon;
import net.silentchaos512.pets.lib.Names;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class PetsEventHandler {

  @SubscribeEvent
  public void onItemCraftedEvent(ItemCraftedEvent event) {

    if (event.craftMatrix instanceof InventoryCrafting) {
      if (ModItems.recipeNamePlate.matches((InventoryCrafting) event.craftMatrix,
          event.player.worldObj)) {
        // Find the name plate.
        ItemStack stack, namePlate = null;
        for (int i = 0; i < event.craftMatrix.getSizeInventory(); ++i) {
          stack = event.craftMatrix.getStackInSlot(i);
          if (stack != null && stack.getItem() instanceof NamePlate) {
            namePlate = stack;
          }
        }

        if (namePlate != null) {
          // Damage and return name plate.
          namePlate.attemptDamageItem(1, SilentPets.instance.random);
          PlayerHelper.addItemToInventoryOrDrop(event.player, namePlate);
        }
      }
    }
  }

  @SubscribeEvent
  public void onLivingDeathEvent(LivingDeathEvent event) {

    // LogHelper.debug(event.source.damageType);
    if (!event.entity.worldObj.isRemote) {
      if (event.entity instanceof EntityPet) {
        // Pet death messages.
        EntityPet pet = (EntityPet) event.entity;
        if (pet.getOwner() instanceof EntityPlayer) {
          EntityPlayer player = (EntityPlayer) pet.getOwner();
          String petName = pet.getPetName();
          String key = "death." + event.source.damageType;
          String message = LocalizationHelper.getPetTalkString(key);
          // Did we get a message for this damage type?
          if (!message.equals(LocalizationHelper.TALK_PREFIX + key + "0")) {
            LogHelper.derp();
            PlayerHelper.addChatMessage(player, String.format(message, petName));
          }
          // Otherwise, generic death message
          else {
            message = LocalizationHelper.getPetTalkString("death");
            PlayerHelper.addChatMessage(player, String.format(message, petName) + " ("
                + event.source.damageType + ")");
          }
        }
      }
    }
  }

  @SubscribeEvent
  public void onLivingDropsEvent(LivingDropsEvent event) {

    double x = event.entity.posX;
    double y = event.entity.posY + 1.0;
    double z = event.entity.posZ;
    World world = event.entity.worldObj;

    int count;

    // Drop Pet Essence sometimes when a player kills something. Looting will increase chance by one third per
    // level.
    if (event.source.damageType.equals("player")
        && SilentPets.instance.random.nextDouble() <= Config.PET_ESSENCE_DROP_RATE
            * (event.lootingLevel / 3.0 + 1.0)) {
      event.drops.add(new EntityItem(world, x, y, z, MultiItem.getStack(Names.PET_ESSENCE_RAW)));
    }

    // Pet drops
    if (event.entity instanceof EntityPet) {
      EntityPet pet = (EntityPet) event.entity;
      // Sometimes drop pet summon when a pet dies.
      if (SilentPets.instance.random.nextDouble() <= Config.PET_ESSENCE_DROP_RATE) {
        ItemStack summoner = PetSummon.getStackForPet(pet);
        if (summoner != null) {
          event.drops.add(new EntityItem(world, x, y, z, summoner));
        }
      }
      // Drop equipment
      ItemStack helmet = pet.getEquipmentInSlot(1);
      if (helmet != null) {
        helmet.stackSize = 1;
        event.drops.add(new EntityItem(world, x, y, z, helmet));
      }
    }

    // Pigs
    if (event.entity instanceof EntityPig) {
      // Pig Leather
      count = SilentPets.instance.random.nextInt(3)
          + SilentPets.instance.random.nextInt(1 + event.lootingLevel);
      ItemStack stack = MultiItem.getStack(Names.PIG_LEATHER, count);
      if (count > 0) {
        event.drops.add(new EntityItem(world, x, y, z, stack));
      }
    }
  }
}
