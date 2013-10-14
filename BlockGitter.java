package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGitter extends Block {
	public BlockGitter(int i) {
		super(i, Material.iron);
	}

	//This drops the block in 4 metal sticks
	@Override
	public int idDropped(int i, Random random, int j) {
		return Wecraft.metalStick.itemID;
	}

	@Override
	public int quantityDropped(Random random) {
		return 4;
	}

	//This makes the block transparent
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	//With this you can see "the other side" of the block
	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return true;
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, (float) j + 1 - 0.025F, k + 1);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityItem && (world.getBlockId(i, j - 1, k) == 0 || world.getBlockId(i, j - 1, k) == Wecraft.gitter.blockID)) {
			entity.setPosition(entity.posX, entity.posY - 1.3D, entity.posZ);
		}
	}
}