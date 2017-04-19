package com.alvin.book.service.impl;

import com.alvin.book.dao.ReadingListRepository;
import com.alvin.book.entity.Book;
import com.alvin.book.service.BookReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class BookReadingServiceImpl implements BookReadingService {
    private ReadingListRepository readingListRepository;

    @Autowired
    public BookReadingServiceImpl(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    public List<Book> getAllBooks() {
        return readingListRepository.findAll();
    }
}
