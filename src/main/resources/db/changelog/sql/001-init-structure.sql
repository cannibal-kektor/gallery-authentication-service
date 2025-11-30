CREATE SCHEMA if NOT EXISTS authentication;
SET search_path TO authentication;

CREATE SEQUENCE id_tokens_sequence_generator START WITH 5000 INCREMENT BY 1 CACHE 100;

CREATE TABLE hashed_passwords
(
    user_id  bigint PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    CONSTRAINT password_length CHECK (LENGTH(password) >= 8 AND LENGTH(password) <= 100)
);

CREATE TABLE refresh_tokens
(
    id          bigint PRIMARY KEY DEFAULT nextval('id_tokens_sequence_generator'),
    token       VARCHAR(100) NOT NULL UNIQUE,
    user_id     bigint       NOT NULL,
    expiry_date timestamptz  NOT NULL,
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES hashed_passwords (user_id) ON DELETE CASCADE,
    CONSTRAINT token_not_expired_on_creation CHECK (expiry_date > CURRENT_TIMESTAMP)
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens (user_id);
CREATE INDEX idx_refresh_tokens_expiry_date ON refresh_tokens (expiry_date);
