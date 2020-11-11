package com.xiao.stack;

/**
 * @Description 栈测试
 * @Auther: 笑笑是一个码农
 * @Date: 21:45 2020/7/8
 */
public class Test {

    public static void main(String[] args) {
        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        for (int i = 0; i < 5; i++) {
            arrayStack.push(i);
        }
        System.out.println(arrayStack);
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack);
    }
}
