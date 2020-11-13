package com.xiao.myvolatile;

/**
 * @description volatile可见性 demo
 * @auther: 笑笑是一个码农
 * @date: 21:27 2020/11/13
 */
public class VolatileVisibleDemo {

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();

        // 验证volatile保证可见性
        new Thread(() -> {
            System.out.println("进入子线程");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.modifyj();
            System.out.println("子线程修改：" + test.j);
        }, "A").start();

        // 主线程使用死循环，来验证是否能得到子线程修改后的值
        while (test.j == 0){
            // 如果没有被修改，一直等着
        }
        System.out.println("主线程获取到修改后的值：" + test.j);



        // 验证不可见性，虽然已经修改并写回了主内存，但没有通知其他线程
        new Thread(() -> {
            System.out.println("进入子线程");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.modifyi();
            System.out.println("子线程修改：" + test.i);
        }, "A").start();

        // 主线程使用死循环，来验证是否能得到子线程修改后的值
        while (test.i == 0){
            // 如果没有被修改，一直等着
            // System.out.println(1);
            // （println是同步代码块）， 所以调用了该方法，就算不被volatile修饰，也会读取到最新的值
            // 这是因为synchronized具体过程是：
            // 获得同步锁；
            // 清空工作内存；
            // 从主内存拷贝对象副本到工作内存；
            // 执行代码(计算或者输出等)；
            // 刷新主内存数据；
            // 释放同步锁。
        }
        System.out.println("主线程获取到修改后的值：" + test.i);

    }
}

// 一个线程调用modify方法，修改了变量i的值，如果变量i被volatile关键字修饰，
// 其他线程可以获取到修改后的值，验证了volatile保证可见性。

// 反之，变量i不被volatile修饰，
// 其他线程不能获取到修改后的值，验证了线程操作的是线程本地的拷贝变量后虽然写回了主内存，但没有通知其他线程
class Test{
    int i = 0;             // 没有被volatile修饰

    volatile int j = 0;    // 被volatile修饰

    public void modifyi(){
        this.i = 100;
    }

    public void modifyj(){
        this.j = 100;
    }
}