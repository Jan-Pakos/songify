package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SongRepositoryInMemory implements SongRepository {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("Song1", "Artist1"),
            2, new Song("Song2", "Artist2"),
            3, new Song("Song3", "Artist3"),
            4, new Song("Song4", "Artist4")
    ));

    @Override
    public Song save(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }

    @Override
    public List<Song> findAll() {
        return database.values().stream().toList();
    }
}
