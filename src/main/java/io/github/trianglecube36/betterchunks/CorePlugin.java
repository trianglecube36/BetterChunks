package io.github.trianglecube36.betterchunks;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class CorePlugin implements IFMLLoadingPlugin{

    public static boolean devMode;
    
    public static LaunchClassLoader loader;
    
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{CoreTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        loader  =  (LaunchClassLoader) data.get("classloader");
        devMode = !(Boolean)           data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
