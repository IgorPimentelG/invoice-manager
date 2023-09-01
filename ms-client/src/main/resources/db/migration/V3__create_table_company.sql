CREATE TABLE IF NOT EXISTS companies (
    id CHAR(36) NOT NULL PRIMARY KEY,
    corporate_name VARCHAR(60) NOT NULL,
    cnpj CHAR(18) not NULL UNIQUE,
    TaxRegime ENUM("SIMPLE_NATIONAL", "PRESUMED_PROFIT"),
    phone CHAR(15) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,

    id_manager CHAR(36) NOT NULL,
    FOREIGN KEY (id_manager) REFERENCES managers (id)
);