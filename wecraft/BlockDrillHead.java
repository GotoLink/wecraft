package wecraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDrillHead extends Block {
	protected BlockDrillHead() {
		super(Material.iron);
        setTickRandomly(true);
	}

	public void destroyBelow(World world, int i, int j, int k) {
		Block upID = world.getBlock(i, j + 1, k);
		if (upID != this && upID != Wecraft.drill) {
			world.setBlockToAir(i, j, k);
		}
	}

	public void destroyUp(World world, int i, int j, int k) {
		Block upId = world.getBlock(i, j + 1, k);
		if (upId == this) {
			world.setBlockMetadataWithNotify(i, j + 1, k, 2, 2);
			world.scheduleBlockUpdate(i, j + 1, k, this, this.tickRate(world));
		} else if (upId == Wecraft.drill) {
			world.setBlockMetadataWithNotify(i, j + 1, k, 0, 2);
		}
		world.setBlockToAir(i, j, k);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l) {
		int m = world.getBlockMetadata(i, j, k);
		if (m == 2) {
			destroyUp(world, i, j, k);
		}
		destroyBelow(world, i, j, k);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		if (l != 1) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
            setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
        onNeighborBlockChange(world, i, j, k, this);
	}
}