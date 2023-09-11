CREATE TABLE IF NOT EXISTS account_verification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code CHAR(6) NOT NULL,
    manager_id CHAR(36) NOT NULL,
    created_at DATETIME NOT NULL,
    checked_at DATETIME,
    expires_at DATETIME NOT NULL,
    checked BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_manager_av FOREIGN KEY (manager_id) REFERENCES managers (id)
);