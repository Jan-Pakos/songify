package com.songify.infrastructure.dto.response;


import com.songify.domain.crud.song.dto.SongDto;

public record CreateSongResponseDto(SongDto song) {
}
