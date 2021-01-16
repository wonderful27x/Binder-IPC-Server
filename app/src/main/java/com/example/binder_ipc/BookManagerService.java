package com.example.binder_ipc;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.example.binder_ipc.aidl.Book;
import com.example.binder_ipc.aidl.IBookManager;
import com.example.binder_ipc.aidl.IOnNewBookArrivedListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> listeners = new RemoteCallbackList<>();

    private Binder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(final Book book) throws RemoteException {
            bookList.add(book);
            int size = listeners.beginBroadcast();
            for (int i=0; i<size; i++){
                IOnNewBookArrivedListener listener = listeners.getBroadcastItem(i);
                if (listener != null){
                    try {
                        listener.onNewBookArrived(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            listeners.finishBroadcast();

            int check = checkCallingPermission("com.wonderful.binder_ipc");
            if (check == PackageManager.PERMISSION_DENIED){
                Log.d(TAG, "permission denied!");
            }else {
                Log.d(TAG, "permission pass!");
            }

            Log.d(TAG, "book name is :" + book.getBookName());
            book.setBookName("wonderful");
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listeners.unregister(listener);
        }
    };

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: " + "服务启动了");
        bookList.add(new Book(1,"book1"));
        bookList.add(new Book(2,"book2"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");

        //注意这三个permission，经过测试得出一些结论
        //checkSelfPermission();           //检查自己的permission，即自己有没有使用uses-permission
        //checkCallingPermission();        //检查调用binder方法的客户端的permission，测试发现只有在binder接口中检查才有效，否则总是为“false”
        //checkCallingOrSelfPermission();  //检查调用binder方法的客户端的permission或自己的permission，如果当前在binder接口中则检查客户端permission，否则检查自己的permission

        int check = checkCallingPermission("com.wonderful.binder_ipc");
        if (check == PackageManager.PERMISSION_DENIED){
            Log.d(TAG, "permission denied!");
        }else {
            Log.d(TAG, "permission pass!");
        }

        return binder;
    }
}
