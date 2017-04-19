package com.alvin.book.service.impl;

import com.alvin.book.dao.ReadingBookRepository;
import com.alvin.book.entity.Book;
import com.alvin.book.entity.QBook;
import com.alvin.book.service.BookReadingService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class BookReadingServiceImpl implements BookReadingService {
    private ReadingBookRepository readingBookRepository;

    @Autowired
    public BookReadingServiceImpl(ReadingBookRepository readingBookRepository) {
        this.readingBookRepository = readingBookRepository;
    }

    public List<Book> getAllBooks() {
        return readingBookRepository.findAll();
    }


    public Book getBookByAuthor(String author) {


        QBook book = QBook.book;
        Predicate authorEqual = book.author.equalsIgnoreCase(author);
        Book one = readingBookRepository.findOne(authorEqual);
        return one;
    }
}
