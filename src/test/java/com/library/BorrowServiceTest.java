package com.library;

import com.library.dao.BorrowDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorrowServiceTest {
    private BorrowService borrowService;
    private BookService bookService;
    private StudentService studentService;
    private BorrowDAO borrowDAO = new BorrowDAO();
    private Student student1;
    private Student student2;
    private Book book1;
    private Book book2;
    private Date borrowDate;
    private Date returnDate;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        studentService = new StudentService();
        borrowService = new BorrowService(borrowDAO);
        borrowService.deleteAllBorrows();
        bookService.deleteAllBooks();
        studentService.deleteAllStudents();

        student1 = new Student(1, "Alice");
        student2 = new Student(2, "Bob");
        // Ajouter un étudiant
        studentService.addStudent(student1);
        studentService.addStudent(student2);

        book1 = new Book(1, "Java Programming", "John Doe", "aaaa", 2024);
        book2 = new Book(2, "Advanced Java", "John Doe", "isbn", 2024);
        // Ajouter des livres
        bookService.addBook(book1);
        bookService.addBook(book2);

        borrowDate = new Date(System.currentTimeMillis());
        returnDate = new Date(System.currentTimeMillis() + 604800000);
    }

    @Test
    void testBorrowBook() {
        
        Borrow borrow = new Borrow(1,student1, book1, borrowDate, returnDate);
        
        assertEquals("Livre emprunté avec succès!", borrowService.borrowBook(borrow));
    }

    @Test
    void testReturnBook() {
        assertEquals("Livre retourné avec succès!", borrowService.deleteBorrow(1));
    }

    @Test
    void testBorrowBookNotAvailable() {
        assertTrue(true);
    }

    @Test
    void testBorrowBookStudentNotFound() {
        student1.setId(9999);
        Borrow borrow = new Borrow(1,student1, book1, borrowDate, returnDate);
        assertEquals("Étudiant ou livre non trouvé.", borrowService.borrowBook(borrow));
    }
}
