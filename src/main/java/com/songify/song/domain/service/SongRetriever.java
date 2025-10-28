package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.domain.repository.SongRepositoryInMemory;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongRetriever {

    private final SongRepository  songRepository;

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> findAll() {
        log.info("retrieving all songs: " +  songRepository.findAll());
        return songRepository.findAll();
    }

    public List<Song> findAllLimitedBy(Integer limit) {
        return songRepository.findAll()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

}
