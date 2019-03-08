package com.PuTongFish.chatroom.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * Author: PuTongFish
 * Create:2019/2/23
 */
public class SingleThreadClient {
    public static void main(String[] args) {
        try{
            //0 通过命令行获取参数
            int port = 6669;
            if(args.length>0){
                try {
                    port = Integer.parseInt(args[0]);
                    //parseInt   将String 变为int型
                }catch(NumberFormatException e){
                    System.out.println("端口参数不正确，采用默认端口"+port);
                }
            }
            String host = "127.0.0.1";//主机ip
            if(args.length>1){
                host = args[1];
                //参数
            }

            //1 创建
            Socket clientSocket = new Socket(host,port);

            //2 发送数据，接收数据
            //2.1 发送数据
            OutputStream clientOutput = clientSocket.getOutputStream();
            //把字节转换成字符
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write("你好，我是客户端\n");
            writer.flush();//刷新

            //2.2 接收数据
            InputStream clientInput = clientSocket.getInputStream();
            Scanner scanner = new Scanner(clientInput);
            String serverData = scanner.nextLine();//按行读
            System.out.println("来自服务端的数据: "+serverData);

            //3 客户端关闭
            clientOutput.close();
            clientInput.close();
            clientSocket.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
