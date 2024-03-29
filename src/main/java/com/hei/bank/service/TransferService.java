package com.hei.bank.service;

import com.hei.bank.DAO.TransferDAO;
import com.hei.bank.model.Account;
import com.hei.bank.model.Transfer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TransferService  {

    private TransferDAO transferDAO;


    public List<Transfer> findAll() throws SQLException {
        return transferDAO.findAll();
    }


    public Transfer findById(UUID id) throws SQLException {
        return transferDAO.findById(id);
    }


    public Transfer save(Transfer toSave) throws SQLException {
        return transferDAO.save(toSave);
    }


    public Boolean delete(UUID id) {
     return transferDAO.delete(id);
    }

    public List<Account> transfer(Transfer transfer) throws SQLException {
        return transferDAO.transfer(transfer);
    }

    public Transfer abortTransfer(UUID transferId) throws SQLException {
        return transferDAO.abortTransfer(transferId);
    }
}
