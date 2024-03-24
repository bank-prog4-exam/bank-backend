package com.hei.bank.DAO;

import ch.qos.logback.core.util.TimeUtil;
import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Account;
import com.hei.bank.model.AccountStatement;
import com.hei.bank.model.OverdraftInterest;
import com.hei.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.util.*;

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

    public Map<String, Double> getAccountBalance (Timestamp timestamp, UUID accountId) throws SQLException {

        Transaction transaction = new Transaction();
        AccountDAO accountDAO = new AccountDAO();
        OverdraftInterestDAO overdraftInterestDAO = new OverdraftInterestDAO();

        List<String> currentTransactionIdList = new ArrayList<>();
        Map<String, Double> result = new HashMap<>();

        Account account = accountDAO.findById(accountId);
        List<String> transactionIdList = getAllTransaction(accountId);
        double loan = 0.00;


        for (String transactionId : transactionIdList) {

            currentTransactionIdList.add(transactionId);
            TransactionDAO transactionDAO = new TransactionDAO();
            Transaction currentTransaction = transactionDAO.findById(UUID.fromString(transactionId));

            Timestamp currentTransactionTimestamp = currentTransaction.getEffectiveDate();

            if (timestamp.after(currentTransactionTimestamp) || timestamp.equals(currentTransactionTimestamp)) {
                account.setPrincipalBalance(00.0);
                if (currentTransaction.getTransactionType().equals("credit")) {
                    account.setPrincipalBalance(account.getPrincipalBalance() + currentTransaction.getTransactionAmount());
                } else if (currentTransaction.getTransactionType().equals("debit")) {

                        account.setPrincipalBalance(account.getPrincipalBalance() - currentTransaction.getTransactionAmount());
                        if (account.getPrincipalBalance() < 0){
                            loan += (account.getPrincipalBalance())*(-1);
                        }
                    }
                }
        }
        if(account.getPrincipalBalance() < 0){

            result.put("principalBalance", 0.00);

            result.put("loan", loan);

            Timestamp lastOverdraftActivityTimestamp = account.getLastOverdraftActivity();

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            long timeDifference = currentTimestamp.getTime() - lastOverdraftActivityTimestamp.getTime();

            long daysDifference = timeDifference / (1000 * 60 * 60 * 24);

            List<OverdraftInterest> overdraftInterestList = overdraftInterestDAO.findAll();
            OverdraftInterest actualOverdraft = overdraftInterestList.get(0);
            if (daysDifference <= 7 ){
                result.put("loanInterest", actualOverdraft.getInterestRateFirstDays() * loan);
            } else {
                result.put("loanInterest", actualOverdraft.getInterestRateAfterDays() * loan);
            }
        }else {
            result.put("principalBalance", account.getPrincipalBalance());
            result.put("loan", loan);
            result.put("loanInterest", 0.00);
        }



        return result;
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
