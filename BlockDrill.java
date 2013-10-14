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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.drillSideOff = par1IconRegister.registerIcon(this.getTextureName() + "SideOff");
		this.drillSideOn = par1IconRegister.registerIcon(this.getTextureName() + "SideOn");
		this.drillSideTop = par1IconRegister.registerIcon(this.getTextureName() + "Top");
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
		onBlockActivated(world, i, j, k, entityplayer, 0, 0, 0, 0);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		int drillStatus = world.getBlockMetadata(i, j, k);
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
		if (drillStatus == 0) {
			world.setBlockMetadataWithNotify(i, j, k, 1, 3);
		} else {
			world.setBlockMetadataWithNotify(i, j, k, 0, 3);
		}
		return true;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		//if no player is near enough to update the "randomDisplayTick"
		if (world.getClosestPlayer(i + 0.5D, j + 0.5D, k + 0.5D, 16D) == null) {
			for (int l = 0; l < 70; l++) {
				randomDisplayTick(world, i, j, k, random);
			}
		}
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(8) == 0) {
			int drillStatus = world.getBlockMetadata(i, j, k);
			if (drillStatus != 0) {
				for (int l = j - 1; l >= 4; l--) {
					int headID = Wecraft.drillHead.blockID;
					int i1 = world.getBlockId(i, l, k);
					if (world.getBlockId(i, 5, k) == headID || world.getBlockId(i, 10, k) == 0) {
						for (int t = 6; t <= j - 1; t++) {
							if (world.getBlockId(i, t + 1, k) == headID) {
								world.setBlockMetadataWithNotify(i, t + 1, k, 0, 3);
							}
							if (world.getBlockId(i, t, k) == headID) {
								world.setBlockToAir(i, t, k);
								break;
							}
						}
						break;
					}
					if (i1 == 0) {
						world.setBlock(i, l, k, headID);
						if (l + 1 != j) {
							world.setBlockMetadataWithNotify(i, l + 1, k, 1, 3);
						}
						break;
					}
					if (i1 != 0 && i1 != headID) {
						//  if((float)random.nextInt(10) == 10.0F - Block.blocksList[i1].getHardness())
						world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.drr", 0.3F, 0.6F);
						Block.blocksList[i1].dropBlockAsItem(world, i, j + 1, k, world.getBlockMetadata(i, l, k), 0);
						world.setBlock(i, l, k, headID);
						if (l + 1 != j) {
							world.setBlockMetadataWithNotify(i, l + 1, k, 1, 3);
						}
						break;
					}
				}
			}
		}
	}
}