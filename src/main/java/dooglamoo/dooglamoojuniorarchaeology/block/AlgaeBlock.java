/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.PlantType;

public class AlgaeBlock extends LilyPadBlock
{
	private static final int[] x_dir = {-2,-2,-2,-1,0,1,2,2,2,1,0,-1};
	private static final int[] z_dir = {1,0,-1,-2,-2,-2,-1,0,1,2,2,2};
	private int[] pos = new int[12];
	
	public AlgaeBlock(Block.Properties builder)
	{
		super(builder);
	}
	
    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random)
    {
    	int air_count = 0;
    	int lily_count = 0;
    	if (world.rand.nextInt(10) == 0)
    	{
    		for (int i = 0; i < x_dir.length; i++)
    		{
    			if (world.getBlockState(pos.add(x_dir[i], 0, z_dir[i])).getBlock() == Blocks.AIR && world.getBlockState(pos.add(x_dir[i], -1, z_dir[i])).getBlock() == Blocks.WATER)
    			{
    				this.pos[air_count++] = i;
    			}
    			else if (world.getBlockState(pos.add(x_dir[i], 0, z_dir[i])).getBlock() == Blocks.LILY_PAD)
    			{
    				lily_count++;
    			}
    		}
    		
    		if (air_count > 1 && lily_count < 2)
    		{
    			int p = this.pos[world.rand.nextInt(air_count)];
    			world.setBlockState(pos.add(x_dir[p], 0, z_dir[p]), Blocks.LILY_PAD.getDefaultState(), 2);
    		}
    	}
    }
    
    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos)
    {
        return PlantType.Water;
    }
}
