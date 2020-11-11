package com.xiao.stack;

/**
 * @Description 栈数据结构接口定义
 * @Auther: 笑笑是一个码农
 * @Date: 21:25 2020/7/8
 */
public interface Stack<E> {
    /**
     * 获取栈中的数据个数
     * @return
     */
    int getSize();

    /**
     * 判断栈是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 向栈中添加元素
     * @param e
     */
    void push(E e);

    /**
     *  弹出栈顶元素
     * @return
     */
    E pop();

    /**
     * 查看栈顶元素
     * @return
     */
    E peek();

}
