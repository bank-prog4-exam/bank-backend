package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountDAO{

    public AccountDAO() {
        ConnectionDB connectionDB = new ConnectionDB();
    }
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

}
