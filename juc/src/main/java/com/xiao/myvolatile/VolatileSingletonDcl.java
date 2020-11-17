package com.xiao.myvolatile;

/**
 * @description 单例模式，双重检查案例（Double Check Lock）
 * @auther: 笑笑是一个码农
 * @date: 22:38 2020/11/17
 */
public class VolatileSingletonDcl {
    public static void main(String[] args) {
        // 单线程下单例模式测试
//        System.out.println(SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance());


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 线程不安全单例模式测试，多个线程，会创建多个对象
//                System.out.println(SingletonDemo.getInstance());
                // DCL 机制测试
                System.out.println(SingletonDemo.dclGetInstance());
            }).start();
        }
    }
}


class SingletonDemo{

    private static SingletonDemo instance = null;

    private static volatile SingletonDemo instance1 = null;

    public SingletonDemo(){
        System.out.println("构造方法被调用");
    }

    // 线程不安全单例模式
    public static SingletonDemo getInstance(){
        if (instance == null){
            instance = new SingletonDemo();
        }
        return instance;
    }

    // DCL机制
    public static SingletonDemo dclGetInstance(){
        if (instance1 == null){
            synchronized (SingletonDemo.class){
                if (instance1 == null){
                    // 创建对象可以拆分为三部
                    // 1、为对象分配内存
                    // 2、初始化对象
                    // 3、设置instance1指向刚才分配的内存，此时instance1不等于null
                    // 但如果不加volatile，会存在指令重排的可能，2、3步不存在数据依赖关系，可能会先执行第3步，单线程没有问题
                    // 多线程下，当第一个线程先执行了第3步，此时cpu发生调度，另一个线程判断instance1 == null为false， 但实际上对象还有没初始化完成
                    instance1 = new SingletonDemo();
                }
            }
        }
        return instance1;
    }
}
