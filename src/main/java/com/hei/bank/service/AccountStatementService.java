package com.hei.bank.service;

import com.hei.bank.DAO.AccountStatementDAO;
import com.hei.bank.model.AccountStatement;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountStatementService implements EntityServices<AccountStatement>{

    private AccountStatementDAO accountStatementDAO;
    @Override
    public List<AccountStatement> findAll() throws SQLException {
        return accountStatementDAO.findAll();
    }

    @Override
    public AccountStatement findById(UUID id) throws SQLException {
        return accountStatementDAO.findById(id);
    }

    @Override
    public AccountStatement save(AccountStatement toSave) throws SQLException {
        return accountStatementDAO.save(toSave);
    }
}
