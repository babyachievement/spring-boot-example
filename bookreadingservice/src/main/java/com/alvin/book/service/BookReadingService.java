package com.alvin.book.service;

import com.alvin.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface BookReadingService {
    List<Book> getAllBooks();

    Page<Book> getBooksByAuthor(String author, PageRequest pageRequest);

    /**
     * 根据bookId获取Book
     * @param bookId
     * @return
     */
    Book getBookById(long bookId);

    Book save(Book book);

    int deleteBookById(long bookId);
}
