package com.yyzc.agent.apmagentdemo.agent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class AgentTest {
    public static void main(String[] args) {
        System.out.println("执行AgentTest方法");
        AgentTest agentTest = new AgentTest();
        agentTest.mathod1();
        agentTest.mathod2();

        agentTest.memoryTest();
    }

    private void memoryTest() {
        boolean is = true;
        List<Object> list = new ArrayList<Object>();
        for(int i=0; i< 100000; i++){
            list.add("hello world");
        }
    }

    private void mathod2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this is method2");
    }

    private void mathod1() {
        System.out.println("this is method1");
    }


}
