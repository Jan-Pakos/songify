package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

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
    public void addArtistTest() {
        ArtistRequestDto justingBieber = ArtistRequestDto.builder().name("Justin Bieber").build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(justingBieber);
        assertThat(artistDto).isNotNull();
        assertThat(artistDto.id()).isEqualTo(0L);
        assertThat(artistDto.name()).isEqualTo("Justin Bieber");

    }

    @Test
    public void findAllArtistsTest() {
        ArtistRequestDto justingBieber = ArtistRequestDto.builder().name("Justin Bieber").build();
        songifyCrudFacade.addArtist(justingBieber);
        ArtistRequestDto taylorSwift = ArtistRequestDto.builder().name("Taylor Swift").build();
        songifyCrudFacade.addArtist(taylorSwift);

        var allArtists = songifyCrudFacade.findAllArtists(org.springframework.data.domain.Pageable.unpaged());
        assertEquals(2, allArtists.size());
        assertNotNull(allArtists);
    }


  
}