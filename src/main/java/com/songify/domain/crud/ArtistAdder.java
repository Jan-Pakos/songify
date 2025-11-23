package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;


    ArtistDto addArtist(String name) {
        Artist save = saveArtist(name);
        return new ArtistDto(save.getName(), save.getId());
    }

    ArtistDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto) {
        String artistName = dto.name();
        Artist save = saveWithDefaultAlbumAndSong(artistName);
        return new ArtistDto(save.getName(), save.getId());
    }

    private Artist saveArtist(final String name) {
        Artist artist = new Artist(name);
        return artistRepository.save(artist);
    }

    private Artist saveWithDefaultAlbumAndSong(String artistName) {
        Artist artist = new Artist(artistName);

        Album album = new Album();
        album.setTitle("Default Album" + UUID.randomUUID());
        album.setReleaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        Song song = new Song("default song name" + UUID.randomUUID());

        album.addSong(song);
        artist.setAlbums(Set.of(album));
        return artistRepository.save(artist);
    }
}
