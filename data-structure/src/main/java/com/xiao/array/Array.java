package com.xiao.array;

import java.util.Arrays;

/**
 * @Description 对Java数组进行二次封装
 * @Auther: 笑笑是一个码农
 * @Date: 23:39 2020/6/3
 */
@SuppressWarnings("all")
public class Array<E> {


    /**
     * 数组默认初始化容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 数据
     */
    private E[] data;

    /**
     * 数组中元素的实际大小
     */
    private int size;

    /**
     * 默认构造方法,初始化数组容量为10
     */
    public Array(){
        this(DEFAULT_CAPACITY);
    }

    /**
     * 构造方法, 指定数组的初始容量大小
     * @param initCapacity 数组大小初始值
     */
    public Array(int initCapacity){
        if (initCapacity > 0){
            this.data = (E[])new Object[initCapacity];
            size = 0;
        }else if (initCapacity == 0){
            this.data = (E[])new Object[]{};
            size = 0;
        }else {
            throw new IllegalArgumentException("Illegal Capacity: " + initCapacity);
        }
    }

    /**
     * 获取当前数组元素的个数
     * @return 数据元素的个数
     */
    public int getSize(){
        return size;
    }

    /**
     * 返回数组的容量大小
     * @return 返回数组的容量大小
     */
    public int getCapacity(){
        return data.length;
    }

    /**
     * 判断数组是否包含元素
     * @return 返回数组是否包含元素的布尔值
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 往指定的索引位置添加一个元素
     * @param index 指定添加元素位置的索引
     * @param e 要添加的元素
     */
    public void add(int index, E e){
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add Element Failed. Require index >= 0 and <= size.");
        if (size == data.length)
            resize(2 * data.length); //扩容，2倍
        // 从该索引的位置开始，所有的元素右移
        for (int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];
        data[index] = e;
        size ++;
    }

    /**
     * 向数组的头部添加一个元素
     * @param e 要添加的元素
     */
    public void addFirst(E e){
        add(0, e);
    }

    /**
     * 向数组的尾部添加一个元素
     * @param e 要添加的元素
     */
    public void addLast(E e){
        add(size, e);
    }

    /**
     * 获取指定索引位置的元素
     * @param index 指定的索引
     * @return 指定索引位置的元素
     */
    public E get(int index){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Illegal Index: " + index);
        return data[index];
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size - 1);
    }
    /**
     * 修改指定索引位置的元素
     * @param index 指定的索引
     * @param e 新元素
     */
    public void set(int index, E e){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Illegal Index: " + index);
        data[index] = e;
    }

    /**
     * 判断数据中是否存在指定的元素
     * @param e 要匹配的元素
     * @return 匹配结果的布尔值
     */
    public boolean contains(E e){
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return true;
        }
        return false;
    }

    /**
     * 查找第一个匹配元素的索引
     * @param e 要匹配的元素
     * @return 匹配元素所在数组中的索引，如果未匹配，返回-1
     */
    public int findFirstIndex(E e){
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return i;
        }
        return -1;
    }

    /**
     * 删除指定位置定元素，返回删除的元素
     * @param index 要删除位置的索引
     * @return 删除的元素
     */
    public E remove(int index){
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException("Illegal Index: " + index);
        E ret = data[index]; // 删除之前的元素
        //从删除位置开始，所有右边的元素左移
        for (int i = index + 1; i < size; i++)
            data[i - 1] = data[i];
        size --;
        //其实不需要手动置为null，因为用户永远无法访问到data[size],但是为了能让jvm更快的进行垃圾回收，这里手动置为null
        data[size] = null;
        // 当数组大小等于容量的四分之一时，重新分配容量（为什么是四分之一，看下面注释，防止时间复杂度的震荡）
        // 如果当数组中的数据长度等于数组的容积时，交替调用addLast和removeLast,
        // 会产生时间复杂度的震荡，因为每调用一次，都需要重新分配容量，所以，当remove时，不要过于着急的减少数组容量。
        if (size == data.length / 4 && data.length / 2 != 0)
            resize(data.length / 2);
        return ret;
    }

    /**
     * 从数组的头部删除一个元素
     * @return 删除的元素
     */
    public E removeFirst(){
        return remove(0);
    }

    /**
     * 从数组的尾部删除一个元素
     * @return 删除的元素
     */
    public E removeLast(){
        return remove(size - 1);
    }

    /**
     * 删除第一个匹配到的元素
     * @param e 要删除的元素
     * @return 删除成功，返回true，不存在元素，返回false
     */
    public boolean removeFirstMatchElement(E e){
        int index = findFirstIndex(e);
        if (index != -1){
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * 获取真实数组
     * @return 返回一个Object数组(为什么返回Object，因为：比如Integer[]并不是Object[]的子类，所以并不能强转，但Integer是Object的子类，可以强转)
     */
    public Object[] getArray(){
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i]= data[i];
        }
        return array;
    }

    /**
     * 对数组扩容
     * @param newCapacity 扩充后的容量
     */
    private void resize(int newCapacity){
        E[] newData = (E[])new Object[newCapacity];
        for (int i = 0; i < size; i++)
            newData[i] = data[i];
        data = newData;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Array: ")
                .append("size: ")
                .append(size)
                .append(" ,")
                .append("capacity: ")
                .append(data.length)
                .append("\n");
        str.append("[");
        for (int i = 0; i < size; i++) {
            if (i == size - 1){
                str.append(data[i]);
            }else {
                str.append(data[i]);
                str.append(",");
            }
        }
        str.append("]");
        return str.toString();
    }
}
