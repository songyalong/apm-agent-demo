package com.yyzc.agent.apmagentdemo.spi;

/**
 * @Author: songyalong
 * @Description: 实现方法
 * @Date: Created in ${time}${date}
 * @Modified By:
 */
public class HangZhouImpl implements SpiInterface{
    @Override
    public void sayHello() {
        System.out.println("hangzhou say hello");
    }
}
