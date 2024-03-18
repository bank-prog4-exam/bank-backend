package com.hei.bank;

import com.hei.bank.DAO.AccountDAO;
import com.hei.bank.model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		AccountDAO accountDAO = new AccountDAO();
		UUID uuid = UUID.randomUUID();
		Instant instant = Instant.now();
		Timestamp timestamp = Timestamp.from(instant);
		//Do transaction
		Transaction transaction = new Transaction(
				uuid,
				uuid,
				300.00,
				"credit",
				"monthly salary",
				timestamp,
				timestamp
		);
		System.out.println(accountDAO.doTransaction(transaction, uuid));
	}

}
