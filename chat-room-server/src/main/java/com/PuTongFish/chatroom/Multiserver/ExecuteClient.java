package com.PuTongFish.chatroom.Multiserver;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: PuTongFish
 * Create:2019/3/2
 */
public class ExecuteClient implements Runnable {
    private static Map<String,Socket> clientMap = new ConcurrentHashMap<String, Socket>();
    //具体处理每个客户端通信的内部类
    private final Socket client;
    public ExecuteClient(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            InputStream clientInput = this.client.getInputStream();
            Scanner in = new Scanner(clientInput);


            while(true){
                while (true){
                    String line = in.nextLine();
                    String strFromClient;
                    //注册流程
                    if(line.startsWith("userName")){
                        strFromClient = line.split("\\:")[1];
                        this.registerUser(strFromClient,this.client);
                    }else if(line.startsWith("P")){
                        String[] msgs = line.split("\\:");
                        String userName =msgs[1].split("-")[0];
                        strFromClient = msgs[1].split("-")[1];
                        this.privateChat(userName,strFromClient);
                    }else if(line.startsWith("G")){
                        strFromClient = line.split("\\:")[1];
                        this.groupChat(strFromClient);
                    }else if(line.equals("byebye")){
                        this.quit();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("服务器通信异常，错误为："+e);
        }
    }

    private void quit() {
        String currentUserName = this.getCurrentUserName();
        System.out.println("用户:" + currentUserName + "下线");
        Socket socket = (Socket)clientMap.get(currentUserName);
        this.sendMessage(socket, "byebye");
        clientMap.remove(currentUserName);
        printOnlineUser();
    }

    //注册方法
    private void registerUser(String userName, Socket client) {
            System.out.println("用户姓名为："+userName);
            System.out.println("用户"+userName+"上线了！"+client.getRemoteSocketAddress());
            //将用户信息保存到map中。
            clientMap.put(userName,client);
            sendMessage(this.client,userName+"注册成功！");
    }

    //群聊方法
    private void groupChat(String msg) {

        for(Socket socket:clientMap.values()){
            if(socket.equals(this.client)){
                continue;
            }
            this.sendMessage(socket, this.getCurrentUserName() + " 说：" + msg);
        }
    }

    //私聊方法
    private void privateChat(String userName, String msg) {
        String currentUserName = this.getCurrentUserName();
        Socket privatesocket = clientMap.get(userName);
        if(privatesocket != null){
            this.sendMessage(privatesocket,currentUserName+"对你说："+msg);
        }
    }

    private String getCurrentUserName() {
        String currentUserName = "";
        for(Map.Entry<String,Socket> entry:clientMap.entrySet()){
            if(this.client.equals(entry.getValue())){
                currentUserName = entry.getKey();
                break;
            }
        }
        return currentUserName;
    }

    private void sendMessage(Socket socket, String message) {
        try {
            OutputStream clientOutput = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private static void printOnlineUser() {
        System.out.println("当前在线人数为："+clientMap.size()+"人,"+"用户名如下列表：");
        Set<Map.Entry<String, Socket>> set = clientMap.entrySet();
        for (Map.Entry<String, Socket> entry : set) {
            System.out.println((String) entry.getKey());
        }

    }
}

