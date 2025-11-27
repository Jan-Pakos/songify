package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class InMemoryAlbumRepository implements AlbumRepository {

    Map<Long, Album> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);


    @Override
    public Album save(Album album) {
        long index = this.index.getAndIncrement();
        db.put(index, album);
        album.setId(index);
        return album;
    }

    @Override
    public Optional<Album> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Optional<AlbumInfo> findAlbumByIdWithSongsAndArtists(Long id) {
        Album album = db.get(id);
        AlbumInfoTestImpl albumInfoTest = new AlbumInfoTestImpl(album);
        return Optional.of(albumInfoTest);
    }

    @Override
    public Set<Album> findAllAlbumsByArtistId(Long id) {
        return db.values().stream()
                .filter(album -> album.getArtists()
                        .stream().anyMatch(artist -> artist.getId().equals(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIdIn(Collection<Long> ids) {
        ids.forEach(id -> db.remove(id));
    }

    @Override
    public Set<Album> findAll(Pageable pageable) {
        return new HashSet<>(db.values());
    }
}
