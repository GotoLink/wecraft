package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGrill extends Block {
	protected BlockGrill() {
		super(Material.field_151573_f);
        func_149675_a(true);
	}

	@Override
	public int func_149738_a(World world) {
		return 5;
	}

	//Different texture for each side
	@Override
	public IIcon func_149691_a(int i, int j) {
		if (i == 1) {
			return field_149761_L;
		}
		return Blocks.furnace.func_149733_h(i);
	}

	//With a click by the player, it burns.
	@Override
	public void func_149699_a(World world, int i, int j, int k, EntityPlayer entityplayer) {
        func_149727_a(world, i, j, k, entityplayer, 0, 0, 0, 0);
	}

	@Override
	public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
		if (world.func_147439_a(i, j + 1, k) == Blocks.air) {
			world.func_147449_b(i, j + 1, k, Blocks.fire);
		} else {
			world.func_147468_f(i, j + 1, k);
		}
		return true;
	}

	//Check if the block is getting power
	@Override
	public void func_149695_a(World world, int i, int j, int k, Block l) {
		if (l.func_149744_f() && world.isBlockIndirectlyGettingPowered(i, j, k)) {
			if (world.func_147439_a(i, j + 1, k) == Blocks.air) {
				world.func_147449_b(i, j + 1, k, Blocks.fire);
			} else if (world.func_147439_a(i, j + 1, k) == Blocks.fire && l.func_149744_f()) {
				world.func_147468_f(i, j + 1, k);
			}
		}
		if (!world.isBlockIndirectlyGettingPowered(i, j, k) && l.func_149744_f()) {
			if (world.func_147439_a(i, j + 1, k) == Blocks.fire) {
				world.func_147468_f(i, j + 1, k);
			}
		}
		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			if (world.func_147439_a(i, j + 1, k) == Blocks.air) {
				world.func_147449_b(i, j + 1, k, Blocks.fire);
			}
		}
	}

	//gravelburner
	@Override
	public void func_149674_a(World world, int i, int j, int k, Random random) {
		//testing powered
		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			if (world.func_147439_a(i, j + 1, k) == Blocks.air) {
				world.func_147449_b(i, j + 1, k, Blocks.fire);
			}
		}
		if (world.func_147439_a(i, j + 1, k) == Blocks.gravel) {
			if (!world.isRemote) {
				if (random.nextInt(2) == 0) {
					Item i1 = Items.flint;
					float f = 0.7F;
					double d = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(world, i + d, j + d1, k + d2, new ItemStack(i1, 1, 0));
					entityitem.field_145804_b = 10;
					world.spawnEntityInWorld(entityitem);
				} else {
					Blocks.gravel.func_149697_b(world, i, j + 1, k, world.getBlockMetadata(i, j + 1, k), 0);
				}
			}
			world.func_147468_f(i, j + 1, k);
			//fire smoke and sound
			world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
			for (int l = 0; l < 3; l++) {
				float f = i + random.nextFloat();
				float f6 = j + random.nextFloat() * 0.5F + 0.5F;
				float f12 = k + random.nextFloat();
				world.spawnParticle("largesmoke", f, f6, f12, 0.0D, 0.0D, 0.0D);
			}
			for (int j1 = 0; j1 < 2; j1++) {
				float f2 = i + 1 - random.nextFloat() * 0.1F;
				float f8 = j + random.nextFloat();
				float f14 = k + random.nextFloat();
				world.spawnParticle("largesmoke", f2, f8, f14, 0.0D, 0.0D, 0.0D);
			}
			for (int k1 = 0; k1 < 2; k1++) {
				float f3 = i + random.nextFloat();
				float f9 = j + random.nextFloat();
				float f15 = k + random.nextFloat() * 0.1F;
				world.spawnParticle("largesmoke", f3, f9, f15, 0.0D, 0.0D, 0.0D);
			}
			for (int l1 = 0; l1 < 2; l1++) {
				float f4 = i + random.nextFloat();
				float f10 = j + random.nextFloat();
				float f16 = k + 1 - random.nextFloat() * 0.1F;
				world.spawnParticle("largesmoke", f4, f10, f16, 0.0D, 0.0D, 0.0D);
			}
			for (int i2 = 0; i2 < 2; i2++) {
				float f5 = i + random.nextFloat();
				float f11 = j + 1 - random.nextFloat() * 0.1F;
				float f17 = k + random.nextFloat();
				world.spawnParticle("largesmoke", f5, f11, f17, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}