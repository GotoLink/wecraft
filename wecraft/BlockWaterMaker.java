package wecraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaterMaker extends Block {
	private IIcon[] waterMakerTexture;

	protected BlockWaterMaker() {
		super(Material.field_151573_f);
	}

	@Override
	public int func_149738_a(World world) {
		return 5;
	}

	//Different texture for each side
	@Override
	public IIcon func_149691_a(int i, int j) {
		if (i == 1 && j == 1) {
			return waterMakerTexture[0];
		}
		if (i == 1 && j == 2) {
			return waterMakerTexture[2];
		}
		if (i == 1) {
			return waterMakerTexture[1];
		}
		return Blocks.stone_slab.func_149691_a(i, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
		this.waterMakerTexture = new IIcon[3];
		for (int i = 0; i < waterMakerTexture.length; i++)
			this.waterMakerTexture[i] = par1IconRegister.registerIcon(this.func_149641_N() + i);
	}

	@Override
	public void func_149695_a(World world, int i, int j, int k, Block l) {
		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			if (world.func_147439_a(i, j + 1, k) == Blocks.air) {
				if (world.getBlockMetadata(i, j, k) == 1) {
					world.func_147449_b(i, j + 1, k, Blocks.flowing_water);
				}
				if (world.getBlockMetadata(i, j, k) == 2) {
					world.func_147449_b(i, j + 1, k, Blocks.flowing_lava);
				}
				world.setBlockMetadataWithNotify(i, j, k, 0, 3);
			}
		} else {
			if (world.func_147439_a(i, j + 1, k).func_149688_o() == Material.field_151586_h && world.getBlockMetadata(i, j + 1, k) == 0) {
				world.func_147468_f(i, j + 1, k);
				world.setBlockMetadataWithNotify(i, j, k, 1, 3);
			}
			if (world.func_147439_a(i, j + 1, k).func_149688_o() == Material.field_151587_i && world.getBlockMetadata(i, j + 1, k) == 0) {
				world.func_147468_f(i, j + 1, k);
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
					if (world.func_147439_a(i1, j1, k1) == Blocks.flowing_lava || world.func_147439_a(i1, j1, k1) == Blocks.lava) {
						world.func_147468_f(i1, j1, k1);
					}
				}
			}
		}
		return false;
	}
}