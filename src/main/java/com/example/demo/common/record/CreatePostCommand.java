package com.example.demo.common.record;

import jakarta.validation.constraints.NotBlank;

public record CreatePostCommand(
        @NotBlank String title,
        @NotBlank String content
) {
}