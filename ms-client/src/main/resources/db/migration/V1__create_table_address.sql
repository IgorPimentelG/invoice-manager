CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    state VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    number VARCHAR(10) NOT NULL,
    zip_code CHAR(9) NOT NULL
);

