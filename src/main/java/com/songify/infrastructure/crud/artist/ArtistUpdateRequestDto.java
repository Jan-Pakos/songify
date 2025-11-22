package com.songify.infrastructure.crud.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ArtistUpdateRequestDto(
        @NotEmpty(message = "Artist name must not be empty")
        @NotNull(message = "Artist name must not be null")
        String newName
) {
}
