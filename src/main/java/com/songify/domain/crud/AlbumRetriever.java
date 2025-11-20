package com.songify.domain.crud;

import com.songify.domain.crud.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;


    AlbumInfo findAlbumByIdWithArtistsAndSongs(Long id) {
        return albumRepository.findAlbumByIdWithSongsAndArtists(id).orElseThrow(
                () -> new AlbumNotFoundException("Album with id " + id + " not found")
        );
//        Set<Artist> artists = album.getArtists();
//        Set<Song> songs = album.getSongs();
//
//        AlbumDto albumDto = new AlbumDto(album.getTitle(), album.getId());
//
//        Set<ArtistDto> artistDto = artists.stream().map(artist ->
//                new ArtistDto(artist.getName(), artist.getId())).collect(Collectors.toSet());
//
//        Set<SongDto> songsDto = songs.stream().map(song ->
//                new SongDto(song.getId(), song.getName())).collect(Collectors.toSet());
//
//        return new AlbumDtoWithArtistsAndSongs(albumDto, artistDto, songsDto);
    }
}
