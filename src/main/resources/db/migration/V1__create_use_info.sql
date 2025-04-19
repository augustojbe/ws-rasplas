CREATE TABLE IF NOT EXISTS subscriptions_type (
    subscriptions_type_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    access_months INT,
    price NUMERIC(10,2) NOT NULL,
    product_key VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS user_type (
    user_type_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    users_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    dt_subscription DATE NOT NULL,
    dt_expiration DATE NOT NULL,
    user_type_id INT,
    subscriptions_type_id INT,
    CONSTRAINT fk_user_type FOREIGN KEY (user_type_id) REFERENCES user_type(user_type_id) ON DELETE SET NULL,
    CONSTRAINT fk_subscriptions_type FOREIGN KEY (subscriptions_type_id) REFERENCES subscriptions_type(subscriptions_type_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS user_payment_info (
    user_payment_info_id SERIAL PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL UNIQUE,
    card_expiration_month INT NOT NULL,
    card_expiration_year INT NOT NULL,
    card_security_code VARCHAR(255) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    instalments INT NOT NULL,
    dt_payment DATE NOT NULL,
    user_id INT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(users_id) ON DELETE CASCADE
);

INSERT INTO subscriptions_type (name, access_months, price, product_key) VALUES
('PLANO MENSAL', 1, 75.00, 'MONTH22'),
('PLANO ANUAL', 12, 697.00, 'YEAR22'),
('PLANO VITALICIO', NULL, 1497.00, 'PERPETUAL22');

INSERT INTO user_type (name, description) VALUES
('PROFESSOR', 'Professores da plataforma - cadastro administrativo'),
('ADMINISTRADOR', 'Administrado da plataforma - cadastro administrativo'),
('ALUNO', 'Aluno da plataforma - cadastro via fluxo normal');
