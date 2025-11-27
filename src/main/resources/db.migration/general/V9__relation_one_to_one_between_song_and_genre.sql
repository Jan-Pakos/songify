ALTER TABLE song
    ADD genre_id BIGINT,
    ADD CONSTRAINT fk_genre
    FOREIGN KEY (genre_id)
    REFERENCES genre (id);