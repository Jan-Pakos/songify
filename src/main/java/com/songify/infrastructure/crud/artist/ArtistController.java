package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity
                .created(java.net.URI.create("/artists/" + artistDto.id()))
                .body(artistDto);
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

    @PutMapping("/{artistId}/{albumId}")
    ResponseEntity<String> deleteArtistById(@PathVariable Long artistId, @PathVariable Long albumId) {
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        return ResponseEntity.ok("Artist has been assigned to album successfully.");
    }

    @PatchMapping("/{artistId}")
    ResponseEntity<String> deleteArtistById(@PathVariable Long artistId, @Valid @RequestBody ArtistUpdateRequestDto artistUpdateRequestDto) {
        songifyCrudFacade.updateArtistNameById(artistId, artistUpdateRequestDto.newName());
        return ResponseEntity.ok("Artist name updated successfully.");
    }


}
