package com.xiao.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @description 原子引用
 * @auther: 笑笑是一个码农
 * @date: 16:41 2020/11/22
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User user1 = new User("aa", 10);
        User user2 = new User("bb", 12);
        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        userAtomicReference.set(user1);
        //期望对象是user1，改为user2
        boolean b1 = userAtomicReference.compareAndSet(user1, user2);
        // 由于第一次已经被改为了user2，所以本次修改失败
        boolean b2 = userAtomicReference.compareAndSet(user1, user2);
        System.out.println(b1 + "\t" + userAtomicReference.get());
        System.out.println(b2 + "\t" + userAtomicReference.get());
    }
}


class User{
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}