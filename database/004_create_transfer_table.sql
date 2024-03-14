CREATE TABLE transfer (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    id_sender_account UUID,
    id_receiver_account UUID,
    transfer_amount DECIMAL(10,2),
    reason VARCHAR(150),
    effective_date TIMESTAMP,
    registration_date TIMESTAMP,
    status VARCHAR(20),
    FOREIGN KEY (id_sender_account) REFERENCES account(id_account),
    FOREIGN KEY (id_receiver_account) REFERENCES account(id_account)
);