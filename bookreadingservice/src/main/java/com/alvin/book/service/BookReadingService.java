package com.alvin.book.service;

import com.alvin.book.entity.Book;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface BookReadingService {
    List<Book> getAllBooks();

    Book getBookByAuthor(String author);
}
