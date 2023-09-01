CREATE TABLE IF NOT EXISTS company_address (
    id_company CHAR(36) NOT NULL,
    id_address BIGINT NOT NULL,

    PRIMARY KEY (id_company, id_address),
    FOREIGN KEY (id_company) REFERENCES companies (id),
    FOREIGN KEY (id_address) REFERENCES addresses (id)
);