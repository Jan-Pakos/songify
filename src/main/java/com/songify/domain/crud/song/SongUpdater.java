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
        Song songById = songRetriever.findById(id);
        songById.setName(request.getName());
        songById.setArtist(request.getArtist());
        log.info("updated song with id: " + id + " to new values" + request);
    }

    public void someComplexMethod() {
        songRepository.updateById(1L, new Song("New Name1", "New Artist1"));
        songRepository.updateById(2L, new Song("New Name2", "New Artist2"));
        songRepository.updateById(3L, new Song("New Name3", "New Artist3"));
        Song piesSong = songAdder.addSong(new Song("pies", "pieso"));
        songRepository.updateById(piesSong.getId(), new Song("pies2", "pieso2"));
    }

    Song updatePartiallyById(Long id, Song songFromRequest) {
        Song songFromDataBase = songRetriever.findById(id);
        if (songFromRequest.getName() != null) {
            songFromDataBase.setName(songFromRequest.getName());
        }
        if (songFromRequest.getArtist() != null) {
            songFromDataBase.setArtist(songFromRequest.getArtist());
        }
        return songFromDataBase;
    }
}
