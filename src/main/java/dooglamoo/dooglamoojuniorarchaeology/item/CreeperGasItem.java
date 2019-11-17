/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class CreeperGasItem extends Item
{
	public CreeperGasItem(Item.Properties builder)
	{
		super(builder);
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
    {
		if (entity.posY < 220.0)
		{
			if (Blocks.AIR == entity.world.getBlockState(new BlockPos(MathHelper.floor(entity.posX), MathHelper.floor(entity.posY + 0.03999999910593033 + 0.501), MathHelper.floor(entity.posZ))).getBlock())
			{
				entity.setMotion(entity.getMotion().add(0, 0.03999999910593033D + 0.001, 0));
			}
		}
        return false;
    }
	
	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		return 2000;
	}
}
