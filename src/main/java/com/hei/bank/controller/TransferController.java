package com.hei.bank.controller;


import com.hei.bank.model.Account;
import com.hei.bank.model.Transfer;
import com.hei.bank.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/all_transfers")
    public List<Transfer> findAll() throws SQLException {
        return transferService.findAll();
    }

    @GetMapping("/transfer/{id}")
    public Transfer findById(@PathVariable UUID id) throws SQLException {
        return transferService.findById(id);
    }

    @PostMapping("/new_transfer")
    public Transfer save(@RequestBody Transfer toSave) throws SQLException {
        return transferService.save(toSave);
    }

    @DeleteMapping("/transfer/{id}")
    public Boolean delete(@PathVariable UUID id){
        return transferService.delete(id);
    }

    @PostMapping("/do_transfer")
    public List<Account> transfer(@RequestBody Transfer transfer) throws SQLException {
        return transferService.transfer(transfer);
    }

    @PutMapping("/abort_transfer/{transferId}")
    public Transfer abortTransfer(@PathVariable UUID transferId) throws SQLException {
        return transferService.abortTransfer(transferId);
    }
}
