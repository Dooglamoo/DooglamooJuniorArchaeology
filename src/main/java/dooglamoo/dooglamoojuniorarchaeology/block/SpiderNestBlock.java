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
import net.minecraft.block.CocoaBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SpiderNestBlock extends CocoaBlock
{
	public SpiderNestBlock(Block.Properties builder)
	{
		super(builder);
	}
	
    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random random)
    {
    	super.tick(state, world, pos, random);
    	if (state.get(AGE) >= 2)
    	{
    		world.setBlockState(pos, Blocks.COBWEB.getDefaultState(), 2);
    	}
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.offset(state.get(HORIZONTAL_FACING))).getBlock();
        return block.isIn(BlockTags.LEAVES);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
    {
    	return false;
    }
}
