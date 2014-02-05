package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGitter extends Block {
	public BlockGitter() {
		super(Material.field_151573_f);
	}

	@Override
	public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
		return AxisAlignedBB.getAABBPool().getAABB(i, j, k, i + 1, (float) j + 1 - 0.025F, k + 1);
	}

	//With this you can see "the other side" of the block
	@Override
	public int func_149701_w() {
		return 1;
	}

	//This drops the block in 4 metal sticks
	@Override
	public Item func_149650_a(int i, Random random, int j) {
		return Wecraft.metalStick;
	}

	//This makes the block transparent
	@Override
	public boolean func_149662_c() {
		return false;
	}

	@Override
	public void func_149670_a(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityItem) {
			int y = 0;
			while (world.func_147437_c(i, j - y - 1, k) || world.func_147439_a(i, j - y - 1, k) == Wecraft.gitter) {
				y += 1;
			}
			while (!world.func_147437_c(i, j - y - 1, k) && !world.func_147437_c(i + 1, j - y, k) && !world.func_147437_c(i - 1, j - y, k) && !world.func_147437_c(i, j - y, k + 1)
					&& !world.func_147437_c(i, j - y, k - 1)) {
				y -= 1;
			}
			if (y > 0) {
				if (world.func_147437_c(i, j - y, k)) {
					entity.setPosition(entity.posX, entity.posY - 1.0D * y, entity.posZ);
				} else if (world.func_147437_c(i + 1, j - (y - 1), k)) {
					entity.setPosition(entity.posX + 1, entity.posY - 1.0D * (y - 1), entity.posZ);
				} else if (world.func_147437_c(i - 1, j - (y - 1), k)) {
					entity.setPosition(entity.posX - 1, entity.posY - 1.0D * (y - 1), entity.posZ);
				} else if (world.func_147437_c(i, j - (y - 1), k + 1)) {
					entity.setPosition(entity.posX, entity.posY - 1.0D * (y - 1), entity.posZ + 1);
				} else if (world.func_147437_c(i, j - (y - 1), k - 1)) {
					entity.setPosition(entity.posX, entity.posY - 1.0D * (y - 1), entity.posZ - 1);
				}
			}
		}
	}

	@Override
	public int func_149745_a(Random random) {
		return 4;
	}

	@Override
	public boolean func_149686_d() {
		return false;
	}

	@Override
	public void func_149719_a(IBlockAccess iblockaccess, int i, int j, int k) {
		float l = 0.002F;
        func_149676_a(0.0F + l, 0.0F + l, 0.0F + l, 1.0F - l, 1.0F - l, 1.0F - l);
	}

	@Override
	public void func_149683_g() {
        func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public boolean func_149646_a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return true;
	}
}