package com.kimfy.kore.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventFactory
{
    public static Event onIceMelt(World world, BlockPos pos, IBlockState state)
    {
        BlockIceMeltEvent event = new BlockIceMeltEvent(world, pos, state);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}