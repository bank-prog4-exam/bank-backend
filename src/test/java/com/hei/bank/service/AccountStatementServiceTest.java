package com.hei.bank.service;

import com.hei.bank.DAO.AccountStatementDAO;
import com.hei.bank.model.AccountStatement;
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


class AccountStatementServiceTest {

    @Mock
    private AccountStatementDAO accountStatementDAO;

    @InjectMocks
    private AccountStatementService accountStatementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws SQLException {
        // Given
        List<AccountStatement> expectedAccountStatements = new ArrayList<>();
        // Add some sample account statements to the list
        expectedAccountStatements.add(new AccountStatement(UUID.randomUUID(), UUID.randomUUID(), "Deposit", 100.0, "credit", null, 1000.0));
        expectedAccountStatements.add(new AccountStatement(UUID.randomUUID(), UUID.randomUUID(), "Withdrawal", 50.0, "debit", null, 950.0));

        // Mock the behavior of accountStatementDAO.findAll() to return the expected list of account statements
        when(accountStatementDAO.findAll()).thenReturn(expectedAccountStatements);

        // When
        List<AccountStatement> actualAccountStatements = accountStatementService.findAll();

        // Then
        assertEquals(expectedAccountStatements.size(), actualAccountStatements.size());
        for (int i = 0; i < expectedAccountStatements.size(); i++) {
            assertEquals(expectedAccountStatements.get(i), actualAccountStatements.get(i));
        }
    }

    @Test
    void testFindById() throws SQLException {
        // Given
        UUID accountStatementId = UUID.randomUUID();
        AccountStatement expectedAccountStatement = new AccountStatement(accountStatementId, UUID.randomUUID(), "Deposit", 100.0, "credit", null, 1000.0);

        // Mock the behavior of accountStatementDAO.findById() to return the expected account statement
        when(accountStatementDAO.findById(accountStatementId)).thenReturn(expectedAccountStatement);

        // When
        AccountStatement actualAccountStatement = accountStatementService.findById(accountStatementId);

        // Then
        assertEquals(expectedAccountStatement, actualAccountStatement);
    }

    @Test
    void testSave() throws SQLException {
        // Given
        AccountStatement accountStatementToSave = new AccountStatement(UUID.randomUUID(), UUID.randomUUID(), "Deposit", 100.0, "credit", null, 1000.0);

        // Mock the behavior of accountStatementDAO.save() to return the saved account statement
        when(accountStatementDAO.save(accountStatementToSave)).thenReturn(accountStatementToSave);

        // When
        AccountStatement savedAccountStatement = accountStatementService.save(accountStatementToSave);

        // Then
        assertEquals(accountStatementToSave, savedAccountStatement);
    }
}
