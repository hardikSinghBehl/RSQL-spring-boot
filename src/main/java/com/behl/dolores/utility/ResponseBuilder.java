package com.behl.dolores.utility;

import org.springframework.data.domain.Page;

import com.behl.dolores.dto.SearchResponseDto;

public class ResponseBuilder {

    public static SearchResponseDto build(final Page<?> result) {
        return SearchResponseDto.builder().result(result.getContent()).count(result.getNumberOfElements())
                .currentPage(result.getNumber() + 1).totalPages(result.getTotalPages()).build();
    }

}
