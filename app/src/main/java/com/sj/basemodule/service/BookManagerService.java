package com.sj.basemodule.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.sj.basemodule.Book;
import com.sj.basemodule.IBoomManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by 13658 on 2018/6/20.
 */

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private CopyOnWriteArrayList<com.sj.basemodule.Book> mBookList = new CopyOnWriteArrayList<>();
    private Binder mBinder = new IBoomManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"IOS"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
