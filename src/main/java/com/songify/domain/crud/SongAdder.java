package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
class SongAdder {

    private final SongRepository songRepository;

    public SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    SongDto addSong(final SongRequestDto songDto) {
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        Song songToSave = new Song(
                songDto.title(),
                songDto.releaseDate(),
                songDto.durationInSeconds(),
                songLanguage
        );
        Song save = songRepository.save(songToSave);
        return SongDto.builder()
                .name(save.getName())
                .id(save.getId())
                .genreId(save.getGenre().getId())
                .build();

    }
}
