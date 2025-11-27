INSERT INTO artist (id, name) VALUES (1, 'The Beatles');
INSERT INTO artist (id, name) VALUES (2, 'Daft Punk');
INSERT INTO artist (id, name) VALUES (3, 'Miles Davis');
INSERT INTO artist (id, name) VALUES (4, 'Adele');
INSERT INTO artist (id, name) VALUES (5, 'Queen');
INSERT INTO artist (id, name) VALUES (6, 'Taylor Swift');
INSERT INTO artist (id, name) VALUES (7, 'Bob Marley');
INSERT INTO artist (id, name) VALUES (8, 'Metallica');
INSERT INTO artist (id, name) VALUES (9, 'Kendrick Lamar');
INSERT INTO artist (id, name) VALUES (10, 'Ludwig van Beethoven');

-- -- Reset Sequence
-- SELECT setval('artist_id_seq', (SELECT MAX(id) FROM artist));

