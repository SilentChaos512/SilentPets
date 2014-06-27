package silent.pets.item;

import java.lang.reflect.Constructor;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import silent.pets.core.registry.SRegistry;
import silent.pets.core.util.LocalizationHelper;
import silent.pets.core.util.LogHelper;
import silent.pets.core.util.RecipeHelper;
import silent.pets.entity.EntityPet;
import silent.pets.entity.PetChicken;
import silent.pets.entity.PetCow;
import silent.pets.entity.PetPig;
import silent.pets.entity.PetSheep;
import silent.pets.lib.Names;

public class PetSummon extends ItemSG {

    public static class PetData {

        private Class<? extends EntityPet> clazz;
        private String name;
        private int id;

        public PetData(Class<? extends EntityPet> pet, String name, int id) {

            this.clazz = pet;
            this.name = name;
            this.id = id;
        }

        public Class<? extends EntityPet> getPetClass() {

            return clazz;
        }

        public String getName() {

            return name;
        }

        public int getId() {

            return id;
        }
    }

    /**
     * Holds data on all registered pets. The length of the array is arbitrary.
     */
    public final static PetData[] pets = new PetData[32];

    public PetSummon() {

        setMaxStackSize(64);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName(Names.PET_SUMMON);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {

        if (pets[stack.getItemDamage()] != null) {
            list.add(LocalizationHelper.getEntityName(pets[stack.getItemDamage()].name));
        }
    }

    public static void addPet(Class<? extends EntityPet> pet, String name, int id) {

        pets[id] = new PetData(pet, name, id);
        // LogHelper.list(pet, name, id);
    }

    @Override
    public void addRecipes() {

        ItemStack petEssence = MultiItem.getStack(Names.PET_ESSENCE);
        RecipeHelper.addSurround(new ItemStack(this, 1, getIdForPet(PetChicken.class)), petEssence, new Object[] { Items.iron_ingot,
                Items.feather });
        RecipeHelper.addSurround(new ItemStack(this, 1, getIdForPet(PetCow.class)), petEssence, new Object[] { Items.iron_ingot,
                Items.leather });
        RecipeHelper.addSurround(new ItemStack(this, 1, getIdForPet(PetPig.class)), petEssence, new Object[] { Items.iron_ingot,
                Items.porkchop });
        RecipeHelper.addSurround(new ItemStack(this, 1, getIdForPet(PetSheep.class)), petEssence, new Object[] { Items.iron_ingot,
                new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
    }

    public static int getIdForPet(Class<? extends EntityPet> pet) {

        for (int i = 0; i < pets.length; ++i) {
            if (pets[i] != null && pets[i].clazz == pet) {
                return i;
            }
        }

        return -1;
    }

    public static PetData getPet(int id) {

        if (id >= 0 && id < pets.length) {
            return pets[id];
        }
        else {
            return null;
        }
    }
    
    public static ItemStack getStackForPet(Entity pet) {
        
        for (int i = 0; i < pets.length; ++i) {
            if (pets[i] != null && pets[i].clazz == pet.getClass()) {
                return new ItemStack(SRegistry.getItem(Names.PET_SUMMON), 1, i);
            }
        }
        
        return null;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {

        for (int i = 0; i < pets.length; ++i) {
            if (pets[i] != null) {
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY,
            float hitZ) {

        if (!world.isRemote && pets[stack.getItemDamage()] != null) {
            if (!player.capabilities.isCreativeMode) {
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
                PetData data = pets[stack.getItemDamage()];
                Class[] paramClasses = new Class[] { World.class };
                Constructor<?> constructor = data.getPetClass().getDeclaredConstructor(paramClasses);
                EntityPet pet = (EntityPet) constructor.newInstance(new Object[] { world });

                // Set position, spawn in world.
                pet.setPosition(x + 0.5, y + pet.height / 2.0f, z + 0.5);
                world.spawnEntityInWorld(pet);

                // Make it tame and set master.
                pet.setTamed(true);
                pet.setOwner(player.getCommandSenderName());
                world.setEntityState(pet, (byte) 7);

                // Custom name?
                if (stack.hasDisplayName()) {
                    pet.setCustomNameTag(stack.getDisplayName());
                }
            }
            catch (Exception ex) {
                LogHelper.severe("Failed to create a pet.");
                ex.printStackTrace();
            }
        }

        return true;
    }
}
