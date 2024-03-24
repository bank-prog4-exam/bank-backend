package com.hei.bank.service;

import com.hei.bank.DAO.AccountDAO;
import com.hei.bank.model.Account;
import com.hei.bank.model.AccountStatement;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AccountService implements EntityServices<Account> {

    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public List<Account> findAll() throws SQLException {
        return accountDAO.findAll();
    }

    @Override
    public Account findById(UUID id) throws SQLException {
        return accountDAO.findById(id);
    }

    @Override
    public Account save(Account toSave) throws SQLException {
        return accountDAO.save(toSave);
    }
    public Map<String, Double> getAccountBalance(Timestamp timestamp, UUID accountId) throws SQLException{
        return accountDAO.getAccountBalance(timestamp, accountId);
    }

    public List<AccountStatement> accountStatements(Timestamp startDate, Timestamp endDate, UUID accountId) throws SQLException {
        return accountDAO.accountStatements(startDate,endDate,accountId);
    }
}
