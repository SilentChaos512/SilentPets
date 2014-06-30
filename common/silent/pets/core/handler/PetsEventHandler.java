package silent.pets.core.handler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import silent.pets.SilentPets;
import silent.pets.configuration.Config;
import silent.pets.core.util.LocalizationHelper;
import silent.pets.core.util.LogHelper;
import silent.pets.core.util.PlayerHelper;
import silent.pets.entity.EntityPet;
import silent.pets.item.ModItems;
import silent.pets.item.MultiItem;
import silent.pets.item.NamePlate;
import silent.pets.item.PetSummon;
import silent.pets.lib.Names;
import silent.pets.lib.Strings;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class PetsEventHandler {

    @SubscribeEvent
    public void onItemCraftedEvent(ItemCraftedEvent event) {

        if (event.craftMatrix instanceof InventoryCrafting) {
            if (ModItems.recipeNamePlate.matches((InventoryCrafting) event.craftMatrix, event.player.worldObj)) {
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

        if (event.entity.worldObj.isRemote) {
            if (event.entity instanceof EntityPet) {
                // Pet death messages.
                EntityPet pet = (EntityPet) event.entity;
                if (pet.getOwner() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) pet.getOwner();
                    String petName = pet.getPetName();
                    String message = LocalizationHelper.getPetTalkString("death");
                    PlayerHelper.addChatMessage(player, String.format(message, petName));
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

        // Drop Pet Essence sometimes when a player kills something. Looting will increase chance by one third per
        // level.
        if (event.source.damageType.equals("player")
                && SilentPets.instance.random.nextDouble() <= Config.PET_ESSENCE_DROP_CHANCE.value * (event.lootingLevel / 3.0 + 1.0)) {
            event.drops.add(new EntityItem(world, x, y, z, MultiItem.getStack(Names.PET_ESSENCE_RAW)));
        }
        
        // Pet drops
        if (event.entity instanceof EntityPet) {
            EntityPet pet = (EntityPet) event.entity;
            // Sometimes drop pet summon when a pet dies.
            if (SilentPets.instance.random.nextDouble() <= Config.PET_ESSENCE_DROP_CHANCE.value) {
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
    }
}
