package com.alvin.book;

import com.alvin.book.entity.Book;
import com.alvin.book.exception.BookNotFoundException;
import com.alvin.book.service.BookReadingService;
import com.alvin.error.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 * use the @Api annotation on BookReadingController class to describe API.
 * <p>
 * use the @ApiOperation annotation to describe the endpoint and its response type
 */
@RestController
@RequestMapping("/books")
@Api(value = "Book Reading", description = "Operations pertaining to books in reading")
public class BookReadingController {
    @Autowired
    private BookReadingService bookReadingService;

    @RequestMapping(value = "/", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有图书信息", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功获取列表"),
            @ApiResponse(code = 401, message = "无权查看此资源"),
            @ApiResponse(code = 403, message = "禁止访问该资源"),
            @ApiResponse(code = 404, message = "资源不存在")
    }
    )
    public List<Book> getAllBooks() {
        return bookReadingService.getAllBooks();
    }


    @RequestMapping(value = "/{bookId}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取图书信息", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功获取书籍信息"),
            @ApiResponse(code = 401, message = "无权查看此资源"),
            @ApiResponse(code = 403, message = "禁止访问该资源"),
            @ApiResponse(code = 404, message = "资源不存在")
    })
    public Book getBookById(@PathVariable(name = "bookId") long bookId) {
        Book book = bookReadingService.getBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException(String.valueOf(bookId));
        }
        return book;
    }


    @RequestMapping(value = "/author/{author}", produces = "application/json; charset=UTF-8", method = RequestMethod
            .GET)
    @ApiOperation(value = "根据作者名称获取作者的书", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功获取列表"),
            @ApiResponse(code = 401, message = "无权查看此资源"),
            @ApiResponse(code = 403, message = "禁止访问该资源"),
            @ApiResponse(code = 404, message = "此作者的书籍不存在")
    })
    public Page<Book> getBookByAuthor(@PathVariable(name = "author") String author,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                      @RequestParam(name = "isAsc", defaultValue = "true") boolean isAsc) {

        //分页排序
        Sort sort = new Sort(new Sort.Order(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField));
        Page<Book> booksByAuthor = bookReadingService.getBooksByAuthor(author, new PageRequest(page, pageSize, sort));
        if (null == booksByAuthor) {
            throw new BookNotFoundException(author);
        }

        return booksByAuthor;
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage bookNotFound(BookNotFoundException e) {
        String bookId = e.getBookId();

        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), "Book of [" + bookId + "] not found");
    }
}
