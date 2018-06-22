// IBoomManager.aidl
package com.sj.basemodule;
import com.sj.basemodule.Book;
import com.sj.basemodule.IBookArrievdNotify;
// Declare any non-default types here with import statements

interface IBoomManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void addBook(in Book book);
     List<Book> getBookList();
     void registerBookNotify(IBookArrievdNotify listener);
     void unRegisterBookNotify(IBookArrievdNotify listener);
}
