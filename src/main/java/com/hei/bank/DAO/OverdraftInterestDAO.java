package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.OverdraftInterest;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class OverdraftInterestDAO {
    public OverdraftInterestDAO() {
        ConnectionDB connectionDB = new ConnectionDB();
    }
    public List<OverdraftInterest> findAll() throws SQLException {
        AutoCrudOperation<OverdraftInterest> autoCrudOp = new AutoCrudOperation<>(OverdraftInterest.class);
        return autoCrudOp.findAll();
    }

    public OverdraftInterest findById(UUID id) throws SQLException {
        AutoCrudOperation<OverdraftInterest> AutoCrudOperation = new AutoCrudOperation<>(OverdraftInterest.class);
        return AutoCrudOperation.findById(id);
    }

    public OverdraftInterest save(OverdraftInterest overdraftInterest) {
        AutoCrudOperation<OverdraftInterest> AutoCrudOperation = new AutoCrudOperation<>(OverdraftInterest.class);
        return AutoCrudOperation.save(overdraftInterest);
    }
}
