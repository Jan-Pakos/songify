package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final SongifyCrudFacade songifyCrudFacade;

    @PostMapping
    ResponseEntity<AlbumDto> postAlbum(@RequestBody AlbumRequestDto albumRequestDto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(albumRequestDto);
        return ResponseEntity.ok(albumDto);
    }

    @GetMapping("/{albumId}")
    ResponseEntity<AlbumResponseDto> getAlbumById(@PathVariable Long albumId) {
        AlbumResponseDto albumDto = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumDto);
    }

    @GetMapping
    ResponseEntity<AllAlbumsDto> getAllAlbums(Pageable pageable) {
        Set<AlbumDto> allAlbums = songifyCrudFacade.getAllAlbums(pageable);
        AllAlbumsDto allAlbumsDto = new AllAlbumsDto(allAlbums);
        return ResponseEntity.ok(allAlbumsDto);
    }
}
