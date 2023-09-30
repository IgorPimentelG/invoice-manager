CREATE TABLE IF NOT EXISTS national_simple_taxes (
    id CHAR(36) NOT NULL PRIMARY KEY,
    invoice_number VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    invoice_amount  DECIMAL(10, 2) NOT NUll,
    tax_amount DECIMAL(10, 2) NOT NUll,
    aliquot VARCHAR(255) NOT NULL,
    regime VARCHAR(255) NOT NULL,

    tax_resume_id CHAR(36) NOT NULL,
    CONSTRAINT fk_national_simple_taxes_tax_resume FOREIGN KEY (tax_resume_id) REFERENCES tax_resumes (id)
);