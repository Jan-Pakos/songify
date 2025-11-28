package com.songify.domain.crud.dto;

import lombok.Builder;

@Builder
public record SongResponseDto(
        Long id,
        String name,
        String genreName
) {
}
