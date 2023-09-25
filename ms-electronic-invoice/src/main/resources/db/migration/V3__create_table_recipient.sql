CREATE TABLE IF NOT EXISTS recipients (
    cnpj CHAR(18) PRIMARY KEY,
    corporate_name VARCHAR(60) NOT NULL,
    state_registration CHAR(15) NOT NULL UNIQUE,
    phone CHAR(15) NOT NULL UNIQUE,

     address_id BIGINT NOT NULL,
     CONSTRAINT fk_recipients_address FOREIGN KEY (address_id) REFERENCES addresses (id)
);


