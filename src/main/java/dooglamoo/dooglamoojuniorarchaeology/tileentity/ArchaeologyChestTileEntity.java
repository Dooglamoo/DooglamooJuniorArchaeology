/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.tileentity;

import dooglamoo.dooglamoojuniorarchaeology.block.ArchaeologyChestBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
  value = Dist.CLIENT,
  _interface = IChestLid.class
)
public class ArchaeologyChestTileEntity extends LockableLootTileEntity implements IChestLid, ITickableTileEntity
{
	private static final int SIZE = 54;
	
	private NonNullList<ItemStack> chestContents = NonNullList.withSize(SIZE, ItemStack.EMPTY);
	protected float lidAngle;
	protected float prevLidAngle;
	protected int numPlayersUsing;
	private int ticksSinceSync;
	private net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> chestHandler;

	protected ArchaeologyChestTileEntity(TileEntityType<?> type)
	{
		super(type);
	}

	public ArchaeologyChestTileEntity()
	{
		this(ModTileEntityTypes.ARCHAEOLOGY_CHEST);
	}

	@Override
	public int getSizeInventory()
	{
		return SIZE;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.chestContents)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.archaeologychest");
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound))
		{
			ItemStackHelper.loadAllItems(compound, this.chestContents);
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		if (!this.checkLootAndWrite(compound))
		{
			ItemStackHelper.saveAllItems(compound, this.chestContents);
		}

		return compound;
	}

	@Override
	public void tick()
	{
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		++this.ticksSinceSync;
		this.numPlayersUsing = getNumPlayersUsingUpdated(this.world, this, this.ticksSinceSync, i, j, k, this.numPlayersUsing);
		this.prevLidAngle = this.lidAngle;
		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
		{
			this.playSound(SoundEvents.BLOCK_CHEST_OPEN);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
		{
			float f1 = this.lidAngle;
			if (this.numPlayersUsing > 0)
			{
				this.lidAngle += 0.1F;
			}
			else
			{
				this.lidAngle -= 0.1F;
			}

			if (this.lidAngle > 1.0F)
			{
				this.lidAngle = 1.0F;
			}

			if (this.lidAngle < 0.5F && f1 >= 0.5F)
			{
				this.playSound(SoundEvents.BLOCK_CHEST_CLOSE);
			}

			if (this.lidAngle < 0.0F)
			{
				this.lidAngle = 0.0F;
			}
		}
	}

	private static int getNumPlayersUsingUpdated(World world, LockableTileEntity te, int ticks, int x, int y, int z, int num)
	{
		if (!world.isRemote && num != 0 && (ticks + x + y + z) % 200 == 0)
		{
			num = getNumPlayersUsing(world, te, x, y, z);
		}

		return num;
	}

	private static int getNumPlayersUsing(World world, LockableTileEntity te, int x, int y, int z)
	{
		int i = 0;

		for (PlayerEntity playerentity : world.getEntitiesWithinAABB(PlayerEntity.class,
				new AxisAlignedBB((double) ((float) x - 5.0F), (double) ((float) y - 5.0F),
						(double) ((float) z - 5.0F), (double) ((float) (x + 1) + 5.0F),
						(double) ((float) (y + 1) + 5.0F), (double) ((float) (z + 1) + 5.0F))))
		{
			if (playerentity.openContainer instanceof ChestContainer)
			{
				IInventory iinventory = ((ChestContainer)playerentity.openContainer).getLowerChestInventory();
				if (iinventory == te)
				{
					// move to a PlayerContainerEvent
                    if (!playerentity.isPotionActive(Effects.HASTE))
                    {
                    	playerentity.addPotionEffect(new EffectInstance(Effects.HASTE, 1800, 0, true, false));
                    }
					++i;
				}
			}
		}

		return i;
	}

	private void playSound(SoundEvent sound)
	{
		double d0 = (double) this.pos.getX() + 0.5D;
		double d1 = (double) this.pos.getY() + 0.5D;
		double d2 = (double) this.pos.getZ() + 0.5D;
		this.world.playSound((PlayerEntity)null, d0, d1, d2, sound, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public boolean receiveClientEvent(int id, int type)
	{
		if (id == 1)
		{
			this.numPlayersUsing = type;
			return true;
		}
		else
		{
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void openInventory(PlayerEntity player)
	{
		if (!player.isSpectator())
		{
			if (this.numPlayersUsing < 0)
			{
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.onOpenOrClose();
		}
	}

	@Override
	public void closeInventory(PlayerEntity player)
	{
		if (!player.isSpectator())
		{
			--this.numPlayersUsing;
			this.onOpenOrClose();
		}
	}

	private void onOpenOrClose()
	{
		Block block = this.getBlockState().getBlock();
		if (block instanceof ArchaeologyChestBlock)
		{
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	@Override
	protected NonNullList<ItemStack> getItems()
	{
		return this.chestContents;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items)
	{
		this.chestContents = items;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getLidAngle(float partialTicks)
	{
		return MathHelper.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player)
	{
		return ChestContainer.createGeneric9X6(id, player, this);
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
		if (this.chestHandler != null)
		{
			this.chestHandler.invalidate();
			this.chestHandler = null;
		}
	}

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, Direction side)
	{
		if (!this.removed && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (this.chestHandler == null)
			{
				this.chestHandler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
			}
			return this.chestHandler.cast();
		}
		return super.getCapability(cap, side);
	}

	private net.minecraftforge.items.IItemHandlerModifiable createHandler()
	{
		return new net.minecraftforge.items.wrapper.InvWrapper(this);
	}

	@Override
	public void remove()
	{
		super.remove();
		if (chestHandler != null)
		{
			chestHandler.invalidate();
		}
	}
}
