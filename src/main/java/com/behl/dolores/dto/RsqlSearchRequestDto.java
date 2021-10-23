package com.behl.dolores.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RsqlSearchRequestDto {

    private final String query;
    private final Integer count;
    private final Integer page;
    private final String sort;

}
