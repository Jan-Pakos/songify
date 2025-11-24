package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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
    }

    @Test
    @DisplayName("Should return Set of size:2 When 'pitbull' and 'eminem' are added")
    public void should_return_set_of_size_2_when_pitbull_and_eminem_are_added() {
        // given
        ArtistRequestDto justingBieber = ArtistRequestDto.builder().name("Pitbull").build();
        songifyCrudFacade.addArtist(justingBieber);
        ArtistRequestDto taylorSwift = ArtistRequestDto.builder().name("Eminem").build();
        songifyCrudFacade.addArtist(taylorSwift);
        // when
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        // then
        assertEquals(2, allArtists.size());
    }


  
}