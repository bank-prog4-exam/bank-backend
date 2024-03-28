package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Account;
import com.hei.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TransactionDAO {
    private final ConnectionDB connectionDB = new ConnectionDB();
    public List<Transaction> findAll() throws SQLException {
        AutoCrudOperation<Transaction> autoCrudOp = new AutoCrudOperation<>(Transaction.class);
        return autoCrudOp.findAll();
    }

    public Transaction findById(UUID id) throws SQLException {
        AutoCrudOperation<Transaction> AutoCrudOperation = new AutoCrudOperation<>(Transaction.class);
        return AutoCrudOperation.findById(id);
    }

    public Transaction save(Transaction transaction) {
        try{
            AccountDAO accountDAO = new AccountDAO();
            AutoCrudOperation<Transaction> AutoCrudOperation = new AutoCrudOperation<>(Transaction.class);
            Account account = accountDAO.findById(transaction.getIdAccount());

            if ("debit".equals(transaction.getTransactionType())) {
                if (account.getPrincipalBalance() - transaction.getTransactionAmount() >= 0) {

                    account.setPrincipalBalance(account.getPrincipalBalance() - transaction.getTransactionAmount());

                } else if(account.getPrincipalBalance() - transaction.getTransactionAmount()<0){

                    double loan = (account.getPrincipalBalance() - transaction.getTransactionAmount())*(-1);
                    double overdraftAmount = account.getMonthlyNetSalary()/3;

                    if (account.getOverdraftStatus().equals(Boolean.TRUE) && loan <= overdraftAmount){

                        Instant instant = Instant.now();
                        Timestamp now = Timestamp.from(instant);

                        account.setLastOverdraftActivity(now);
                        account.setPrincipalBalance(account.getPrincipalBalance() - transaction.getTransactionAmount());
                    }
                    else throw new  RuntimeException("Your account is not active for overdraft or loan amount permission is too low for this transacion amount");
                }
            } else if ("credit".equals(transaction.getTransactionType())) {
                account.setPrincipalBalance(account.getPrincipalBalance() + transaction.getTransactionAmount());
            } else {
                throw new RuntimeException("Invalid transaction type");
            }
            accountDAO.save(account);
            return AutoCrudOperation.save(transaction);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //==================================Do transaction==================================
    public Account doTransaction(Transaction transaction) {
        try {
            AccountDAO accountDAO = new AccountDAO();
            TransactionDAO transactionDAO = new TransactionDAO();

            Account account = accountDAO.findById(transaction.getIdAccount());


            if ("debit".equals(transaction.getTransactionType())) {
                if (account.getPrincipalBalance() - transaction.getTransactionAmount() >= 0) {

                    account.setPrincipalBalance(account.getPrincipalBalance() - transaction.getTransactionAmount());

                } else if(account.getPrincipalBalance() - transaction.getTransactionAmount()<0){

                    double loan = (account.getPrincipalBalance() - transaction.getTransactionAmount())*(-1);
                    double overdraftAmount = account.getMonthlyNetSalary()/3;

                    if (account.getOverdraftStatus().equals(Boolean.TRUE) && loan <= overdraftAmount){

                        Instant instant = Instant.now();
                        Timestamp now = Timestamp.from(instant);

                        account.setLastOverdraftActivity(now);
                        account.setPrincipalBalance(account.getPrincipalBalance() - transaction.getTransactionAmount());
                    }
                    else throw new  RuntimeException("Your account is not active for overdraft or loan amount permission is too low for this transacion amount");
                }
            } else if ("credit".equals(transaction.getTransactionType())) {
                account.setPrincipalBalance(account.getPrincipalBalance() + transaction.getTransactionAmount());
            } else {
                throw new RuntimeException("Invalid transaction type");
            }

            transactionDAO.save(transaction);
            accountDAO.save(account);


            return account;
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while performing transaction", e);
        }
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
