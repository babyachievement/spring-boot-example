package com.alvin.book;

import com.alvin.book.entity.Book;
import com.alvin.book.service.BookReadingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2017/4/21.
 * spring mvc rest unit test
 * http://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
 */
public class BookReadingControllerTest {

    @Mock
    private BookReadingService bookReadingService;


    private MockMvc mockMvc;

    @InjectMocks
    private BookReadingController readingController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(readingController).build();
    }


    @Test
    public void getAllBooks() throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(Book.builder().createdDate(new Date()).id(1L).author("alvin").title("book1").build());
        bookList.add(Book.builder().createdDate(new Date()).id(2L).author("alvin").title("book2").build());
        when(bookReadingService.getAllBooks()).thenReturn(bookList);



        this.mockMvc.perform(get("/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].author", is("alvin")))
                .andExpect(jsonPath("$[0].title", is("book1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].author", is("alvin")))
                .andExpect(jsonPath("$[1].title", is("book2")));

        verify(bookReadingService, times(1)).getAllBooks();
        verifyNoMoreInteractions(bookReadingService);
    }

    @Test
    public void saveBook() throws Exception {
        Book result = Book.builder().id(1L).title("book1").author("alvin").createdDate(new Date()).build();
        Book post = Book.builder().title("book1").author("alvin").createdDate(new Date()).build();
        when(bookReadingService.save(post)).thenReturn(result);

        this.mockMvc.perform(put("/books/book").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"book1\", " +
                        "\"author\":\"alvin\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deleteBook() throws Exception {
        when(bookReadingService.deleteBookById(1L)).thenReturn(1);
        when(bookReadingService.deleteBookById(2L)).thenReturn(0);

        this.mockMvc.perform(delete("/books/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("删除成功")));



        this.mockMvc.perform(delete("/books/book/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message", is("删除失败")));

        verify(bookReadingService, times(1)).deleteBookById(1L);
        verify(bookReadingService, times(1)).deleteBookById(2L);
        verifyNoMoreInteractions(bookReadingService);
    }

    @Test
    public void getBookById() throws Exception {
    }

    @Test
    public void getBookByAuthor() throws Exception {
    }

    @Test
    public void bookNotFound() throws Exception {
    }

}