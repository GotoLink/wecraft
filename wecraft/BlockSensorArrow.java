package wecraft;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSensorArrow extends Block {
	protected BlockSensorArrow(int i) {
		super(i, Material.cloth);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		AxisAlignedBB box = super.getCollisionBoundingBoxFromPool(world, i, j, k);
		List<?> list = world.getEntitiesWithinAABB(Entity.class, box);
		for (Object obj : list) {
			if (obj instanceof EntityArrow) {
				EntityArrow arrow = (EntityArrow) obj;
				if (world.getBlockMetadata(i, j, k) != 1) {
					if (!world.isRemote) {
						EntityItem entityitem = new EntityItem(world, (float) arrow.posX, (float) arrow.posY, (float) arrow.posZ, new ItemStack(Item.arrow));
						world.spawnEntityInWorld(entityitem);
						arrow.setDead();
					}
					world.setBlockMetadataWithNotify(i, j, k, 1, 3);
					world.notifyBlocksOfNeighborChange(i, j, k, blockID);
					world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
					world.scheduleBlockUpdate(i, j, k, blockID, 20);
				}
			}
		}
		return box;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		world.scheduleBlockUpdate(i, j, k, blockID, 20);
		if (l != blockID) {
			world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		// if(world.getClosestPlayer((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 16D) == null){
		getCollisionBoundingBoxFromPool(world, i, j, k);
		for (int l = 0; l < 60; l++) {
			randomDisplayTick(world, i, j, k, random);
			if (world.getBlockMetadata(i, j, k) == 1) {
				world.setBlockMetadataWithNotify(i, j, k, 0, 3);
				world.notifyBlocksOfNeighborChange(i, j, k, blockID);
				world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
			}
		}
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int par5, int par6) {
		world.notifyBlocksOfNeighborChange(i, j, k, blockID);
		world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
	}

	@Override
	public Icon getIcon(int i, int j) {
		if (i > 1) {
			return blockIcon;
		} else {
			return Block.cloth.getIcon(i, 8);//gray wool
		}
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return iblockaccess.getBlockMetadata(i, j, k) == 1 ? 15 : 0;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}
}