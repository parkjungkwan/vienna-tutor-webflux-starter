package com.example.demo.common.record;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostCommand(
        @NotBlank String title,
        @NotBlank String content
) {
}