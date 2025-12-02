package feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songify.SongifyApplication;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class HappyPathIntegrationTest {

    private static final String GENRE_HIP_HOP = "Hip Hop";
    private static final String ARTIST_EMINEM = "Eminem";
    private static final String ALBUM_TITLE = "The Eminem Show";
    private static final String SONG_LOSE_YOURSELF = "Lose Yourself";
    private static final String SONG_STAN = "Stan";

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @Test
    @DisplayName("Should_create_album_with_artist_and_songs")
    public void should_create_album_with_artist_and_songs() throws Exception {

        // 1 User sends a GET request to /genres and receives an empty list.
        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres").exists())
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.genres.length()").value(0));

        // 2 User sends a POST request to /genres with body { "name" : "Hip Hop" }
        // and the genre "Hip Hop" is returned with id 1 and HTTP status 201.
        mockMvc.perform(post("/genres")
                        .content("""
                                {
                                  "name": "Hip Hop"
                                }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value(GENRE_HIP_HOP));

        // 3 When I send a GET request to /songs and sees nothing.
        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs").exists())
                .andExpect(jsonPath("$.songs").isArray())
                .andExpect(jsonPath("$.songs.length()").value(0));

        // 4 User sends a POST request to /songs with body { "title": "Lose Yourself", "duration": 326 }
        // to create the song "Lose Yourself" the song is returned with id 1
        SongRequestDto requestDto = new SongRequestDto(
                "Lose Yourself",
                Instant.parse("2025-11-27T19:32:43.045Z"),
                326L,
                SongLanguageDto.English,
                1L
        );
        mockMvc.perform(post("/songs")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.name").value(SONG_LOSE_YOURSELF))
                .andExpect(jsonPath("$.song.id").exists());

        // 5 User sends a POST request to /songs with body { "title": "Stan", "duration": 356 }
        // to create the song "Lose Yourself" the song is returned with id 2 and HTTP status 201.
        SongRequestDto requestDto2 = new SongRequestDto(
                "Stan",
                Instant.parse("2025-11-27T19:32:43.045Z"),
                326L,
                SongLanguageDto.English,
                1L
        );
        mockMvc.perform(post("/songs")
                        .content(objectMapper.writeValueAsString(requestDto2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.name").value(SONG_STAN))
                .andExpect(jsonPath("$.song.id").exists());

        // 6 User sends a GET request to /artists and receives an empty list.
        mockMvc.perform(get("/artists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artists").exists())
                .andExpect(jsonPath("$.artists").isArray())
                .andExpect(jsonPath("$.artists.length()").value(0));

        // 7 User sends a POST request to /artists with body { "name" : "Eminem" }
        // and the artist "Eminem" is returned with id 1 and HTTP status 201.
        mockMvc.perform(post("/artists")
                        .content("""
                                {
                                  "name": "Eminem"
                                }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(ARTIST_EMINEM))
                .andExpect(jsonPath("$.id").value(1));

        // 8. User sends a GET request to /albums and receives an empty list.
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.albums").exists())
                .andExpect(jsonPath("$.albums").isArray())
                .andExpect(jsonPath("$.albums.length()").value(0));

        // User sends a POST request to /albums with body { "title": "The Eminem Show" }
        // then the album "The Eminem Show" is returned with id 1 and HTTP status 201.
        mockMvc.perform(post("/albums")
                        .content("""
                                {
                                          "title": "The Eminem Show",
                                          "releaseDate": "2025-12-01T18:36:39.085Z",
                                          "songIds": [
                                            1,2
                                          ]
                                        }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(ALBUM_TITLE))
                .andExpect(jsonPath("$.id").value(1));

        // 9. User sends a PUT request to /artists/1/1 to assign artist with id 1 to album with id 1
        mockMvc.perform(put("/artists/1/1")).andExpect(status().isOk()).andExpect(content().string("Artist has been assigned to album successfully."));

        // 10. User sends a GET request to /albums/1 and receives the album ("The Eminem Show") and the songs ("Lose Yourself")
        //and ("Stan") with genre "Hip Hop"
        mockMvc.perform(get("/albums/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(ALBUM_TITLE))
                .andExpect(jsonPath("$.artists[0].name").value(ARTIST_EMINEM));

    }


}
