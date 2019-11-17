/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.tileentity;

import dooglamoo.dooglamoojuniorarchaeology.DooglamooJuniorArchaeology;
import dooglamoo.dooglamoojuniorarchaeology.block.ModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(DooglamooJuniorArchaeology.MODID)
public class ModTileEntityTypes
{
	public static final TileEntityType<ArchaeologyChestTileEntity> ARCHAEOLOGY_CHEST = null;
	
	public static void registerTileEntityTypes(IForgeRegistry<TileEntityType<?>> registry)
    {
		register(registry, "archaeology_chest", TileEntityType.Builder.create(ArchaeologyChestTileEntity::new, ModBlocks.ARCHAEOLOGY_CHEST));
    }
	
	private static <T extends TileEntity> void register(IForgeRegistry<TileEntityType<?>> registry, String name, TileEntityType.Builder<T> builder)
	{
		registry.register(builder.build(null).setRegistryName(name));
	}
}
