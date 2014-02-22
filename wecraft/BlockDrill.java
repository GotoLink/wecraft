package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDrill extends Block {
	private IIcon drillSideOff, drillSideOn, drillSideTop;

	public BlockDrill() {
		super(Material.wood);
        setTickRandomly(true);
	}

	@Override
	public IIcon getIcon(int i, int j) {
		if (i > 1) {
			if (j == 0) {
				return drillSideOff;
			} else {
				return drillSideOn;
			}
		}
		return drillSideTop;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		int drillStatus = world.getBlockMetadata(i, j, k);
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
		if (drillStatus == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 1, 2);
			world.scheduleBlockUpdate(i, j, k, this, this.tickRate(world) * 2);
		} else {
			int y = j;
			while (y > 3) {
				if (world.getBlock(i, y - 1, k) == Wecraft.drillHead) {
					y--;
				} else {
					break;
				}
			}
			if (y < j) {
				world.setBlockMetadataWithNotify(i, y, k, 2, 2);
				world.scheduleBlockUpdate(i, y, k, Wecraft.drillHead, this.tickRate(world));
			} else {
				world.setBlockMetadataWithNotify(i, j, k, 0, 2);
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
        onBlockActivated(world, i, j, k, entityplayer, 0, 0, 0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.drillSideOff = par1IconRegister.registerIcon(this.getTextureName() + "SideOff");
		this.drillSideOn = par1IconRegister.registerIcon(this.getTextureName() + "SideOn");
		this.drillSideTop = par1IconRegister.registerIcon(this.getTextureName() + "Top");
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (world.getBlockMetadata(i, j, k) != 0) {
			Block headID = Wecraft.drillHead;
			for (int l = j - 1; l >= 4; l--) {
                Block block = world.getBlock(i, l, k);
				if (block != headID && block != Blocks.bedrock) {
					if (!block.getMaterial().isLiquid()) {
						world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.drr", 0.3F, 0.6F);
						block.dropBlockAsItem(world, i, j + 1, k, world.getBlockMetadata(i, l, k), 0);
					}
					world.setBlock(i, l, k, headID);
					if (l != j - 1) {
						world.setBlockMetadataWithNotify(i, l + 1, k, 1, 2);
					}
					if (l == 4) {
						world.setBlockMetadataWithNotify(i, l, k, 2, 2);
						world.scheduleBlockUpdate(i, l, k, headID, this.tickRate(world));
						return;
					}
					break;
				} else if (block == Blocks.bedrock) {
					if (world.getBlock(i, l + 1, k) == headID) {
						world.setBlockMetadataWithNotify(i, l + 1, k, 2, 2);
						world.scheduleBlockUpdate(i, l + 1, k, headID, this.tickRate(world));
						return;
					}
				}
			}
			world.scheduleBlockUpdate(i, j, k, this, this.tickRate(world) * 2);
		}
	}
}