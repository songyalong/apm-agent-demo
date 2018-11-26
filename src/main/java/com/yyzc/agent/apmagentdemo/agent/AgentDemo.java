package com.yyzc.agent.apmagentdemo.agent;

import org.slf4j.Logger;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.logging.LogManager;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class AgentDemo {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("开始");
        System.out.println(agentArgs);
        inst.addTransformer(new ClassFileTransformer() {
        });
        System.out.println();
    }
}
