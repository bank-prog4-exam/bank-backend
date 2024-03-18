package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Account;
import com.hei.bank.model.AccountStatement;
import com.hei.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
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
    public Account doTransaction(Transaction transaction) {
        try {
            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.findById(transaction.getIdAccount());

            if ("debit".equals(transaction.getTransactionType())) {
                if (account.getPrincipalBalance() - transaction.getTransactionAmount() >= 0) {
                    account.setPrincipalBalance(account.getPrincipalBalance() - transaction.getTransactionAmount());
                } else if(account.getPrincipalBalance() - transaction.getTransactionAmount()<0){
                    Double loan = (account.getPrincipalBalance() - transaction.getTransactionAmount())*(-1);
                    Double overdraftAmount = account.getMonthlyNetSalary()/3;
                    if (account.getOverdraftStatus().equals(Boolean.TRUE) && loan <= overdraftAmount){
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

            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.save(transaction);

            return account;
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while performing transaction", e);
        }
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
