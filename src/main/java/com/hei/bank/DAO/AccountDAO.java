package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Account;
import com.hei.bank.model.AccountStatement;
import com.hei.bank.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDAO{

    private final ConnectionDB connectionDB = new ConnectionDB();
    public List<Account> findAll() throws SQLException {
        AutoCrudOperation<Account> autoCrudOp = new AutoCrudOperation<>(Account.class);
        return autoCrudOp.findAll();
    }

    public Account findById(UUID id) throws SQLException {
        AutoCrudOperation<Account> AutoCrudOperation = new AutoCrudOperation<>(Account.class);
        return AutoCrudOperation.findById(id);
    }

    public Account save(Account account) {
        AutoCrudOperation<Account> AutoCrudOperation = new AutoCrudOperation<>(Account.class);
        return AutoCrudOperation.save(account);
    }

    //==================================Do transaction==================================
    public AccountStatement doTransaction (Transaction transaction, UUID accountStatementId) throws SQLException {
        AccountStatementDAO accountStatementDAO = new AccountStatementDAO();
        AccountStatement accountStatement = accountStatementDAO.findById(accountStatementId);

        if(transaction.getTransactionType().equals("debit")){
            if (accountStatement.getPrincipalBalance()-transaction.getTransactionAmount()<0){
                accountStatement.setPrincipalBalance(accountStatement.getPrincipalBalance()-transaction.getTransactionAmount());
            }
            else {
                throw new RuntimeException("Transaction failed");
            }
        }
        else if(transaction.getTransactionType().equals("credit")){
            accountStatement.setPrincipalBalance(accountStatement.getPrincipalBalance()+transaction.getTransactionAmount());
        }
        else {
            throw new RuntimeException("Transaction failed");
        }
        AccountStatementDAO accountStatementDAO1 = new AccountStatementDAO();
        accountStatementDAO1.save(accountStatement);
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.save(transaction);
        return accountStatement;
    }
    public Account getAccountBalance (Timestamp timestamp, UUID accountId) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.findById(accountId);
        List<String> currentTransactionIdList = new ArrayList<>();
        List<String> transactionIdList = getAllTransaction(accountId);

        for (String transactionId : transactionIdList) {

            currentTransactionIdList.add(transactionId);
            TransactionDAO transactionDAO = new TransactionDAO();
            Transaction currentTransaction = transactionDAO.findById(UUID.fromString(transactionId));

            Timestamp currentTransactionTimestamp = currentTransaction.getEffectiveDate();

            if (timestamp.after(currentTransactionTimestamp) || timestamp.equals(currentTransactionTimestamp)) {
                if (currentTransaction.getTransactionType().equals("credit")) {
                    account.setPrincipalBalance(account.getPrincipalBalance() + currentTransaction.getTransactionAmount());
                } else if (currentTransaction.getTransactionType().equals("debit")) {
                    account.setPrincipalBalance(account.getPrincipalBalance() - currentTransaction.getTransactionAmount());
                }
            } else {
                break;
            }
        }
        return account;
    }

    public List<String> getAllTransaction(UUID accountId) throws SQLException {
        String SELECT_QUERY = "SELECT id FROM transaction WHERE id_account = ?";
        List<String> result = new ArrayList<>();

        try (
                Connection connection = connectionDB.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
        ){
            statement.setObject(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result.add(resultSet.getString("id"));
                    while (resultSet.next()) {
                        result.add(resultSet.getString("id"));
                    }
                } else {
                    throw new SQLException("No transaction found for account: " + accountId);
                }
            }
        }

        return result;
    }

}
