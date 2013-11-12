package wecraft;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDrillHead extends Block {
	protected BlockDrillHead(int i) {
		super(i, Material.iron);
		setTickRandomly(true);
	}

	public void destroyBelow(World world, int i, int j, int k) {
		if (world.getBlockId(i, j + 1, k) == 0 || world.getBlockId(i, 4, k) == this.blockID) {
			int t = 4;
			while (t < 256) {
				if (world.getBlockId(i, t, k) == blockID) {
					world.setBlockToAir(i, t, k);
				}
				t++;
			}
		}
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		int m = world.getBlockMetadata(i, j, k);
		if (m != 0) {
			destroyBelow(world, i, j, k);
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		if (l == 0) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		int l = world.getBlockMetadata(i, j, k);
		if (l != 0) {
			destroyBelow(world, i, j, k);
		}
	}
}