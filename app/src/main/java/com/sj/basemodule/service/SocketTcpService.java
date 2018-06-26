package com.sj.basemodule.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by 13658 on 2018/6/26.
 */

public class SocketTcpService extends Service {
    private static final String TAG = "SocketTcpService";
    private boolean isServiceDestory;
    private String[] mReviewMessage = new String[]{
            "哈哈",
            "呵呵",
            "哦",
            "hello world！"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new SocketTcpTask()).start();

    }

    @Override
    public void onDestroy() {
        isServiceDestory = true;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class SocketTcpTask implements Runnable {
        @Override
        public void run() {
            //监听本地端口为8000socket
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8000);
                while (!isServiceDestory) {
                    //接收客户端的socket
                    final Socket clientSocket = serverSocket.accept();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                                out.println("欢迎");
                                while (!isServiceDestory) {
                                    String msg = in.readLine();
                                    if (msg == null) {
                                        break;
                                    }
                                    Log.i(TAG, "client :" + msg);
                                    String reviewMsg = mReviewMessage[new Random().nextInt(mReviewMessage.length)];
                                    out.println(reviewMsg);
                                    Log.i(TAG, "service :" + reviewMsg);
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
