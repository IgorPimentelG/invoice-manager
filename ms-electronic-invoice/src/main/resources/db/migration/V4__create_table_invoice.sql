CREATE TABLE IF NOT EXISTS invoices (
    number VARCHAR(255) NOT NULL PRIMARY KEY,
    validation_code VARCHAR(255) NOT NULL UNIQUE,
    date_issue DATETIME NOT NUll,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NUll,
    canceled BOOLEAN DEFAULT FALSE,

    recipient_id CHAR(18) NOT NULL,
    issuer_id CHAR(18) NOT NULL,

    CONSTRAINT fk_recipient FOREIGN KEY (recipient_id) REFERENCES recipients (cnpj),
    CONSTRAINT fk_issuer FOREIGN KEY (issuer_id) REFERENCES issuers (cnpj)
);