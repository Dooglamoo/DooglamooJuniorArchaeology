/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.item;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;

public class ArchaeologyToolItem extends ToolItem
{
	private static final Set<Block> set = Sets.newHashSet(new Block[] {
			Blocks.DIRT,
			Blocks.COARSE_DIRT,
			Blocks.PODZOL,
			Blocks.GRASS_BLOCK,
			Blocks.STONE,
			Blocks.GRANITE,
			Blocks.ANDESITE,
			Blocks.DIORITE,
			Blocks.GRAVEL,
			Blocks.SAND,
			Blocks.RED_SAND,
			Blocks.SANDSTONE,
			Blocks.RED_SANDSTONE,
			Blocks.CLAY,
			Blocks.ICE,
			Blocks.PACKED_ICE,
			Blocks.SNOW_BLOCK,
			Blocks.OBSIDIAN,
			Blocks.TERRACOTTA,
			Blocks.WHITE_TERRACOTTA,
			Blocks.ORANGE_TERRACOTTA,
			Blocks.MAGENTA_TERRACOTTA,
			Blocks.LIGHT_BLUE_TERRACOTTA,
			Blocks.YELLOW_TERRACOTTA,
			Blocks.LIME_TERRACOTTA,
			Blocks.PINK_TERRACOTTA,
			Blocks.GRAY_TERRACOTTA,
			Blocks.LIGHT_GRAY_TERRACOTTA,
			Blocks.CYAN_TERRACOTTA,
			Blocks.PURPLE_TERRACOTTA,
			Blocks.BLUE_TERRACOTTA,
			Blocks.BROWN_TERRACOTTA,
			Blocks.GREEN_TERRACOTTA,
			Blocks.RED_TERRACOTTA,
			Blocks.BLACK_TERRACOTTA
	});
	
    public ArchaeologyToolItem(IItemTier tier, Item.Properties builder)
    {
    	super(0.0f, 2.0f, tier, set, builder.maxDamage((int)(tier.getMaxUses() * 0.75)));
    }

    @Override
    public boolean canHarvestBlock(BlockState state)
    {
        if (state.getBlock() == Blocks.OBSIDIAN)
        {
        	return this.getTier().getHarvestLevel() == 3;
        }
        else if (set.contains(state.getBlock()))
        {
        	return true;
        }
		else
		{
			return false;
		}
    }
}
