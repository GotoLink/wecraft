package wecraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWaterMaker extends Block {
	private IIcon[] waterMakerTexture;

	protected BlockWaterMaker() {
		super(Material.iron);
	}

	@Override
	public int tickRate(World world) {
		return 5;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (i == 1 && j == 1) {
			return waterMakerTexture[0];
		}
		if (i == 1 && j == 2) {
			return waterMakerTexture[2];
		}
		if (i == 1) {
			return waterMakerTexture[1];
		}
		return Blocks.stone_slab.getIcon(i, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.waterMakerTexture = new IIcon[3];
		for (int i = 0; i < waterMakerTexture.length; i++)
			this.waterMakerTexture[i] = par1IconRegister.registerIcon(this.getTextureName() + i);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l) {
		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			if (world.getBlock(i, j + 1, k) == Blocks.air) {
				if (world.getBlockMetadata(i, j, k) == 1) {
					world.setBlock(i, j + 1, k, Blocks.flowing_water);
				}
				if (world.getBlockMetadata(i, j, k) == 2) {
					world.setBlock(i, j + 1, k, Blocks.flowing_lava);
				}
				world.setBlockMetadataWithNotify(i, j, k, 0, 3);
			}
		} else {
			if (world.getBlock(i, j + 1, k).getMaterial() == Material.water && world.getBlockMetadata(i, j + 1, k) == 0) {
				world.setBlockToAir(i, j + 1, k);
				world.setBlockMetadataWithNotify(i, j, k, 1, 3);
			}
			if (world.getBlock(i, j + 1, k).getMaterial() == Material.lava && world.getBlockMetadata(i, j + 1, k) == 0) {
				world.setBlockToAir(i, j + 1, k);
				world.setBlockMetadataWithNotify(i, j, k, 2, 3);
				if (isLavaNearby(world, i, j, k)) {
				}
			}
		}
	}

	private boolean isLavaNearby(World world, int i, int j, int k) {
		for (int i1 = i - Wecraft.waterTankRadiusWide; i1 <= i + Wecraft.waterTankRadiusWide; i1++) {
			for (int j1 = j - Wecraft.waterTankRadiusDepth; j1 <= j + 1; j1++) {
				for (int k1 = k - Wecraft.waterTankRadiusWide; k1 <= k + Wecraft.waterTankRadiusWide; k1++) {
					if (world.getBlock(i1, j1, k1) == Blocks.flowing_lava || world.getBlock(i1, j1, k1) == Blocks.lava) {
						world.setBlockToAir(i1, j1, k1);
					}
				}
			}
		}
		return false;
	}
}