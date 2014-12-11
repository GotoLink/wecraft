package wecraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockGitter extends Block {
	public BlockGitter() {
		super(Material.iron);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, (float) j + 1 - 0.025F, k + 1);
	}

	@Override
    @SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	//This drops the block in 4 metal sticks
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Wecraft.metalStick;
	}

	//This makes the block transparent
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityItem) {
			int y = 0;
			while (world.isAirBlock(i, j - y - 1, k) || world.getBlock(i, j - y - 1, k) == Wecraft.gitter) {
				y += 1;
			}
			while (!world.isAirBlock(i, j - y - 1, k) && !world.isAirBlock(i + 1, j - y, k) && !world.isAirBlock(i - 1, j - y, k) && !world.isAirBlock(i, j - y, k + 1)
					&& !world.isAirBlock(i, j - y, k - 1)) {
				y -= 1;
			}
			if (y > 0) {
				if (world.isAirBlock(i, j - y, k)) {
					entity.setPosition(entity.posX, entity.posY - 1.0D * y, entity.posZ);
				} else if (world.isAirBlock(i + 1, j - (y - 1), k)) {
					entity.setPosition(entity.posX + 1, entity.posY - 1.0D * (y - 1), entity.posZ);
				} else if (world.isAirBlock(i - 1, j - (y - 1), k)) {
					entity.setPosition(entity.posX - 1, entity.posY - 1.0D * (y - 1), entity.posZ);
				} else if (world.isAirBlock(i, j - (y - 1), k + 1)) {
					entity.setPosition(entity.posX, entity.posY - 1.0D * (y - 1), entity.posZ + 1);
				} else if (world.isAirBlock(i, j - (y - 1), k - 1)) {
					entity.setPosition(entity.posX, entity.posY - 1.0D * (y - 1), entity.posZ - 1);
				}
			}
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 4;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		float l = 0.002F;
        setBlockBounds(0.0F + l, 0.0F + l, 0.0F + l, 1.0F - l, 1.0F - l, 1.0F - l);
	}

	@Override
	public void setBlockBoundsForItemRender() {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
    @SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return true;
	}
}