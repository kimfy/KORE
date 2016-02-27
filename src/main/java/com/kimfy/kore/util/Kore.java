package com.kimfy.kore.util;

import com.kimfy.kore.event.BlockIceMeltEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "kore")
public class Kore
{
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(new KoreEventHandler());
    }

    public static class KoreEventHandler
    {
        @SubscribeEvent
        public void onIceMelt(BlockIceMeltEvent event)
        {
        }
    }
}
