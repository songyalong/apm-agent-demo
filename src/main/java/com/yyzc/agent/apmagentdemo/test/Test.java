package com.yyzc.agent.apmagentdemo.test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import net.sf.json.JSONObject;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class Test {
    private static String SPECIFIED_CONFIG_PATH = "application.properties";
    public static void main(String[] args) {
//        testByteBuddy();
//        loadClass();
        // 重新加载
//        Test test = new Test();
//        test.reLoadClass();
//
        modifyToString();
    }

    // 修改toString方法
    public static void modifyToString(){
        Class<?> loaded = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("hello world"))
                .make()
                .load(Test.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
        try {
            System.out.println(loaded.newInstance().toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //  加载没有加载的类
    public static void noLoadToClassLoad(){
        TypePool typePool = TypePool.Default.ofClassPath();
        new ByteBuddy()
                .redefine(typePool.describe("com.yyzc.agent.apmagentdemo.test").resolve(), // do not use 'Bar.class'
                        ClassFileLocator.ForClassLoader.ofClassPath())
                .defineField("qux", String.class) // we learn more about defining fields later
                .make()
                .load(ClassLoader.getSystemClassLoader());
        Field[] declaredFields = Bar.class.getDeclaredFields();
        for(Field field : declaredFields){
            System.out.println(field.getName());
        }
        System.out.println(declaredFields.length);
    }


    // 类重新定义
    public static void reDefinitionClass(){
    }

    // 重新加载类
    class Foo{
        String m(){
            return "foo";
        }
    }

    class Bar{
        String m(){
            return "bar";
        }
    }

    public void reLoadClass(){
        ByteBuddyAgent.install();
        Foo foo = new Foo();
        DynamicType.Loaded<Bar> load1 = new ByteBuddy().redefine(Bar.class).name(Foo.class.getName())
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        System.out.println(foo.m());

        DynamicType.Loaded<Foo> load = new ByteBuddy().redefine(Foo.class).name(Foo.class.getName()).make().load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        System.out.println(foo.m());
    }


    // 加载一个类

    public static void loadClass(){
        Class<?> loaded = new ByteBuddy().subclass(Object.class)
                            .make()
                            .load(ClassLoader.getSystemClassLoader().getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                            .getLoaded();
        System.out.println(loaded.getName());
        System.out.println(loaded.getDeclaredMethods());
    }

    /*
      定义一个类
     */
    public static void testByteBuddy(){
        DynamicType.Unloaded<Object> byteBuddy = new ByteBuddy().with(new NamingStrategy.AbstractBase() {
            @Override
            protected String name(TypeDescription typeDescription) {
                return "i.love.ByteBuddy." + typeDescription.getSimpleName();
            }
        }).subclass(Object.class).make();
        System.out.println(byteBuddy.getTypeDescription().getComponentType() + ", " + byteBuddy.getTypeDescription().getCanonicalName());
    }

    public static void getSystemConfiguration(){
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for(Map.Entry<Object, Object> entry : entries){
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key = "+ key+", value="+value);
        }
    }

}
