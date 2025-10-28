package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "artist", nullable = false)
    String artist;

    @Column(name = "name", nullable = false)
    String name;

    public Song(String artist, String name) {
        this.artist = artist;
        this.name = name;
    }

    public Song() {

    }

    public Song(Long id, String artist, String name) {
        this.id = id;
        this.artist = artist;
        this.name = name;
    }
}
