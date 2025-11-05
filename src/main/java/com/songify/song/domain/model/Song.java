package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Data
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "artist", nullable = false)
    String artist;

    public Song(String name, String artist) {
        this.artist = artist;
        this.name = name;
    }

    public Song() {

    }

    public Song(Long id, String name, String artist) {
        this.id = id;
        this.artist = artist;
        this.name = name;
    }
}
