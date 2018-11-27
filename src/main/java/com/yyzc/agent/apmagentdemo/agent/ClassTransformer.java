package com.yyzc.agent.apmagentdemo.agent;

import javassist.*;
import javassist.expr.Cast;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class ClassTransformer implements ClassFileTransformer {

    private static final Set<String> classNameSet = new HashSet<>();
    static {
        classNameSet.add("com.yyzc.agent.apmagentdemo.agent.AgentTest");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String currentClassName = className.replace('/','.');
        if(!classNameSet.contains(currentClassName)){
            return null;
        }
        System.out.println("访问方法:"+ currentClassName);
        try {
            CtClass ctClass = ClassPool.getDefault().get(currentClassName);
            CtBehavior[] declaredBehaviors = ctClass.getDeclaredBehaviors();
            for(CtBehavior ctBehavior : declaredBehaviors){
                try {
                    enhanceMethod(ctBehavior);
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                }
            }
            try {
                return ctClass.toBytecode();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void enhanceMethod(CtBehavior ctBehavior) throws CannotCompileException {
        if(ctBehavior.isEmpty()){
            return ;
        }
        String name = ctBehavior.getName();
        if(name.equalsIgnoreCase("main")){
            return ;
        }
        final StringBuffer source = new StringBuffer();
        source.append("{").append("long start = System.currentTimeMillis();").append("$_ = $proceed($$);")
                .append("System.out.println(\"method:[" + name + "]\");").append("\n")
                .append("System.out.println(\" cost:[\" +(System.currentTimeMillis() -start)+ \"ns]\");")
                .append("}");
        ExprEditor exprEditor = new ExprEditor(){
            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                methodCall.replace(source.toString());
            }
        };
        ctBehavior.instrument(exprEditor);
    }
}
