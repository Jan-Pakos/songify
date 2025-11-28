package com.songify.domain.crud;


import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

interface GenreRepository extends Repository<Genre, Long> {

    Genre save(Genre genre);

    void deleteById(Long id);

    Set<Genre> findAll(Pageable pageable);

    Optional<Genre> findById(Long id);
}
