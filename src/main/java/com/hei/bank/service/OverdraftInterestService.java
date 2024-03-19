package com.hei.bank.service;

import com.hei.bank.DAO.OverdraftInterestDAO;
import com.hei.bank.model.OverdraftInterest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class OverdraftInterestService implements EntityServices<OverdraftInterest> {
    private OverdraftInterestDAO overdraftInterestDAO;

    public OverdraftInterestService(OverdraftInterestDAO overdraftInterestDAO) {
        this.overdraftInterestDAO = overdraftInterestDAO;
    }

    @Override
    public List<OverdraftInterest> findAll() throws SQLException {
        return overdraftInterestDAO.findAll();
    }

    @Override
    public OverdraftInterest findById(UUID id) throws SQLException {
        return overdraftInterestDAO.findById(id);
    }

    @Override
    public OverdraftInterest save(OverdraftInterest toSave) throws SQLException {
        return overdraftInterestDAO.save(toSave);
    }
}
