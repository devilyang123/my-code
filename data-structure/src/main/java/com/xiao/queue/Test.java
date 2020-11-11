package com.xiao.queue;

import java.util.Random;

/**
 * @Description 队列测试类
 * @Auther: 笑笑是一个码农
 * @Date: 21:20 2020/7/12
 */
public class Test {

    public static void main(String[] args) {
//        Queue<Integer> queue = new ArrayQueue<>();
//        Queue<Integer> queue = new LoopQueue<>();
//        for (int i = 0; i < 20; i++) {
//            queue.enqueue(i);
//        }
//        System.out.println(queue);
//        queue.enqueue(67);
//        System.out.println(queue);
//        queue.dequeue();
//        System.out.println(queue);
//        System.out.println(queue.isEmpty());
//        System.out.println(queue.getFront());

        int optCount = 1000000;
        double arrayQueueTime = testQueue(new ArrayQueue<Integer>(), optCount);
        double loopQueueTime = testQueue(new LoopQueue<>(), optCount);
        System.out.println("arrayQueueTime:" + arrayQueueTime);
        System.out.println("loopQueueTime:" + loopQueueTime);
    }

    private static double testQueue(Queue<Integer> queue, int opCount){
        long startTime = System.nanoTime();

        Random random = new Random();
        for (int i = 0; i < opCount; i++) {
            queue.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for (int i = 0; i < opCount; i++) {
            queue.dequeue();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000000.0;
    }
}
