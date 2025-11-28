package com.songify.domain.crud.dto;


import lombok.Builder;

import java.time.Instant;

@Builder
public record SongRequestDto(
        String title,
        Instant releaseDate,
        Long durationInSeconds,
        SongLanguageDto language,
        Long genreId
) {
}
