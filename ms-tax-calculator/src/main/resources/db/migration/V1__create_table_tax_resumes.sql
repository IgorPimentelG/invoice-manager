CREATE TABLE IF NOT EXISTS tax_resumes (
    id CHAR(36) NOT NULL PRIMARY KEY,
    reference VARCHAR(255) NOT NULL,
    amount VARCHAR(50) NOT NULL,
    paid boolean NOT NULL DEFAULT FALSE,
    owner CHAR(36) NOT NULL
);