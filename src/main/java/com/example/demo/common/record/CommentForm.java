package com.example.demo.common.record;

import jakarta.validation.constraints.NotBlank;

public record CommentForm(
        @NotBlank
        String content

) {
}