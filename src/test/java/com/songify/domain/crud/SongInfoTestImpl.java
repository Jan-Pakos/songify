package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;

class SongInfoTestImpl implements AlbumInfo.SongInfo {

    private final Song song;

    SongInfoTestImpl(Song song) {
        this.song = song;
    }

    @Override
    public Long getId() {
        return song.getId();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Long getDuration() {
        return 0L;
    }

    @Override
    public GenreInfo getGenre() {
        return null;
    }
}
