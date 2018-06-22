// IBoomManager.aidl
package com.sj.myapplication;
import com.sj.myapplication.Book;
// Declare any non-default types here with import statements

interface IBoomManager {
   void addBook(in Book book);
   List<Book> getBooks();
}
