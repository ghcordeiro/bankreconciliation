CREATE TABLE IF NOT EXISTS bank_transactions (
    id SERIAL PRIMARY KEY,
    date DATE,
    description VARCHAR(255),
    amount NUMERIC,
    type VARCHAR(10),
    reconciled BOOLEAN
);

CREATE TABLE IF NOT EXISTS accounting_transactions (
    id SERIAL PRIMARY KEY,
    date DATE,
    description VARCHAR(255),
    amount NUMERIC,
    type VARCHAR(10),
    reconciled BOOLEAN
);
