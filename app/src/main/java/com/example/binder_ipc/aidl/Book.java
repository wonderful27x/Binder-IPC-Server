package com.example.binder_ipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private int bookId;
    private String bookName;

    public Book(){

    }

    public Book(int bookId, String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>(){

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in){
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in){
        bookName = in.readString();
        bookId = in.readInt();
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
