CREATE TABLE "transaction" (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_account UUID,
    transaction_amount DOUBLE PRECISION,
    transaction_type VARCHAR(20),
    reason VARCHAR(150),
    effective_date TIMESTAMP,
    registration_date TIMESTAMP,
    FOREIGN KEY (id_account) REFERENCES account(id_account)
);