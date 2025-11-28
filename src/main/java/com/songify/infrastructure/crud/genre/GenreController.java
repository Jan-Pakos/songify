package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/genres")
public class GenreController {

    private final SongifyCrudFacade songifyCrudFacade;

    @PostMapping
    ResponseEntity<GenreDto> postGenre(@RequestBody GenreRequestDto genreRequestDto) {
        GenreDto genreDto = songifyCrudFacade.addGenre(genreRequestDto);
        return ResponseEntity.ok(genreDto);
    }

    @GetMapping
    ResponseEntity<AllGenresDto> getGenre(Pageable pageable) {
        Set<GenreDto> allGenres = songifyCrudFacade.getAllGenres(pageable);
        AllGenresDto genreDtos = new AllGenresDto(allGenres);
        return ResponseEntity.ok(genreDtos);
    }
}
