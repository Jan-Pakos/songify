INSERT INTO album (id, title, release_date) VALUES (1, 'Abbey Road', '1969-09-26 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (2, 'Discovery', '2001-03-12 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (3, 'Kind of Blue', '1959-08-17 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (4, '25', '2015-11-20 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (5, 'A Night at the Opera', '1975-11-21 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (6, '1989', '2014-10-27 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (7, 'Exodus', '1977-06-03 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (8, 'Master of Puppets', '1986-03-03 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (9, 'DAMN.', '2017-04-14 00:00:00+00');
INSERT INTO album (id, title, release_date) VALUES (10, 'Symphony No. 9', '1824-05-07 00:00:00+00');

-- -- Reset Sequence
-- SELECT setval('album_id_seq', (SELECT MAX(id) FROM album));
