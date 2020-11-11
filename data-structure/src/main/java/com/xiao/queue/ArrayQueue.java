package com.xiao.queue;

import com.xiao.array.Array;

/**
 * @Description 队列的数组实现
 * @Auther: 笑笑是一个码农
 * @Date: 21:16 2020/7/12
 */
public class ArrayQueue<E> implements Queue<E>{

    Array<E> array;

    public ArrayQueue(){
        array = new Array<>();
    }
    public ArrayQueue(int initCapacity){
        array = new Array<>(initCapacity);
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Queue:");
        s.append("front is left [");
        for (int i = 0; i < array.getSize(); i++) {
            if (i == getSize() -1){
                s.append(array.get(i));
            }else {
                s.append(array.get(i));
                s.append(", ");
            }
        }
        s.append("] tail");
        return s.toString();
    }
}
