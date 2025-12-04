
CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY ,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255),
    enabled     BOOLEAN      NOT NULL,
    authorities TEXT[],
    confirmation_token VARCHAR(255)
);

