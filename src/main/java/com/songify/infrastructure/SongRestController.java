package com.songify.infrastructure;

import com.songify.domain.crud.song.Song;
import com.songify.domain.crud.song.SongCrudFacade;
import com.songify.domain.crud.song.SongRepository;
import com.songify.domain.crud.song.SongAdder;
import com.songify.domain.crud.song.SongDeleter;
import com.songify.domain.crud.song.SongRetriever;
import com.songify.domain.crud.song.SongUpdater;
import com.songify.infrastructure.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.dto.response.*;
import com.songify.song.infrastructure.controller.dto.response.*;
import jakarta.validation.Valid;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.songify.infrastructure.SongMapper.*;
import static com.songify.infrastructure.SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong;
import static com.songify.infrastructure.SongMapper.mapFromSongToCreateSongResponseDto;
import static com.songify.infrastructure.SongMapper.mapFromSongToGetAllSongsResponseDto;
import static com.songify.infrastructure.SongMapper.mapFromSongToPartiallyUpdateSongResponseDto;
import static com.songify.infrastructure.SongMapper.mapFromSongToUpdateSongResponseDto;
import static com.songify.infrastructure.SongMapper.mapFromUpdateSongRequestDtoToSong;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongRestController {
    private final SongRepository songRepository;

    private final SongCrudFacade songCrudFacade;


    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(Pageable pageable) {
        List<Song> allSongs = songCrudFacade.findAll(pageable);
        GetAllSongsResponseDto response = mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        Song song = songCrudFacade.findById(id);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        Song song = mapFromCreateSongRequestDtoToSong(request);
        Song savedSong = songCrudFacade.addSong(song);
        CreateSongResponseDto body = mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songCrudFacade.deleteById(id);
        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateSongRequestDto request) {
        Song newSong = mapFromUpdateSongRequestDtoToSong(request);
        songCrudFacade.updateById(id, newSong);
        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request) {
        Song requestSong = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        Song savedSong = songCrudFacade.updatePartiallyById(id, requestSong);
        PartiallyUpdateSongResponseDto body = mapFromSongToPartiallyUpdateSongResponseDto(savedSong);
        return ResponseEntity.ok(body);

    }
}
