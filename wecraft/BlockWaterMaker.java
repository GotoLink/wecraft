package wecraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaterMaker extends Block {
	private Icon[] waterMakerTexture;

	protected BlockWaterMaker(int i) {
		super(i, Material.iron);
	}

	@Override
	public int tickRate(World world) {
		return 5;
	}

	//Different texture for each side
	@Override
	public Icon getIcon(int i, int j) {
		if (i == 1 && j == 1) {
			return waterMakerTexture[0];
		}
		if (i == 1 && j == 2) {
			return waterMakerTexture[2];
		}
		if (i == 1) {
			return waterMakerTexture[1];
		}
		return Block.stoneSingleSlab.getIcon(i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.waterMakerTexture = new Icon[3];
		for (int i = 0; i < waterMakerTexture.length; i++)
			this.waterMakerTexture[i] = par1IconRegister.registerIcon(this.getTextureName() + i);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			if (world.getBlockId(i, j + 1, k) == 0) {
				if (world.getBlockMetadata(i, j, k) == 1) {
					world.setBlock(i, j + 1, k, Block.waterMoving.blockID);
				}
				if (world.getBlockMetadata(i, j, k) == 2) {
					world.setBlock(i, j + 1, k, Block.lavaMoving.blockID);
				}
				world.setBlockMetadataWithNotify(i, j, k, 0, 3);
			}
		} else {
			if (world.getBlockMaterial(i, j + 1, k) == Material.water && world.getBlockMetadata(i, j + 1, k) == 0) {
				world.setBlock(i, j + 1, k, 0);
				world.setBlockMetadataWithNotify(i, j, k, 1, 3);
			}
			if (world.getBlockMaterial(i, j + 1, k) == Material.lava && world.getBlockMetadata(i, j + 1, k) == 0) {
				world.setBlock(i, j + 1, k, 0);
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
					if (world.getBlockId(i1, j1, k1) == Block.lavaMoving.blockID || world.getBlockId(i1, j1, k1) == Block.lavaStill.blockID) {
						world.setBlock(i1, j1, k1, 0);
					}
				}
			}
		}
		return false;
	}
}