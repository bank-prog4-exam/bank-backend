package com.hei.bank.service;


import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface EntityServices<T> {
    List<T> findAll() throws SQLException;
    T findById(UUID id) throws SQLException;
    T save(T toSave) throws SQLException;
}
