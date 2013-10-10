package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockGrill extends Block
{
	protected BlockGrill(int i)
    {
        super(i, Material.iron);
        setTickRandomly(true);
	}
	@Override
	public int tickRate(World world)
    {
        return 5;
    }
	
	//Different texture for each side
	@Override
    public Icon getIcon(int i, int j)
    {
        if(i == 1)
        {
            return blockIcon;
        }
        return Block.furnaceIdle.getBlockTextureFromSide(i);
    }

   //With a click by the player, it burns.
	@Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
		onBlockActivated(world, i, j, k, entityplayer,0,0,0,0);
    }
	@Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
        if(world.getBlockId(i,j+1,k) == 0)
        {               
        	world.setBlock(i, j+1, k, Block.fire.blockID);
        }else{
        	world.setBlockToAir(i, j+1, k);
        }
        return true;
    }
    //Check if the block is getting power
	@Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(l > 0 && Block.blocksList[l].canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k))
        {
            if(world.getBlockId(i,j+1,k) == 0)
            {               
            	world.setBlock(i, j+1, k, Block.fire.blockID);
            }else if(world.getBlockId(i,j+1,k) == Block.fire.blockID && l > 0 && Block.blocksList[l].canProvidePower())
            {
            	world.setBlockToAir(i, j+1, k);
            }
        }
        if(!world.isBlockIndirectlyGettingPowered(i, j, k) && l > 0 && Block.blocksList[l].canProvidePower())
        {
            if(world.getBlockId(i,j+1,k) == Block.fire.blockID)
            {               
            	world.setBlockToAir(i, j+1, k);
            }
        }
        if(world.isBlockIndirectlyGettingPowered(i, j, k))
        {
            if(world.getBlockId(i,j+1,k) == 0)
            {               
               world.setBlock(i, j+1, k, Block.fire.blockID);
            }
        }
    }
    //gravelburner
	@Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        //testing powered
        if(world.isBlockIndirectlyGettingPowered(i, j, k))
        {
            if(world.getBlockId(i,j+1,k) == 0)
            {               
               world.setBlock(i, j+1, k, Block.fire.blockID);
            }
        }
        if(world.getBlockId(i,j+1,k) == Block.gravel.blockID)
        {
        	if(!world.isRemote){
                if(random.nextInt(2) == 0){
	                int i1 = Item.flint.itemID;
	                float f = 0.7F;
	                double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	                EntityItem entityitem = new EntityItem(world, (double)i + d, (double)j + d1, (double)k + d2, new ItemStack(i1, 1, 0));
	                entityitem.delayBeforeCanPickup = 10;
	                world.spawnEntityInWorld(entityitem);
                }else{
                	Block.gravel.dropBlockAsItem(world, i, j+1, k, world.getBlockMetadata(i, j+1, k), 0);       
                }
        	}
            world.setBlockToAir(i, j+1, k);
        //fire smoke and sound
            world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
            for(int l = 0; l < 3; l++)
            {
                float f = (float)i + random.nextFloat();
                float f6 = (float)j + random.nextFloat() * 0.5F + 0.5F;
                float f12 = (float)k + random.nextFloat();
                world.spawnParticle("largesmoke", f, f6, f12, 0.0D, 0.0D, 0.0D);
            }
            for(int j1 = 0; j1 < 2; j1++)
            {
                float f2 = (float)(i + 1) - random.nextFloat() * 0.1F;
                float f8 = (float)j + random.nextFloat();
                float f14 = (float)k + random.nextFloat();
                world.spawnParticle("largesmoke", f2, f8, f14, 0.0D, 0.0D, 0.0D);
            }
            for(int k1 = 0; k1 < 2; k1++)
            {
                float f3 = (float)i + random.nextFloat();
                float f9 = (float)j + random.nextFloat();
                float f15 = (float)k + random.nextFloat() * 0.1F;
                world.spawnParticle("largesmoke", f3, f9, f15, 0.0D, 0.0D, 0.0D);
            }
            for(int l1 = 0; l1 < 2; l1++)
            {
                float f4 = (float)i + random.nextFloat();
                float f10 = (float)j + random.nextFloat();
                float f16 = (float)(k + 1) - random.nextFloat() * 0.1F;
                world.spawnParticle("largesmoke", f4, f10, f16, 0.0D, 0.0D, 0.0D);
            }
            for(int i2 = 0; i2 < 2; i2++)
            {
                float f5 = (float)i + random.nextFloat();
                float f11 = (float)(j + 1) - random.nextFloat() * 0.1F;
                float f17 = (float)k + random.nextFloat();
                world.spawnParticle("largesmoke", f5, f11, f17, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}