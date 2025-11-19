package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;


    ArtistDto addArtist(String name) {
        Artist artist = new Artist(name);
        Artist artist1 = artistRepository.save(artist);
        return new ArtistDto(artist1.getName(), artist1.getId());
    }
}
