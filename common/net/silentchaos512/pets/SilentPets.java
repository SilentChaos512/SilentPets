package net.silentchaos512.pets;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.silentchaos512.pets.block.ModBlocks;
import net.silentchaos512.pets.configuration.Config;
import net.silentchaos512.pets.core.handler.PetsEventHandler;
import net.silentchaos512.pets.core.proxy.CommonProxy;
import net.silentchaos512.pets.core.registry.SRegistry;
import net.silentchaos512.pets.entity.ModEntities;
import net.silentchaos512.pets.item.ModItems;
import net.silentchaos512.pets.lib.Names;
import net.silentchaos512.pets.lib.PetStats;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SilentPets.MOD_ID, name = SilentPets.MOD_NAME, version = SilentPets.VERSION_NUMBER)
public class SilentPets {
  
  public static final String MOD_ID = "SilentPets";
  public static final String MOD_NAME = "Silent's Pets";
  public static final String VERSION_NUMBER = "@VERSION@";
  public static final String CHANNEL_NAME = MOD_ID;

  public Random random = new Random();
  public static Logger logger = LogManager.getLogger(MOD_NAME);

  @Instance(SilentPets.MOD_ID)
  public static SilentPets instance;

  @SidedProxy(clientSide = "net.silentchaos512.pets.core.proxy.ClientProxy", serverSide = "net.silentchaos512.pets.core.proxy.CommonProxy")
  public static CommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {

    Config.init(event.getSuggestedConfigurationFile());

    ModBlocks.init();
    ModItems.init();
    PetStats.init();
    ModEntities.init();

    Config.save();

    MinecraftForge.EVENT_BUS.register(new PetsEventHandler());
    FMLCommonHandler.instance().bus().register(new PetsEventHandler());
  }

  @EventHandler
  public void load(FMLInitializationEvent event) {
    
    ModItems.initItemRecipes();
    SRegistry.addRecipesAndOreDictEntries();
    ModItems.addRandomChestGenLoot();

    proxy.registerTileEntities();
    proxy.registerRenderers();
    proxy.registerKeyHandlers();
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

    SRegistry.addThaumcraftStuff();
  }

  public static CreativeTabs tabSilentPets = new CreativeTabs("tabSilentPets") {

    @Override
    public Item getTabIconItem() {

      return SRegistry.getItem(Names.MULTI_ITEM);
    }
  };
}
