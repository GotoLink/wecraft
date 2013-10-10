package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSensorFire extends Block
{
	protected BlockSensorFire(int i)
    {
        super(i, Material.rock);
    }
	@Override
	public int tickRate(World world)
    {
        return 5;
    }
	@Override
    public Icon getIcon(int i, int j)
    {
        if(i==0)
        {
        	return blockIcon;
        }
        return Block.stoneSingleSlab.getIcon(i, j);
    }
	@Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        for(int l = 0; l < 50; l++){
        	randomDisplayTick(world, i, j, k, random);
        }
    }
	@Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if(random.nextInt(5) == 0)
        {
	        if(isFireNearby(world, i, j, k))
	        {
	        	world.setBlockMetadataWithNotify(i, j, k, 1, 3);
	        	world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.drr", 0.3F, 0.6F);
	        }else
	        {
	        	world.setBlockMetadataWithNotify(i, j, k, 0, 3);
	        }
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j+1, k, blockID);
        }
    }
	@Override
    public int isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return iblockaccess.getBlockMetadata(i, j, k) == 1?15:0;
    }
	
    private boolean isFireNearby(World world, int i, int j, int k)
    {
        for(int l = i - 5; l <= i + 5; l++)
        {
            for(int i1 = j-5; i1 <= j-1; i1++)
            {
                for(int j1 = k - 5; j1 <= k + 5; j1++)
                {
                    if(world.getBlockId(l, i1, j1) == Block.fire.blockID)
                    {
                        return true;
                    }
                }

            }
        }
        return false;
    }
	@Override
    public boolean canProvidePower()
    {
        return true;
    }
}