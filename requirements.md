SONGIFY: APP FOR MANAGING ALBUMS, ARTISTS, AND SONGS

# Functional requirements for the Songify Application
1. ~~One can add an artist (artist's name).~~ (DONE)
2. ~~One can add a music genre (genre name).~~ (DONE)
3. One can add an album (title, release date, but it must contain at least one song).
4. One can add a song (title, duration, release date, and the artist it belongs to). 
5. One can delete an artist (this also deletes their songs and albums). 
6. One can delete a music genre (but there must not be any song currently assigned to that genre). 
7. One can delete an album (but only when there are no songs assigned to the album). 
8. One can delete a song. 
9. One can edit an artist's songs and their name. 
10. One can edit a music genre's name. 
11. One can edit an album (add songs, add artists, change the album name). 
12. One can edit a song (duration, artist, song name). 
13. One can assign songs only to albums. 
14. One can assign artists to albums (an album can have multiple artists, an artist can have multiple albums). 
15. One can assign only one music genre to a song. 
16. If no genre is assigned to a song, then display "Default"
17. One can display all songs. 
18. One can display all genres. 
19. One can display all artists. 
20. One can display all albums. 
21. One can display all albums with artists and songs within the album. 
22. One can display specific music genres along with their songs. 
23. One can display specific artists along with their albums. 
24. We want to have persistent data.

# Non-functional requirements for the Songify Application
1. The application should be developed using Spring Boot framework.
2. The application should use a relational database (PostgreSQL) on Docker for data persistence.
3. The application should expose RESTful APIs for all functionalities.

# HAPPY PATH (user creates an Eminem album with songs "Lose Yourself" and "Stan" with genre "Hip Hop")
given there are no songs, artists, albums, or genres in the system
1. User sends a GET request to /songs and receives an empty list.
2. User sends a POST request to /songs with body { "title": "Lose Yourself", "duration": 326 } to create the song "Lose Yourself" the song is returned with id 1 and HTTP status 201.
3. User sends a POST request to /songs with body { "title": "Stan", "duration": 356 } to create the song "Lose Yourself" the song is returned with id 2 and HTTP status 201.
4. User sends a GET request to /artists and receives an empty list.
5. User sends a POST request to /artists with body { "name" : "Eminem" } and the artist "Eminem" is returned with id 1 and HTTP status 201.
5. User sends a GET request to /genres and receives an empty list.
6. User sends a POST request to /genres with body { "name" : "Hip Hop" } and the genre "Hip Hop" is returned with id 1 and HTTP status 201.
7. User sends a PUT request to /songs/1 to assign artist id 1 and genre id 1 to the song "Lose Yourself".
8. User sends a PUT request to /songs/2 to assign artist id 1 and genre id 1 to the song "Stan".
9. User sends a GET request to /albums and receives an empty list.
10. User sends a POST request to /albums with body { "title": "The Eminem Show" } then the album "The Eminem Show" is returned with id 1 and HTTP status 201.
11. User sends a PATCH request to /albums/1 to add song with id 1 and artist id 1 to the album "The Eminem Show".
12. User sends a PATCH request to /albums/1 to add song with id 2 and artist id 1 to the album "The Eminem Show".
