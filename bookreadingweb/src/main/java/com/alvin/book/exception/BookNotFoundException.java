package com.alvin.book.exception;

/**
 * Created by Administrator on 2017/4/19.
 */
public class BookNotFoundException extends RuntimeException {
    private String bookId;

    public BookNotFoundException(String bookId) {
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
