package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Log4j2
class ArtistDeleter {

    private final ArtistRepository artistRepository;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final SongDeleter songDeleter;
    private final AlbumDeleter albumDeleter;


    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
        Artist artist = artistRetriever.findById(artistId);
        Set<Album> albumsByArtistId = albumRetriever.findAlbumsByArtistId(artist.getId());
        if (albumsByArtistId.isEmpty()) {
            log.info("Artist with id {} has no albums. Deleting artist only.", artistId);
            artistRepository.deleteById(artistId);
        }

        albumsByArtistId.stream()
                .filter(album -> album.getArtists().size() > 1)
                .forEach(album -> album.removeArtist(artist));

        Set<Album> albumsWithOneArtist = albumsByArtistId.stream()
                .filter(album -> album.getArtists().size() == 1)
                .collect(Collectors.toSet());

        Set<Long> allSongsFromAlbumsWithOnlyThisArtist =  albumsWithOneArtist
                .stream()
                .flatMap(album -> album.getSongs().stream())
                .map(Song::getId)
                .collect(Collectors.toSet());

        songDeleter.deleteAllSongsById(allSongsFromAlbumsWithOnlyThisArtist);
        Set<Long> albumIdsToDelete = albumsWithOneArtist.stream().map(Album::getId).collect(Collectors.toSet());
        albumDeleter.deleteAllAlbumsByIds(albumIdsToDelete);
        artistRepository.deleteById(artistId);

    }
}
