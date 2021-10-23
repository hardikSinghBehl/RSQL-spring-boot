package com.behl.dolores.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class SearchResponseDto {

    private final Integer currentPage;
    private final Integer totalPages;
    private final Integer count;
    private final List<?> result;

}