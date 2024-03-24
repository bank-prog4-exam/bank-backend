package com.hei.bank.service;

import com.hei.bank.DAO.TransferDAO;
import com.hei.bank.model.Transfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@Service

public class TransferService implements EntityServices<Transfer> {

    private TransferDAO transferDAO;


    @Override
    public List<Transfer> findAll() throws SQLException {
        return transferDAO.findAll();
    }

    @Override
    public Transfer findById(UUID id) throws SQLException {
        return transferDAO.findById(id);
    }

    @Override
    public Transfer save(Transfer toSave) throws SQLException {
        return transferDAO.save(toSave);
    }
}
