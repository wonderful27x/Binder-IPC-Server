// IBookManager.aidl
package com.example.binder_ipc.aidl;

// Declare any non-default types here with import statements
import com.example.binder_ipc.aidl.Book;
import com.example.binder_ipc.aidl.IOnNewBookArrivedListener;
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     List<Book> getBookList();
     void addBook(inout Book book);
     void registerListener(IOnNewBookArrivedListener listener);
     void unregisterListener(IOnNewBookArrivedListener listener);
}
