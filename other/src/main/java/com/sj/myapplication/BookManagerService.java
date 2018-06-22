package com.sj.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by 13658 on 2018/6/21.
 */

public class BookManagerService extends Service {
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    IBoomManager iBoomManager = new IBoomManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return bookList;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Book book = new Book(1, "Android");
            iBoomManager.addBook(book);
            Book book2 = new Book(2, "IOS");
            iBoomManager.addBook(book2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBoomManager.asBinder();
    }
}
