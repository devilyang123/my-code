package com.xiao;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

//@WebServlet("/test01")
@WebServlet(value = "/test01", asyncSupported = true) // 开启异步支持
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start = System.currentTimeMillis();
//        doSomethings(req, resp); // 同步
//        CompletableFuture.runAsync(() ->  doSomethings(req, resp)); // 如果不使用异步，该方式其实就是开了一个线程去跑了，程序继续向下执行
        AsyncContext asyncContext = req.startAsync();
        //使用异步Servlet，不占用tomcat的工作线程，把任务交由其他线程处理，但对于前端来说，还是需要阻塞等待返回
        CompletableFuture.runAsync(() ->  asyncDoSomethings(asyncContext, req, resp));
        long end = System.currentTimeMillis();
        resp.getWriter().write("done");
        System.out.println("sync-servlet:" + (end - start));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void asyncDoSomethings(AsyncContext asyncContext, HttpServletRequest req, HttpServletResponse resp){
        System.out.println("异步处理开始");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步处理结束");
        asyncContext.complete();
    }


    private void doSomethings(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("处理开始");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理结束");
    }
}
