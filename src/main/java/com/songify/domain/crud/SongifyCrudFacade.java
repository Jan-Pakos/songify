package com.songify.domain.crud;

import com.songify.domain.crud.dto.*;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class SongifyCrudFacade {

    private final SongRetriever songRetriever;
    private final SongUpdater songUpdater;
    private final SongDeleter songDeleter;
    private final SongAdder songAdder;
    private final ArtistAdder artistAdder;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;
    private final GenreRetriever genreRetriever;


    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }

    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto dto) {
        return albumAdder.addAlbumWithSong(dto.songIds(), dto.title(), dto.releaseDate());
    }

    public void addArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);

    }

    public ArtistDto updateArtistNameById(Long id, String name) {
        return artistUpdater.updateArtistNameById(id, name);
    }

    public SongResponseDto addSong(final SongRequestDto dto) {
        return songAdder.addSong(dto);

    }

    public AlbumInfo findAlbumByIdWithArtistsAndSongs(Long id) {
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(id);
    }

    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistRetriever.findAllArtists(pageable);
    }

    public List<SongResponseDto> findAllSongs(Pageable pageable) {
        return songRetriever.findAll(pageable)
                .stream()
                .map(song -> SongResponseDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .build())
                .toList();
    }

    public void updateSongById(Long id, SongResponseDto newSongDto) {
        songRetriever.existsById(id);
        // some domain validator
        Song songValidatedAndReadyToUpdate = new Song(newSongDto.name());
        // some domain validator ended checking
        songUpdater.updateById(id, songValidatedAndReadyToUpdate);
    }

    public SongResponseDto partiallyUpdateSongById(Long id, PartiallyUpdateSongRequestDto songFromRequest) {
        Genre genreById = genreRetriever.findGenreById(songFromRequest.genreId());
        Song toSave = Song.builder()
                .name(songFromRequest.name())
                .genre(genreById)
                .build();
        songUpdater.updateById(id, toSave);
        return SongResponseDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .genreName(toSave.getGenre().getName())
                .build();

    }

    public SongResponseDto deleteSongById(Long id) {
        return songDeleter.deleteById(id);

    }

    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
        artistDeleter.deleteArtistByIdWithAlbumsAndSongs(artistId);
    }

    public SongResponseDto findSongDtoById(Long id) {
        Song song = songRetriever.findSongById(id);
        return SongResponseDto.builder()
                .id(song.getId())
                .name(song.getName())
                .build();
    }

    public Set<AlbumDto> findAllArtistAlbumsById(Long artistId) {
        return albumRetriever.findAlbumsDtoByArtistId(artistId);
    }

    public int countArtistByAlbumId(Long albumId) {
        return albumRetriever.countArtistsByAlbumId(albumId);
    }

    public Set<AlbumDto> findAllAlbums(Pageable pageable) {
        return albumRetriever.findAllAlbums(pageable);
    }

    public AlbumDto findAlbumById(Long albumId) {
        return albumRetriever.findAlbumDtoById(albumId);
    }

    public Set<GenreDto> getAllGenres(Pageable pageable) {
        return genreRetriever.getAllGenres(pageable);
    }
}
