package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
@AllArgsConstructor
class SongUpdater {

    private final SongRetriever songRetriever;
    private final SongRepository songRepository;
    private final SongAdder songAdder;

    void updateById(Long id, @Valid Song request) {
        Song songById = songRetriever.findSongById(id);
        songById.setName(request.getName());
        log.info("updated song with id: " + id + " to new values" + request);
    }

    Song updatePartiallyById(Long id, Song songFromRequest) {
        Song songFromDataBase = songRetriever.findSongById(id);
        if (songFromRequest.getName() != null) {
            songFromDataBase.setName(songFromRequest.getName());
        }
        return songFromDataBase;
    }
}
