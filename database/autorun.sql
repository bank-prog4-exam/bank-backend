DROP DATABASE virtual_bank;

CREATE DATABASE virtual_bank;

\c virtual_bank

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS account (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    last_name VARCHAR(150),
    first_name VARCHAR(150),
    date_of_birth DATE CHECK (date_of_birth <= CURRENT_DATE - INTERVAL '23 years'),
    monthly_net_salary DOUBLE PRECISION,
    bank_name VARCHAR(50),
    unique_account_number VARCHAR(20) UNIQUE,
    overdraft_status BOOLEAN,
    principal_balance DOUBLE PRECISION DEFAULT 0.00,
    last_overdraft_activity TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "transaction" (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_account UUID,
    transaction_amount DOUBLE PRECISION,
    transaction_type VARCHAR(50),
    reason VARCHAR(150),
    reference VARCHAR(50) UNIQUE,
    effective_date TIMESTAMP,
    registration_date TIMESTAMP,
    FOREIGN KEY (id_account) REFERENCES account(id)
);

CREATE TABLE IF NOT EXISTS overdraft_interest (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    interest_rate_first_days DOUBLE PRECISION,
    interest_rate_after_days DOUBLE PRECISION,
    modification_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transfer (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_sender_account UUID,
    id_receiver_account UUID,
    transfer_amount DOUBLE PRECISION,
    reason VARCHAR(150),
    effective_date TIMESTAMP,
    registration_date TIMESTAMP,
    status VARCHAR(20) DEFAULT 'pending',
    reference VARCHAR(50) UNIQUE,
    label VARCHAR(50),
    FOREIGN KEY (id_sender_account) REFERENCES account(id),
    FOREIGN KEY (id_receiver_account) REFERENCES account(id)
);

CREATE TABLE IF NOT EXISTS account_statement (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_account UUID,
    statement_date TIMESTAMP,
    reference VARCHAR(50),
    operation_motive VARCHAR(150),
    credit_amount DOUBLE PRECISION,
    debit_amount DOUBLE PRECISION,
    balance DOUBLE PRECISION,
    FOREIGN KEY (id_account) REFERENCES account(id)
);