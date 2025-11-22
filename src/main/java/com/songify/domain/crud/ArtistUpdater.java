package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistUpdater {

    private final ArtistRepository artistRepository;

    ArtistDto updateArtistNameById(final Long id, final String newName) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist with id " + id + " not found"));
        artist.setName(newName);
        return new ArtistDto(artist.getName(), artist.getId());

    }
}
