package com.songify.domain.crud;

import com.songify.domain.crud.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    }

    Set<Album> findAlbumsByArtistId(final Long id) {
        return albumRepository.findAllAlbumsByArtistId(id);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long id) {
        return albumRepository.findAllAlbumsByArtistId(id).stream().map(
                album -> new AlbumDto(
                        album.getTitle(),
                        album.getId()
                )
        ).collect(Collectors.toSet()
        );
    }

    int countArtistsByAlbumId(Long albumId) {
        return findById(albumId).getArtists().size();
    }

    public Set<AlbumDto> findAllAlbums(Pageable pageable) {
        return albumRepository.findAll(pageable).stream()
                .map(album -> new AlbumDto(album.getTitle(), album.getId()))
                .collect(Collectors.toSet());
    }

    public AlbumDto findAlbumDtoById(Long albumId) {
        Album album = findById(albumId);
        return new AlbumDto(album.getTitle(), album.getId());
    }

    public Album findById(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id " + albumId + " not found"));
    }
}
