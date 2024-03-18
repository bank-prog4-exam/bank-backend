--for account table

INSERT INTO account (id, last_name, first_name, date_of_birth, monthly_net_salary, unique_account_number, overdraft_status)
VALUES ('5e6f984c-12b2-4b7b-b628-959811ca07b4','Doe', 'John', '1990-05-15', 5000.00, 'ABC123456', FALSE),
       ('7b9fb905-dad8-43fa-bfb3-8d72c458337f','Smith', 'Alice', '1985-10-20', 7000.00, 'DEF987654', TRUE),
       ('75dd98d5-eba3-4d14-9ac8-c589477f2970','Johnson', 'Michael', '1982-03-25', 6000.00, 'GHI456789', FALSE);

--for "transaction" table

INSERT INTO "transaction" (id_account, transaction_type, transaction_amount, reason, effective_date, registration_date)
VALUES ('5e6f984c-12b2-4b7b-b628-959811ca07b4', 'deposit', 2000.00, 'Initial deposit', '2024-03-01 10:00:00', NOW()),
       ('7b9fb905-dad8-43fa-bfb3-8d72c458337f', 'withdrawal', 500.00, 'Groceries', '2024-03-10 15:30:00', NOW()),
       ('5e6f984c-12b2-4b7b-b628-959811ca07b4', 'ingoing transfer', 3000.00, 'Salary', '2024-03-05 12:00:00', NOW()),
       ('75dd98d5-eba3-4d14-9ac8-c589477f2970', 'outgoing transfer', 1000.00, 'Rent', '2024-03-15 09:00:00', NOW());

--for overdraft interest table

INSERT INTO overdraft_interest (interest_rate_first_days, interest_rate_after_days, modification_date)
VALUES (1.0, 2.0, NOW());

-- for transfer table

INSERT INTO transfer (id_sender_account, id_receiver_account, transfer_amount, reason, effective_date, registration_date, status, reference, label)
VALUES ('7b9fb905-dad8-43fa-bfb3-8d72c458337f', '5e6f984c-12b2-4b7b-b628-959811ca07b4', 1000.00, 'Monthly rent', '2024-03-20 10:00:00', NOW(), 'pending', 'REF123456', 'Rent'),
       ('5e6f984c-12b2-4b7b-b628-959811ca07b4', '75dd98d5-eba3-4d14-9ac8-c589477f2970', 500.00, 'Reimbursement', '2024-03-25 15:00:00', NOW(), 'completed', 'REF789012', 'Reimbursement');

--for account statement table

INSERT INTO account_statement (id_account, operation_motive, operation_amount, operation_type, effective_date, principal_balance)
VALUES ('5e6f984c-12b2-4b7b-b628-959811ca07b4', 'Initial deposit', 2000.00, 'credit', '2024-03-01 10:00:00', 2000.00),
       ('5e6f984c-12b2-4b7b-b628-959811ca07b4', 'Groceries', -500.00, 'debit', '2024-03-10 15:30:00', 1500.00),
       ('75dd98d5-eba3-4d14-9ac8-c589477f2970', 'Salary', 3000.00, 'credit', '2024-03-05 12:00:00', 3000.00),
       ('7b9fb905-dad8-43fa-bfb3-8d72c458337f', 'Rent', -1000.00, 'debit', '2024-03-15 09:00:00', 2000.00);
