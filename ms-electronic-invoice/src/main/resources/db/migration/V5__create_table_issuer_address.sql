CREATE TABLE IF NOT EXISTS issuer_address (
    issuer_id CHAR(18) NOT NULL,
    address_id BIGINT NOT NULL,

    PRIMARY KEY (issuer_id, address_id),
    FOREIGN KEY (issuer_id) REFERENCES issuers (cnpj),
    FOREIGN KEY (address_id) REFERENCES addresses (id)
);