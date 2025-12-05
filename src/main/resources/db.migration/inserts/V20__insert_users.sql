INSERT INTO users (email, password, authorities, enabled)
VALUES ('Admin', '1234', '{ROLE_USER, ROLE_ADMIN}', true),
       ('User', '4321', '{ROLE_USER}', true);