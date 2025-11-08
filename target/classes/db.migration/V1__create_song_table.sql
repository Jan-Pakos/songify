CREATE TABLE song (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      artist VARCHAR(255) NOT NULL,
                      release_date TIMESTAMP,
                      duration BIGINT
);
