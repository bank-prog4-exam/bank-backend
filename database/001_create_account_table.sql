CREATE TABLE IF NOT EXISTS account (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    last_name VARCHAR(150),
    first_name VARCHAR(150),
    date_of_birth DATE,
    monthly_net_salary DOUBLE PRECISION,
    unique_account_number VARCHAR(20),
    overdraft_status BOOLEAN
);