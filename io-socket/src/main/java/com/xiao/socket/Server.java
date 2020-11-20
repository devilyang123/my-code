package com.xiao.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description ServerSocket 简易服务器
 * @auther: 笑笑是一个码农
 * @date: 22:12 2020/11/20
 */
public class Server {

    // 默认端口
    final static int DEFAULT_PORT = 8888;

    /**
     * 默认客户端退出指令定义
     */
    final static String QUIT = "quit";

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            // 绑定监听端口
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("[服务器日志] 服务器已启动，当前监听端口：" + DEFAULT_PORT);
            while (true){
                // accept方法，等待客户端连接，会阻塞当前线程，有客户端建立连接时，会返回一个Socket对象，使用该对象，就可以跟客户端通信
                Socket socket = serverSocket.accept();
                System.out.println("[服务器日志] 客户端["+socket.getInetAddress().getHostAddress()+":"+socket.getPort()+"]已连接");
                // 获取客户端的输入数据reader对象,读取客户端发送过来的数据
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // 获取向客户端发送数据writer对象，向客户端发送数据
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String message;

                // 循环读取某个客户端发的消息
                // null判断，客户端的输入流关闭或不可用，读取的为null
                while ((message = reader.readLine()) != null) {
                    System.out.println("[服务器日志] 接收到客户端["+socket.getPort()+"]消息:" + message);
                    // 回复客户端消息
                    writer.write("服务器回复消息：" + message + "\n");
                    writer.flush();

                    // 客户端是否退出
                    if (QUIT.equals(message)){
                        System.out.println("[服务器日志] 客户端["+socket.getPort()+"]已断开连接");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(serverSocket);
        }
    }

    /**
     * 抽取关闭方法
     * @param serverSocket
     */
    private static void close(ServerSocket serverSocket) {
        if (serverSocket != null){
            try {
                serverSocket.close();
                System.out.println("[服务器日志] 关闭serverSocket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
