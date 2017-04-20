package com.alvin.book.dao;

import com.alvin.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 *
 * QueryDslPredicate 简单实用方法
 * http://www.jianshu.com/p/2b68af9aa0f5
 */
public interface ReadingBookRepository extends JpaRepository<Book, Long>, QueryDslPredicateExecutor<Book> {
    List<Book> findByReader(String reader);
}
