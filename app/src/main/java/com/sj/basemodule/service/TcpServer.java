package com.sj.basemodule.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by 13658 on 2018/6/25.
 */

public class TcpServer extends Service {
    private boolean isServerDestory;
    private String[] reviewMsg = new String[]{
            "哈哈",
            "呵呵",
            "哦",
            "马蛋"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new tcpTask()).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServerDestory = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //与客户端简历连接
    private class tcpTask implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听客户端8668端口
                serverSocket = new ServerSocket(8668);
                while (!isServerDestory) {
                    final Socket clientSocket = serverSocket.accept();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                                out.println("欢迎");
                                while (true) {
                                    String msg = in.readLine();
                                    System.out.println("收到客户端消息:" + msg);
                                    if (msg == null) {
                                        break;
                                    } else {
                                        String review = reviewMsg[new Random().nextInt(reviewMsg.length)];
                                        out.println(review);
                                    }

                                }
                                in.close();
                                out.close();
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
