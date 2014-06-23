package silent.pets.entity;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PetSheep extends EntityPet implements IShearable {

    public final static int DATA_FLEECE = 18;

    protected int sheepTimer;
    protected EntityAIEatGrass field_146087_bs = new EntityAIEatGrass(this);

    public PetSheep(World world) {

        super(world);

        attackDamage = 4;
        entityName = "sheep";

        this.setFleeceColor(getRandomFleeceColor(this.worldObj.rand));

        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, this.field_146087_bs);
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }

    @Override
    public void eatGrassBonus() {

        this.setSheared(false);

        if (this.isChild()) {
            this.addGrowth(60);
        }
    }

    @Override
    protected void entityInit() {

        super.entityInit();
        this.dataWatcher.addObject(DATA_FLEECE, new Byte((byte) 0));
    }

    @SideOnly(Side.CLIENT)
    public float func_70894_j(float par1) {

        return this.sheepTimer <= 0 ? 0.0F : (this.sheepTimer >= 4 && this.sheepTimer <= 36 ? 1.0F
                : (this.sheepTimer < 4 ? ((float) this.sheepTimer - par1) / 4.0F : -((float) (this.sheepTimer - 40) - par1) / 4.0F));
    }

    @SideOnly(Side.CLIENT)
    public float func_70890_k(float par1) {

        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f1 = ((float) (this.sheepTimer - 4) - par1) / 32.0F;
            return ((float) Math.PI / 5F) + ((float) Math.PI * 7F / 100F) * MathHelper.sin(f1 * 28.7F);
        }
        else {
            return this.sheepTimer > 0 ? ((float) Math.PI / 5F) : this.rotationPitch / (180F / (float) Math.PI);
        }
    }

    public int getFleeceColor() {

        return this.dataWatcher.getWatchableObjectByte(DATA_FLEECE) & 15;
    }
    
    @Override
    public String getHurtSound() {
        
        return "mob.sheep.say";
    }

    public static int getRandomFleeceColor(Random par0Random) {

        int i = par0Random.nextInt(100);
        return i < 5 ? 15 : (i < 10 ? 7 : (i < 15 ? 8 : (i < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 6 : 0))));
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared() {

        return (this.dataWatcher.getWatchableObjectByte(DATA_FLEECE) & 16) != 0;
    }

    @Override
    public void handleHealthUpdate(byte par1) {

        if (par1 == 10) {
            this.sheepTimer = 40;
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    @Override
    public boolean interact(EntityPlayer player) {
        
        ItemStack stack = player.inventory.getCurrentItem();
        
        if (stack != null) {
            if (stack.getItem() == Items.dye) {
                int i = BlockColored.func_150032_b(stack.getItemDamage());
                
                if (!this.getSheared() && this.getFleeceColor() != i) {
                    this.setFleeceColor(i);
                    --stack.stackSize;
                    return true;
                }
                else {
                    return super.interact(player);
                }
            }
            else {
                return super.interact(player);
            }
        }
        else {
            return super.interact(player);
        }
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {

        return !getSheared() && !isChild();
    }

    @Override
    public void onLivingUpdate() {

        if (this.worldObj.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {

        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        setSheared(true);
        int i = 1 + rand.nextInt(3);
        for (int j = 0; j < i; j++) {
            ret.add(new ItemStack(Blocks.wool, 1, getFleeceColor()));
        }
        this.playSound("mob.sheep.shear", 1.0F, 1.0F);
        return ret;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {

        super.readEntityFromNBT(par1NBTTagCompound);
        this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
        this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
    }

    public void setFleeceColor(int par1) {

        byte b0 = this.dataWatcher.getWatchableObjectByte(DATA_FLEECE);
        this.dataWatcher.updateObject(DATA_FLEECE, Byte.valueOf((byte) (b0 & 240 | par1 & 15)));
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean par1) {

        byte b0 = this.dataWatcher.getWatchableObjectByte(DATA_FLEECE);

        if (par1) {
            this.dataWatcher.updateObject(DATA_FLEECE, Byte.valueOf((byte) (b0 | 16)));
        }
        else {
            this.dataWatcher.updateObject(DATA_FLEECE, Byte.valueOf((byte) (b0 & -17)));
        }
    }

    @Override
    protected void updateAITasks() {

        this.sheepTimer = this.field_146087_bs.func_151499_f();
        super.updateAITasks();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {

        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
        par1NBTTagCompound.setByte("Color", (byte) this.getFleeceColor());
    }
}
