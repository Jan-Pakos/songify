package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import org.springframework.data.repository.Repository;

interface GenreRepository extends Repository<Genre, Long> {

    Genre save(Genre genre);

    void deleteById(Long id);
}
