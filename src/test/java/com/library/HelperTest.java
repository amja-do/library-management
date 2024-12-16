package com.library;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.library.helper.Helper;
import com.library.model.Book;
import com.library.service.BookService;

public class HelperTest {
    BookService bookService;
    
    @BeforeEach
    void setUp() {
        bookService = new BookService();
        bookService.deleteAllBooks();
    }


    @Test
    void testGetEntityId(){
        assertEquals(1, Helper.getEntityId("books", "id"));
        assertEquals(1, Helper.getEntityId("books"));
        Book book = new Book(1, "Java Programming", "John Doe", "isbn", 2024);
        bookService.addBook(book);
        assertEquals(2, Helper.getEntityId("books", "id"));
        assertEquals(2, Helper.getEntityId("books"));

    }

}
