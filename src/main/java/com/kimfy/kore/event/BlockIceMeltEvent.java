package com.kimfy.kore.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
@Event.HasResult
public class BlockIceMeltEvent extends BlockEvent
{
    private IBlockState resultState = Blocks.water.getDefaultState();

    public BlockIceMeltEvent(World world, BlockPos pos, IBlockState state)
    {
        super(world, pos, state);
    }

    public void setResultState(IBlockState state)
    {
        this.resultState = state;
    }

    public IBlockState getResultState()
    {
        return this.resultState;
    }

    @Override
    public void setCanceled(boolean cancel)
    {
        super.setCanceled(cancel);
        this.setResult(cancel ? Result.DENY : Result.DEFAULT);
    }
}
