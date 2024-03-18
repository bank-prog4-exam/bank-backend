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