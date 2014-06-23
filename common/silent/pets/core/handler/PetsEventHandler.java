package silent.pets.core.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import silent.pets.SilentPets;
import silent.pets.configuration.Config;
import silent.pets.core.util.PlayerHelper;
import silent.pets.item.ModItems;
import silent.pets.item.MultiItem;
import silent.pets.item.NamePlate;
import silent.pets.lib.Names;
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
    public void onLivingDropsEvent(LivingDropsEvent event) {

        // Drop Pet Essence sometimes when a player kills something. Looting will increase chance by one third per
        // level.
        if (event.source.damageType.equals("player")
                && SilentPets.instance.random.nextDouble() <= Config.PET_ESSENCE_DROP_CHANCE.value * (event.lootingLevel / 3.0 + 1.0)) {
            EntityLivingBase e = event.entityLiving;
            event.drops.add(new EntityItem(e.worldObj, e.posX, e.posY + 1.0, e.posZ, MultiItem.getStack(Names.PET_ESSENCE_RAW)));
        }
    }
}
