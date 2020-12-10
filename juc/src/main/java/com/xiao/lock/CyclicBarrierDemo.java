package com.xiao.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description 线程加法计数器代码验证
 * @auther: 笑笑是一个码农
 * @date: 20:22 2020/12/10
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () ->{
            System.out.println("最后执行");
        });

        for (int i = 0; i < 10; i ++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "运行");
                try {
                    cyclicBarrier.await(); // 先执行完成的线程等待，直到指定数量的线程都执行完成
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
