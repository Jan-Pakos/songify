create table if not exists song (
    id BIGSERIAL PRIMARY KEY,
    artist VARCHAR NOT NULL,
    name VARCHAR NOT NULL
);