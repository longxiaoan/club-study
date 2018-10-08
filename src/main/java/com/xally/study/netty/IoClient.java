package com.xally.study.netty;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoanlong on 2018/10/8.
 */
public class IoClient {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Socket socket = new Socket("127.0.0.1",8000);
                while (true){
                    socket.getOutputStream().write((new Date()+": hello world").getBytes());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
