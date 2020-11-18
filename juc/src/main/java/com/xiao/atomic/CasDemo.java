package com.xiao.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description CAS 演示代码
 * @auther: 笑笑是一个码农
 * @date: 23:15 2020/11/18
 */
public class CasDemo {
    public static void main(String[] args) {
        CasDemoTest casDemoTest = new CasDemoTest();
        casDemoTest.increase();
        System.out.println(casDemoTest.atomicInteger.get());
    }
}

class CasDemoTest{
    AtomicInteger atomicInteger = new AtomicInteger(1);

    public void increase(){
        // unsafe.compareAndSwapInt(this, valueOffset, expect, update)
        // 实际调用了 unsafe类的compareAndSwapInt方法
        // 比较并交换，线程会有一个原子变量的拷贝值，先判断拷贝值与主内存中的期望值是否相等
        // 相等，则可以修改，反之，主内存中的值已被其他线程修改，当前线程不能再修改
        //  这里会出现ABA问题，另一个线程先把主内存的值改为了其他值，然后又改了回来，但当前线程并不知情。
        atomicInteger.compareAndSet(1,2);
        atomicInteger.compareAndSet(1,100); //  第二次修改失败
    }
}
