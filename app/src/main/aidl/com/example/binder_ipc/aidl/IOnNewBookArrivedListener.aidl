// IOnNewBookArrivedListener.aidl
package com.example.binder_ipc.aidl;

// Declare any non-default types here with import statements
import com.example.binder_ipc.aidl.Book;
interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book book);
}
