package com.alvin.book.dao;

import com.alvin.book.entity.Book;
import com.alvin.book.entity.QBook;
import com.querydsl.core.types.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Administrator on 2017/4/20.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ReadingBookRepositoryTest {

    @Autowired
    private ReadingBookRepository repository;

    static List<Book> bookList;
    List<Book> save;

    static {

        bookList = new ArrayList<>();
        Book book = new Book();
        book.setAuthor("alvin");
        book.setTitle("title1");
        bookList.add(book);
        Book book2 = new Book();
        book2.setAuthor("alvin");
        book2.setTitle("title2");
        bookList.add(book2);
    }

    @Before
    public void setup() {
        save = repository.save(bookList);
    }

    @Test
    public void testGetAll() throws Exception {

        QBook book = QBook.book;
        Predicate authorEqual = book.author.eq("alvin");
        Sort order = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        PageRequest pageRequest = new PageRequest(0, 10, order);

        //查找结果
        Page<Book> bookPage = repository.findAll(authorEqual, pageRequest);
        List<Book> content = bookPage.getContent();
        for (Book b : content) {
            System.out.println(b);
        }

        assertThat(content.size(), is(2));
    }


    @After
    public void tearDown() {
        repository.delete(save);
    }
}