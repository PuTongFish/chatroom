package com.PuTongFish.chatroom.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Author: PuTongFish
 * Create:2019/2/23
 */
public class SingleThreadServer {
    public static void main(String[] args) {

        try {
            //0 通过命令行获取服务器端口号
            //设置默认端口号
            int port= 6669;
            if(args.length>0) {
                try {
                    //如果端口信息是数据，则使用该端口号
                    //parseInt方法是将字符转换成整数
                    port = Integer.parseInt(args[0]);
                }catch(NumberFormatException e){
                    //若端口信息时null，或者字符串之类不正确的形式，则会使用默认端口
                    System.out.println("端口参数不正确，采用默认端口"+port);
                }
            }

            //1 创建服务器对象
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器"+serverSocket.getLocalSocketAddress()+"已启动！\n");

            //2 创建客户端连接
            System.out.println("...等待客户端连接...\n");
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端信息："+clientSocket.getRemoteSocketAddress());

            //3 接收数据
            //创建接收数据的对象
            InputStream clientInput = clientSocket.getInputStream();
            //该代码允许用户从clientInput读取信息
            Scanner scanner = new Scanner(clientInput);
            //将clientInput信息按行读取，存放在新的变量中。
            String clientData = scanner.nextLine();
            //输出读取到的信息
            System.out.println("来自客户端的信息："+clientData);

            //4 发送数据
            //创建发送信息的对象
            OutputStream clientOutput = clientSocket.getOutputStream();
            //把发送的信息字节型转换成字符型
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            //将字符型的信息写出来
            writer.write("你好，欢迎连接！\n");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
