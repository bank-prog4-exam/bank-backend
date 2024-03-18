--for account table

INSERT INTO account (last_name, first_name, date_of_birth, monthly_net_salary, unique_account_number, overdraft_status, credit_authorized)
VALUES ('Doe', 'John', '1990-05-15', 5000.00, 'ABC123456', FALSE, 1666.67),
       ('Smith', 'Alice', '1985-10-20', 7000.00, 'DEF987654', TRUE, 2333.33),
       ('Johnson', 'Michael', '1982-03-25', 6000.00, 'GHI456789', FALSE, 2000.00);

--for "transaction" table

INSERT INTO "transaction" (id_account, transaction_type, transaction_amount, reason, effective_date, registration_date)
VALUES ('e9e53e56-5bfb-4c29-9b8d-24dabb1f5741', 'deposit', 2000.00, 'Initial deposit', '2024-03-01 10:00:00', NOW()),
       ('e9e53e56-5bfb-4c29-9b8d-24dabb1f5741', 'withdrawal', 500.00, 'Groceries', '2024-03-10 15:30:00', NOW()),
       ('c3f89214-2c35-4811-804e-06e536cc0595', 'deposit', 3000.00, 'Salary', '2024-03-05 12:00:00', NOW()),
       ('c3f89214-2c35-4811-804e-06e536cc0595', 'withdrawal', 1000.00, 'Rent', '2024-03-15 09:00:00', NOW());

--for overdraft interest table

INSERT INTO overdraft_interest (id_account, interest_rate_first_days, interest_rate_after_days, modification_date)
VALUES ('e9e53e56-5bfb-4c29-9b8d-24dabb1f5741', 1.0, 2.0, NOW()),
       ('c3f89214-2c35-4811-804e-06e536cc0595', 1.5, 2.5, NOW());

-- for transfer table

INSERT INTO transfer (id_sender_account, id_receiver_account, transfer_amount, reason, effective_date, registration_date, status, reference, label)
VALUES ('e9e53e56-5bfb-4c29-9b8d-24dabb1f5741', 'c3f89214-2c35-4811-804e-06e536cc0595', 1000.00, 'Monthly rent', '2024-03-20 10:00:00', NOW(), 'pending', 'REF123456', 'Rent'),
       ('c3f89214-2c35-4811-804e-06e536cc0595', 'e9e53e56-5bfb-4c29-9b8d-24dabb1f5741', 500.00, 'Reimbursement', '2024-03-25 15:00:00', NOW(), 'completed', 'REF789012', 'Reimbursement');

--for account statement table

INSERT INTO account_statement (id_account, operation_motive, operation_amount, operation_type, effective_date, principal_balance)
VALUES ('e9e53e56-5bfb-4c29-9b8d-24dabb1f5741', 'Initial deposit', 2000.00, 'credit', '2024-03-01 10:00:00', 2000.00),
       ('e9e53e56-5bfb-4c29-9b8d-24dabb1f5741', 'Groceries', -500.00, 'debit', '2024-03-10 15:30:00', 1500.00),
       ('c3f89214-2c35-4811-804e-06e536cc0595', 'Salary', 3000.00, 'credit', '2024-03-05 12:00:00', 3000.00),
       ('c3f89214-2c35-4811-804e-06e536cc0595', 'Rent', -1000.00, 'debit', '2024-03-15 09:00:00', 2000.00);
