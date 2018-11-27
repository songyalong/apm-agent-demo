package com.yyzc.agent.apmagentdemo.agent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: songyalong
 * @Description:
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class MemoryAgentTest {
    public static void main(String[] args) throws Exception {
        List<Object> list = new ArrayList<Object>();
       for(int i=0; i< 100000; i++){
           list.add("hello world");
       }
    }
}
