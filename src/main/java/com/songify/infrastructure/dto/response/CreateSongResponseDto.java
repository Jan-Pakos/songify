package com.songify.infrastructure.dto.response;


import com.songify.domain.crud.dto.SongDto;

public record CreateSongResponseDto(SongDto song) {
}
