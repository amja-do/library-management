package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService studentService;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        studentDAO = new StudentDAO();
        studentService = new StudentService(studentDAO);
        studentService.deleteAllStudents();

    }

    @Test
    void testAddStudent() {
        studentService.addStudent(new Student(1, "Alice"));
        assertEquals(1, studentDAO.getAllStudents().size());
        assertEquals("Alice", studentDAO.getStudentById(1).getName());

    }

    @Test
    void testUpdateStudent() {
        studentService.addStudent(new Student(6, "Alice"));
        studentService.updateStudent(new Student(6, "Alice Smith"));
        assertEquals("Alice Smith", studentDAO.getStudentById(6).getName());

    }

    @Test
    void testDeleteStudent() {
        studentService.addStudent(new Student(7, "Alice"));
        studentService.deleteStudent(7);
        assertNull(studentDAO.getStudentById(7));

    }

    @Test
    void testGetAllStudents() {
        studentService.addStudent(new Student(1, "Alice"));
        studentService.addStudent(new Student(2, "Bob"));
        assertEquals(2, studentDAO.getAllStudents().size());
        
    }
}
