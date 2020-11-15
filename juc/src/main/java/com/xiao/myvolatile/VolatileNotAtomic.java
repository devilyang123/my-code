package com.xiao.myvolatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description volatile不保证原子性代码验证
 * @auther: 笑笑是一个码农
 * @date: 21:54 2020/11/14
 *
 * 什么是原子性？  某个线程在操作具体的业务时，需要整体完整，不可分割，保证数据的一致性、完整性。
 */
public class VolatileNotAtomic {

    public static void main(String[] args) throws InterruptedException {
        Test1 t = new Test1();
        // 多个不同的线程，都调用1000次increase方法，对i进行++操作，如果volatile保证原子性，那么最终结果应该会和预期结果相等
        // 不等于预期结果，那么说明volatile不保证原子性，出现了线程安全问题
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    t.increase();
//                    t.syncIncrease();
//                    t.increaseAtomicj();
                }
            }, i + "").start();
        }
//        Thread.sleep(3000); // 保证上面20个线程执行完毕
        while (Thread.activeCount() > 2){ // 保证上面20个线程执行完毕, 为什么是激活线程大于2，一个是main线程，一个是后台的GC线程，大于2表示有其他子线程在执行
            Thread.yield();
        }
        System.out.println("最终结果为" + t.i); // 此时我们得到的最终结果会基本都会小于预期结果
//        System.out.println("最终结果为" + t.j); // 测试原子变量解决线程安全问题
    }
}

// 资源类
class Test1{
    volatile int i = 0;

    AtomicInteger j = new AtomicInteger(0);

    public void increase(){
        // i ++ 不是原子性操作说明
        // 1、读取i的值
        // 2、对i进行+1操作
        // 3、把改变后的写回
        // 假设一个子线程读取到的值为1，当它修改后，没有写回主内存之前，CPU调度了另一个线程，另一个线程读取到了原来的值，也为1，这样就会存在丢失操作的问题
        i++;
    }
    // 加同步锁可以解决该问题，但是加锁太重，降低并发性能，可以使用Atomic变量
    public synchronized void syncIncrease(){
        i++;
    }
    // 使用原子变量可以解决该问题
    public void increaseAtomicj(){
        j.getAndIncrement();
    }
}