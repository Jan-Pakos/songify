package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
class AlbumAdder {

    private final SongRetriever songRetriever;
    private final AlbumRepository albumRepository;

    AlbumDto addAlbumWithSong(final Set<Long> songIds, final String title, final Instant releaseDate) {
        Set<Song> songs = songIds.stream().map(songRetriever::findSongById).collect(Collectors.toSet());
        Album album = new Album();
        album.setTitle(title);
        album.addSongs(songs);
        album.setReleaseDate(releaseDate);
        Album savedAlbum = albumRepository.save(album);
        return new AlbumDto(savedAlbum.getTitle(), savedAlbum.getId());
    }
}
