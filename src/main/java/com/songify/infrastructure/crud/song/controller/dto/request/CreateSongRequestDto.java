package com.songify.infrastructure.crud.song.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateSongRequestDto(
//        @NotNull(message = "name must not be null")
        @NotEmpty(message = "name must not be empty or null")
        String name,

//        @NotNull(message = "artist must not be null")
        @NotEmpty(message = "artist must not be empty or null")
        String artist
) {
}
