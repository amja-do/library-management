
package com.library.service;

import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

public class BorrowService {

    private BorrowDAO borrowDAO;

    // Constructeur avec BorrowDAO
    public BorrowService(BorrowDAO borrowDAO) {
        this.borrowDAO = borrowDAO;
    }

    // Méthode pour emprunter un livre
    public String borrowBook(Borrow borrow) {
        // Sauvegarde de l'emprunt dans la base de données
        return borrowDAO.addBorrow(borrow);
    }

    // Afficher les emprunts (méthode fictive, à adapter)
    public void displayBorrows() {
        System.out.println("Liste des emprunts...");
        // Afficher les emprunts enregistrés (adapté selon votre DAO)
    }

    public Borrow getBorrowById(int id) {
        return borrowDAO.getById(id);
    }

    public String deleteBorrow(int id) {
        return borrowDAO.deleteBorrow(id);
    }
}
