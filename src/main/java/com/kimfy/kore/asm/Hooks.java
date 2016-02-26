package com.kimfy.kore.asm;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

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

    public static boolean canWallConnectTo(Block wall, IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();
        return block == Blocks.barrier ? false : (block != wall && !(block instanceof BlockFenceGate) ? (block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false) : true);
    }
}

