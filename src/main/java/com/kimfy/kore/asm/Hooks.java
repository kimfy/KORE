package com.kimfy.kore.asm;

import com.kimfy.kore.event.BlockIceMeltEvent;
import com.kimfy.kore.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Random;

public class Hooks
{
    public static boolean isFenceCompatibilityEnabled()
    {
        return true;
    }

    public static boolean canFenceConnectTo(IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        return block == Blocks.barrier ? false : ((!(block instanceof BlockFence)) && !(block instanceof BlockFenceGate) ? (block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false) : true);
    }

    public static boolean isWallCompatibilityEnabled()
    {
        return true;
    }

    public static boolean canWallConnectTo(IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        return block == Blocks.barrier ? false : (!(block instanceof BlockWall) && !(block instanceof BlockFenceGate) ? (block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false) : true);
    }

    public static void updateTick(Block block, World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - block.getLightOpacity())
        {
            if (world.provider.doesWaterVaporize())
            {
                world.setBlockToAir(pos);
            }
            else
            {
                Event onIceMelt = EventFactory.onIceMelt(world, pos, state);
                Event.Result canIceMelt = onIceMelt.getResult();

                if (canIceMelt == Event.Result.ALLOW || canIceMelt == Event.Result.DEFAULT)
                {
                    block.dropBlockAsItem(world, pos, state, 0);
                    world.setBlockState(pos, ((BlockIceMeltEvent) onIceMelt).getResultState());
                }
            }
        }
    }
}