package com.xiao.queue;

/**
 * @Description 基于数组的循环队列实现
 * @Auther: 笑笑是一个码农
 * @Date: 22:34 2020/8/7
 */
@SuppressWarnings("all")
public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    // front指向队首元素，tail指向下一个队尾元素的索引，当front == tail时认为队列为空
    // 这样，当出队时，就不用了移动队列中的元素，时间复杂度变为o(1)
    private int front, tail;
    private int size;

    public LoopQueue(int initCapacity){
        // 为什么要加1： 因为队列为循环，当front == tail队列为空，所以要人为的空出一个位置，作为队首和队尾的分割
        data = (E[])new Object[initCapacity +1];
        front = 0;
        tail = 0;
        size = 0;
    }
    public LoopQueue(){
        this(10);
    }
    @Override
    public void enqueue(E e) {
        // tail+1和数组大小取余，决定下一个tail的索引，加入data.length=8，tail=7, 8%8=0，下一个tail的索引就为0，
        if ((tail + 1) % data.length == front)  // 如果相等，表示队列以满
            resize(getCapacity() * 2);
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size ++;
    }
    @Override
    public E dequeue() {
        if (isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size --;
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity() / 2);
        return ret;
    }
    @Override
    public E getFront() {
        if (isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        return data[front];
    }
    @Override
    public int getSize() {
        return size;
    }
    @Override
    public boolean isEmpty() {
       return front == tail;
    }
    public int getCapacity(){
        return data.length - 1;
    }

    private void resize(int newCapacity){
       E[] newData = (E[])new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            // 把旧数组中，从队首元素开始放入到新的数组中，此时front为队列元素的偏移地址，和数组大小取余，防止越界
            // (因为是循环队列，所以数组的最后一个元素，不一定等于队列的最后一个元素)
            newData[i] = data[(i + front) % data.length];
        }
        data = newData;
        front = 0;
        tail = size; // tail指向下一个队尾元素的索引
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Loop Queue:");
        s.append("front is left [");
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            s.append(data[i]);
            if ((i + 1) % data.length != tail){
                s.append(", ");
            }
        }
        s.append("] tail");
        return s.toString();
    }
}
