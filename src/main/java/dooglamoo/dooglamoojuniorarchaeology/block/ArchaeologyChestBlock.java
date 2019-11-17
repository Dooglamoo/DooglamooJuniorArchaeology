/*******************************************************************************
 * Copyright 2014-2019 Dooglamoo
 * 
 * Licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License, (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 * 
 *   http://creativecommons.org/licenses/by-nc-nd/4.0
 ******************************************************************************/
package dooglamoo.dooglamoojuniorarchaeology.block;

import java.util.List;

import javax.annotation.Nullable;

import dooglamoo.dooglamoojuniorarchaeology.tileentity.ArchaeologyChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArchaeologyChestBlock extends ContainerBlock implements IWaterLoggable
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 16.0D);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 16.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	private static final InventoryFactory<IInventory> field_220109_i = new InventoryFactory<IInventory>() {
		public IInventory forSingle(ArchaeologyChestTileEntity te)
		{
			return te;
		}
	};
	private static final InventoryFactory<INamedContainerProvider> field_220110_j = new InventoryFactory<INamedContainerProvider>() {
		public INamedContainerProvider forSingle(ArchaeologyChestTileEntity te)
		{
			return te;
		}
	};

	protected ArchaeologyChestBlock(Block.Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED,	Boolean.valueOf(false)));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean hasCustomBreakingProgress(BlockState state)
	{
		return true;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
	{
		if (state.get(WATERLOGGED))
		{
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		
		return state;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		Direction direction = context.getPlacementHorizontalFacing().getOpposite();
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return this.getDefaultState().with(FACING, direction).with(WATERLOGGED,	Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	}

	@Override
	public IFluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		if (stack.hasDisplayName())
		{
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof ArchaeologyChestTileEntity)
			{
				((ArchaeologyChestTileEntity)tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof IInventory)
			{
				InventoryHelper.dropInventoryItems(world, pos, (IInventory)tileentity);
				world.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			INamedContainerProvider inamedcontainerprovider = this.getContainer(state, world, pos);
			if (inamedcontainerprovider != null)
			{
				player.openContainer(inamedcontainerprovider);
				player.addStat(this.getOpenStat());
			}

			return true;
		}
	}

	private Stat<ResourceLocation> getOpenStat()
	{
		return Stats.CUSTOM.get(Stats.OPEN_CHEST);
	}

	@Nullable
	private static <T> T getInventoryFromFactory(BlockState state, IWorld world, BlockPos pos, boolean allowBlocked, InventoryFactory<T> p_220106_4_)
	{
		TileEntity tileentity = world.getTileEntity(pos);
		if (!(tileentity instanceof ArchaeologyChestTileEntity))
		{
			return (T) null;
		}
		else if (!allowBlocked && isBlocked(world, pos))
		{
			return (T) null;
		}
		else
		{
			return p_220106_4_.forSingle((ArchaeologyChestTileEntity)tileentity);
		}
	}

	@Nullable
	private static IInventory getInventory(BlockState state, World world, BlockPos pos, boolean allowBlocked)
	{
		return getInventoryFromFactory(state, world, pos, allowBlocked, field_220109_i);
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos)
	{
		return getInventoryFromFactory(state, world, pos, false, field_220110_j);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return new ArchaeologyChestTileEntity();
	}

	private static boolean isBlocked(IWorld world, BlockPos pos)
	{
		return isBelowSolidBlock(world, pos) || isCatSittingOn(world, pos);
	}

	private static boolean isBelowSolidBlock(IBlockReader world, BlockPos pos)
	{
		BlockPos blockpos = pos.up();
		return world.getBlockState(blockpos).isNormalCube(world, blockpos);
	}

	private static boolean isCatSittingOn(IWorld world, BlockPos pos)
	{
		List<CatEntity> list = world.getEntitiesWithinAABB(CatEntity.class,
				new AxisAlignedBB((double) pos.getX(), (double) (pos.getY() + 1),
						(double) pos.getZ(), (double) (pos.getX() + 1),
						(double) (pos.getY() + 2), (double) (pos.getZ() + 1)));
		if (!list.isEmpty())
		{
			for (CatEntity catentity : list)
			{
				if (catentity.isSitting())
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean hasComparatorInputOverride(BlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos)
	{
		return Container.calcRedstoneFromInventory(getInventory(blockState, world, pos, false));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type)
	{
		return false;
	}

	interface InventoryFactory<T>
	{
		T forSingle(ArchaeologyChestTileEntity te);
	}
}
