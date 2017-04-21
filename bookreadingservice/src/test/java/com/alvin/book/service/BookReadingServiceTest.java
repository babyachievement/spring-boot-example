package com.alvin.book.service;

import com.alvin.book.dao.ReadingBookRepository;
import com.alvin.book.entity.Book;
import com.alvin.book.entity.QBook;
import com.alvin.book.service.impl.BookReadingServiceImpl;
import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by Administrator on 2017/4/18.
 */
public class BookReadingServiceTest {
    @Test
    public void getAllBooks() throws Exception {
        ReadingBookRepository readingBookRepository = Mockito.mock(ReadingBookRepository.class);
        List<Book> books = new ArrayList<>();
        PageRequest pageRequest = new PageRequest(0, 10);
        PageImpl<Book> bookPage = new PageImpl<>(books);
        given(readingBookRepository.findAll(pageRequest)).willReturn(bookPage);


        BookReadingServiceImpl bookReadingService = new BookReadingServiceImpl(readingBookRepository);
        Page<Book> all = bookReadingService.getAllBooks(pageRequest);
        assertThat(all.getContent().isEmpty(), is(true));
    }

    @Test
    public void testGetBookByAuthor() throws Exception {
        ReadingBookRepository readingBookRepository = Mockito.mock(ReadingBookRepository.class);

        QBook book = QBook.book;
        Predicate authorEqual = book.author.equalsIgnoreCase("");
        given(readingBookRepository.findOne(authorEqual)).willReturn(null);

        //分页排序
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        BookReadingServiceImpl bookReadingService = new BookReadingServiceImpl(readingBookRepository);
        Iterable<Book> booksByAuthor = bookReadingService.getBooksByAuthor("", new PageRequest(0, 10, sort));
        assertThat(booksByAuthor==null, is(true));
    }
}