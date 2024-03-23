package com.hei.bank.service;

import com.hei.bank.DAO.AccountDAO;
import com.hei.bank.model.Account;
import com.hei.bank.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws SQLException {
        // Given
        List<Account> expectedAccounts = new ArrayList<>();
        // Add some sample accounts to the list
        expectedAccounts.add(new Account(UUID.randomUUID(), "Doe", "John", new Timestamp(System.currentTimeMillis()),
                5000.0, "123456", true, 1000.0));
        expectedAccounts.add(new Account(UUID.randomUUID(), "Smith", "Jane", new Timestamp(System.currentTimeMillis()),
                6000.0, "654321", false, 2000.0));

        // Mock the behavior of accountDAO.findAll() to return the expected list of accounts
        when(accountDAO.findAll()).thenReturn(expectedAccounts);

        // When
        List<Account> actualAccounts = accountService.findAll();

        // Then
        assertEquals(expectedAccounts.size(), actualAccounts.size());
        for (int i = 0; i < expectedAccounts.size(); i++) {
            assertEquals(expectedAccounts.get(i), actualAccounts.get(i));
        }
    }

    @Test
    void testFindById() throws SQLException {
        // Given
        UUID accountId = UUID.randomUUID();
        Account expectedAccount = new Account(accountId, "Doe", "John", new Timestamp(System.currentTimeMillis()),
                5000.0, "123456", true, 1000.0);

        // Mock the behavior of accountDAO.findById() to return the expected account
        when(accountDAO.findById(accountId)).thenReturn(expectedAccount);

        // When
        Account actualAccount = accountService.findById(accountId);

        // Then
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void testSave() throws SQLException {
        // Given
        Account accountToSave = new Account(null, "Doe", "John", new Timestamp(System.currentTimeMillis()),
                5000.0, "123456", true, 1000.0);
        Account savedAccount = new Account(UUID.randomUUID(), "Doe", "John", new Timestamp(System.currentTimeMillis()),
                5000.0, "123456", true, 1000.0);

        // Mock the behavior of accountDAO.save() to return the saved account
        when(accountDAO.save(accountToSave)).thenReturn(savedAccount);

        // When
        Account actualSavedAccount = accountService.save(accountToSave);

        // Then
        assertEquals(savedAccount, actualSavedAccount);
    }

    @Test
    void testDoTransaction() throws SQLException {
        // Given
        Transaction transaction = new Transaction(UUID.randomUUID(), UUID.randomUUID(), 100.0, "debit", "Test Transaction", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        Account initialAccount = new Account(UUID.randomUUID(), "Doe", "John", new Timestamp(System.currentTimeMillis()), 5000.0, "123456", true, 1000.0);
        Account expectedUpdatedAccount = new Account(initialAccount.getId(), initialAccount.getLastName(), initialAccount.getFirstName(),
                initialAccount.getDateOfBirth(), initialAccount.getMonthlyNetSalary(), initialAccount.getUniqueAccountNumber(),
                initialAccount.getOverdraftStatus(), initialAccount.getPrincipalBalance() - transaction.getTransactionAmount());

        // Mock the behavior of accountDAO.doTransaction() to return the updated account
        when(accountDAO.doTransaction(transaction)).thenReturn(expectedUpdatedAccount);

        // When
        Account actualUpdatedAccount = accountService.doTransaction(transaction);

        // Then
        assertEquals(expectedUpdatedAccount, actualUpdatedAccount);
    }

    @Test
    void testGetAllTransaction() throws SQLException {
        // Given
        UUID accountId = UUID.randomUUID();
        List<String> expectedTransactionIds = new ArrayList<>();
        expectedTransactionIds.add(UUID.randomUUID().toString());
        expectedTransactionIds.add(UUID.randomUUID().toString());

        // Mock the behavior of accountDAO.getAllTransaction() to return the expected list of transaction IDs
        when(accountDAO.getAllTransaction(accountId)).thenReturn(expectedTransactionIds);

        // When
        List<String> actualTransactionIds = accountService.getAllTransaction(accountId);

        // Then
        assertEquals(expectedTransactionIds.size(), actualTransactionIds.size());
        for (int i = 0; i < expectedTransactionIds.size(); i++) {
            assertEquals(expectedTransactionIds.get(i), actualTransactionIds.get(i));
        }
    }

    @Test
    void testGetAccountBalance() throws SQLException {
        // Given
        UUID accountId = UUID.randomUUID();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Account expectedAccount = new Account(accountId, "Doe", "John", new Timestamp(System.currentTimeMillis()),
                5000.0, "123456", true, 1000.0);

        // Mock the behavior of accountDAO.getAccountBalance() to return the expected account balance
        when(accountDAO.getAccountBalance(timestamp, accountId)).thenReturn(expectedAccount);

        // When
        Account actualAccount = accountService.getAccountBalance(timestamp, accountId);

        // Then
        assertEquals(expectedAccount, actualAccount);
    }
}