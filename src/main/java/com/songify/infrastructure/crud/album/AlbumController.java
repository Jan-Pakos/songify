package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<AlbumInfo> getAlbumById(@PathVariable Long albumId) {
        AlbumInfo albumDto = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumDto);
    }
}
