package com.songify.domain.crud.song;

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
        Song songById = songRetriever.findSongDtoById(id);
        songById.setName(request.getName());
        songById.setArtist(request.getArtist());
        log.info("updated song with id: " + id + " to new values" + request);
    }


    Song updatePartiallyById(Long id, Song songFromRequest) {
        Song songFromDataBase = songRetriever.findSongDtoById(id);
        if (songFromRequest.getName() != null) {
            songFromDataBase.setName(songFromRequest.getName());
        }
        if (songFromRequest.getArtist() != null) {
            songFromDataBase.setArtist(songFromRequest.getArtist());
        }
        return songFromDataBase;
    }
}
