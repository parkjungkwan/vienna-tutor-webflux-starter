package com.example.demo.common.record;

import java.util.List;

public record PaginatedResult<T>(List<T> data, Long count) {
}
