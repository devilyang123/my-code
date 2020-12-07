package com.xiao.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description 读写锁代码验证
 * @auther: 笑笑是一个码农
 * @date: 21:29 2020/12/7
 */
public class ReadWriteDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        // 100个线程写
        for (int i = 0; i < 5; i++) {
            final String n = String.valueOf(i);
            new Thread(() -> {
                myCache.put(n, n);
            }, String.valueOf(i)).start();
        }
        // 100个线程读
        for (int i = 0; i < 5; i++) {
            final String n = String.valueOf(i);
            new Thread(() -> {
                myCache.get(n);
            }, String.valueOf(i)).start();
        }
    }
}

// 自定义缓存
class MyCache{

    private volatile Map<String, Object> map = new HashMap<>();
    // 读写锁
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 添加缓存
     * @param key
     * @param value
     */
    public void put(String key, Object value)  {
        rwLock.writeLock().lock();
        try {
           System.out.println(Thread.currentThread().getName() + "线程开始写入");
           map.put(key, value);
           // 模拟写入耗时
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println(Thread.currentThread().getName() + "线程写入完成");
       }catch (Exception e){
          e.printStackTrace();
       }finally {
           rwLock.writeLock().unlock();
       }
    }

    /**
     * 获取缓存
     * @param key
     */
    public Object get(String key) {
        rwLock.readLock().lock();
        try {
             System.out.println(Thread.currentThread().getName() + "线程开始读取");
             Object result = map.get(key);
             // 模拟读取耗时
             try {
                 Thread.sleep(300);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             System.out.println(Thread.currentThread().getName() + "线程读取完成" + result);
             return result;
         }catch (Exception e){
            e.printStackTrace();
             return null;
         }finally {
             rwLock.readLock().unlock();
         }
    }
}