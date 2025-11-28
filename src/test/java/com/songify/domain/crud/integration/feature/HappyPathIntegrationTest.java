package com.songify.domain.crud.integration.feature;

import com.songify.SongifyApplication;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class HappyPathIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    public MockMvc mockMvc;


    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @Test
    public void f() throws Exception {
        // 1 When I send a GET request to /songs and sees nothing.
        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs").exists())
                .andExpect(jsonPath("$.songs").isArray())
                .andExpect(jsonPath("$.songs.length()").value(0));
        // 2 User sends a POST request to /songs with body { "title": "Lose Yourself", "duration": 326 }
        // to create the song "Lose Yourself" the song is returned with id 1 and HTTP status 201.
        mockMvc.perform(post("/songs")
                .content("""
                        {
                          "title": "Lose Yourself",
                          "releaseDate": "2025-11-27T19:32:43.045Z",
                          "durationInSeconds": 326,
                          "language": "English"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.name").value("Lose Yourself"))
                .andExpect(jsonPath("$.song.id").value(1));
        // 3 User sends a POST request to /songs with body { "title": "Stan", "duration": 356 }
        // to create the song "Lose Yourself" the song is returned with id 2 and HTTP status 201.
        mockMvc.perform(post("/songs")
                        .content("""
                        {
                          "title": "Stan",
                          "releaseDate": "2025-11-27T19:32:43.045Z",
                          "durationInSeconds": 384,
                          "language": "English"
                        }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.name").value("Stan"))
                .andExpect(jsonPath("$.song.id").value(2));
        // 4 User sends a GET request to /artists and receives an empty list.
        mockMvc.perform(get("/artists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artists").exists())
                .andExpect(jsonPath("$.artists").isArray())
                .andExpect(jsonPath("$.artists.length()").value(0));
        // 5 User sends a POST request to /artists with body { "name" : "Eminem" }
        // and the artist "Eminem" is returned with id 1 and HTTP status 201.
        mockMvc.perform(post("/artists")
                        .content("""
                                {
                                  "name": "Eminem"
                                }
                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Eminem"))
                .andExpect(jsonPath("$.id").value(1));
        // User sends a GET request to /genres and receives an empty list.
        mockMvc.perform(get("/genre")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs").exists())
                .andExpect(jsonPath("$.songs").isArray())
                .andExpect(jsonPath("$.songs.length()").value(0));
    }


}
