package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
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
    public void should_add_return_justin_bieber_when_() {
        // given
        ArtistRequestDto justingBieber = ArtistRequestDto.builder().name("Pitbull").build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        // when
        ArtistDto artistDto = songifyCrudFacade.addArtist(justingBieber);
        // then
        assertThat(artistDto.name()).isEqualTo("Pitbull");
        assertThat(artistDto.id()).isEqualTo(0L);
    }

    @Test
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