package com.yyzc.agent.apmagentdemo.spi;

import java.util.ServiceLoader;

/**
 * @Author: songyalong
 * @Description: spi的使用
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class SpiTest {
    public static void main(String[] args) {
        ServiceLoader<SpiInterface> interfaces = ServiceLoader.load(SpiInterface.class);
        for(SpiInterface spiInterface : interfaces){
            spiInterface.sayHello();
        }
    }
}
