package io.github.trianglecube36.betterchunks;

import java.lang.reflect.Field;
import java.util.Iterator;

import io.github.trianglecube36.betterchunks.connectors.Connector;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = BetterChunks.MODID, version = BetterChunks.VERSION)
public class BetterChunks
{
    public static final String MODID = "betterchunks";
    public static final String VERSION = "0.1";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        TestClass t = new TestClass();
        t.coolMethod(0, 0, null);
        t.coolMethod(22, 33, "yay");
        
        try {
            World.class.getDeclaredField(Connector.GLUE_FIELD_NAME);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
