package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDrill extends Block {
	private Icon drillSideOff, drillSideOn, drillSideTop;

	public BlockDrill(int i) {
		super(i, Material.wood);
		setTickRandomly(true);
	}

	@Override
	public Icon getIcon(int i, int j) {
		int drillStatus = j;
		if (i > 1) {
			if (drillStatus == 0) {
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
			world.setBlockMetadataWithNotify(i, j, k, 1, 3);
		} else {
			world.setBlockMetadataWithNotify(i, j, k, 0, 3);
			int y = j - 1;
			while (y > 3) {
				if (world.getBlockId(i, y, k) == Wecraft.drillHead.blockID) {
					world.setBlockToAir(i, y, k);
				}
				y--;
			}
		}
		world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate(world));
		return true;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		onBlockActivated(world, i, j, k, entityplayer, 0, 0, 0, 0);
	}

	public boolean randomTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(8) == 0) {
			int drillStatus = world.getBlockMetadata(i, j, k);
			if (drillStatus != 0) {
				int headID = Wecraft.drillHead.blockID;
				if (world.getBlockId(i, 4, k) == headID) {
					for (int t = 4; t <= j - 1; t++) {
						if (world.getBlockId(i, t, k) == headID) {
							world.setBlockToAir(i, t, k);
							break;
						}
					}
					world.setBlockMetadataWithNotify(i, j, k, 0, 3);
					return true;
				}
				for (int l = j - 1; l >= 4; l--) {
					int i1 = world.getBlockId(i, l, k);
					Block block = Block.blocksList[i1];
					if (block == null || block.blockMaterial.isLiquid()) {
						world.setBlock(i, l, k, headID);
						if (l + 1 != j) {
							world.setBlockMetadataWithNotify(i, l + 1, k, 1, 3);
						}
						break;
					} else if (i1 != headID) {
						world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.drr", 0.3F, 0.6F);
						block.dropBlockAsItem(world, i, j + 1, k, world.getBlockMetadata(i, l, k), 0);
						world.setBlock(i, l, k, headID);
						if (l + 1 != j) {
							world.setBlockMetadataWithNotify(i, l + 1, k, 1, 3);
						}
						break;
					}
				}
				world.scheduleBlockUpdate(i, j, k, this.blockID, this.tickRate(world));
			}
			return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.drillSideOff = par1IconRegister.registerIcon(this.getTextureName() + "SideOff");
		this.drillSideOn = par1IconRegister.registerIcon(this.getTextureName() + "SideOn");
		this.drillSideTop = par1IconRegister.registerIcon(this.getTextureName() + "Top");
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		for (int l = 0; l < 70; l++) {
			if (randomTick(world, i, j, k, random)) {
				break;
			}
		}
	}
}