package com.PuTongFish.chatroom.Multiclient;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author: PuTongFish
 * Create:2019/3/2
 */
public class MultiThreadClient {
    public static void main(String[] args) {
        try {
            //0 通过命令行获取参数
        int port = 6669;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
                //parseInt   将String 变为int型
            } catch (NumberFormatException e) {
                System.out.println("端口参数不正确，采用默认端口" + port);
            }
        }
        String host = "127.0.0.1";//主机ip
        if (args.length > 1) {
            host = args[1];
            //参数
        }
        Socket client = new Socket(host, port);
        Thread readFromServer = new Thread(new ReadFromServerThread(client));
        Thread writeFromServer = new Thread(new WriteFromServerThread(client));
        //1 往服务器发送数据
        readFromServer.start();
        //2 从服务器读取数据
        writeFromServer.start();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}



