package com.hei.bank.DAO;

import com.hei.bank.model.Account;
import com.hei.bank.model.AccountStatement;
import com.hei.bank.model.OverdraftInterest;
import com.hei.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class AccountDAO{

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

    public Map<String, Double> getAccountBalance (Timestamp timestamp, UUID accountId) throws SQLException {

        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        OverdraftInterestDAO overdraftInterestDAO = new OverdraftInterestDAO();

        Map<String, Double> result = new HashMap<>();

        Account account = accountDAO.findById(accountId);
        List<String> transactionIdList = transactionDAO.getAllTransaction(accountId);
        double loan = 0.00;


        account.setPrincipalBalance(00.0);

        for (String transactionId : transactionIdList) {

            TransactionDAO transactionDAO1 = new TransactionDAO();
            Transaction currentTransaction = transactionDAO1.findById(UUID.fromString(transactionId));

            Timestamp currentTransactionTimestamp = currentTransaction.getEffectiveDate();

            if (timestamp.after(currentTransactionTimestamp) || timestamp.equals(currentTransactionTimestamp)) {
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

    public List<AccountStatement> accountStatements(Date startDate, Date endDate, UUID accountID) throws SQLException {
        TransactionDAO transactionDAO = new TransactionDAO();
        List<AccountStatement> accountStatements = new ArrayList<>();

        List<String> transactionIdList = transactionDAO.getAllTransaction(accountID);
        for (String transactionId : transactionIdList) {

            TransactionDAO transactionDAO1 = new TransactionDAO();
            Transaction transaction = transactionDAO1.findById(UUID.fromString(transactionId));
            Timestamp date = transaction.getEffectiveDate();

            if ((date.equals(startDate) || date.after(startDate)) && (date.equals(endDate) || date.before(endDate))){
                Double creditAmount = 0.00;
                Double debitAmount = 0.00;

                if (transaction.getTransactionType().equals("debit")) {
                    debitAmount = transaction.getTransactionAmount();
                } else if (transaction.getTransactionType().equals("credit")) {
                    creditAmount = transaction.getTransactionAmount();
                }
                Map<String, Double> accountBalance = getAccountBalance(transaction.getEffectiveDate(), accountID);
                AccountStatement currentAccountStatement = new AccountStatement(
                        UUID.randomUUID(),
                        accountID,
                        transaction.getEffectiveDate(),
                        transaction.getReference(),
                        transaction.getReason(),
                        creditAmount,
                        debitAmount,
                        accountBalance.get("principalBalance")
                );
                accountStatements.add(currentAccountStatement);
            }
        }
        if (accountStatements.isEmpty()){
            System.out.println("no transaction from "+ startDate + " to "+ endDate);
        }
        return accountStatements;
    }
}
