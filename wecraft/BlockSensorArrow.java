package wecraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSensorArrow extends Block {
	protected BlockSensorArrow() {
		super(Material.field_151580_n);
	}

	@Override
	public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
		AxisAlignedBB box = super.func_149668_a(world, i, j, k);
		List<?> list = world.getEntitiesWithinAABB(Entity.class, box);
		for (Object obj : list) {
			if (obj instanceof EntityArrow) {
				EntityArrow arrow = (EntityArrow) obj;
				if (world.getBlockMetadata(i, j, k) != 1) {
					if (!world.isRemote) {
						EntityItem entityitem = new EntityItem(world, (float) arrow.posX, (float) arrow.posY, (float) arrow.posZ, new ItemStack(Items.arrow));
						world.spawnEntityInWorld(entityitem);
						arrow.setDead();
					}
					world.setBlockMetadataWithNotify(i, j, k, 1, 3);
					world.func_147459_d(i, j, k, this);
					world.func_147459_d(i, j + 1, k, this);
					world.func_147464_a(i, j, k, this, 20);
				}
			}
		}
		return box;
	}

	@Override
	public void func_149695_a(World world, int i, int j, int k, Block l) {
		world.func_147464_a(i, j, k, this, 20);
		if (l != this) {
			world.func_147459_d(i, j + 1, k, this);
		}
	}

	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
        func_149668_a(world, i, j, k);
		for (int l = 0; l < 60; l++) {
            func_149734_b(world, i, j, k, random);
			if (world.getBlockMetadata(i, j, k) == 1) {
				world.setBlockMetadataWithNotify(i, j, k, 0, 3);
				world.func_147459_d(i, j, k, this);
				world.func_147459_d(i, j + 1, k, this);
			}
		}
	}

	@Override
	public boolean func_149696_a(World world, int i, int j, int k, int par5, int par6) {
		world.func_147459_d(i, j, k, this);
		world.func_147459_d(i, j + 1, k, this);
        return false;
	}

	@Override
	public IIcon func_149691_a(int i, int j) {
		if (i > 1) {
			return field_149761_L;
		} else {
			return Blocks.wool.func_149691_a(i, 8);//gray wool
		}
	}

	@Override
	public int func_149709_b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return iblockaccess.getBlockMetadata(i, j, k) == 1 ? 15 : 0;
	}

	@Override
	public boolean func_149744_f() {
		return true;
	}
}