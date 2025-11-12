package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
@Transactional
class SongAdder {

    private final SongRepository songRepository;

    public SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    Song addSong(final Song song) {
        song.setDuration(200L);
        song.setReleaseDate(Instant.now());
        songRepository.save(song);
        return song;
    }
}
