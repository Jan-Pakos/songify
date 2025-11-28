package com.songify.infrastructure.crud.song.controller.dto.response;

import com.songify.domain.crud.dto.SongResponseDto;

public record PartiallyUpdateSongResponseDto(SongResponseDto updatedSong) {
}
