package com.PuTongFish.chatroom.Multiclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author: PuTongFish
 * Create:2019/3/2
 */
public class ReadFromServerThread implements Runnable{
    private Socket client;
    public ReadFromServerThread (Socket client){
        this.client = client;
    }
    public void run() {
        try {
            InputStream clientInput = client.getInputStream();
            Scanner in = new Scanner(clientInput);
            System.out.println();
            while(true){
                String msg = in.nextLine();
                System.out.println("来自服务器的消息："+msg);

                if(msg.equals("byebye")){
                    break;
                }
//                if(in.hasNext()){
//                    System.out.println("从服务器端发来的信息为："+in.next());
//                }
//                //此客户端退出
//                if(client.isClosed()){
//                    System.out.println("客户端已经关闭！");
//                    break;
//                }
            }
        } catch (IOException e) {
            System.err.println("客户端读线程异常，错误为："+e);
        }

    }
}

