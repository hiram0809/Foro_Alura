package com.alura.forokev.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicoRequest(
        @NotBlank String titulo,
        @NotBlank String mensaje
) {
}