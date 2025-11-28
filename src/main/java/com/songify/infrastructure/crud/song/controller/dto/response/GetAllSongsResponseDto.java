package com.songify.infrastructure.crud.song.controller.dto.response;

import com.songify.domain.crud.dto.SongResponseDto;

import java.util.List;

public record GetAllSongsResponseDto(List<SongResponseDto> songs) {
}
