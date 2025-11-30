package com.songify.domain.crud;

import com.songify.domain.crud.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrudFacade(
            new InMemorySongRepository(),
            new InMemoryArtistRepository(),
            new InMemoryGenreRepository(),
            new InMemoryAlbumRepository()
    );

    @Test
    @DisplayName("Should add artist 'pitbull' with id:0 When pitbull is sent")
    public void should_add_artist_pitbull_with_id_zero_when_pitbull_is_sent() {
        // given
        ArtistRequestDto pitbullArtist = ArtistRequestDto.builder().name("pitbull").build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        // when
        ArtistDto artistDto = songifyCrudFacade.addArtist(pitbullArtist);
        // then
        assertThat(artistDto.name()).isEqualTo("pitbull");
        assertThat(artistDto.id()).isEqualTo(0L);
        Set<ArtistDto> allArtistsAfterAddArtist = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtistsAfterAddArtist).hasSize(1);
    }

    @Test
    @DisplayName("Should return Set of size:2 When 'pitbull' and 'eminem' are added")
    public void should_return_set_of_size_2_when_pitbull_and_eminem_are_added() {
        // given
        ArtistRequestDto pitbullArtist = ArtistRequestDto.builder().name("Pitbull").build();
        songifyCrudFacade.addArtist(pitbullArtist);
        ArtistRequestDto eminemArtist = ArtistRequestDto.builder().name("Eminem").build();
        songifyCrudFacade.addArtist(eminemArtist);
        // when
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        // then
        assertEquals(2, allArtists.size());
    }

    @Test
    @DisplayName("Should throw ArtistNotFoundException When artist with id:0 is not found")
    public void should_throw_exception_artist_not_found_when_id_was_zero() {
        // given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));
        // then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class).hasMessage("Artist with id 0 not found");
    }

    @Test
    @DisplayName("Should delete artist by id When they have no albums")
    public void should_delete_artist_by_id_when_they_have_no_albums() {
        // given
        ArtistRequestDto pitbullArtist = ArtistRequestDto.builder().name("pitbull").build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(pitbullArtist);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        Long artistId = artistDto.id();
        assertThat(songifyCrudFacade.findAllArtistAlbumsById(artistId)).isEmpty();
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist with album and songs by id when artist has one album and was the only artist in that album")
    public void should_delete_artist_with_album_and_songs_by_id_when_artist_has_one_album_and_was_the_only_artist_in_that_album() {
        // given
        GenreDto genreDto = songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        Long genreId = genreDto.id();
        ArtistRequestDto pitbullArtist = ArtistRequestDto.builder().name("pitbull").build();
        Long artistId = songifyCrudFacade.addArtist(pitbullArtist).id();
        SongResponseDto song1 = songifyCrudFacade.addSong(SongRequestDto.builder()
                .title("Song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .genreId(genreId)
                .language(SongLanguageDto.English).build());
        SongResponseDto song2 = songifyCrudFacade.addSong(SongRequestDto.builder()
                .title("Song 2")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .genreId(genreId)
                .language(SongLanguageDto.English).build());
        SongResponseDto song3 = songifyCrudFacade.addSong(SongRequestDto.builder()
                .title("Song 4")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .genreId(genreId)
                .language(SongLanguageDto.English).build());
        SongResponseDto song4 = songifyCrudFacade.addSong(SongRequestDto.builder()
                .title("Song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .genreId(genreId)
                .language(SongLanguageDto.English).build());
        Long songId = song1.id();
        Long song2Id = song2.id();
        Long song3Id = song3.id();
        Long song4Id = song4.id();
        AlbumDto album1 = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .title("Album 1")
                .releaseDate(Instant.now())
                .songIds(Set.of(songId, song2Id))
                .build());
        AlbumDto album2 = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .title("Album 2")
                .releaseDate(Instant.now())
                .songIds(Set.of(song3Id, song4Id))
                .build());
        songifyCrudFacade.addArtistToAlbum(artistId, album1.id());
        songifyCrudFacade.addArtistToAlbum(artistId, album2.id());
        assertThat(songifyCrudFacade.findAllArtistAlbumsById(artistId)).size().isEqualTo(2);
        assertThat(songifyCrudFacade.countArtistByAlbumId(album1.id())).isEqualTo(1L);
        assertThat(songifyCrudFacade.countArtistByAlbumId(album2.id())).isEqualTo(1L);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).hasSize(1);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).hasSize(4);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should add album with song")
    public void should_add_album_with_song() {
        // given
        songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        SongResponseDto songDto = songifyCrudFacade.addSong(SongRequestDto.builder()
                .title("Song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .language(SongLanguageDto.English)
                .genreId(0L)
                .build());
        AlbumRequestDto album = AlbumRequestDto.builder()
                .title("Album 1")
                .releaseDate(Instant.now())
                .songIds(Set.of(songDto.id()))
                .build();
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        // when
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        // then
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isNotEmpty();
        AlbumInfo albumWithSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
        Set<AlbumInfo.SongInfo> songs = albumWithSongs.getSongs();
        assertTrue(songs.stream().anyMatch(songInfo -> songInfo.getId().equals(songDto.id())));
    }

    @Test
    @DisplayName("Should add song")
    public void should_add_song() {
        // given
        GenreDto genreDto = songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        Long genreId = genreDto.id();
        SongRequestDto song = SongRequestDto.builder()
                .title("song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .language(SongLanguageDto.English)
                .genreId(genreId)
                .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        // when
        SongResponseDto songDto = songifyCrudFacade.addSong(song);
        // then
        List<SongResponseDto> allSongs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
        assertThat(allSongs)
                .extracting(SongResponseDto::id)
                .containsExactly(songDto.id());
    }

    @Test
    @DisplayName("Should add artist to album")
    public void should_add_artist_to_album() {
        // given
        GenreDto genreDto = songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        Long genreId = genreDto.id();
        ArtistRequestDto artistRequestDto = ArtistRequestDto.builder()
                .name("artist 1")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        SongRequestDto song = SongRequestDto.builder()
                .title("song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .language(SongLanguageDto.English)
                .genreId(genreId)
                .build();
        SongResponseDto songDto = songifyCrudFacade.addSong(song);
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .title("Album 1")
                .releaseDate(Instant.now())
                .songIds(Set.of(songDto.id()))
                .build());
        Set<AlbumDto> allArtistAlbumsByIdBefore = songifyCrudFacade.findAllArtistAlbumsById(artistDto.id());
        assertThat(allArtistAlbumsByIdBefore).isEmpty();
        // when
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
        // then
        Set<AlbumDto> allArtistAlbumsById = songifyCrudFacade.findAllArtistAlbumsById(artistDto.id());
        assertThat(allArtistAlbumsById).isNotEmpty();
        assertThat(allArtistAlbumsById).extracting(AlbumDto::id).containsExactly(albumDto.id());
    }

    @Test
    @DisplayName("Should return album by id")
    public void should_return_album_by_id() {
        // given
        GenreDto genreDto = songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        Long genreId = genreDto.id();
        SongRequestDto song = SongRequestDto.builder()
                .title("song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .language(SongLanguageDto.English)
                .genreId(genreId)
                .build();
        SongResponseDto songDto = songifyCrudFacade.addSong(song);
        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder()
                .title("Album 1")
                .releaseDate(Instant.now())
                .songIds(Set.of(songDto.id()))
                .build();
        AlbumDto albumAddedToDB = songifyCrudFacade.addAlbumWithSong(albumRequestDto);
        // when
        AlbumDto albumFetchedFromDB = songifyCrudFacade.findAlbumById(albumAddedToDB.id());
        // then
        assertThat(albumFetchedFromDB).isEqualTo(albumAddedToDB);

    }

    @Test
    @DisplayName("Should throw exception when album not found by id")
    public void should_throw_exception_when_album_not_found_by_id() {
        // given
        GenreDto genreDto = songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        Long genreId = genreDto.id();
        SongRequestDto song = SongRequestDto.builder()
                .title("song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .language(SongLanguageDto.English)
                .genreId(genreId)
                .build();
        SongResponseDto songDto = songifyCrudFacade.addSong(song);
        AlbumRequestDto albumRequestDto = AlbumRequestDto.builder()
                .title("Album 1")
                .releaseDate(Instant.now())
                .songIds(Set.of(songDto.id()))
                .build();
        // when
        Long albumIdThatDoestntExist = 10L;
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumIdThatDoestntExist));
        // then
        assertThat(throwable).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Album with id " + albumIdThatDoestntExist + " not found");
    }

    @Test
    @DisplayName("Should throw exception when song not found by id")
    public void should_throw_exception_when_song_not_found_by_id() {
        // given
        GenreDto genreDto = songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        Long genreId = genreDto.id();
        SongRequestDto song = SongRequestDto.builder()
                .title("song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .language(SongLanguageDto.English)
                .genreId(genreId)
                .build();
        SongResponseDto songDto = songifyCrudFacade.addSong(song);
        // when
        Long songIdThatDoesntExist = songDto.id() + 10L;
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songIdThatDoesntExist));
        // then
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id " + songIdThatDoesntExist + " not found");
    }

    @Test
    @DisplayName("Should delete only artist from album by id When there are more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_when_there_were_more_than_one_artist_in_album() {
        // given
        GenreDto genreDto = songifyCrudFacade.addGenre(GenreRequestDto.builder().name("Hip Hop").build());
        Long genreId = genreDto.id();
        ArtistDto artist1 = songifyCrudFacade.addArtist(ArtistRequestDto.builder()
                .name("artist 1")
                .build());
        ArtistDto artist2 = songifyCrudFacade.addArtist(ArtistRequestDto.builder()
                .name("artist 2")
                .build());
        SongRequestDto song = SongRequestDto.builder()
                .title("song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .genreId(genreId)
                .language(SongLanguageDto.English)
                .build();
        SongResponseDto songDto = songifyCrudFacade.addSong(song);
        AlbumDto album = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .title("Album 1")
                .releaseDate(Instant.now())
                .songIds(Set.of(songDto.id()))
                .build());
        songifyCrudFacade.addArtistToAlbum(artist1.id(), album.id());
        songifyCrudFacade.addArtistToAlbum(artist2.id(), album.id());
        assertThat(songifyCrudFacade.countArtistByAlbumId(album.id())).isEqualTo(2L);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artist1.id());
        // then
        AlbumInfo albumInfo = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(album.id());
        Set<AlbumInfo.ArtistInfo> artists = albumInfo.getArtists();
        assertThat(artists).extracting("id").containsOnly(artist2.id());
    }

    @Test
    @DisplayName("Should Add Genre With Id Zero When Genre Rap Is Sent")
    public void should_add_genre_with_id_zero_when_genre_rap_is_sent(){
        // given
        GenreRequestDto request = GenreRequestDto.builder().name("Rap").build();
        assertThat(songifyCrudFacade.getAllGenres(Pageable.unpaged())).isEmpty();
        // when
        GenreDto genreDto = songifyCrudFacade.addGenre(request);
        // then
        assertThat(songifyCrudFacade.getAllGenres(Pageable.unpaged())).hasSize(1);
        assertThat(songifyCrudFacade.findGenreById(genreDto.id())).extracting("name").isEqualTo("Rap");
    }

}