// IBookArrievdNotify.aidl
package com.sj.basemodule;
import com.sj.basemodule.Book;
// Declare any non-default types here with import statements

interface IBookArrievdNotify {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void bookArrievdNotify(in Book book);
}
