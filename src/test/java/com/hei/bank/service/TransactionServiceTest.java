package com.hei.bank.service;

import com.hei.bank.DAO.TransactionDAO;
import com.hei.bank.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    private TransactionDAO transactionDAO;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws SQLException {
        // Given
        List<Transaction> expectedTransactions = new ArrayList<>();
        // Add some sample transactions to the list
        expectedTransactions.add(new Transaction(UUID.randomUUID(), UUID.randomUUID(), 100.0,
                "debit", "Transaction 1", null, null));
        expectedTransactions.add(new Transaction(UUID.randomUUID(), UUID.randomUUID(), 200.0,
                "credit", "Transaction 2", null, null));

        // Mock the behavior of transactionDAO.findAll() to return the expected list of transactions
        when(transactionDAO.findAll()).thenReturn(expectedTransactions);

        // When
        List<Transaction> actualTransactions = transactionService.findAll();

        // Then
        assertEquals(expectedTransactions.size(), actualTransactions.size());
        for (int i = 0; i < expectedTransactions.size(); i++) {
            assertEquals(expectedTransactions.get(i), actualTransactions.get(i));
        }
    }

    @Test
    void testFindById() throws SQLException {
        // Given
        UUID transactionId = UUID.randomUUID();
        Transaction expectedTransaction = new Transaction(transactionId, UUID.randomUUID(), 100.0,
                "debit", "Transaction 1", null, null);

        // Mock the behavior of transactionDAO.findById() to return the expected transaction
        when(transactionDAO.findById(transactionId)).thenReturn(expectedTransaction);

        // When
        Transaction actualTransaction = transactionService.findById(transactionId);

        // Then
        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    void testSave() throws SQLException {
        // Given
        Transaction transactionToSave = new Transaction(UUID.randomUUID(), UUID.randomUUID(), 100.0,
                "debit", "Transaction 1", null, null);

        // Mock the behavior of transactionDAO.save() to return the saved transaction
        when(transactionDAO.save(transactionToSave)).thenReturn(transactionToSave);

        // When
        Transaction savedTransaction = transactionService.save(transactionToSave);

        // Then
        assertEquals(transactionToSave, savedTransaction);
    }
}