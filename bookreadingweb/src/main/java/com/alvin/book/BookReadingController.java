package com.alvin.book;

import com.alvin.book.entity.Book;
import com.alvin.book.service.BookReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@RestController
@RequestMapping("/books/")
public class BookReadingController {
    @Autowired
    private BookReadingService bookReadingService;

    @RequestMapping(value = "/", produces = "application/json; charset=UTF-8")
    public List<Book> getAllBooks() {
        return bookReadingService.getAllBooks();
    }
}
