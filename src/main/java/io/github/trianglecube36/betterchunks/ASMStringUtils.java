package io.github.trianglecube36.betterchunks;

import java.util.Iterator;
import java.util.LinkedList;

import org.objectweb.asm.Opcodes;

public class ASMStringUtils {
    
    /**
     * sets the return type of a method descriptor
     * 
     * @param desc the old descriptor
     * @param retType the new return type. ex: "Ljava/lang/String;"
     * @return the new descriptor
     */
    public static String setDescReturnType(String desc, String retType){
        for(int i = 0;i < desc.length();i++){
            if(desc.charAt(i) == ')'){
                return desc.substring(0, i + 1) + retType;
            }
        }
        return null;
    }
    
    public static int[] getDescArgsLoadCodes(String desc){
        LinkedList<Integer> codes = new LinkedList<Integer>();
        
        boolean skipnext = false;
        int code;
        _breakloop:
        for(int i = 0;i < desc.length();i++){
            code = 0;
            switch(desc.charAt(i)){
            case 'Z':
            case 'C':
            case 'B':
            case 'S':
            case 'I':
                code = Opcodes.ILOAD;
                break;
            case 'F':
                code = Opcodes.FLOAD;
                break;
            case 'J':
                code = Opcodes.LLOAD;
                break;
            case 'D':
                code = Opcodes.DLOAD;
                break;
            case 'L':
                code = Opcodes.ALOAD;
                do{
                    i++;
                }while(desc.charAt(i) != ';'); // crash if i is not < desc.length()
                break;
            case '[':
                if(!skipnext){
                    codes.add(Opcodes.ALOAD); // there is no way it cant be aload
                }
                skipnext = true;
                break;
            case '(':
                break;
            case ')':
                break _breakloop;
            }
            
            if(!skipnext && code != 0){
                codes.add(code);
            }else if(code != 0){ // we are no longer seeing ['s
                skipnext = false;
            }
        }
        
        int[] array = new int[codes.size()];
        Iterator<Integer> it = codes.iterator();
        int i = 0;
        while(it.hasNext()){
            array[i] = it.next();
            i++;
        }
        
        return array;
    }
}
