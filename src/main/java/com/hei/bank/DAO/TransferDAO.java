package com.hei.bank.DAO;

import com.hei.bank.configuration.ConnectionDB;
import com.hei.bank.model.Account;
import com.hei.bank.model.Transaction;
import com.hei.bank.model.Transfer;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
        AutoCrudOperation<Transfer> autoCrudOperation = new AutoCrudOperation<>(Transfer.class);

        try {
            Transfer savedTransfer = autoCrudOperation.save(transfer);
            updateAccountBalance(transfer.getIdSenderAccount(), -transfer.getTransferAmount());
            updateAccountBalance(transfer.getIdReceiverAccount(), transfer.getTransferAmount());

            return savedTransfer;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void updateAccountBalance(UUID accountId, double amount) throws SQLException {
        String sql = "UPDATE account SET principal_balance = principal_balance + ? WHERE id = ?";

        try (Connection connection = new ConnectionDB().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setObject(2, accountId);
            statement.executeUpdate();
        }
    }


    public boolean delete(UUID id) {
        String sql = "SELECT id_sender_account, id_receiver_account, transfer_amount FROM transfer WHERE id = ?";
        String updateSenderSql = "UPDATE account SET principal_balance = principal_balance + ? WHERE id = ?";
        String updateReceiverSql = "UPDATE account SET principal_balance = principal_balance - ? WHERE id = ?";

        try (Connection connection = new ConnectionDB().getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(sql);
             PreparedStatement updateSenderStatement = connection.prepareStatement(updateSenderSql);
             PreparedStatement updateReceiverStatement = connection.prepareStatement(updateReceiverSql)) {

            connection.setAutoCommit(false);

            selectStatement.setObject(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (!resultSet.next()) {

                return false;
            }
            UUID senderAccountId = resultSet.getObject("id_sender_account", UUID.class);
            UUID receiverAccountId = resultSet.getObject("id_receiver_account", UUID.class);
            double transferAmount = resultSet.getDouble("transfer_amount");


            updateSenderStatement.setDouble(1, transferAmount);
            updateSenderStatement.setObject(2, senderAccountId);
            int senderRowsAffected = updateSenderStatement.executeUpdate();


            updateReceiverStatement.setDouble(1, transferAmount);
            updateReceiverStatement.setObject(2, receiverAccountId);
            int receiverRowsAffected = updateReceiverStatement.executeUpdate();

            if (senderRowsAffected > 0 && receiverRowsAffected > 0) {
                String deleteSql = "DELETE FROM transfer WHERE id = ?";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                    deleteStatement.setObject(1, id);
                    int deleteRowsAffected = deleteStatement.executeUpdate();
                    if (deleteRowsAffected > 0) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Account> transfer(Transfer transfer) throws SQLException {

        AccountDAO accountDAO = new AccountDAO();
        AccountDAO accountDAO1 = new AccountDAO();

        Account creditor = accountDAO.findById(transfer.getIdSenderAccount());
        Account debtor = accountDAO1.findById(transfer.getIdReceiverAccount());
        Timestamp now = Timestamp.from(Instant.now());

        if (creditor.equals(debtor)) {
            throw new RuntimeException("Sender and Receiver cannot be the same person");
        } else if (transfer.getStatus().equals("aborted")) {
            throw new RuntimeException("this transaction has been aborted");
        } else {

            if (creditor.getPrincipalBalance() >= transfer.getTransferAmount()) {

                creditor.setPrincipalBalance(creditor.getPrincipalBalance() - transfer.getTransferAmount());
                debtor.setPrincipalBalance(debtor.getPrincipalBalance() + transfer.getTransferAmount());

                String creditorTransactionReason = "transfer of " + transfer.getTransferAmount() + "Ar to " + debtor.getFirstName() + " " + debtor.getLastName();
                String debtorTransactionReason = "transfer of " + transfer.getTransferAmount() + "Ar from " + creditor.getFirstName() + " " + creditor.getLastName();
                String reference = transfer.getReference();
                Transaction transactionCreditor = new Transaction(
                        UUID.randomUUID(),
                        creditor.getId(),
                        transfer.getTransferAmount(),
                        "debit",
                        creditorTransactionReason,
                        transfer.getReference() + "01",
                        transfer.getEffectiveDate(),
                        now
                );

                TransactionDAO transactionDAO = new TransactionDAO();
                transactionDAO.doTransaction(transactionCreditor);

                Transaction transactionDebtor = new Transaction(
                        UUID.randomUUID(),
                        debtor.getId(),
                        transfer.getTransferAmount(),
                        "credit",
                        debtorTransactionReason,
                        reference + "02",
                        transfer.getEffectiveDate(),
                        now
                );
                TransactionDAO transactionDAO1 = new TransactionDAO();
                TransferDAO transferDAO = new TransferDAO();

                transfer.setStatus("completed");

                transferDAO.save(transfer);
                transactionDAO1.doTransaction(transactionDebtor);

                return Arrays.asList(creditor, debtor);
            } else {
                throw new RuntimeException("Sender balance is not enough");
            }
        }
    }

    public Transfer abortTransfer (UUID transferId) throws SQLException {

        TransferDAO transferDAO = new TransferDAO();
        TransferDAO transferDAO1 = new TransferDAO();

        Transfer transfer = transferDAO.findById(transferId);

        transfer.setStatus("aborted");

        transferDAO1.save(transfer);

        return transfer;

    }
}
