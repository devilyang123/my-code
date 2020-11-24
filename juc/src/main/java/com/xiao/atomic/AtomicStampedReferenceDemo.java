package com.xiao.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description 时间戳原子引用，ABA问题演示与解决
 * @auther: 笑笑是一个码农
 * @date: 22:08 2020/11/24
 */
public class AtomicStampedReferenceDemo {
    
    static AtomicInteger atomicInteger = new AtomicInteger(100);
    
    static AtomicStampedReference<Integer> integerAtomicStampedReference = new AtomicStampedReference<>(100, 1);
    
    
    public static void main(String[] args) throws InterruptedException {
        // ABA问题演示
        abaDemo();

        Thread.sleep(3000);

        // ABA问题解决
        abaResolve();

    }

    /**
     * ABA问题解决
     */
    private static void abaResolve() {
        // T1线程，先做一次ABA操作，但会加修改后的版本号
        new Thread(() -> {
            // 获取当前版本号
            int stamp1 = integerAtomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "线程初始版本号为：" + stamp1);
            // 确保T2线程拿到与T1线程相同的版本号
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 第一次修改，参数1：期望值，参数2：新值，参数3：期望版本号，参数4：新版本号
            boolean b1 = integerAtomicStampedReference.compareAndSet(
                    integerAtomicStampedReference.getReference(), 1000,
                    integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp() + 1
            );
            System.out.println(Thread.currentThread().getName() + "线程第一次修改后版本号为：" + integerAtomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName() + "线程第一次修改结果为：" + b1);
            // 第二次修改
            boolean b2 = integerAtomicStampedReference.compareAndSet(
                    integerAtomicStampedReference.getReference(), 100,
                    integerAtomicStampedReference.getStamp(), integerAtomicStampedReference.getStamp() + 1
            );
            System.out.println(Thread.currentThread().getName() + "线程第二次修改结果为：" + b2);
            System.out.println(Thread.currentThread().getName() + "线程第二次修改后版本号为：" + integerAtomicStampedReference.getStamp());
        },"T1").start();

        // T2线程
        new Thread(() -> {
            // 获取当前版本号
            int stamp = integerAtomicStampedReference.getStamp();
            Integer reference = integerAtomicStampedReference.getReference();
            System.out.println(Thread.currentThread().getName() + "线程初始版本号为：" + stamp + ", 初始期望引用为：" + reference);
            // 确保T1线程完成一次ABA操作
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // T2线程在不知情的情况下修改
            boolean b = integerAtomicStampedReference.compareAndSet(
                    reference, 9999,
                    stamp, integerAtomicStampedReference.getStamp() + 1
            );
            System.out.println(Thread.currentThread().getName() + "线程修改结果为：" + b + ", " +
                    "当前版本号为：" + integerAtomicStampedReference.getStamp() +", 当前最新值为：" + integerAtomicStampedReference.getReference());

        },"T2").start();
    }

    /**
     * ABA问题演示
     * @throws InterruptedException
     */
    private static void abaDemo() throws InterruptedException {
        // T1线程，先做一次ABA操作
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 1000);
            atomicInteger.compareAndSet(1000, 100);
        },"T1").start();

        Thread.sleep(1000); // 确保T1线程的ABA操作执行完成再运行T2线程

        // T2线程
        new Thread(() -> {
            // T2 线程并不知道T1线程已经修改过一次，所以本次会修改成功
            boolean b = atomicInteger.compareAndSet(100, 9999);
            System.out.println(Thread.currentThread().getName() + "线程修改结果:" + b);
        },"T2").start();
    }

}
