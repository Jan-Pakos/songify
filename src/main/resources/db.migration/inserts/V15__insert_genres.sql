INSERT INTO genre (id, name) VALUES (1, 'Rock');
INSERT INTO genre (id, name) VALUES (2, 'Pop');
INSERT INTO genre (id, name) VALUES (3, 'Jazz');
INSERT INTO genre (id, name) VALUES (4, 'Electronic');
INSERT INTO genre (id, name) VALUES (5, 'Hip Hop');
INSERT INTO genre (id, name) VALUES (6, 'Classical');
INSERT INTO genre (id, name) VALUES (7, 'Reggae');
INSERT INTO genre (id, name) VALUES (8, 'Heavy Metal');
INSERT INTO genre (id, name) VALUES (9, 'Country');
INSERT INTO genre (id, name) VALUES (10, 'R&B');

-- -- Reset Sequence
-- SELECT setval('genre_id_seq', (SELECT MAX(id) FROM genre));


