package com.xiao.lock;

import java.util.concurrent.Semaphore;

/**
 * @description 信号量测试代码
 * @auther: 笑笑是一个码农
 * @date: 19:33 2020/12/17
 */
public class SemaphoreDemo {

    private static Semaphore semaphore = new Semaphore(3); // 模拟三个座位

    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) { // 模拟六个人，去抢三个座位，直到有人离开，后面的人才能坐
            new Thread(() -> {
                try {
                    semaphore.acquire(); // 抢占
                    System.out.println(Thread.currentThread().getName() + "抢到座位，开始休息");
                    Thread.sleep(3000); // 当前抢到的线程，坐3秒
                    System.out.println(Thread.currentThread().getName() + "休息够了，离开");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
