package com.songify.infrastructure;

import com.songify.domain.crud.song.Song;
import com.songify.infrastructure.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.dto.response.*;
import com.songify.song.infrastructure.controller.dto.response.*;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
public class SongMapper {

    public static SongDto mapFromSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
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

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        SongDto songDto = mapFromSongToSongDto(song);
        return new CreateSongResponseDto(songDto);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        return new UpdateSongResponseDto(newSong.getName(), newSong.getArtist());
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(Song updatedSong) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(updatedSong);
        return new PartiallyUpdateSongResponseDto(songDto);
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(Song song) {
        SongDto songDto = mapFromSongToSongDto(song);
        return new GetSongResponseDto(songDto);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<Song> songs) {
        List<SongDto> songDtos = songs.stream().map(
                SongMapper::mapFromSongToSongDto).toList();
        return new GetAllSongsResponseDto(songDtos);
    }
}
