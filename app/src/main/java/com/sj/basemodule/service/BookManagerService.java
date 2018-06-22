package com.sj.basemodule.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sj.basemodule.Book;
import com.sj.basemodule.IBookArrievdNotify;
import com.sj.basemodule.IBoomManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.tencent.bugly.crashreport.crash.c.j;

/**
 * Created by 13658 on 2018/6/21.
 */

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private AtomicBoolean isBinderActive = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IBookArrievdNotify> remoteCallbackList = new RemoteCallbackList<>();
    IBoomManager iBoomManager = new IBoomManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            books.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public void registerBookNotify(IBookArrievdNotify listener) throws RemoteException {
            remoteCallbackList.register(listener);
            Log.i(TAG, "registerBookNotify: size :" + remoteCallbackList.beginBroadcast());
            remoteCallbackList.finishBroadcast();
        }

        @Override
        public void unRegisterBookNotify(IBookArrievdNotify listener) throws RemoteException {
            remoteCallbackList.unregister(listener);
            Log.i(TAG, "registerBookNotify: size :" + remoteCallbackList.beginBroadcast());
            remoteCallbackList.finishBroadcast();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            iBoomManager.addBook(new Book(1, "Android"));
            iBoomManager.addBook(new Book(2, "IOS"));
            new Thread(new workRun()).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.sj.basemodule.permission.ACCEESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return iBoomManager.asBinder();
    }

    //新书到达会通知
    private void newBookNotify(Book newBook) throws RemoteException {
        int N = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IBookArrievdNotify iBookArrievdNotify = remoteCallbackList.getBroadcastItem(i);
            iBookArrievdNotify.bookArrievdNotify(newBook);
        }
        remoteCallbackList.finishBroadcast();
    }

    private class workRun implements Runnable {

        @Override
        public void run() {
            while (!isBinderActive.get()) {
                try {
                    Thread.sleep(5000);
                    Book book = new Book(books.size() + 1, "book # " + (books.size() + 1));
                    iBoomManager.addBook(book);
                    //通知
                    newBookNotify(book);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
