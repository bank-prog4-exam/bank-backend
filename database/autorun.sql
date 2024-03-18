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
    unique_account_number VARCHAR(20) UNIQUE,
    overdraft_status BOOLEAN
);

CREATE TABLE "transaction" (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_account UUID,
    transaction_amount DOUBLE PRECISION,
    transaction_type VARCHAR(50),
    reason VARCHAR(150),
    effective_date TIMESTAMP,
    registration_date TIMESTAMP,
    FOREIGN KEY (id_account) REFERENCES account(id)
);

CREATE TABLE overdraft_interest (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_account UUID,
    interest_rate_first_days DOUBLE PRECISION,
    interest_rate_after_days DOUBLE PRECISION,
    modification_date TIMESTAMP,
    FOREIGN KEY (id_account) REFERENCES account(id)
);

CREATE TABLE transfer (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_sender_account UUID,
    id_receiver_account UUID,
    transfer_amount DOUBLE PRECISION,
    reason VARCHAR(150),
    effective_date TIMESTAMP,
    registration_date TIMESTAMP,
    status VARCHAR(20),
    reference VARCHAR(50) UNIQUE,
    label VARCHAR(50),
    FOREIGN KEY (id_sender_account) REFERENCES account(id),
    FOREIGN KEY (id_receiver_account) REFERENCES account(id)
);

CREATE TABLE account_statement (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_account UUID,
    operation_motive VARCHAR(150),
    operation_amount DOUBLE PRECISION,
    operation_type VARCHAR(50),
    effective_date TIMESTAMP,
    principal_balance DOUBLE PRECISION,
    FOREIGN KEY (id_account) REFERENCES account(id)
);