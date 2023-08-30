ALTER TABLE email_log RENAME COLUMN `from` TO `recipient`;
ALTER TABLE email_log RENAME COLUMN `to` TO `sender`;