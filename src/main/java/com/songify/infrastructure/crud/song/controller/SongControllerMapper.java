package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.SongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.*;


import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;


@Log4j2
public class SongControllerMapper {


    static SongResponseDto mapFromUpdateSongRequestDtoToSongDto(UpdateSongRequestDto dto) {
        return SongResponseDto
                .builder()
                .name(dto.name())
                .build();
    }

    static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongResponseDto songDto) {
        return new CreateSongResponseDto(songDto);
    }

    static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id, String name) {
        return new DeleteSongResponseDto("You deleted song with id: " + id + " and name: " + name , HttpStatus.OK);
    }

    static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongResponseDto newSong) {
        return new UpdateSongResponseDto(newSong.name(), "test");
    }

    static PartiallyUpdateSongResponseDto mapFromSongDtoToPartiallyUpdateSongResponseDto(SongResponseDto songDto) {
        return new PartiallyUpdateSongResponseDto(songDto);
    }

    static GetSongResponseDto mapFromSongToGetSongResponseDto(SongResponseDto songDto) {
        return new GetSongResponseDto(songDto);
    }

    static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<SongResponseDto> songs) {
        return new GetAllSongsResponseDto(songs);
    }
}
