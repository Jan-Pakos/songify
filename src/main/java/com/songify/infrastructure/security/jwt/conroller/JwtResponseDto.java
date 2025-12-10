package com.songify.infrastructure.security.jwt.conroller;

import lombok.Builder;

@Builder
record JwtResponseDto(
        String token
) {
}
