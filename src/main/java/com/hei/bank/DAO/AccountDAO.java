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


}
