package com.gendeathrow.hatchery.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import com.gendeathrow.hatchery.Hatchery;
import com.gendeathrow.hatchery.core.init.ModBlocks;
import com.gendeathrow.hatchery.core.init.ModFluids;
import com.gendeathrow.hatchery.util.FluidHandlerSprayer;

public class Sprayer extends Item
{

	Random rand = new Random();
	
	int capacity = 3000;
	//int maxcapacity = 3000;
	
	
	public Sprayer()
	{
		this.setCreativeTab(Hatchery.hatcheryTabs);
		this.setMaxStackSize(1);
		this.setMaxDamage(3000);
		this.setNoRepair();

	}
	
	@Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
//	
//	@Override
//    public int getDamage(ItemStack stack)
//    {
//    	FluidStack properties = FluidUtil.getFluidContained(stack);
//    	if(properties == null) return 0;
//    	System.out.println(properties.amount);
//    	return this.capacity - properties.amount; 
//    }
//    
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return ((double)stack.getMaxDamage() - (double)getAmount(stack)) / stack.getMaxDamage();
    }
    
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {

        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

        if (raytraceresult == null)
        {
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        else
        {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos))
            {
                return new ActionResult(EnumActionResult.FAIL, itemStackIn);
            }
 
                if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemStackIn))
                {
                    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
                }
                else
                {
            		if(!worldIn.isRemote)
            		{
            			if(worldIn.getBlockState(blockpos).getBlock() == ModFluids.liquidfertilizer.getBlock())
            			{
            				ItemStack newStack = FluidUtil.tryFillContainer(itemStackIn ,FluidUtil.getFluidHandler(worldIn, blockpos, raytraceresult.sideHit), 1000, playerIn, true);
            				
            				if(newStack == null) return new ActionResult(EnumActionResult.FAIL, itemStackIn);
            				else return new ActionResult(EnumActionResult.SUCCESS, newStack);
            			}
//            			else if(worldIn.getTileEntity(blockpos) != null)
//            			{
//            				TileEntity te = worldIn.getTileEntity(blockpos);
//            				if(te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, raytraceresult.sideHit))
//            				{
//                				ItemStack newStack = FluidUtil.tryFillContainer(itemStackIn ,FluidUtil.getFluidHandler(worldIn, blockpos, raytraceresult.sideHit), 1000, playerIn, true);
//                				
//                				if(newStack == null) return new ActionResult(EnumActionResult.FAIL, itemStackIn);
//                				else return new ActionResult(EnumActionResult.SUCCESS, newStack);
//            				}
//            			}
            		
                        
                    }
                }
           }
		return new ActionResult(EnumActionResult.FAIL, itemStackIn);
    }
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		
	    if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)) 
	    {
	    	return EnumActionResult.FAIL;
	    }
	    
	    if(getAmount(stack) < 5) return EnumActionResult.FAIL;
	    
		if(worldIn.isRemote)
		{
			for(int x = -1; x <= 1; x++)
			{
				for (int z = -1; z <= 1; z++)
				{
					double d0 = pos.add(x, 0, z).getX() + rand.nextFloat();
					double d1 = pos.add(x, 0, z).getY() + 1.0D;
					double d2 = pos.add(x, 0, z).getZ() + rand.nextFloat();
		        
					IBlockState checkSolidState = worldIn.getBlockState(pos);
					Block checkSolid = checkSolidState.getBlock();
		        
					if ((checkSolidState.isOpaqueCube()) || ((checkSolid instanceof BlockFarmland))) 
					{	
						d1 += 1.0D;
					}
		        
					worldIn.spawnParticle(EnumParticleTypes.WATER_DROP, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[5]);
		        
				}
			}
		}
		else
		{
			int chance = this.rand.nextInt(99)+1;
			
			if(chance < 75)
			{
		        for (int xAxis = -2; xAxis <= 2; xAxis++) 
		        {
		          for (int zAxis = -2; zAxis <= 2; zAxis++) 
		          {
		            for (int yAxis = -1; yAxis <= 2; yAxis++)
		            {
		            	Block checkBlock = worldIn.getBlockState(pos.add(xAxis, yAxis, zAxis)).getBlock();
		            	
		            	Block aboveBlock = worldIn.getBlockState(pos.add(xAxis, yAxis, zAxis)).getBlock();
		            	
		               	if((checkBlock == Blocks.FARMLAND || checkBlock == Blocks.DIRT))
		               	{
		               		if(rand.nextInt(99)+1 < 30)
		               		{
		               			Block block;
		               			if(checkBlock == Blocks.FARMLAND)
		               			{
		               				block = ModBlocks.fertilzedFarmland;
		               			}
		               			else
		               			{
		               				block = ModBlocks.fertlizedDirt;
		               			}
		               		
		               			worldIn.setBlockState(pos.add(xAxis, yAxis, zAxis), block.getDefaultState(), 2);
		               			worldIn.setBlockState(pos.add(xAxis, yAxis, zAxis), block.getDefaultState(), 1);
		               			

		               			
		               			if(this.rand.nextDouble() < .45) 
		               			{
			               			FluidUtil.getFluidHandler(stack).drain(2, true);
			               			stack.setItemDamage(this.capacity - getAmount(stack));
		               			}

		               		}
		               	}
		               	else if(checkBlock == ModBlocks.fertilzedFarmland || checkBlock == ModBlocks.fertlizedDirt)
		               	{
	               			worldIn.scheduleBlockUpdate(pos.add(xAxis, yAxis, zAxis), checkBlock, 0, 1);

	               			if(this.rand.nextDouble() < .45) 
	               			{
	               				FluidUtil.getFluidHandler(stack).drain(1, true);
	               				stack.setItemDamage(this.capacity - getAmount(stack));
	               			}
		               	}
		            }
		          }
		        }
			}
		}
		
		return EnumActionResult.FAIL;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) 
	{
		return new FluidHandlerSprayer(stack, capacity);
	}
	
	
	public int getAmount(ItemStack stack)
	{
		FluidStack fstack = FluidUtil.getFluidContained(stack);
		if(fstack != null) return fstack.amount;
		else return 0; 
	}
	
	
//	@Override
//	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
//	{
//		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
//	}
//
//	@Override
//	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
//	{
//        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
//        {
//            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
//        }
//        return null;
//	}

	
	
	
}
