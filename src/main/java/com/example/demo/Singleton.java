package com.example.demo;

import ch.qos.logback.core.UnsynchronizedAppenderBase;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: Singleton
 * Package: com.example.demo
 * Description:
 *
 * @Author 浙工大-黄泳涛
 * @Create 2025/1/14 19:22
 * @Version 1.0
 */
public class Singleton {
    private volatile static Singleton instance;
    private int i;
    public static ReentrantLock lock = new ReentrantLock();

    private Singleton(){
    }
    public static Singleton getInstance(){
        if(instance == null){
            synchronized(Singleton.class){
                if(instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public void A(){
        lock.lock();
        try {
            i++;
        }finally {
            lock.unlock();
        }
    }

}

/*  1. 分配内存空间  */

