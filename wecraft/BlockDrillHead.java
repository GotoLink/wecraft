package wecraft;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDrillHead extends Block {
	protected BlockDrillHead() {
		super(Material.field_151573_f);
        func_149675_a(true);
	}

	public void destroyBelow(World world, int i, int j, int k) {
		Block upID = world.func_147439_a(i, j + 1, k);
		if (upID != this && upID != Wecraft.drill) {
			world.func_147468_f(i, j, k);
		}
	}

	public void destroyUp(World world, int i, int j, int k) {
		Block upId = world.func_147439_a(i, j + 1, k);
		if (upId == this) {
			world.setBlockMetadataWithNotify(i, j + 1, k, 2, 2);
			world.func_147464_a(i, j + 1, k, this, this.func_149738_a(world));
		} else if (upId == Wecraft.drill) {
			world.setBlockMetadataWithNotify(i, j + 1, k, 0, 2);
		}
		world.func_147468_f(i, j, k);
	}

	@Override
	public Item func_149650_a(int i, Random random, int j) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item func_149694_d(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public boolean func_149662_c() {
		return false;
	}

	@Override
	public void func_149695_a(World world, int i, int j, int k, Block l) {
		int m = world.getBlockMetadata(i, j, k);
		if (m == 2) {
			destroyUp(world, i, j, k);
		}
		destroyBelow(world, i, j, k);
	}

	@Override
	public boolean func_149686_d() {
		return false;
	}

	@Override
	public void func_149719_a(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		if (l != 1) {
            func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
            func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
		}
	}

	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
        func_149695_a(world, i, j, k, this);
	}
}