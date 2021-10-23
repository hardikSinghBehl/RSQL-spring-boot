package com.behl.dolores.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        final var result = wandService.retreive(null, null, null);

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithSingleEqualCondition_matching() {
        final var result = wandService.retreive("wood==" + WOOD, null, null);

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithSingleEqualCondition_notMatching() {
        final var result = wandService.retreive("wood==" + RandomString.make(4), null, null);

        assertEquals(0, result.getResult().size());
    }

    @Test
    void retreiveWithSingleNotEqualCondition_matching() {
        final var result = wandService.retreive("wood!=" + RandomString.make(4), null, null);

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithANDConditions_matching() {
        final var result = wandService.retreive("wood==" + WOOD + ";length>" + (LENGTH - 1), null, null);

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithANDConditions_notMatching() {
        final var result = wandService.retreive("wood==" + RandomString.make(4) + ";core==" + CORE, null, null);

        assertEquals(0, result.getResult().size());
    }

    @Test
    void retreiveWithORConditions_matching() {
        final var result = wandService.retreive("wood==" + WOOD + ",length>" + (LENGTH + 100), null, null);

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithORConditions_notMatching() {
        final var result = wandService.retreive("wood==" + RandomString.make(4) + ",core==" + RandomString.make(4),
                null, null);

        assertEquals(0, result.getResult().size());
    }

    @Test
    void retreiveWithEqualIgnoreCaseCondition_matching() {
        final var result = wandService.retreive("wood=eic=" + WOOD.toUpperCase(), null, null);

        assertEquals(1, result.getResult().size());
        final Wand wand = (Wand) result.getResult().get(0);
        assertEquals(CORE, wand.getCore());
        assertEquals(WOOD, wand.getWood());
        assertEquals(LENGTH, wand.getLength());
    }

    @Test
    void retreiveWithNotEqualIgnoreCaseCondition_matching() {
        final var result = wandService.retreive("wood=neic=" + WOOD.toUpperCase(), null, null);

        assertEquals(0, result.getResult().size());
    }

}
