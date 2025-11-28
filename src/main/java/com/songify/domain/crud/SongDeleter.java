package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongDeleter {


    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    private final GenreDeleter genreDeleter;


    SongResponseDto deleteById(Long id) {
        Song songDtoById = songRetriever.findSongById(id);
        Long genreId = songDtoById.getGenre().getId();
        SongResponseDto songDto = SongResponseDto.builder()
                .id(songDtoById.getId())
                .name(songDtoById.getName())
                .build();
        songRepository.deleteById(id);
        genreDeleter.deleteById(genreId);
        log.info("deleted song with id: " + id);
        return songDto;
    }

    void deleteAllSongsById(final Set<Long> songsIds) {
        songRepository.deleteByIdIn(songsIds);
    }
}
