package com.alvin.book.dao;

import com.alvin.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface ReadingBookRepository extends JpaRepository<Book, Long>, QueryDslPredicateExecutor<Book> {
    List<Book> findByReader(String reader);
}
