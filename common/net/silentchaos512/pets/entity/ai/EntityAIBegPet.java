package net.silentchaos512.pets.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.silentchaos512.pets.entity.EntityPet;
import net.silentchaos512.pets.entity.PetDog;

public class EntityAIBegPet extends EntityAIBase {

    private EntityPet pet;
    private EntityPlayer thePlayer;
    private World worldObject;
    private float minPlayerDistance;
    private int field_75384_e;

    public EntityAIBegPet(EntityPet pet, float minDistance)
    {
        this.pet = pet;
        this.worldObject = pet.worldObj;
        this.minPlayerDistance = minDistance;
        this.setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.pet, (double)this.minPlayerDistance);
        return this.thePlayer == null ? false : this.hasPlayerGotBoneInHand(this.thePlayer);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.thePlayer.isEntityAlive() ? false : (this.pet.getDistanceSqToEntity(this.thePlayer) > (double)(this.minPlayerDistance * this.minPlayerDistance) ? false : this.field_75384_e > 0 && this.hasPlayerGotBoneInHand(this.thePlayer));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.pet.func_70918_i(true);
        this.field_75384_e = 40 + this.pet.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.pet.func_70918_i(false);
        this.thePlayer = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.pet.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + (double)this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, (float)this.pet.getVerticalFaceSpeed());
        --this.field_75384_e;
    }

    /**
     * Gets if the Player has the Bone in the hand.
     */
    private boolean hasPlayerGotBoneInHand(EntityPlayer p_75382_1_)
    {
        ItemStack itemstack = p_75382_1_.inventory.getCurrentItem();
        return itemstack == null ? false : (!this.pet.isTamed() && itemstack.getItem() == Items.bone ? true : this.pet.isBreedingItem(itemstack));
    }
}
