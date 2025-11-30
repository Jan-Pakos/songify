package com.songify.domain.crud.dto;

import lombok.Builder;

@Builder
public record GenreDto(
        String name,
        Long id
) {
}
