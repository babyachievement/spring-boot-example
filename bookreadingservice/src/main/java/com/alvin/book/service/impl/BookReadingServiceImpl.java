package com.alvin.book.service.impl;

import com.alvin.book.dao.ReadingBookRepository;
import com.alvin.book.entity.Book;
import com.alvin.book.entity.QBook;
import com.alvin.book.service.BookReadingService;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class BookReadingServiceImpl implements BookReadingService {
    private static Logger logger = LoggerFactory.getLogger(BookReadingServiceImpl.class);

    private ReadingBookRepository readingBookRepository;

    @Autowired
    public BookReadingServiceImpl(ReadingBookRepository readingBookRepository) {
        this.readingBookRepository = readingBookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return readingBookRepository.findAll();
    }

    @Override
    public Page<Book> getBooksByAuthor(String author, PageRequest pageRequest) {

        /**
         *
         QBook book = QBook.book;
         Predicate authorEqual = book.author.eq(author);

         //查找结果
         Page<Book> bookPage = readingBookRepository.findAll(authorEqual, pageRequest);
         返回空？TODO：为什么？
         */

        QBook book = QBook.book;
        Predicate authorEqual = book.author.eq(author);

        logger.info("Author:" + author);
        logger.info("PagerRequest:" + pageRequest);

        //查找结果
        Page<Book> bookPage = readingBookRepository.findAll(authorEqual, pageRequest);

        return bookPage;
    }


    @Override
    public Book getBookById(long bookId) {
        QBook book = QBook.book;
        Predicate idEqual = book.id.eq(bookId);
        Book one = readingBookRepository.findOne(idEqual);
        return one;
    }

    @Override
    public Book save(Book book) {
        Book result = readingBookRepository.save(book);
        return result;
    }

    @Override
    public int deleteBookById(long bookId) {
        try {
            readingBookRepository.delete(bookId);
        } catch (Exception e) {
            logger.error("删除ID为[" +bookId+"]的Book 失败", e);
            return 0;
        }
        return 1;
    }
}
