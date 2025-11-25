package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


class InMemorySongRepository implements SongRepository {


    Map<Long, Song> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Song save(Song song) {
        long index = this.index.getAndIncrement();
        db.put(index, song);
        song.setId(index);
        song.setGenre(new Genre("Default Genre"));
        return song;
    }

    @Override
    public List<Song> findAll(Pageable pageable) {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<Song> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }

    @Override
    public void updateById(Long id, Song song) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void deleteByIdIn(Collection<Long> ids) {
        ids.forEach(id -> db.remove(id));
    }
}
