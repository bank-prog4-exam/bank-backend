CREATE TABLE overdraft_interest (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    interest_rate_first_days DECIMAL(5,2),
    interest_rate_after_days DECIMAL(5,2),
    modification_date TIMESTAMP
);