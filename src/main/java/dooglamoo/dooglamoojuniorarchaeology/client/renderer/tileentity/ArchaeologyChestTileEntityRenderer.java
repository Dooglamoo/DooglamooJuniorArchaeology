/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;

import dooglamoo.dooglamoojuniorarchaeology.DooglamooJuniorArchaeology;
import dooglamoo.dooglamoojuniorarchaeology.block.ArchaeologyChestBlock;
import dooglamoo.dooglamoojuniorarchaeology.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.model.ChestModel;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArchaeologyChestTileEntityRenderer<T extends TileEntity & IChestLid> extends TileEntityRenderer<T>
{
	private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(DooglamooJuniorArchaeology.MODID, "textures/entity/chest/archaeology_chest.png");
	private final ChestModel simpleChest = new ChestModel();

	public void render(T tileEntity, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GlStateManager.enableDepthTest();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		BlockState blockstate = tileEntity.hasWorld() ? tileEntity.getBlockState()
				: ModBlocks.ARCHAEOLOGY_CHEST.getDefaultState().with(ArchaeologyChestBlock.FACING, Direction.SOUTH);
		ChestType chesttype = ChestType.SINGLE;
		if (chesttype != ChestType.LEFT)
		{
			boolean flag = chesttype != ChestType.SINGLE;
			ChestModel chestmodel = this.getChestModel(tileEntity, destroyStage);
			if (destroyStage >= 0)
			{
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scalef(flag ? 8.0F : 4.0F, 4.0F, 1.0F);
				GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
				GlStateManager.matrixMode(5888);
			}
			else
			{
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.translatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
			GlStateManager.scalef(1.0F, -1.0F, -1.0F);
			float f = blockstate.get(ArchaeologyChestBlock.FACING).getHorizontalAngle();
			if ((double) Math.abs(f) > 1.0E-5D)
			{
				GlStateManager.translatef(0.5F, 0.5F, 0.5F);
				GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
				GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
			}

			this.applyLidRotation(tileEntity, partialTicks, chestmodel);
			chestmodel.renderAll();
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			if (destroyStage >= 0)
			{
				GlStateManager.matrixMode(5890);
				GlStateManager.popMatrix();
				GlStateManager.matrixMode(5888);
			}
		}
	}

	private ChestModel getChestModel(T tileEntity, int destroyStage)
	{
		ResourceLocation resourcelocation;
		if (destroyStage >= 0)
		{
			resourcelocation = DESTROY_STAGES[destroyStage];
		}
		else
		{
			resourcelocation = TEXTURE_NORMAL;
		}

		this.bindTexture(resourcelocation);
		return this.simpleChest;
	}

	private void applyLidRotation(T tileEntity, float ticks, ChestModel model)
	{
		float f = ((IChestLid)tileEntity).getLidAngle(ticks);
		f = 1.0F - f;
		f = 1.0F - f * f * f;
		model.getLid().rotateAngleX = -(f * ((float) Math.PI / 2F));
	}
}
