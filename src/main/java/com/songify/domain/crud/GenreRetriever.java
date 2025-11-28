package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class GenreRetriever {

    private final GenreRepository genreRepository;

    Set<GenreDto> getAllGenres(Pageable pageable) {
        Set<Genre> allGenres = genreRepository.findAll(pageable);
        Set<GenreDto> allGenresDto = allGenres.stream().map(
                genre -> new GenreDto(genre.getName(), genre.getId())
        ).collect(Collectors.toSet());
        return allGenresDto;
    }

    public Genre findGenreById(Long genreId) {
        return genreRepository
                .findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id: " + genreId + " not found" ));
    }
}
