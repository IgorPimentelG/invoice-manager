CREATE TABLE IF NOT EXISTS recipient_address (
    recipient_id CHAR(18) NOT NULL,
    address_id BIGINT NOT NULL,

    PRIMARY KEY (recipient_id, address_id),
    FOREIGN KEY (recipient_id) REFERENCES recipients (cnpj),
    FOREIGN KEY (address_id) REFERENCES addresses (id)
);