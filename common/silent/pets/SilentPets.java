package silent.pets;

import java.util.Random;

import net.minecraftforge.common.MinecraftForge;
import silent.pets.block.ModBlocks;
import silent.pets.configuration.Config;
import silent.pets.core.handler.PetsEventHandler;
import silent.pets.core.proxy.CommonProxy;
import silent.pets.core.registry.SRegistry;
import silent.pets.entity.ModEntities;
import silent.pets.item.ModItems;
import silent.pets.lib.PetStats;
import silent.pets.lib.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
public class SilentPets {

    public Random random = new Random();
    
    @Instance(Reference.MOD_ID)
    public static SilentPets instance;
    
    @SidedProxy(clientSide = "silent.pets.core.proxy.ClientProxy", serverSide = "silent.pets.core.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Config.init(event.getSuggestedConfigurationFile());
        
        ModBlocks.init();
        ModItems.init();
        PetStats.init();
        ModEntities.init();
        
        ModItems.initItemRecipes();
        
        SRegistry.addRecipesAndOreDictEntries();
        ModItems.addRandomChestGenLoot();
        
        Config.save();
        
        MinecraftForge.EVENT_BUS.register(new PetsEventHandler());
        FMLCommonHandler.instance().bus().register(new PetsEventHandler());
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
    
        proxy.registerTileEntities();
        proxy.registerRenderers();
        proxy.registerKeyHandlers();
        
        //GameRegistry.registerWorldGenerator(new GemsWorldGenerator(), 0);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        // Is this the right place for this?
        SRegistry.addThaumcraftStuff();
    }
}
