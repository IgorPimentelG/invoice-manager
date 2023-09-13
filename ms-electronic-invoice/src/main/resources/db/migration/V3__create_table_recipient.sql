CREATE TABLE IF NOT EXISTS recipients (
    cnpj CHAR(18) PRIMARY KEY,
    corporate_name VARCHAR(60) NOT NULL,
    state_registration CHAR(13) NOT NULL UNIQUE,
    phone CHAR(15) NOT NULL UNIQUE
);


