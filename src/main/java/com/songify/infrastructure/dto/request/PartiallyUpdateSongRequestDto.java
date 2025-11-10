package com.songify.infrastructure.dto.request;

public record PartiallyUpdateSongRequestDto(
        String name,
        String artist
) {
}
