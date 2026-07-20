package com.example.bookapi.controller;

import com.example.bookapi.model.Book;
import com.example.bookapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book(1L, "Effective Java", "Joshua Bloch", "978-0134685991", 45.0);
        book2 = new Book(2L, "Clean Code", "Robert C. Martin", "978-0132350884", 40.0);
    }

    @Test
    public void testCreateBook() throws Exception {
        Mockito.when(bookService.createBook(any(Book.class))).thenReturn(book1);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Effective Java")))
                .andExpect(jsonPath("$.author", is("Joshua Bloch")));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Effective Java")))
                .andExpect(jsonPath("$[1].title", is("Clean Code")));
    }

    @Test
    public void testGetBookById() throws Exception {
        Mockito.when(bookService.getBookById(1L)).thenReturn(book1);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Effective Java")))
                .andExpect(jsonPath("$.author", is("Joshua Bloch")));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book updatedDetails = new Book(1L, "Effective Java 3rd Edition", "Joshua Bloch", "978-0134685991", 50.0);
        Mockito.when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedDetails);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Effective Java 3rd Edition")))
                .andExpect(jsonPath("$.price", is(50.0)));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));
    }
}
