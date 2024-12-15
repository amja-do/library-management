
package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.StudentService;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BorrowDAO {
    private static final Logger LOGGER = Logger.getLogger(BorrowDAO.class.getName());

    public List<Borrow> getAllBorrows() {
        StudentDAO studentDAO = new StudentDAO();
        BookDAO bookDAO = new BookDAO();
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
        Student student;
        Book book;
        try ( Statement stmt = DbConnection.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                student = studentDAO.getStudentById(rs.getInt("member"));
                book = bookDAO.getBookById(rs.getInt("book"));
                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        student,
                        book,
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date"));
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la récupération des emprunts : " + e.getMessage());
        }
        return borrows;
    }

    public void save(Borrow borrow) {
        String query = "UPDATE borrows SET member = ?, book = ?, borrow_date = ?, return_date = ? WHERE id = ?";
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.setInt(5, borrow.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la mise à jour de l'emprunt : " + e.getMessage());
        }
    }

    public String addBorrow(Borrow borrow) {
        if(new StudentService().findStudentById(borrow.getStudent().getId()) == null || new BookDAO().getBookById(borrow.getBook().getId()) == null){
            return "Étudiant ou livre non trouvé.";
        }
        String query = "INSERT INTO borrows (id, member, book, borrow_date, return_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, borrow.getId());
            stmt.setInt(2, borrow.getStudent().getId());
            stmt.setInt(3, borrow.getBook().getId());
            stmt.setDate(4, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(5, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
            return "Livre emprunté avec succès!";
        } catch (SQLException e) {
            return "Erreur lors de l'emprunt!";
        }
    }

    public Borrow getById(int id) {
        StudentDAO studentDAO = new StudentDAO();
        BookDAO bookDAO = new BookDAO();
        String query = "SELECT * FROM borrows WHERE id = ?";
        Student student;
        Book book;
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = studentDAO.getStudentById(rs.getInt("member"));
                    book = bookDAO.getBookById(rs.getInt("book"));
                    return new Borrow(
                            rs.getInt("id"),
                            student,
                            book,
                            rs.getDate("borrow_date"),
                            rs.getDate("return_date"));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la récupération de l'emprunt : " + e.getMessage());
        }
        return null;
    }

    public String deleteBorrow(int id) {
        String query = "DELETE FROM borrows WHERE id = ?";
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return "Livre retourné avec succès!";
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors du retour du livre : " + e.getMessage());
            return "Erreur lors du retour du livre!";
        }
    }

    public void deleteAll() {
        String query = "DELETE FROM borrows";
        try (Statement stmt = DbConnection.getConnection().createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors du retour de tous les livres : " + e.getMessage());
        }
    }
}
