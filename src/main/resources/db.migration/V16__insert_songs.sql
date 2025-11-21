-- Songs for Abbey Road (Rock)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Come Together', 'English', 1, 1, 259, '1969-09-26 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Something', 'English', 1, 1, 182, '1969-09-26 00:00:00+00', uuid_generate_v4());

-- Songs for Discovery (Electronic)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('One More Time', 'English', 4, 2, 320, '2000-11-13 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Digital Love', 'English', 4, 2, 298, '2001-06-11 00:00:00+00', uuid_generate_v4());

-- Songs for Kind of Blue (Jazz)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('So What', 'Instrumental', 3, 3, 562, '1959-08-17 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Blue in Green', 'Instrumental', 3, 3, 327, '1959-08-17 00:00:00+00', uuid_generate_v4());

-- Songs for 25 (Pop)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Hello', 'English', 2, 4, 295, '2015-10-23 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Water Under the Bridge', 'English', 2, 4, 240, '2015-11-20 00:00:00+00', uuid_generate_v4());

-- Songs for A Night at the Opera (Rock)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Bohemian Rhapsody', 'English', 1, 5, 354, '1975-10-31 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Love of My Life', 'English', 1, 5, 219, '1975-11-21 00:00:00+00', uuid_generate_v4());

-- Songs for 1989 (Pop)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Blank Space', 'English', 2, 6, 231, '2014-11-10 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Style', 'English', 2, 6, 231, '2015-02-09 00:00:00+00', uuid_generate_v4());

-- Songs for Exodus (Reggae)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Jamming', 'English', 7, 7, 211, '1977-06-03 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Three Little Birds', 'English', 7, 7, 180, '1977-06-03 00:00:00+00', uuid_generate_v4());

-- Songs for Master of Puppets (Metal)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Battery', 'English', 8, 8, 312, '1986-03-03 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Master of Puppets', 'English', 8, 8, 515, '1986-03-03 00:00:00+00', uuid_generate_v4());

-- Songs for DAMN. (Hip Hop)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('DNA.', 'English', 5, 9, 185, '2017-04-14 00:00:00+00', uuid_generate_v4());
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('HUMBLE.', 'English', 5, 9, 177, '2017-03-30 00:00:00+00', uuid_generate_v4());

-- Songs for Symphony No. 9 (Classical)
INSERT INTO song (name, language, genre_id, album_id, duration, release_date, uuid)
VALUES ('Ode to Joy', 'German', 6, 10, 1080, '1824-05-07 00:00:00+00', uuid_generate_v4());

-- -- Reset Sequence (assuming standard song_id_seq)
-- SELECT setval('song_id_seq', (SELECT MAX(id) FROM song));