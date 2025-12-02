package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.dto.AlbumDto;

import java.util.Set;

record AllAlbumsDto(
        Set<AlbumDto> albums
) {
}
