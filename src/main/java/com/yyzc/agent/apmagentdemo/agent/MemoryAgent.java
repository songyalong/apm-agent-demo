package com.yyzc.agent.apmagentdemo.agent;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class MemoryAgent {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("this is an perform monitor agent.");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                MemoryMetric.printMemoryInfo();
                MemoryMetric.printGCInfo();
            }
        },0, 5000, TimeUnit.MILLISECONDS);
    }
}
