package silent.pets.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import silent.pets.configuration.Config;
import silent.pets.core.util.LocalizationHelper;
import silent.pets.core.util.LogHelper;
import silent.pets.core.util.PlayerHelper;
import silent.pets.item.PetWand;
import silent.pets.lib.Names;

public class EntityPet extends EntityTameable {

    protected int attackDamage = 4;
    protected String entityName = "null";

    protected int timerHealthRegen = Config.PET_REGEN_DELAY.value;

    public EntityPet(World world) {

        super(world);
    }

    @Override
    protected void applyEntityAttributes() {

        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {

        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.attackDamage);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if (this.isEntityInvulnerable()) {
            return false;
        }
        else {
            // reset regen timer
            timerHealthRegen = Config.PET_REGEN_DELAY.value;

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
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {

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

        return 0.4f;
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
        }
        else {
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
                        --stack.stackSize;
                        return true;
                    }
                }
            }

            if (player.getCommandSenderName().equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote
                    && !this.isBreedingItem(stack)) {
                this.aiSit.setSitting(!this.isSitting());
                if (this.isSitting()) {
                    PlayerHelper.addChatMessage(player, LocalizationHelper.getPetTalk(this, "following"));
                }
                else {
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
            }
            else if (player.worldObj.isRemote && !player.isSneaking()) {
                // Display armor, if any
                if (helmet != null) {
                    String armorName = StatCollector.translateToLocal(helmet.getItem().getUnlocalizedName(helmet) + ".name");
                    String s = LocalizationHelper.getOtherItemKey(Names.PET_WAND, "state.armor.isWearing");
                    s = String.format(s, this.getPetName(), armorName);
                    PlayerHelper.addChatMessage(player, s);
                }
                else {
                    String s = LocalizationHelper.getOtherItemKey(Names.PET_WAND, "state.armor.noArmor");
                    s = String.format(s, this.getPetName());
                    PlayerHelper.addChatMessage(player, s);
                }
            }
        }
        else if (state == PetWand.State.CARRY) {
            // TODO
        }
        else if (state == PetWand.State.TALK) {
            // TODO
        }
        else if (state == PetWand.State.NONE) {
            // Do nothing.
        }
        else {
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
        if (--timerHealthRegen <= 0) {
            timerHealthRegen = Config.PET_REGEN_DELAY.value;
            this.heal(1);
        }
    }

    public String getPetName() {

        if (this.hasCustomNameTag()) {
            return this.getCustomNameTag();
        }
        else {
            return LocalizationHelper.getMiscText("Pet.NoName");
        }
    }
}
