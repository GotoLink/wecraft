package wecraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConveyorBelt extends Block{
	public int timePeriod = 0;
	private Icon[] topDownTextures;
	private Icon[] topTextures;

	public BlockConveyorBelt(int i)
	{
		super(i, Material.iron);
		setTickRandomly(true);
	}
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	@Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return world.isBlockNormalCube(i, j - 1, k);
    }
	@Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBox(i , j, k,  i+1 , (float)j + 0.52F ,  k+1);
    }
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
		this.blockIcon = par1IconRegister.registerIcon(this.getTextureName()+"Front");
		this.topDownTextures = new Icon[5];
		this.topTextures = new Icon[5];
		for(int i=0;i<5;i++){
			this.topDownTextures[i] = par1IconRegister.registerIcon(this.getTextureName()+"Top"+i);
			this.topTextures[i] = par1IconRegister.registerIcon(this.getTextureName()+"TopDown"+i);
		}
    }
	@Override
    public Icon getIcon(int i, int j)
    {
        int timePeriod2 = 0;
        if(timePeriod==0){
        	timePeriod2=0;
        }
        if(timePeriod==1){
        	timePeriod2=4;
        }
        if(timePeriod==2){
        	timePeriod2=3;
        }
        if(timePeriod==3){
        	timePeriod2=2;
        }
        if(timePeriod==4){
        	timePeriod2=1;
        }
        if(((j == 0) || (j ==2))  && (i == 2 || i == 3)) 
        {
            if((j==0 && i==2) || (j==2 && i==3)){
            return getTexture1(timePeriod2-2);
            }
            return getTexture1(timePeriod+2);
        }
            
        if(((j == 0) || (j == 2)) && (i == 0 || i==1))
        {
            if(j==0){
            return getTexture1(timePeriod);
            }
            return getTexture1(timePeriod2);
        }
        if(((j == 1) || (j == 3))  && (i == 4 || i == 5))
        {
            if((j==1 && i==4) || (j==3 && i==5)){
            return getTexture1(timePeriod+2);
            }
            return getTexture1(timePeriod2-2);
        } 
        if(((j == 1) || (j == 3)) && (i == 0 || i==1))
        {
             if(j==1){
            return getTexture2(timePeriod2);
            }
            return getTexture2(timePeriod);
        }
        return blockIcon;
    }
    
    private Icon getTexture1(int j){
        if(j>4){
            j-=4;
        }
        if(j<0){
            j+=4;
        }
        if(j < 5){
            return topDownTextures[j];            
        }
        return topDownTextures[5];
    
    }
     private Icon getTexture2(int j){
        if(j < 5){
            return topTextures[j];            
        }
        return topTextures[0];
    } 
     @Override
     public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack par6ItemStack)
     {
        int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, l, 3);
     }
     @Override
     public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
     {
        int l = world.getBlockMetadata(i,j,k);
        if(entity instanceof EntityLiving){
           if(l==0){
        	   entity.motionZ = 0.1D;
           }
           if(l==2){
        	   entity.motionZ = -0.1D;
           }
           if(l==1){
        	   entity.motionX = -0.1D;
           }
           if(l==3){
        	   entity.motionX = 0.1D;
           }
       }
       if(entity instanceof EntityItem){
           entity.motionY = 0D;
           entity.motionX = 0D;
           entity.motionZ = 0D;
           if(l==0){
        	   entity.setPosition(entity.posX+((float)i+0.5F-entity.posX)/1.5F,(float)j+0.7F,entity.posZ+0.1D);
           }
           if(l==2){
        	   entity.setPosition(entity.posX+((float)i+0.5F-entity.posX)/1.5F,(float)j+0.7F,entity.posZ-0.1D);
           }
           if(l==1){
        	   entity.setPosition(entity.posX-0.1D,(float)j+0.7F,entity.posZ+((float)k+0.5F-entity.posZ)/1.5F);
           }
           if(l==3){
        	   entity.setPosition(entity.posX+0.1D,(float)j+0.7F,entity.posZ+((float)k+0.5F-entity.posZ)/1.5F);
           }
       }
    }
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	 world.scheduleBlockUpdate(i, j, k, blockID, 5);
    	 if(world.isRemote && random.nextInt(5) == 0 && Wecraft.convertyTexture == 1)
    	 {
    		 timePeriod = timePeriod +1;
    		 if(timePeriod == 5){
    			 timePeriod = 0;
    		 }
    		 world.markBlockForRenderUpdate(i, j, k);
    		 return;
    	 }
    }
}