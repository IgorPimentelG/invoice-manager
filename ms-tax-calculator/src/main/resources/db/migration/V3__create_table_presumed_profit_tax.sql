CREATE TABLE IF NOT EXISTS presumed_profit_taxes (
    id CHAR(36) NOT NULL PRIMARY KEY,
    invoice_number VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    invoice_amount  DECIMAL(10, 2) NOT NUll,
    tax_amount DECIMAL(10, 2) NOT NUll,
    aliquot_irpj VARCHAR(10) NOT NULL,
    aliquot_iss VARCHAR(10) NOT NULL,
    aliquot_confins VARCHAR(10) NOT NULL,
    tax_irpj DECIMAL(10, 2) NOT NULL,
    tax_iss VARCHAR(10) NOT NULL,
    tax_confins VARCHAR(10) NOT NULL,

    tax_resume_id CHAR(36) NOT NULL,
    CONSTRAINT fk_presumed_profit_taxes_tax_resume FOREIGN KEY (tax_resume_id) REFERENCES tax_resumes (id)
);