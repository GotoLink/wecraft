package wecraft;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockSaverItem extends Block {
	public BlockSaverItem() {
		super(Material.iron);
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (j == 2 && i == 3) {
			return Wecraft.gitter.getIcon(i, j);
		}
		if (j == 3 && i == 4) {
			return Wecraft.gitter.getIcon(i, j);
		}
		if (j == 0 && i == 2) {
			return Wecraft.gitter.getIcon(i, j);
		}
		if (j == 1 && i == 5) {
			return Wecraft.gitter.getIcon(i, j);
		} else {
			return Blocks.planks.getIcon(i, j);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack par6ItemStack) {
		int l = MathHelper.floor_double((entityliving.rotationYaw * 4F) / 360F + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, l, 3);
	}

	//Chest postiton
	private int getCoordX(World world, int i, int j, int k) {
		int m = world.getBlockMetadata(i, j, k);
		if (m == 1) {
			return -1;
		}
		if (m == 3) {
			return 1;
		}
		return 0;
	}

	private int getCoordZ(World world, int i, int j, int k) {
		int m = world.getBlockMetadata(i, j, k);
		if (m == 0) {
			return 1;
		}
		if (m == 2) {
			return -1;
		}
		return 0;
	}

	//  return world.getBlockMetadata
	//This makes the block transparent
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, (float) j + 1 - 0.025F, k + 1);
	}

	@Override
	public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			entityplayer.inventory.dropAllItems();
		}
		if (entity instanceof EntityItem) {
			TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(i + getCoordX(world, i, j, k), j, k + getCoordZ(world, i, j, k));
			if (tileentitychest == null) {
				return;
			} else {
				EntityItem entityItem = (EntityItem) entity;
				ItemStack itemstack = entityItem.getEntityItem();
				//entity.ItemStack;
				if (itemstack != null) {
					Random random = new Random();
					/*
					 * ItemStack itemstack2 =
					 * tileentitychest.getStackInSlot(random
					 * .nextInt(tileentitychest.getSizeInventory()));
					 * if(itemstack.itemID == itemstack2.itemID || itemstack2 ==
					 * null) { if(itemstack.stackSize+itemstack2.stackSize <=
					 * 64){ itemstack.stackSize = itemstack.stackSize +
					 * itemstack2.stackSize;
					 */
					tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack);
					//tileentitychest.putStackInSlot(1, itemstack);
					entity.setDead();
				}
			}
		}
	}
}