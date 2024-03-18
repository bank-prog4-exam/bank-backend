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

	public static void main(String[] args) {
		AccountDAO accountDAO = new AccountDAO();
		UUID accountId = UUID.fromString("7b9fb905-dad8-43fa-bfb3-8d72c458337f");
		double transactionAmount = 3000.00;
		String transactionType = "debit";
		String reason = "monthly salary";
		Instant effectiveInstant = Instant.parse("2024-03-01T10:00:00Z");
		Timestamp effectiveDate = Timestamp.from(effectiveInstant);
		Timestamp registrationDate = Timestamp.from(Instant.now());

		Transaction transaction = new Transaction(
				UUID.randomUUID(), // Nouvel identifiant de transaction
				accountId,
				transactionAmount,
				transactionType,
				reason,
				effectiveDate,
				registrationDate
		);

		try {
			System.out.println("Before transaction:");
			System.out.println(accountDAO.findById(accountId));

			// Effectuer la transaction
			Account updatedAccount = accountDAO.doTransaction(transaction, accountId);

			System.out.println("After transaction:");
			System.out.println(updatedAccount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
