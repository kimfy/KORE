package com.kimfy.kore.asm;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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

    public static boolean canIceMelt()
    {
        return true;
    }

    public static Block ice = Blocks.ice;
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - 22)
        {
            if (worldIn.provider.doesWaterVaporize())
            {
                worldIn.setBlockToAir(pos);
            }
            else
            {
                if (Hooks.canIceMelt())
                {
                    ice.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
                    worldIn.setBlockState(pos, Hooks.getLiquidDropForIce());
                }
            }
        }
    }

    public static IBlockState blockIceReplacement = Blocks.flowing_water.getDefaultState();

    public static IBlockState getLiquidDropForIce()
    {
        return blockIceReplacement;
    }
}

