package com.hei.bank.DAO;

import com.hei.bank.model.Account;

import java.util.UUID;

public interface test <T>{
    default T findById(UUID uuid){
        AutoCrudOperation<T> AutoCrudOperation = new AutoCrudOperation<>(T.class);
        return AutoCrudOperation.findById(uuid);
    }
}
