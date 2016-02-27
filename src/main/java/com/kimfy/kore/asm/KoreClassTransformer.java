package com.kimfy.kore.asm;

import com.kimfy.kore.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Arrays;
import java.util.Random;

public class KoreClassTransformer implements IClassTransformer
{
    public static String[] classes =
            {
                    "net.minecraft.block.BlockFence",
                    "net.minecraft.block.BlockWall",
                    "net.minecraft.block.BlockIce"
            };

    @Override
    public byte[] transform(String className, String transformedName, byte[] bytecode)
    {
        boolean isObfuscated = !(className.equals(transformedName));
        int index = Arrays.asList(classes).indexOf(transformedName);
        return index != -1 ? transform(index, bytecode, isObfuscated) : bytecode;
    }

    private static byte[] transform(int index, byte[] bytecode, boolean isObfuscated)
    {
        Constants.logger.info("Transforming: " + classes[index]);
        try
        {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(bytecode);
            classReader.accept(classNode, 0);

            switch (index)
            {
                case 0:
                    transformBlockFence(classNode, isObfuscated);
                    break;
                case 1:
                    transformBlockWall(classNode, isObfuscated);
                    break;
                case 2:
                    transformBlockIce(classNode, isObfuscated);
                    break;
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return bytecode;
    }

    /**
     * Transforms the BlockFence class' canConnectTo method
     * so that it returns true when asking to connect to
     * any fence that is an instanceof BlockFence.
     * <p/>
     * Removes 'block.getMaterial() != this.blockMaterial'
     * from the ternary check.
     * <p/>
     * What the method will look like after transforming is done:
     * <p/>
     * <pre>
     *     public boolean canConnectToOriginal(IBlockAccess worldIn, BlockPos pos)
     *     {
     *         if (Hooks.isFenceCompatibilityEnabled())
     *         {
     *             return Hooks.canFenceConnectTo(worldIn, pos);
     *         }
     *         Block block = worldIn.getBlockState(pos).getBlock();
     *         return block == Blocks.barrier ? false : ((!(block instanceof BlockFence) || block.getMaterial() != this.blockMaterial) && !(block instanceof BlockFenceGate) ? (block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false) : true);
     *     }
     * </pre>
     *
     * @param classNode The {@link ClassNode} to manipulate
     * @param isObfuscated Whether or not we're in a obfuscated or deobfuscated environment
     */
    private static void transformBlockFence(ClassNode classNode, boolean isObfuscated)
    {
        final String CAN_CONNECT_TO = isObfuscated ? "func_176524_e" : "canConnectTo";
        final String CAN_CONNECT_TO_DESC = isObfuscated ? "(Ladq;Lcj;)Z" : "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z";

        for (MethodNode method : classNode.methods)
        {
            if (method.name.equals(CAN_CONNECT_TO) && method.desc.equals(CAN_CONNECT_TO_DESC))
            {
                LabelNode L0 = (LabelNode) method.instructions.getFirst();
                LabelNode L2 = new LabelNode();

                method.instructions.insertBefore(L0, L2);
                method.instructions.insertBefore(L0, new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "isFenceCompatibilityEnabled", "()Z", false));
                method.instructions.insertBefore(L0, new JumpInsnNode(Opcodes.IFEQ, L0));
                method.instructions.insertBefore(L0, new VarInsnNode(Opcodes.ALOAD, 1));
                method.instructions.insertBefore(L0, new VarInsnNode(Opcodes.ALOAD, 2));
                method.instructions.insertBefore(L0, new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "canFenceConnectTo", CAN_CONNECT_TO_DESC, false));
                method.instructions.insertBefore(L0, new InsnNode(Opcodes.IRETURN));
                method.instructions.insertBefore(L0, new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

                Constants.logger.info("Transformed: BlockFence.class");
            }
        }
    }
    /**
     * Transforms the BlockWall class' canConnectTo method
     * so that it returns true when asking to connect to
     * any fence that is an instanceof BlockWall
     *
     * @param classNode The {@link ClassNode} to manipulate
     * @param isObfuscated Whether or not we're in a obfuscated or deobfuscated environment
     */
    private static void transformBlockWall(ClassNode classNode, boolean isObfuscated)
    {
        final String CAN_CONNECT_TO = isObfuscated ? "func_176253_e" : "canConnectTo";
        final String CAN_CONNECT_TO_DESC = isObfuscated ? "(Ladq;Lcj;)Z" : "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;)Z";

        for (MethodNode method : classNode.methods)
        {
            if (method.name.equals(CAN_CONNECT_TO) && method.desc.equals(CAN_CONNECT_TO_DESC))
            {
                LabelNode L0 = (LabelNode) method.instructions.getFirst();
                LabelNode L2 = new LabelNode();

                method.instructions.insertBefore(L0, L2);
                method.instructions.insertBefore(L0, new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "isFenceCompatibilityEnabled", "()Z", false));
                method.instructions.insertBefore(L0, new JumpInsnNode(Opcodes.IFEQ, L0));
                method.instructions.insertBefore(L0, new VarInsnNode(Opcodes.ALOAD, 1));
                method.instructions.insertBefore(L0, new VarInsnNode(Opcodes.ALOAD, 2));
                method.instructions.insertBefore(L0, new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "canWallConnectTo", CAN_CONNECT_TO_DESC, false));
                method.instructions.insertBefore(L0, new InsnNode(Opcodes.IRETURN));
                method.instructions.insertBefore(L0, new FrameNode(Opcodes.F_SAME, 0, null, 0, null));

                Constants.logger.info("Transformed: BlockWall.class");
            }
        }
    }

    /**
     * Replaces {@link net.minecraft.block.BlockIce#updateTick(World, BlockPos, IBlockState, Random)}
     * method instructions with {@link Hooks#updateTick(Block, World, BlockPos, IBlockState, Random)}
     * by removing all instructions from the method and inserting new instructions equivalent with a
     * invokation on the new method.
     *
     * @param classNode The {@link ClassNode} to manipulate
     * @param isObfuscated Whether or not we're in a obfuscated or deobfuscated environment
     */
    private static void transformBlockIce(ClassNode classNode, boolean isObfuscated)
    {
        final String UPDATE_TICK = isObfuscated ? "func_180650_b" : "updateTick";
        final String UPDATE_TICK_DESC = isObfuscated ?
                "(Ladm;Lcj;Lalz;Ljava/util/Random;)V" :
                "(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V";

        for (MethodNode method : classNode.methods)
        {
            if (method.name.equals(UPDATE_TICK) && method.desc.equals(UPDATE_TICK_DESC))
            {
                if (method.instructions.size() != 48)
                {
                    Constants.logger.error("Someone already modified BlockIce.updateTick(). Skipping to avoid conflicts/crashes!");
                }

                method.instructions.clear();
                final String HOOKS_UPDATE_TICK_DESC = isObfuscated ?
                        "(Lafh;Ladm;Lcj;Lalz;Ljava/util/Random;)V" :
                        "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V";

                InsnList instructions = new InsnList();
                instructions.add(new LabelNode());
                instructions.add(new VarInsnNode(Opcodes.ALOAD, 0)); // BlockIce
                instructions.add(new VarInsnNode(Opcodes.ALOAD, 1)); // World
                instructions.add(new VarInsnNode(Opcodes.ALOAD, 2)); // BlockPos
                instructions.add(new VarInsnNode(Opcodes.ALOAD, 3)); // IBlockState
                instructions.add(new VarInsnNode(Opcodes.ALOAD, 4)); // Random
                instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "updateTick", HOOKS_UPDATE_TICK_DESC, false));
                instructions.add(new InsnNode(Opcodes.RETURN));
                method.instructions.insert(instructions);
                Constants.logger.info("Transformed: BlockIce.class");
            }
        }
    }
}