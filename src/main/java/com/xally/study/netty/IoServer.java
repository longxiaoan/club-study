package com.xally.study.netty;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by xiaoanlong on 2018/10/8.
 */
public class IoServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            new Thread(()->{
                while (true){
                    try {
                        Socket accept = serverSocket.accept();
                        new Thread(()->{
                            try {
                                int len;
                                byte[] data = new byte[1024];
                                InputStream inputStream = accept.getInputStream();
                                while ((len=inputStream.read(data))!=-1){
                                    System.out.println(new String(data, 0, len));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
