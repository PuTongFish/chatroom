package com.PuTongFish.chatroom.Multiclient;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author: PuTongFish
 * Create:2019/3/2
 */
public class WriteFromServerThread extends Thread{
    private final Socket client;
    public WriteFromServerThread(Socket client){
        this.client = client;
    }
    public void run() {
        //获取键盘输入
        Scanner scanner = new Scanner(System .in);
        System.out.println();
        //获取客户都拿输出流
        try {
            OutputStream outputStream = client.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(outputStream);
            while(true){
                System.out.println("请输入将要发送的信息：");
                String strToServer = scanner.nextLine();

                out.write(strToServer+"\n");
                out.flush();

                if(strToServer.equals("byebye")){
                    //表示客户端要关闭
                    System.out.println("关闭客户端");
//                    scanner.close();
//                    out.close();
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("客户端写线程异常，错误为："+e);
        }

    }
}
