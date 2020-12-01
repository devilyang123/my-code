package com.xiao.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description 线程不安全集合解决方案，写时复制
 * @auther: 笑笑是一个码农
 * @date: 21:59 2020/12/1
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {

//        unSafeList();
        safeList();
    }

    /**
     * 线程安全CopyOnWriteArrayList集合
     */
    private static void safeList() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 16));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 线程不安全集合
     */
    private static void unSafeList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 16));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

}
