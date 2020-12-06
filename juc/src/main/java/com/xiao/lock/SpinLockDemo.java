package com.xiao.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description 自旋锁演示代码
 * @auther: 笑笑是一个码农
 * @date: 17:15 2020/12/6
 */
public class SpinLockDemo {
    // 原子引用线程，初始为null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.myLock();
            // 线程A锁住3秒
            System.out.println(Thread.currentThread().getName() + "进入");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放锁
            spinLockDemo.myUnLock();
        }, "A").start();

        // 确保线程A先运行
        Thread.sleep(100);

        new Thread(() -> {
            spinLockDemo.myLock();
            // 线程B需要等待线程A释放锁才能进入，否则一直自旋等待锁
            System.out.println(Thread.currentThread().getName() + "进入");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        }, "B").start();
    }

    /**
     * 自旋锁-加锁
     */
    private void myLock(){
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + "获取锁");

        //  compareAndSet返回true，说明当前该原子引用没有其他线程修改，表示可以获取锁，取反退去循环，反之，一直循环等待获取锁
        while (!atomicReference.compareAndSet(null, currentThread)){
            // 自旋等待锁
        }
    }

    /**
     * 自旋锁-释放锁
     */
    private void myUnLock(){
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + "释放锁");
        atomicReference.compareAndSet(currentThread, null);
    }

}
