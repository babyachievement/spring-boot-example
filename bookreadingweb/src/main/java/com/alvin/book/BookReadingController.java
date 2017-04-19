package com.alvin.book;

import com.alvin.book.entity.Book;
import com.alvin.book.exception.BookNotFoundException;
import com.alvin.book.service.BookReadingService;
import com.alvin.error.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@RestController
@RequestMapping("/books")
public class BookReadingController {
    @Autowired
    private BookReadingService bookReadingService;

    @RequestMapping(value = "/", produces = "application/json; charset=UTF-8")
    public List<Book> getAllBooks() {
        return bookReadingService.getAllBooks();
    }


    @RequestMapping(value = "/author/{author}")
    public Book getBookByAuthor(@PathVariable(name = "author") String author) {

        Book bookByAuthor = bookReadingService.getBookByAuthor(author);
        if (null == bookByAuthor) {
            throw new BookNotFoundException(author);
        }

        return bookByAuthor;
    }

//    @ExceptionHandler(BookNotFoundException.class)
//    public ResponseEntity<ErrorMessage> bookNotFound(BookNotFoundException e) {
//        String bookId = e.getBookId();
//        ErrorMessage error = new ErrorMessage(4, "Book of [" + bookId + "] not found");
//
//        return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
//
//    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage bookNotFound(BookNotFoundException e) {
        String bookId = e.getBookId();

        return new ErrorMessage(4, "Book of [" + bookId + "] not found");

    }
}
