package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        bookDAO = new BookDAO();
        bookService.deleteAllBooks();

    }

    @Test
    void testAddBook() {
        Book book = new Book(1, "Java Programming", "John Doe", "isbn", 2024);
        assertEquals("Livre inséré avec succès !", bookService.addBook(book));
        assertEquals(1, bookDAO.getAllBooks().size());
        assertEquals("Java Programming", bookDAO.getBookById(1).getTitle());

    }

    @Test
    void testUpdateBook() {
        Book book = new Book(1, "Java Programming", "John Doe", "isbn", 2024);
        assertEquals("Livre inséré avec succès !", bookService.addBook(book));
        String msg = bookService.updateBook(new Book(1, "Advanced Java", "John Doe", "isbn", 2024));
        assertEquals("Advanced Java", bookDAO.getBookById(1).getTitle());
        assertEquals("Book updated successfully!", msg);

    }

    @Test
    void testDeleteBook() {
        Book book = new Book(1, "Java Programming", "John Doe", "isbn", 2024);
        assertEquals("Livre inséré avec succès !", bookService.addBook(book));
        assertEquals("Book successfully deleted!", bookService.deleteBook(1));
        assertNull(bookDAO.getBookById(1));

    }
}
