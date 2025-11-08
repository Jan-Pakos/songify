package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "artist", nullable = false)
    private String artist;

    private Instant releaseDate;

    private Long duration;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    public Song(String name, String artist) {
        this.artist = artist;
        this.name = name;
    }

}
