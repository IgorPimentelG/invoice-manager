CREATE TABLE IF NOT EXISTS companies (
    id CHAR(36) NOT NULL PRIMARY KEY,
    corporate_name VARCHAR(60) NOT NULL,
    cnpj CHAR(18) not NULL UNIQUE,
    tax_regime ENUM("SIMPLE_NATIONAL", "PRESUMED_PROFIT"),
    phone CHAR(15) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,

    manager_id CHAR(36) NOT NULL,
    CONSTRAINT fk_manager FOREIGN KEY (manager_id) REFERENCES managers (id)
);