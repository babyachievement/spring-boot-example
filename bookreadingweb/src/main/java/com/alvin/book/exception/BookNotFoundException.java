package com.alvin.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Administrator on 2017/4/19.
 */
/**
 * 使用这个注解就不需要显示使用ExceptionHandler，而是由
 * @see org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver#doResolveException 自动处理
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String bookId) {
        super("could not find book '" + bookId + "'.");
    }

}
