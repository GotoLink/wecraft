package wecraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSensorFire extends Block {
	protected BlockSensorFire() {
		super(Material.rock);
	}

	@Override
	public int tickRate(World world) {
		return 5;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (i == 0) {
			return blockIcon;
		}
		return Blocks.stone_slab.getIcon(i, 0);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
        if (isFireNearby(world, i, j, k)) {
            if(world.getBlockMetadata(i,j,k)==0)
                world.setBlockMetadataWithNotify(i, j, k, 1, 3);
            world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.drr", 0.3F, 0.6F);
            world.scheduleBlockUpdate(i, j, k, this, this.tickRate(world) * 2);
        } else {
            world.setBlockMetadataWithNotify(i, j, k, 0, 3);
        }
        world.notifyBlocksOfNeighborChange(i, j, k, this);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return iblockaccess.getBlockMetadata(i, j, k) == 1 ? 15 : 0;
	}

	private boolean isFireNearby(World world, int i, int j, int k) {
		for (int l = i - 5; l <= i + 5; l++) {
			for (int i1 = j - 5; i1 <= j - 1; i1++) {
				for (int j1 = k - 5; j1 <= k + 5; j1++) {
					if (world.getBlock(l, i1, j1) == Blocks.fire) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}
}