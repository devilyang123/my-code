package com.xiao.socket;

import java.io.*;
import java.net.Socket;

/**
 * @description Socket 简易版客户端
 * @auther: 笑笑是一个码农
 * @date: 22:34 2020/11/20
 */
public class Client {

    // 默认服务器主机地址
    final static String DEFAULT_SERVER_HOST = "127.0.0.1";
    // 默认服务器端口
    final static int DEFAULT_SERVER_PORT = 8888;
    /**
     * 默认客户端退出指令定义
     */
    final static String QUIT = "quit";

    public static void main(String[] args) {

        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            // 创建Socket
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
            System.out.println("[客户端日志] 已连接服务器");

            // 创建输入流，读取服务器发送的数据
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 创建输出流，向服务器发送数据
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 等待用户输入信息（TODO 思考：有几种方式实现？ 这里为什么用这种方式?）
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                String input = consoleReader.readLine();
                // 发送消息给服务器
                writer.write(input +"\n");
                writer.flush();
                System.out.println("[客户端日志] 客户端发送消息：" + input);

                // 读取服务器返回的消息
                String serverMessage = reader.readLine();
                System.out.println("[客户端日志] 接收到服务器消息：["+serverMessage+"]");

                if (input.equals(QUIT)){
                    System.out.println("[客户端日志] 退出客户端");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(writer);
        }
    }

    /**
     * 关闭方法抽取
     * @param writer
     */
    private static void close(BufferedWriter writer) {
        if (writer != null){
            try {
                writer.close(); // 会自动关闭socket
                System.out.println("[客户端日志] 关闭客户端的socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
