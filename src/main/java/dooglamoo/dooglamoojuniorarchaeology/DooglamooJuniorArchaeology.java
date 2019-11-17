/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dooglamoo.dooglamoojuniorarchaeology.block.ModBlocks;
import dooglamoo.dooglamoojuniorarchaeology.client.ClientProxy;
import dooglamoo.dooglamoojuniorarchaeology.item.ModItems;
import dooglamoo.dooglamoojuniorarchaeology.tileentity.ModTileEntityTypes;

@Mod(DooglamooJuniorArchaeology.MODID)
public class DooglamooJuniorArchaeology
{
	public static final String MODID = "dooglamoojuniorarchaeology";
	public static CommonProxy proxy;

	private static final Logger LOGGER = LogManager.getLogger();

	public DooglamooJuniorArchaeology()
	{
		proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event)
	{

	}

	private void doClientStuff(final FMLClientSetupEvent event)
	{
		proxy.setup();
	}

	@SubscribeEvent
	public void lootTableLoad(LootTableLoadEvent event)
	{
		ResourceLocation table = null;
		switch (event.getName().toString())
		{
		case "minecraft:blocks/dirt":
			table = new ResourceLocation(MODID, "inject/dirt");
			break;
		case "minecraft:blocks/coarse_dirt":
			table = new ResourceLocation(MODID, "inject/coarse_dirt");
			break;
		case "minecraft:blocks/podzol":
			table = new ResourceLocation(MODID, "inject/podzol");
			break;
		case "minecraft:blocks/grass_block":
			table = new ResourceLocation(MODID, "inject/grass_block");
			break;
		case "minecraft:blocks/stone":
			table = new ResourceLocation(MODID, "inject/stone");
			break;
		case "minecraft:blocks/granite":
			table = new ResourceLocation(MODID, "inject/granite");
			break;
		case "minecraft:blocks/andesite":
			table = new ResourceLocation(MODID, "inject/andesite");
			break;
		case "minecraft:blocks/diorite":
			table = new ResourceLocation(MODID, "inject/diorite");
			break;
		case "minecraft:blocks/gravel":
			table = new ResourceLocation(MODID, "inject/gravel");
			break;
		case "minecraft:blocks/sand":
			table = new ResourceLocation(MODID, "inject/sand");
			break;
		case "minecraft:blocks/red_sand":
			table = new ResourceLocation(MODID, "inject/red_sand");
			break;
		case "minecraft:blocks/sandstone":
			table = new ResourceLocation(MODID, "inject/sandstone");
			break;
		case "minecraft:blocks/red_sandstone":
			table = new ResourceLocation(MODID, "inject/red_sandstone");
			break;
		case "minecraft:blocks/clay":
			table = new ResourceLocation(MODID, "inject/clay");
			break;
		case "minecraft:blocks/ice":
			table = new ResourceLocation(MODID, "inject/ice");
			break;
		case "minecraft:blocks/packed_ice":
			table = new ResourceLocation(MODID, "inject/packed_ice");
			break;
		case "minecraft:blocks/snow_block":
			table = new ResourceLocation(MODID, "inject/snow_block");
			break;
		case "minecraft:blocks/obsidian":
			table = new ResourceLocation(MODID, "inject/obsidian");
			break;
		case "minecraft:blocks/terracotta":
			table = new ResourceLocation(MODID, "inject/terracotta");
			break;
		case "minecraft:blocks/white_terracotta":
			table = new ResourceLocation(MODID, "inject/white_terracotta");
			break;
		case "minecraft:blocks/orange_terracotta":
			table = new ResourceLocation(MODID, "inject/orange_terracotta");
			break;
		case "minecraft:blocks/magenta_terracotta":
			table = new ResourceLocation(MODID, "inject/magenta_terracotta");
			break;
		case "minecraft:blocks/light_blue_terracotta":
			table = new ResourceLocation(MODID, "inject/light_blue_terracotta");
			break;
		case "minecraft:blocks/yellow_terracotta":
			table = new ResourceLocation(MODID, "inject/yellow_terracotta");
			break;
		case "minecraft:blocks/lime_terracotta":
			table = new ResourceLocation(MODID, "inject/lime_terracotta");
			break;
		case "minecraft:blocks/pink_terracotta":
			table = new ResourceLocation(MODID, "inject/pink_terracotta");
			break;
		case "minecraft:blocks/gray_terracotta":
			table = new ResourceLocation(MODID, "inject/gray_terracotta");
			break;
		case "minecraft:blocks/light_gray_terracotta":
			table = new ResourceLocation(MODID, "inject/light_gray_terracotta");
			break;
		case "minecraft:blocks/cyan_terracotta":
			table = new ResourceLocation(MODID, "inject/cyan_terracotta");
			break;
		case "minecraft:blocks/purple_terracotta":
			table = new ResourceLocation(MODID, "inject/purple_terracotta");
			break;
		case "minecraft:blocks/blue_terracotta":
			table = new ResourceLocation(MODID, "inject/blue_terracotta");
			break;
		case "minecraft:blocks/brown_terracotta":
			table = new ResourceLocation(MODID, "inject/brown_terracotta");
			break;
		case "minecraft:blocks/green_terracotta":
			table = new ResourceLocation(MODID, "inject/green_terracotta");
			break;
		case "minecraft:blocks/red_terracotta":
			table = new ResourceLocation(MODID, "inject/red_terracotta");
			break;
		case "minecraft:blocks/black_terracotta":
			table = new ResourceLocation(MODID, "inject/black_terracotta");
			break;
		}

		if (table != null)
		{
			event.getTable().removePool("main");
			event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(table)).build());
		}
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void onBlockRegistry(RegistryEvent.Register<Block> event)
		{
			ModBlocks.registerBlocks(event.getRegistry());
		}

		@SubscribeEvent
		public static void onItemRegistry(RegistryEvent.Register<Item> event)
		{
			ModItems.registerItems(event.getRegistry());
		}

		@SubscribeEvent
		public static void onTileEntityTypeRegistry(RegistryEvent.Register<TileEntityType<?>> event)
		{
			ModTileEntityTypes.registerTileEntityTypes(event.getRegistry());
		}
	}
}
