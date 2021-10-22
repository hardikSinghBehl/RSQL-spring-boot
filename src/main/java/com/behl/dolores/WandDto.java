package com.behl.dolores;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class WandDto {

    private final String wood;
    private final String core;
    private final Double length;

}
