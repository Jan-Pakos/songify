package com.songify.infrastructure.usercrud.controller;

record RegisterUserRequestDto(
        String userName,
        String password
) {
}
