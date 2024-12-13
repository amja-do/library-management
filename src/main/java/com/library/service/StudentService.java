package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.err.println("Erreur lors de l'affichage des étudiants : " + e.getMessage());
        }
    }

    // Trouver un étudiant par ID
    public Student findStudentById(int id) {
        try {
            return studentDAO.getStudentById(id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche de l'étudiant par ID : " + e.getMessage());
        }
        return null;
    }

    // Supprimer un étudiant par ID
    public void deleteStudent(int id) {
        try {
            studentDAO.deleteStudent(id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'étudiant : " + e.getMessage());
        }
    }

    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    // delete all students
    public void deleteAllStudents() {
        try {
            studentDAO.deleteAllStudents();
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de tous les étudiants : " + e.getMessage());
        }
    }
}
