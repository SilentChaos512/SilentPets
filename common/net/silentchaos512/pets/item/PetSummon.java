package net.silentchaos512.pets.item;

import java.lang.reflect.Constructor;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.pets.core.registry.SRegistry;
import net.silentchaos512.pets.core.util.LocalizationHelper;
import net.silentchaos512.pets.core.util.LogHelper;
import net.silentchaos512.pets.entity.EntityPet;
import net.silentchaos512.pets.entity.PetCat;
import net.silentchaos512.pets.entity.PetChicken;
import net.silentchaos512.pets.entity.PetCow;
import net.silentchaos512.pets.entity.PetDog;
import net.silentchaos512.pets.entity.PetMooshroom;
import net.silentchaos512.pets.entity.PetPig;
import net.silentchaos512.pets.entity.PetSheep;
import net.silentchaos512.pets.lib.Names;
import net.silentchaos512.pets.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

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

  public static final int MAX_PETS = 32;
  /**
   * Holds data on all registered pets. The length of the array is arbitrary.
   */
  public static final PetData[] pets = new PetData[MAX_PETS];

  public PetSummon() {

    setMaxStackSize(64);
    setUnlocalizedName(Names.PET_SUMMON);
    setHasSubtypes(true);
    setMaxDamage(0);
    icons = new IIcon[MAX_PETS];
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {

    if (pets[stack.getItemDamage()] != null) {
      list.add(LocalizationHelper.getEntityName(pets[stack.getItemDamage()].name));
    }
  }

  public static void addPet(Class<? extends EntityPet> pet, String name, int id) {

    pets[id] = new PetData(pet, name, id);
  }

  @Override
  public void addRecipes() {

    ItemStack petEssence = MultiItem.getStack(Names.PET_ESSENCE);
    // Cat
    addRecipe(PetCat.class, Items.fish, Items.fish, Items.string);
    // Chicken
    addRecipe(PetChicken.class, Items.feather);
    // Cow
    addRecipe(PetCow.class, Items.leather);
    // Dog
    addRecipe(PetDog.class, Items.bone, Items.bone, Items.porkchop);
    // Mooshroom
    addRecipe(PetMooshroom.class, Blocks.red_mushroom, Blocks.red_mushroom, Items.leather);
    // Pig
    addRecipe(PetPig.class, MultiItem.getStack(Names.PIG_LEATHER));
    // Sheep
    addRecipe(PetSheep.class, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
  }

  private void addRecipe(Class<? extends EntityPet> pet, Object... obj) {

    // Derp catcher
    if (obj.length == 0) {
      LogHelper.derp("PetSummon.addRecipe: obj array length is 0!");
      return;
    }

    ItemStack petEssence = MultiItem.getStack(Names.PET_ESSENCE);
    ItemStack summon = new ItemStack(this, 1, getIdForPet(pet));
    Object[] o = new Object[4];

    // Copy the object array, expanding it to four items, duplicating the last if necessary.
    for (int i = 0; i < 4; ++i) {
      if (i < obj.length) {
        o[i] = obj[i];
      } else {
        o[i] = obj[obj.length - 1];
      }
    }

    GameRegistry.addShapedRecipe(summon, "aib", "ipi", "cid", 'i', Items.iron_ingot, 'p',
        petEssence, 'a', o[0], 'b', o[1], 'c', o[2], 'd', o[3]);
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
    } else {
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
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z,
      int side, float hitX, float hitY, float hitZ) {

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
        Constructor<?> constructor = data.getPetClass().getDeclaredConstructor(World.class);
        EntityPet pet = (EntityPet) constructor.newInstance(world);

        // Set position, spawn in world.
        pet.setPosition(x + 0.5, y + pet.height / 2.0f, z + 0.5);
        world.spawnEntityInWorld(pet);

        // Make it tame and set master.
        pet.setTamed(true);
        pet.func_152115_b(player.getUniqueID().toString());
        world.setEntityState(pet, (byte) 7);

        // Custom name?
        if (stack.hasDisplayName()) {
          pet.setCustomNameTag(stack.getDisplayName());
        }
      } catch (Exception ex) {
        LogHelper.severe("Failed to create a pet.");
        ex.printStackTrace();
      }
    }

    return true;
  }

  @Override
  public void registerIcons(IIconRegister reg) {

    String s;
    for (int i = 0; i < pets.length; ++i) {
      if (pets[i] != null) {
        s = pets[i].name.substring(4);
        icons[i] = reg.registerIcon(Strings.RESOURCE_PREFIX + this.itemName + "_" + s);
      }
    }
    itemIcon = reg.registerIcon(Strings.RESOURCE_PREFIX + this.itemName);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return LocalizationHelper.ITEM_PREFIX + itemName;
  }
}
