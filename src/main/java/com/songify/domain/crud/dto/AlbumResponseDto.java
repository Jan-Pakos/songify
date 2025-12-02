package com.songify.domain.crud.dto;

import java.time.Instant;
import java.util.Set;

public record AlbumResponseDto(
        Long id,
        String title,
        Instant releaseDate,
        Set<SongDto> songs,
        Set<ArtistDto> artists
) {

    public record SongDto(
            Long id,
            String name,
            Long duration,
            GenreDto genre
    ) {}

    public record GenreDto(
            String name
    ) {}

    public record ArtistDto(
            Long id,
            String name
    ) {}
}

