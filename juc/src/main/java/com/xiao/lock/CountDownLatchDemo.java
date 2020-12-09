package com.xiao.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @description CountDownLatch 线程减少计数器验证代码
 * @auther: 笑笑是一个码农
 * @date: 20:40 2020/12/9
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i <= 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "执行完成");
                latch.countDown();
            }, String.valueOf(i)).start();
        }

        // fixme 进行了多次试验，有时候主线程的输出语句会提前于上面的最后执行的线程，暂未找到原因
        // 等待10个线程运行完成后，主线程才执行
        try {
            latch.await(); // 会阻塞，直到latch中的计数器减为0
            System.out.println(Thread.currentThread().getName() + "线程执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
