package net.silentchaos512.pets.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.silentchaos512.pets.core.registry.IAddRecipe;
import net.silentchaos512.pets.core.registry.IAddThaumcraftStuff;
import net.silentchaos512.pets.lib.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSG extends Block implements IAddRecipe, IAddThaumcraftStuff {

	public IIcon[] icons = null;
	public boolean hasSubtypes = false;
	protected String blockName = "null";
	
	public BlockSG(Material material) {
		
		super(material);
		
		setHardness(3.0f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockTextureName(Strings.RESOURCE_PREFIX + blockName);
	}
	
	@Override
    public void addOreDict() {

    }

	@Override
    public void addRecipes() {

    }
	
	@Override
	public void addThaumcraftStuff() {
	    
	}
	
	@Override
	public int damageDropped(int meta) {
		
		return hasSubtypes ? meta : 0;
	}
	
	public boolean getHasSubtypes() {
	    
	    return hasSubtypes;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		
		if (hasSubtypes && meta < icons.length) {
			return icons[meta];
		}
		else {
			return blockIcon;
		}
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		
		if (hasSubtypes) {
			for (int i = 0; i < icons.length; ++i) {
				subItems.add(new ItemStack(this, 1, i));
			}
		}
		else {
			super.getSubBlocks(item, tab, subItems);
		}
	}
	
	@Override
	public String getUnlocalizedName() {
		
		return "tile." + this.blockName;
	}
	
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        
        if (hasSubtypes && icons != null) {
            for (int i = 0; i < icons.length; ++i) {
                icons[i] = reg.registerIcon(Strings.RESOURCE_PREFIX + this.blockName + i);
            }
        }
        else {
            blockIcon = reg.registerIcon(Strings.RESOURCE_PREFIX + blockName);
        }
    }
	
	public Block setHasSubtypes(boolean value) {
		
		hasSubtypes = true;
		return this;
	}
	
	public Block setUnlocalizedName(String blockName) {
		
		this.blockName = blockName;
		setBlockName(blockName);
		return this;
	}
}
