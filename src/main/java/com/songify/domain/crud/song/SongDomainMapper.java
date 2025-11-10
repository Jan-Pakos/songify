package com.songify.domain.crud.song;

import com.songify.infrastructure.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.dto.response.SongControllerResponseDto;

class SongDomainMapper {

    public static SongControllerResponseDto mapFromSongToSongDto(Song song) {
        return new SongControllerResponseDto(song.getId(), song.getName(), song.getArtist());
    }

    public static Song mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new Song(dto.name(), dto.artist());
    }

    public static Song mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto dto) {
        return new Song(dto.name(), dto.artist());
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new Song(dto.name(), dto.artist());
    }
}
