package com.songify.domain.crud.song;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongCrudFacade {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    public SongCrudFacade(final SongAdder songAdder, final SongRetriever songRetriever, final SongDeleter songDeleter, final SongUpdater songUpdater) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songDeleter = songDeleter;
        this.songUpdater = songUpdater;
    }

    public List<Song> findAll(final Pageable pageable) {
        return songRetriever.findAll(pageable);
    }
}
