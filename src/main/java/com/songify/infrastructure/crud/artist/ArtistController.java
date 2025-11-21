package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/artists")
public class ArtistController {

    private final SongifyCrudFacade songifyCrudFacade;

    @PostMapping
    ResponseEntity<ArtistDto> postArtist(@RequestBody ArtistRequestDto artistRequestDto) {
        ArtistDto artistDto = songifyCrudFacade.addArtist(artistRequestDto);
        return ResponseEntity.ok(artistDto);
    }

    @GetMapping
    ResponseEntity<AllArtistsDto> findAllArtists(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Set<ArtistDto> setOfAllArtists = songifyCrudFacade.findAllArtists(pageable);
        AllArtistsDto allArtistsDto = new AllArtistsDto(setOfAllArtists);
        return ResponseEntity.ok(allArtistsDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteArtistById(@PathVariable Long id) {
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(id);
        return ResponseEntity.ok("Artist has been deleted successfully.");
    }
}
