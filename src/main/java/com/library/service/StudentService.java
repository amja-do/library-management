package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService() {
    this.studentDAO = new StudentDAO();
}

    // Constructeur
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    

    // Ajouter un étudiant
    public void addStudent(Student student) {
        try {
            studentDAO.addStudent(student);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'étudiant : " + e.getMessage());
        }
    }

    // Afficher tous les étudiants
    public void displayStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + " | Nom: " + student.getName());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des étudiants : " + e.getMessage());
        }
    }

    // Trouver un étudiant par ID
    public Student findStudentById(int id) {
        try {
            return studentDAO.getStudentById(id);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'étudiant par ID : " + e.getMessage());
        }
        return null;
    }
}
