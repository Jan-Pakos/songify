package com.songify.infrastructure.dto.response;

import lombok.Builder;

@Builder
public record SongControllerResponseDto(Long id, String name, String artist) {
}
