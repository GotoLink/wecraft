package wecraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConveyorBelt extends Block {
	private IIcon topDownTexture;
	private IIcon topTexture;

	public BlockConveyorBelt() {
		super(Material.iron);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return world.getBlock(i, j - 1, k).isNormalCube(world, i, j-1, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return AxisAlignedBB.getAABBPool().getAABB(i, j, k, i + 1, j + 0.52F, k + 1);
	}

	@Override
    @SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (((j == 0) || (j == 2)) && (i == 2 || i == 3)) {
			return getTexture1();
		}
		if (((j == 0) || (j == 2)) && (i == 0 || i == 1)) {
			return getTexture1();
		}
		if (((j == 1) || (j == 3)) && (i == 4 || i == 5)) {
			return getTexture1();
		}
		if (((j == 1) || (j == 3)) && (i == 0 || i == 1)) {
			return getTexture2();
		}
		return blockIcon;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack par6ItemStack) {
		int l = MathHelper.floor_double((entityliving.rotationYaw * 4F) / 360F + 2.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, l, 3);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		int l = world.getBlockMetadata(i, j, k);
		if (entity instanceof EntityLivingBase) {
			if (l == 0) {
				entity.motionZ = 0.1D;
			}
			if (l == 2) {
				entity.motionZ = -0.1D;
			}
			if (l == 1) {
				entity.motionX = -0.1D;
			}
			if (l == 3) {
				entity.motionX = 0.1D;
			}
		}
		else if (entity instanceof EntityItem) {
			entity.motionY = 0D;
			entity.motionX = 0D;
			entity.motionZ = 0D;
			if (l == 0) {
				entity.setPosition(entity.posX + (i + 0.5F - entity.posX) / 1.5F, j + 0.7F, entity.posZ + 0.1D);
			}
			if (l == 2) {
				entity.setPosition(entity.posX + (i + 0.5F - entity.posX) / 1.5F, j + 0.7F, entity.posZ - 0.1D);
			}
			if (l == 1) {
				entity.setPosition(entity.posX - 0.1D, j + 0.7F, entity.posZ + (k + 0.5F - entity.posZ) / 1.5F);
			}
			if (l == 3) {
				entity.setPosition(entity.posX + 0.1D, j + 0.7F, entity.posZ + (k + 0.5F - entity.posZ) / 1.5F);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "Front");
        this.topDownTexture = par1IconRegister.registerIcon(this.getTextureName() + "Top");
        this.topTexture = par1IconRegister.registerIcon(this.getTextureName() + "TopDown");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public void setBlockBoundsForItemRender() {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	private IIcon getTexture1() {
		return topDownTexture;
	}

	private IIcon getTexture2() {
		return topTexture;
	}
}