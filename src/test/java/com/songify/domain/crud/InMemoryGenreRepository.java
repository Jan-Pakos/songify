package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryGenreRepository implements GenreRepository {

    Map<Long, Genre> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Genre save(Genre genre) {
        long index = this.index.getAndIncrement();
        db.put(index, genre);
        genre.setId(index);
        return genre;
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }

    @Override
    public Set<Genre> findAll(Pageable pageable) {
        return new HashSet<>(db.values());
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }
}
