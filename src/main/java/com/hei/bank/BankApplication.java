package com.hei.bank;

import com.hei.bank.DAO.AccountDAO;
import com.hei.bank.model.Account;
import com.hei.bank.model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		AccountDAO accountDAO = new AccountDAO();
//		UUID uuid = UUID.randomUUID();
		Instant instant = Instant.now();
//		UUID idAccount = UUID.fromString("5e6f984c-12b2-4b7b-b628-959811ca07b4");
//		Instant instant1 = Instant.parse("2024-03-01T10:00:00Z");
//		Timestamp timestamp2 =Timestamp.from(instant1);
		Timestamp timestamp = Timestamp.from(instant);
//		//Do transaction
//		Transaction transaction = new Transaction(
//				uuid,
//				idAccount,
//				300.00,
//				"credit",
//				"monthly salary",
//				timestamp2,
//				timestamp
//		);
//		System.out.println(accountDAO.doTransaction(transaction, uuid));

		UUID id = UUID.fromString("5e6f984c-12b2-4b7b-b628-959811ca07b4");
		System.out.println(accountDAO.getAccountBalance(timestamp, id));
	}

}
