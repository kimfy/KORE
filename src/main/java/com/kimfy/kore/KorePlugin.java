package com.kimfy.kore;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.8.9")
@IFMLLoadingPlugin.TransformerExclusions({"com.kimfy.kore", "com.kimfy.kore.asm"})
public class KorePlugin implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] {"com.kimfy.kore.asm.KoreClassTransformer"};
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        return;
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
