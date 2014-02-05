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
		super(Material.field_151575_d);
        func_149675_a(true);
	}

	@Override
	public IIcon func_149691_a(int i, int j) {
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
	public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		int drillStatus = world.getBlockMetadata(i, j, k);
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
		if (drillStatus == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 1, 2);
			world.func_147464_a(i, j, k, this, this.func_149738_a(world) * 2);
		} else {
			int y = j;
			while (y > 3) {
				if (world.func_147439_a(i, y - 1, k) == Wecraft.drillHead) {
					y--;
				} else {
					break;
				}
			}
			if (y < j) {
				world.setBlockMetadataWithNotify(i, y, k, 2, 2);
				world.func_147464_a(i, y, k, Wecraft.drillHead, this.func_149738_a(world));
			} else {
				world.setBlockMetadataWithNotify(i, j, k, 0, 2);
			}
		}
		return true;
	}

	@Override
	public void func_149699_a(World world, int i, int j, int k, EntityPlayer entityplayer) {
        func_149727_a(world, i, j, k, entityplayer, 0, 0, 0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void func_149651_a(IIconRegister par1IconRegister) {
		this.drillSideOff = par1IconRegister.registerIcon(this.func_149641_N() + "SideOff");
		this.drillSideOn = par1IconRegister.registerIcon(this.func_149641_N() + "SideOn");
		this.drillSideTop = par1IconRegister.registerIcon(this.func_149641_N() + "Top");
	}

	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
		if (world.getBlockMetadata(i, j, k) != 0) {
			Block headID = Wecraft.drillHead;
			for (int l = j - 1; l >= 4; l--) {
                Block block = world.func_147439_a(i, l, k);
				if (block != headID && block != Blocks.bedrock) {
					if (!block.func_149688_o().isLiquid()) {
						world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.drr", 0.3F, 0.6F);
						block.func_149697_b(world, i, j + 1, k, world.getBlockMetadata(i, l, k), 0);
					}
					world.func_147449_b(i, l, k, headID);
					if (l != j - 1) {
						world.setBlockMetadataWithNotify(i, l + 1, k, 1, 2);
					}
					if (l == 4) {
						world.setBlockMetadataWithNotify(i, l, k, 2, 2);
						world.func_147464_a(i, l, k, headID, this.func_149738_a(world));
						return;
					}
					break;
				} else if (block == Blocks.bedrock) {
					if (world.func_147439_a(i, l + 1, k) == headID) {
						world.setBlockMetadataWithNotify(i, l + 1, k, 2, 2);
						world.func_147464_a(i, l + 1, k, headID, this.func_149738_a(world));
						return;
					}
				}
			}
			world.func_147464_a(i, j, k, this, this.func_149738_a(world) * 2);
		}
	}
}