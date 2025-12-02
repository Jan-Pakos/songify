package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongResponseDto;
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
    private final GenreRetriever genreRetriever;


    SongResponseDto addSong(final SongRequestDto songDto) {
        Genre genre = genreRetriever.findGenreById(songDto.genreId());
        SongLanguage songLanguage = SongLanguage.valueOf(songDto.language().name());

        Song songToSave = new Song(
                songDto.title(),
                songDto.releaseDate(),
                songDto.durationInSeconds(),
                songLanguage,
                genre
        );
        Song savedSong = songRepository.save(songToSave);

        return SongResponseDto.builder()
                .name(savedSong.getName())
                .id(savedSong.getId())
                .genreName(savedSong.getGenre().getName())
                .build();
    }
}
