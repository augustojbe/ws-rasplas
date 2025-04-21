CREATE TABLE IF NOT EXISTS user_credentials (
    users_credentials_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL UNIQUE,
    user_type_id INT,
    CONSTRAINT fk_2_user_type_id FOREIGN KEY (user_type_id) REFERENCES user_type(user_type_id) ON DELETE SET NULL,
);