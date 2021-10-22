package com.behl.dolores;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class WizardDto {

    private final String name;
    private final String species;
    private final String gender;
    private final String house;
    private final LocalDate dateOfBirth;
    private final String ancestry;
    private final String eyeColour;
    private final String hairColour;
    private final String patronus;
    private final Boolean hogwartsStaff;
    private final Boolean alive;
    private final String image;
    private final WandDto wand;

}
