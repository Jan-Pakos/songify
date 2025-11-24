package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor



class ArtistRetriever {

    private final ArtistRepository artistRepository;

    Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistRepository.findAll(pageable)
                .stream()
                .map(artist -> new ArtistDto(
                        artist.getName(),
                        artist.getId()))
                .collect(Collectors.toSet());
    }

    Artist findById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundException("Artist with id " + artistId + " not found"));
    }
}
