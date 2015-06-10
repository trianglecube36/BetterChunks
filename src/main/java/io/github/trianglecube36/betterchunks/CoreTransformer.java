package io.github.trianglecube36.betterchunks;

import io.github.trianglecube36.betterchunks.connectors.Connector;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.launchwrapper.IClassTransformer;

public class CoreTransformer implements IClassTransformer{

    private Map<String, Connector> connectors = new HashMap<String, Connector>();
    
    public CoreTransformer(){
        connectors.put("io.github.trianglecube36.betterchunks.TestClass", new Connector());
    }
    
    @Override
    public byte[] transform(String name, String deobfname,
            byte[] basicClass) {
        Connector con = connectors.get(deobfname);
        if(con != null){
            byte[] newclass = con.init(basicClass);
            connectors.remove(deobfname);
            return newclass;
        }
        return basicClass;
    }

}
