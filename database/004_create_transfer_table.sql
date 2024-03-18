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