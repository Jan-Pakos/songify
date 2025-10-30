package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.infrastructure.controller.SongMapper;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
public class SongUpdater {


    private final SongRetriever songRetriever;

    public SongUpdater(SongRetriever songRetriever) {
        this.songRetriever = songRetriever;
    }


    public void updateById(Long id, @Valid Song request) {
        Song songById = songRetriever.findById(id);
        songById.setName(request.getName());
        songById.setArtist(request.getArtist());
        log.info("updated song with id: " + id + " to new values" + request);
    }

    public Song updatePartiallyById(Long id, Song songFromRequest) {
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
