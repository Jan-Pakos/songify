package com.songify.infrastructure.security.jwt.conroller;

record TokenRequestDto(
        String username,
        String password
) {
}
