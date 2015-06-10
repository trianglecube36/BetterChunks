package io.github.trianglecube36.betterchunks.connectors;

import io.github.trianglecube36.betterchunks.CoreMappings;
import io.github.trianglecube36.betterchunks.Method;
import io.github.trianglecube36.betterchunks.engines.IEngine;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.TraceClassVisitor;

public class Connector {

    public static final String GLUE_FIELD_NAME = "glue";

    protected Map<Method, Method> mHooks;
    protected Map<Method, Method> mReplaceHooks;
    protected Map<Method, Method> mReplace;

    protected boolean addGlueField;
    protected IEngine glueFieldValue;

    public Connector() {
        addGlueField = true; // default to true

        mHooks        = new HashMap<Method, Method>();
        mReplaceHooks = new HashMap<Method, Method>();
        mReplace      = new HashMap<Method, Method>();
    }

    public byte[] init(byte[] a) {
        ClassNode clazz = new ClassNode();
        ClassReader cr = new ClassReader(a);
        cr.accept(clazz, 0);

        if (addGlueField) {
            FieldNode f = new FieldNode(Opcodes.ACC_PUBLIC, GLUE_FIELD_NAME,
                    "L" + IEngine.class.getName().replace('.', '/') + ";",
                    null, null);
            clazz.fields.add(f);
        }

        addMethodHook(new Method("io/github/trianglecube36/betterchunks/TestClass", "coolMethod", "(IILjava/lang/String;)Ljava/lang/String;"),
                      new Method("io/github/trianglecube36/betterchunks/TestClass", "cool2",      "(IILjava/lang/String;)V"));
        
        for(MethodNode m : clazz.methods){
            Method methodDesc = new Method(clazz.name, m.name, m.desc);
            
            Method hook = mHooks.get(methodDesc);
            if(hook != null){
                InsnList ins = new InsnList();
                ins.add(new VarInsnNode(Opcodes.ALOAD, 0)); // load "this" onto stack (what we will call the function on)
                ins.add(new VarInsnNode(Opcodes.ILOAD, 1)); // load parameters
                ins.add(new VarInsnNode(Opcodes.ILOAD, 2)); // load parameters
                ins.add(new VarInsnNode(Opcodes.ALOAD, 3)); // load parameters
                ins.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, hook.owner, hook.name, hook.desc, false));
                m.instructions.insert(ins);
            }
        }
        
        TraceClassVisitor printer = new TraceClassVisitor(new PrintWriter(System.out));
        clazz.accept(printer);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS
                + ClassWriter.COMPUTE_FRAMES);
        clazz.accept(cw);
        return cw.toByteArray();
    }

    /**
     * adds a hook at the start of a method
     * 
     * @param method
     *            example:
     *            "net/minecraft/world/World.getSomething (Ljava/lang/String;)Ljava/lang/Object;"
     * @param hook
     *            example:
     *            "io/github/trianglecube63/betterchunks/engines/WorldEngine.foo (Ljava/lang/String;)V"
     */
    public void addMethodHook(Method method, Method hook) {
        Method realMethod = CoreMappings.getMapping(method);
        mHooks.put(realMethod, hook);
    }

    /**
     * replace a method with something like { return hook(args); }
     * 
     * @param method
     *            example:
     *            "net/minecraft/world/World.getSomething (Ljava/lang/String;)Ljava/lang/Object;"
     * @param hook
     *            example:
     *            "io/github/trianglecube63/engines/WorldEngine.bar (Ljava/lang/String;)Ljava/lang/Object;"
     */
    public void addMethodReplaceHook(Method method, Method hook) {
        Method realMethod = CoreMappings.getMapping(method);
        mReplaceHooks.put(realMethod, hook);
    }

    // TODO: get an idea
    /**
     * no idea
     * 
     * @param method
     * @param newstuff
     */
    public void addMethodReplace(Method method, Method newstuff) {
        Method realMethod = CoreMappings.getMapping(method);
        mReplace.put(realMethod, newstuff);
    }
}
