--for account table

INSERT INTO account (id, last_name, first_name, date_of_birth, monthly_net_salary, bank_name, unique_account_number, overdraft_status,last_overdraft_activity)
VALUES ('5e6f984c-12b2-4b7b-b628-959811ca07b4','Doe', 'John', '1990-05-15', 5000.00, 'BMOI', 'ABC123456', FALSE, now()),
       ('7b9fb905-dad8-43fa-bfb3-8d72c458337f','Smith', 'Alice', '1985-10-20', 7000.00, 'BMOI', 'DEF987654', TRUE, now()),
       ('75dd98d5-eba3-4d14-9ac8-c589477f2970','Johnson', 'Michael', '1982-03-25', 6000.00, 'BOA', 'GHI456789', FALSE,now());

--for transaction table
INSERT INTO "transaction" (id_account, transaction_amount, transaction_type, reason, reference, effective_date, registration_date)
VALUES
    ('5e6f984c-12b2-4b7b-b628-959811ca07b4', 2000.00, 'credit', 'Initial deposit', 'REF1A2B3C', '2024-03-01 10:00:00', CURRENT_TIMESTAMP),
    ('5e6f984c-12b2-4b7b-b628-959811ca07b4', 500.00, 'credit', 'Groceries', 'REF4D5E6F', '2024-03-10 15:30:00', CURRENT_TIMESTAMP),
    ('75dd98d5-eba3-4d14-9ac8-c589477f2970', 3000.00, 'credit', 'Salary', 'REF7G8H9I', '2024-03-05 12:00:00', CURRENT_TIMESTAMP),
    ('7b9fb905-dad8-43fa-bfb3-8d72c458337f', 1000.00, 'credit', 'Rent', 'REFJKL1M2N', '2024-03-15 09:00:00', CURRENT_TIMESTAMP);

--for overdraft interest table

INSERT INTO overdraft_interest (interest_rate_first_days, interest_rate_after_days, modification_date)
VALUES (0.1, 0.2, NOW());

-- for transfer table

INSERT INTO transfer (id_sender_account, id_receiver_account, transfer_amount, reason, effective_date, registration_date, status, reference, label)
VALUES ('7b9fb905-dad8-43fa-bfb3-8d72c458337f', '5e6f984c-12b2-4b7b-b628-959811ca07b4', 1000.00, 'Monthly rent', '2024-03-20 10:00:00', NOW(), 'pending', 'REF123456', 'Rent'),
       ('5e6f984c-12b2-4b7b-b628-959811ca07b4', '75dd98d5-eba3-4d14-9ac8-c589477f2970', 500.00, 'Reimbursement', '2024-03-25 15:00:00', NOW(), 'completed', 'REF789012', 'Reimbursement');

--for account statement table
INSERT INTO account_statement (id_account, statement_date, reference, operation_motive, credit_amount, debit_amount, balance)
VALUES
    ('5e6f984c-12b2-4b7b-b628-959811ca07b4', '2024-03-01 10:00:00', 'REF1A2B3C', 'Initial deposit', 2000.00, 0.00, 2000.00),
    ('5e6f984c-12b2-4b7b-b628-959811ca07b4', '2024-03-10 15:30:00', 'REF4D5E6F', 'Groceries', 0.00, 500.00, 1500.00),
    ('75dd98d5-eba3-4d14-9ac8-c589477f2970', '2024-03-05 12:00:00', 'REF7G8H9I', 'Salary', 3000.00, 0.00, 3000.00),
    ('7b9fb905-dad8-43fa-bfb3-8d72c458337f', '2024-03-15 09:00:00', 'REFJKL1M2N', 'Rent', 0.00, 1000.00, 2000.00);
