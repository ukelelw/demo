package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 比较 synchronized 和 ReentrantLock 在多线程环境下的性能差异
 */
public class LockPerformanceTests {

    private static final int NUM_THREADS = 100;
    private static final int NUM_ITERATIONS = 100000;

    public static void main(String[] args) throws InterruptedException {
        // 测试 synchronized
        long synchronizedTime = testSynchronized();
        System.out.println("Synchronized Time: " + synchronizedTime + " ms");

        // 测试 ReentrantLock
        long reentrantLockTime = testReentrantLock();
        System.out.println("ReentrantLock Time: " + reentrantLockTime + " ms");
    }

    private static long testSynchronized() throws InterruptedException {
        final Object lock = new Object();
        final Counter counter = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < NUM_ITERATIONS; j++) {
                    synchronized (lock) {
                        counter.increment();
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        return System.currentTimeMillis() - startTime;
    }

    private static long testReentrantLock() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final Counter counter = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < NUM_ITERATIONS; j++) {
                    lock.lock();
                    try {
                        counter.increment();
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        return System.currentTimeMillis() - startTime;
    }

    private static class Counter {
        private int count = 0;

        public void increment() {
            count++;
        }
    }
}