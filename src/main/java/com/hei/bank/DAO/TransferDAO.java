package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Transfer;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class TransferDAO {
    public  TransferDAO () {
        ConnectionDB connectionDB = new ConnectionDB();
    }

    public List<Transfer> findAll() throws SQLException {
        AutoCrudOperation<Transfer> autoCrudOp = new AutoCrudOperation<>(Transfer.class);
        return autoCrudOp.findAll();
    }

    public Transfer findById(UUID id) throws SQLException {
        AutoCrudOperation<Transfer> AutoCrudOperation = new AutoCrudOperation<>(Transfer.class);
        return AutoCrudOperation.findById(id);
    }

    public Transfer save(Transfer transfer) {
        AutoCrudOperation<Transfer> AutoCrudOperation = new AutoCrudOperation<>(Transfer.class);
        return AutoCrudOperation.save(transfer);
    }

}
