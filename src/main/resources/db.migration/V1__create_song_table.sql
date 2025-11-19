CREATE TABLE song (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      release_date TIMESTAMP,
                      duration BIGINT
);

ALTER TABLE song
ADD uuid UUID;

CREATE SEQUENCE IF NOT EXISTS song_id_seq
    START WITH 1
    INCREMENT BY 1;