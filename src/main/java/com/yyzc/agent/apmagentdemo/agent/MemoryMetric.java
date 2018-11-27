package com.yyzc.agent.apmagentdemo.agent;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class MemoryMetric {

    private static final long MB = 1048576L;
    public static void printMemoryInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        String info = String.format("\\heapMemoryUsage: %s\\t max: %s\\t used: %s\\t committed: %s\\t use rate: %s\\n",
                heapMemoryUsage.getInit()/MB+"MB",
                heapMemoryUsage.getMax()/MB+"MB", heapMemoryUsage.getUsed()/MB+"MB",
                heapMemoryUsage.getCommitted() / MB + "MB",
                heapMemoryUsage.getUsed() * 100 / heapMemoryUsage.getCommitted() + "%"
                );
        System.out.println(info);
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        info = String.format("nonHeapMemoryUsage: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                nonHeapMemoryUsage.getInit() / MB + "MB",
                nonHeapMemoryUsage.getMax() / MB + "MB", nonHeapMemoryUsage.getUsed() / MB + "MB",
                nonHeapMemoryUsage.getCommitted() / MB + "MB",
                nonHeapMemoryUsage.getUsed() * 100 / nonHeapMemoryUsage.getCommitted() + "%"
        );
        System.out.println(info);
    }

    public static void printGCInfo() {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans){
            String info = String.format("garbageCollector: name: %s\t count:%s\t took:%s\t pool name:%s",
                    garbageCollectorMXBean.getName(),
                    garbageCollectorMXBean.getCollectionCount(),
                    garbageCollectorMXBean.getCollectionTime(),
                    Arrays.deepToString(garbageCollectorMXBean.getMemoryPoolNames()));
            System.out.println(info);
        }
    }
}
