CREATE TABLE IF NOT EXISTS issuers (
    cnpj CHAR(18) PRIMARY KEY,
    corporate_name VARCHAR(60) NOT NULL
);