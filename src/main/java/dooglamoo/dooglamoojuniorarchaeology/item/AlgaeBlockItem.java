/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.item;

import dooglamoo.dooglamoojuniorarchaeology.block.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class AlgaeBlockItem extends BlockItem
{
    public AlgaeBlockItem(Block block, Item.Properties builder)
    {
        super(block, builder);
    }
    
    @Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		return ActionResultType.PASS;
	}

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
    	ItemStack itemstack = player.getHeldItem(hand);
        RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS)
        {
           return new ActionResult<>(ActionResultType.PASS, itemstack);
        }
        else
        {
           if (raytraceresult.getType() == RayTraceResult.Type.BLOCK)
           {
              BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
              BlockPos blockpos = blockraytraceresult.getPos();
              Direction direction = blockraytraceresult.getFace();
              if (!world.isBlockModifiable(player, blockpos) || !player.canPlayerEdit(blockpos.offset(direction), direction, itemstack))
              {
                 return new ActionResult<>(ActionResultType.FAIL, itemstack);
              }

              BlockPos blockpos1 = blockpos.up();
              BlockState blockstate = world.getBlockState(blockpos);
              Material material = blockstate.getMaterial();
              IFluidState ifluidstate = world.getFluidState(blockpos);
              if ((ifluidstate.getFluid() == Fluids.WATER || material == Material.ICE) && world.isAirBlock(blockpos1))
              {
                 // special case for handling block placement with water lilies
                 net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, blockpos1);
                 world.setBlockState(blockpos1, ModBlocks.ALGAE.getDefaultState(), 11);
                 if (net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, blocksnapshot, net.minecraft.util.Direction.UP))
                 {
                    blocksnapshot.restore(true, false);
                    return new ActionResult<ItemStack>(ActionResultType.FAIL, itemstack);
                 }

                 if (player instanceof ServerPlayerEntity)
                 {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, blockpos1, itemstack);
                 }

                 if (!player.abilities.isCreativeMode)
                 {
                    itemstack.shrink(1);
                 }

                 player.addStat(Stats.ITEM_USED.get(this));
                 world.playSound(player, blockpos, SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                 return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
              }
           }

           return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
    }
}
