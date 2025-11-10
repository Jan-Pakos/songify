package com.songify.infrastructure.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateSongRequestDto(
//        @NotNull(message = "name must not be null")
        @NotEmpty(message = "name must not be empty or null")
        String name,

//        @NotNull(message = "artist must not be null")
        @NotEmpty(message = "artist must not be empty or null")
        String artist
) {
}
