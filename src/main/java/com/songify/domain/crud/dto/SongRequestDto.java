package com.songify.domain.crud.dto;


import java.time.Instant;

public record SongRequestDto(
        String title,
        Instant releaseDate,
        Long durationInSeconds,
        SongLanguageDto language
) {
}
