package com.songify.domain.crud.song;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
class SongRetriever {

    private final SongRepository songRepository;

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    List<Song> findAll(Pageable pageable) {
        log.info("retrieving all songs: " + songRepository.findAll(pageable));
        return songRepository.findAll(pageable);
    }


    Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(
                () -> new SongNotFoundException("Song with id " + id + " not found")
        );
    }


}
