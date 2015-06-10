package io.github.trianglecube36.betterchunks;

import java.util.HashMap;
import java.util.Map;

public class CoreMappings {
    
    public static Map<Method, Method> nameMappings;
    
    static{
        nameMappings = new HashMap<Method, Method>();
    }
    
    public static Method getMapping(Method mcp){
        if(CorePlugin.devMode){
            return mcp;
        }
        
        Method s = nameMappings.get(mcp);
        if(s == null){
            return mcp;
        }else{
            return s;
        }
    }
}
