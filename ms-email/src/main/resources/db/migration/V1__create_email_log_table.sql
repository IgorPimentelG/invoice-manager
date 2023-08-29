CREATE TABLE IF NOT EXISTS email_log (
    `id` CHAR(36) PRIMARY KEY,
    `owner_ref` CHAR(36) NOT NULL,
    `from` VARCHAR(100) NOT NULL,
    `to` VARCHAR(100) NOT NULL,
    `subject` VARCHAR(255) NOT NULL,
    `content` TEXT NOT NULL,
    `status` ENUM('SENT', 'ERROR') NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);