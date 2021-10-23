package com.behl.dolores.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.entity.Wand;
import com.behl.dolores.repository.WandRepository;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WandServiceTest {

    private static final String CORE = "\"phoenix feather\"";
    private static final String WOOD = "holly";
    private static final Double LENGTH = 11.5;

    private final WandService wandService;
    private final WandRepository wandRepository;

    @Autowired
    public WandServiceTest(final WandService wandService, final WandRepository wandRepository) {
        this.wandService = wandService;
        this.wandRepository = wandRepository;
    }

    @BeforeAll
    void setUp() {
        final var wand = new Wand();
        wand.setCore(CORE);
        wand.setWood(WOOD);
        wand.setLength(LENGTH);
        wandRepository.save(wand);
    }

    @Test
    void retreiveAllWithoutAnyQuery_returnsAllRecords() {
        final var result = wandService.retreive(RsqlSearchRequestDto.builder().build());

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithSingleEqualCondition_matching() {
        final var result = wandService.retreive(RsqlSearchRequestDto.builder().query("wood==" + WOOD).build());

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithSingleEqualCondition_notMatching() {
        final var result = wandService
                .retreive(RsqlSearchRequestDto.builder().query("wood==" + RandomString.make(4)).build());

        assertEquals(0, result.getResult().size());
    }

    @Test
    void retreiveWithSingleNotEqualCondition_matching() {
        final var result = wandService
                .retreive(RsqlSearchRequestDto.builder().query("wood!=" + RandomString.make(4)).build());

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithANDConditions_matching() {
        final var result = wandService
                .retreive(RsqlSearchRequestDto.builder().query("wood==" + WOOD + ";length>" + (LENGTH - 1)).build());

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithANDConditions_notMatching() {
        final var result = wandService.retreive(
                RsqlSearchRequestDto.builder().query("wood==" + RandomString.make(4) + ";core==" + CORE).build());

        assertEquals(0, result.getResult().size());
    }

    @Test
    void retreiveWithORConditions_matching() {
        final var result = wandService
                .retreive(RsqlSearchRequestDto.builder().query("wood==" + WOOD + ",length>" + (LENGTH + 100)).build());

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithORConditions_notMatching() {
        final var result = wandService.retreive(RsqlSearchRequestDto.builder()
                .query("wood==" + RandomString.make(4) + ",core==" + RandomString.make(4)).build());

        assertEquals(0, result.getResult().size());
    }

    @Test
    void retreiveWithEqualIgnoreCaseCondition_matching() {
        final var result = wandService
                .retreive(RsqlSearchRequestDto.builder().query("wood=eic=" + WOOD.toUpperCase()).build());

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithNotEqualIgnoreCaseCondition_matching() {
        final var result = wandService
                .retreive(RsqlSearchRequestDto.builder().query("wood=neic=" + WOOD.toUpperCase()).build());

        assertEquals(0, result.getResult().size());
    }

}
