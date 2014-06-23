package silent.pets.core.proxy;

import silent.pets.lib.Names;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public CommonProxy() {

    }

    public void registerRenderers() {

    }

    public void registerTileEntities() {

        String prefix = "tile.silentpets:";
        //GameRegistry.registerTileEntity(TileTeleporter.class, prefix + Names.TELEPORTER);
    }

    public void registerKeyHandlers() {

    }
}
