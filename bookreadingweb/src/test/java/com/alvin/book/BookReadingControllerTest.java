package com.alvin.book;

import com.alvin.book.entity.Book;
import com.alvin.book.service.BookReadingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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

        PageImpl<Book> bookPage = new PageImpl<>(bookList);
        when(bookReadingService.getAllBooks(any())).thenReturn(bookPage);


        this.mockMvc.perform(get("/books?page=0&pageSize=10&sortField=id&isAsc=true")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(t -> System.out.println(t.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].author", is("alvin")))
                .andExpect(jsonPath("$.content[0].title", is("book1")))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].author", is("alvin")))
                .andExpect(jsonPath("$.content[1].title", is("book2")));

        verify(bookReadingService, times(1)).getAllBooks(any());
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
        Book book = Book.builder().createdDate(new Date()).id(1L).author("alvin").title("book1").build();
        when(bookReadingService.getBookById(1)).thenReturn(book);


        this.mockMvc.perform(get("/books/book/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.author", is("alvin")))
                .andExpect(jsonPath("$.title", is("book1")));

        verify(bookReadingService, times(1)).getBookById(1L);
        verifyNoMoreInteractions(bookReadingService);
    }


    @Test
    public void whenBookNotFound404ErrorMessageShouldReturn() throws Exception {
        when(bookReadingService.getBookById(anyLong())).thenReturn(null);

        this.mockMvc.perform(get("/books/book/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Book of [2] not found")))
                .andExpect(jsonPath("$.code", is(404)));


        verify(bookReadingService, times(1)).getBookById(2L);
        verifyNoMoreInteractions(bookReadingService);
    }

    @Test
    public void getBookByAuthor() throws Exception {
    }

    @Test
    public void bookNotFound() throws Exception {
    }

}