CREATE TABLE IF NOT EXISTS issuers (
    cnpj CHAR(18) PRIMARY KEY,
    corporate_name VARCHAR(60) NOT NULL,

    address_id BIGINT NOT NULL,
    CONSTRAINT fk_issuer_address FOREIGN KEY (address_id) REFERENCES addresses (id)
);