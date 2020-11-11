package com.xiao.stack;

import com.xiao.array.Array;

/**
 * 栈的数组实现
 * @param <E>
 */
public class ArrayStack<E> implements Stack<E> {

    /**
     * 基于动态数组实现
     */
    Array<E> array;

    public ArrayStack(){
        array = new Array<>();
    }

    public ArrayStack(int initCapacity) {
        array = new Array<>(initCapacity);
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
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Stack:");
        s.append(" [");
        for (int i = 0; i < array.getSize(); i++) {
            if (i == getSize() -1){
                s.append(array.get(i));
            }else {
                s.append(array.get(i));
                s.append(", ");
            }
        }
        s.append("] top is right");
        return s.toString();
    }
}
