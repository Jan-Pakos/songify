package com.songify.domain.crud;

import com.songify.domain.crud.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDateTime;
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
        ArtistRequestDto pitbullArtist = ArtistRequestDto.builder().name("pitbull").build();
        Long artistId = songifyCrudFacade.addArtist(pitbullArtist).id();
        SongDto songDto = songifyCrudFacade.addSong(SongRequestDto.builder()
                .title("Song 1")
                .releaseDate(Instant.now())
                .durationInSeconds(100L)
                .language(SongLanguageDto.English).build());
        Long songId = songDto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto.builder()
                .title(songDto.name())
                .releaseDate(Instant.now())
                .songId(songId)
                .build());
        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        assertThat(songifyCrudFacade.findAllArtistAlbumsById(artistId)).size().isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId)).isEqualTo(1);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class).hasMessage("Song with id " + songId + " not found");
    }



    @Test
    @DisplayName("Should delete only artist from album only when there is more than one artist in that album")
    public void should_delete_only_artist_from_album_only_when_there_is_more_than_one_artist_in_that_album() {
        // given
//        AlbumInfo albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
//        assertThat(albumByIdWithArtistsAndSongs.getArtists()).hasSize(1);
        // when

        // then
    }

}