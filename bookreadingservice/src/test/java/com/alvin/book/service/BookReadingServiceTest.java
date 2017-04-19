package com.alvin.book.service;

import com.alvin.book.dao.ReadingListRepository;
import com.alvin.book.entity.Book;
import com.alvin.book.service.impl.BookReadingServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

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
        ReadingListRepository readingListRepository = Mockito.mock(ReadingListRepository.class);
        List<Book> books = Mockito.mock(List.class);

        given(books.isEmpty()).willReturn(true);
        given(readingListRepository.findAll()).willReturn(books);

        BookReadingServiceImpl bookReadingService = new BookReadingServiceImpl(readingListRepository);
        List<Book> all = bookReadingService.getAllBooks();
        assertThat(all.isEmpty(), is(true));
    }

}