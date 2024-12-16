package com.library.dao;

import com.library.model.Book;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookDAO {
    private final Logger logger = Logger.getLogger(DbConnection.class.getName());

    // Ajouter un nouveau livre dans la base de données
    public String add(Book book) {
        String sql = "INSERT INTO books (id, title, author, isbn, published_year) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, book.getPublishedYear());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return "Livre inséré avec succès !";
            }
        } catch (SQLException e) {
            logger.severe("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
        return "Erreur lors de l'ajout du livre !";
    }

    // Récupérer un livre par son ISBN
    public Book getBookByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        Book book = null;

        try (Connection connection = DbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear(resultSet.getInt("published_year"));
            }
        } catch (SQLException e) {
            logger.severe("Erreur lors de la récupération du livre : " + e.getMessage());
        }

        return book;
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Book(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("isbn"),
                            resultSet.getInt("published_year"));
                }
            }
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
        return null;
    }

    public String delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                return "Book successfully deleted!";
            }
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
        return "Erreur lors de la suppression du livre !";
    }

    public String update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, published_year = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, book.getPublishedYear());
            preparedStatement.setInt(5, book.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return "Book updated successfully!";
            }
        } catch (SQLException e) {
            logger.severe("Error: " + e.getMessage());
        }
        return "Erreur lors de la mise à jour du livre !";
    }

    // Récupérer tous les livres
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = DbConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setPublishedYear(resultSet.getInt("published_year"));
                books.add(book);
            }
        } catch (SQLException e) {
            logger.severe("Erreur lors de la récupération des livres : " + e.getMessage());
        }

        return books;
    }

    // Supprimer tous les livres
    public String deleteAll() {
        String sql = "DELETE FROM books";
        try (Connection connection = DbConnection.getConnection();
                Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            return "Tous les livres ont été supprimés avec succès !";
        } catch (SQLException e) {
            logger.severe("Erreur lors de la suppression de tous les livres : " + e.getMessage());
            return "Erreur lors de la suppression de tous les livres : ";
        }
    }

}
