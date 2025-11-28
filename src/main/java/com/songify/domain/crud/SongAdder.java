package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongResponseDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
class SongAdder {

    private final SongRepository songRepository;
    private final GenreAssigner genreAssigner;


    SongResponseDto addSong(final SongRequestDto songDto) {
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        Song songToSave = new Song(
                songDto.title(),
                songDto.releaseDate(),
                songDto.durationInSeconds(),
                songLanguage
        );
        Song save = songRepository.save(songToSave);
        genreAssigner.assignGenre(songDto.genreId(), save.getId());
        return SongResponseDto.builder()
                .name(save.getName())
                .id(save.getId())
                .genreName(save.getGenre().getName())
                .build();

    }
}
