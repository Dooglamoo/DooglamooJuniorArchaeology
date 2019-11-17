/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.block;

import dooglamoo.dooglamoojuniorarchaeology.DooglamooJuniorArchaeology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(DooglamooJuniorArchaeology.MODID)
public class ModBlocks
{
	public static final Block SPIDER_NEST = null;
	public static final Block SKELETON = null;
	public static final Block ALGAE = null;
	public static final Block ARCHAEOLOGY_CHEST = null;
	
	public static void registerBlocks(IForgeRegistry<Block> registry)
    {
		register(registry, "spider_nest", new SpiderNestBlock(Block.Properties.create(Material.PLANTS).tickRandomly().hardnessAndResistance(0.2F, 3.0F).sound(SoundType.CLOTH)));
		register(registry, "skeleton", new Block(Block.Properties.create(Material.ROCK, MaterialColor.SAND).hardnessAndResistance(0.8F)));
		register(registry, "algae", new AlgaeBlock(Block.Properties.create(Material.PLANTS).tickRandomly().hardnessAndResistance(0.0F).lightValue(10).sound(SoundType.SLIME)));
		register(registry, "archaeology_chest", new ArchaeologyChestBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F, 10.0F).sound(SoundType.STONE)));
    }
	
	private static void register(IForgeRegistry<Block> registry, String name, Block block)
	{
		registry.register(block.setRegistryName(name));
	}
}
