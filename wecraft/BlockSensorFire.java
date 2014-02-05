package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSensorFire extends Block {
	protected BlockSensorFire() {
		super(Material.field_151576_e);
	}

	@Override
	public int func_149738_a(World world) {
		return 5;
	}

	@Override
	public IIcon func_149691_a(int i, int j) {
		if (i == 0) {
			return field_149761_L;
		}
		return Blocks.stone_slab.func_149691_a(i, 0);
	}

	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
        if (isFireNearby(world, i, j, k)) {
            if(world.getBlockMetadata(i,j,k)==0)
                world.setBlockMetadataWithNotify(i, j, k, 1, 3);
            world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.drr", 0.3F, 0.6F);
            world.func_147464_a(i, j, k, this, this.func_149738_a(world) * 2);
        } else {
            world.setBlockMetadataWithNotify(i, j, k, 0, 3);
        }
        world.func_147459_d(i, j, k, this);
	}

	@Override
	public int func_149709_b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return iblockaccess.getBlockMetadata(i, j, k) == 1 ? 15 : 0;
	}

	private boolean isFireNearby(World world, int i, int j, int k) {
		for (int l = i - 5; l <= i + 5; l++) {
			for (int i1 = j - 5; i1 <= j - 1; i1++) {
				for (int j1 = k - 5; j1 <= k + 5; j1++) {
					if (world.func_147439_a(l, i1, j1) == Blocks.fire) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean func_149744_f() {
		return true;
	}
}