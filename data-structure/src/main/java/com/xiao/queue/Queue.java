package com.xiao.queue;

/**
 * @Description 队列数据结构接口定义
 * @Auther: 笑笑是一个码农
 * @Date: 21:09 2020/7/12
 */
public interface Queue<E> {

    /**
     * 入队
     * @param e
     */
    void enqueue(E e);

    /**
     * 出队
     * @return
     */
    E dequeue();

    /**
     * 查看队首元素
     * @return
     */
    E getFront();

    /**
     * 获取队列数据个数
     * @return
     */
    int getSize();

    /**
     * 判断队列是否为空
     * @return
     */
    boolean isEmpty();
}
