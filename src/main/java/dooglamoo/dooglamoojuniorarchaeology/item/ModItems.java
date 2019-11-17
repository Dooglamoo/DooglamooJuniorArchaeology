/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.item;

import dooglamoo.dooglamoojuniorarchaeology.DooglamooJuniorArchaeology;
import dooglamoo.dooglamoojuniorarchaeology.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(DooglamooJuniorArchaeology.MODID)
public class ModItems
{
	public static final Item HAMMER = null;
	public static final Item PICK = null;
	public static final Item DRILL = null;
	public static final Item BUBBLE_GUM = null;
	public static final Item SPIDER_NEST = null;
	public static final Item FEATHERY_SPINE = null;
	public static final Item BLACK_PEARL = null;
	public static final Item LEATHER_PELT = null;
	public static final Item BLAZE_STONE = null;
	public static final Item CREEPER_GAS = null;
	public static final Item BUBBLE_GUM_ARTIFACT = null;
	public static final Item SPIDER_NEST_ARTIFACT = null;
	public static final Item FEATHERY_SPINE_ARTIFACT = null;
	public static final Item BLACK_PEARL_ARTIFACT = null;
	public static final Item SKELETON_ARTIFACT = null;
	public static final Item ALGAE_ARTIFACT = null;
	public static final Item LEATHER_PELT_ARTIFACT = null;
	public static final Item BLAZE_STONE_ARTIFACT = null;
	public static final Item CREEPER_GAS_ARTIFACT = null;
	public static final Item BADGE1 = null;
	public static final Item BADGE2 = null;
	public static final Item BADGE3 = null;
	public static final Item BADGE4 = null;
	public static final Item BADGE5 = null;
	public static final Item BADGE6 = null;
	public static final Item BADGE7 = null;
	public static final Item BADGE8 = null;
	public static final Item BADGE9 = null;
	
	public static void registerItems(IForgeRegistry<Item> registry)
    {
		register(registry, new AlgaeBlockItem(ModBlocks.ALGAE, new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, new BlockItem(ModBlocks.SKELETON, new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, new BlockItem(ModBlocks.ARCHAEOLOGY_CHEST, new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, new BlockItem(ModBlocks.SPIDER_NEST, new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		
		register(registry, "hammer", new ArchaeologyToolItem(ItemTier.STONE, new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "pick", new ArchaeologyToolItem(ItemTier.IRON, new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "drill", new ArchaeologyToolItem(ItemTier.DIAMOND, new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "bubble_gum", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "feathery_spine", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "black_pearl", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "leather_pelt", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "blaze_stone", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "creeper_gas", new CreeperGasItem(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "bubble_gum_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "spider_nest_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "feathery_spine_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "black_pearl_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "skeleton_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "algae_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "leather_pelt_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "blaze_stone_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "creeper_gas_artifact", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge1", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge2", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge3", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge4", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge5", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge6", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge7", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge8", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
		register(registry, "badge9", new Item(new Item.Properties().group(ModItemGroups.ARCHAEOLOGY)));
    }

	private static void register(IForgeRegistry<Item> registry, BlockItem blockItem)
	{
		register(registry, blockItem.getBlock(), blockItem);
	}

	protected static void register(IForgeRegistry<Item> registry, Block block, Item item)
	{
		register(registry, block.getRegistryName(), item);
	}

	private static void register(IForgeRegistry<Item> registry, String name, Item item)
	{
		register(registry, new ResourceLocation(DooglamooJuniorArchaeology.MODID, name), item);
	}

	private static void register(IForgeRegistry<Item> registry, ResourceLocation name, Item item)
	{
		if (item instanceof BlockItem)
		{
			((BlockItem)item).addToBlockToItemMap(Item.BLOCK_TO_ITEM, item);
		}
		registry.register(item.setRegistryName(name));
	}
}
