package com.hei.bank.controller;

import com.hei.bank.model.OverdraftInterest;
import com.hei.bank.service.OverdraftInterestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class OverdraftInterestController {
    private final OverdraftInterestService overdraftInterestService;

    public OverdraftInterestController(OverdraftInterestService overdraftInterestService) {
        this.overdraftInterestService = overdraftInterestService;
    }
    @GetMapping("/all_overdraftInterests")
    public List<OverdraftInterest> findAll() throws SQLException {
        return overdraftInterestService.findAll();
    }

    @GetMapping("/overdraftInterest/{id}")
    public OverdraftInterest findById(@PathVariable UUID id)throws SQLException{
        return overdraftInterestService.findById(id);
    }

    @PostMapping("/new_overdraftInterest")
    public OverdraftInterest save(@RequestBody OverdraftInterest toSave) throws SQLException{
        return overdraftInterestService.save(toSave);
    }
}
