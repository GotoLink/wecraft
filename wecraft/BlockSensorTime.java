package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSensorTime extends Block {
	private IIcon[] sensorTexture;

	protected BlockSensorTime() {
		super(Material.wood);
        setTickRandomly(true);
	}

	@Override
	public int tickRate(World world) {
		return 10;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			if (j < 4)
				return sensorTexture[j];
			else
				return sensorTexture[0];
		} else {
			return Blocks.planks.getBlockTextureFromSide(i);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.sensorTexture = new IIcon[4];
		for (int i = 0; i < sensorTexture.length; i++)
			this.sensorTexture[i] = par1IconRegister.registerIcon(this.getTextureName() + i);
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
        onBlockActivated(world, i, j, k, entityplayer, 0, 0, 0, 0);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.click", 0.3F, 0.6F);
		int l = world.getBlockMetadata(i, j, k);
		l++;
		if (l == 4) {
			l = 0;
		}
		world.setBlockMetadataWithNotify(i, j, k, l, 3);
		notify(world, i, j, k);
		return true;
	}

	private void notify(World world, int i, int j, int k) {
		world.notifyBlocksOfNeighborChange(i, j, k, this);
		world.notifyBlocksOfNeighborChange(i, j + 1, k, this);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		notify(world, i, j, k);
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		notify(world, i, j, k);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int i, int j, int k, int l) {
		int m = world.getBlockMetadata(i, j, k);
		float floatTime = ((World) world).getCelestialAngle(1.0F) * 24.0F;
		if (floatTime >= 12.0F && floatTime <= 18.0F && m == 2) {
			return (int) (floatTime - 11.0F) * 2;
		}
		if (floatTime >= 18.0F && floatTime <= 24.0F && m == 3) {
			return (int) (floatTime - 17.0F) * 2;
		}
		if (floatTime >= 0.0F && floatTime <= 6.0F && m == 0) {
			return (int) (floatTime + 1) * 2;
		}
		if (floatTime >= 6.0F && floatTime <= 12.0F && m == 1) {
			return (int) (floatTime - 5.0F) * 2;
		}
		return 0;
	}

	@Override
	public boolean onBlockEventReceived(World world, int i, int j, int k, int par5, int par6) {
		notify(world, i, j, k);
        return false;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		notify(world, i, j, k);
	}
}