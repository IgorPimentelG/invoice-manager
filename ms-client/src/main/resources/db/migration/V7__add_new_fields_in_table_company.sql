ALTER TABLE companies
ADD COLUMN created_at DATETIME NOT NULL,
ADD COLUMN updated_at DATETIME DEFAULT NULL;
