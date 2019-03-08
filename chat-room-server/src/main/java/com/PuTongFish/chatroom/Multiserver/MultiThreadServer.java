package com.PuTongFish.chatroom.Multiserver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * Author: PuTongFish
 * Create:2019/2/27
 */
public class MultiThreadServer {

    public static void main(String[] args){
            int port = 6669;
            if (args.length > 0) {
                try {
                    port = Integer.parseInt(args[0]);
                } catch (NumberFormatException var6) {
                    System.out.println("端口参数不正确，采用默认端口" + port);
                }
            }

            ExecutorService executorService = Executors.newFixedThreadPool(10);

            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("等待客户端连接...");

                while(true) {
                    Socket client = serverSocket.accept();
                    executorService.submit(new ExecuteClient(client));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
