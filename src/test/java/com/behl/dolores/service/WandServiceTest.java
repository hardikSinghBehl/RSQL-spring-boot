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
        final var result = wandService.retreive(null);

        assertEquals(1, result.size());
        assertEquals(CORE, result.get(0).getCore());
        assertEquals(WOOD, result.get(0).getWood());
        assertEquals(LENGTH, result.get(0).getLength());
    }

    @Test
    void retreiveWithSingleEqualCondition_matching() {
        final var result = wandService.retreive("wood==" + WOOD);

        assertEquals(1, result.size());
        assertEquals(CORE, result.get(0).getCore());
        assertEquals(WOOD, result.get(0).getWood());
        assertEquals(LENGTH, result.get(0).getLength());
    }

    @Test
    void retreiveWithSingleEqualCondition_notMatching() {
        final var result = wandService.retreive("wood==" + RandomString.make(4));

        assertEquals(0, result.size());
    }

    @Test
    void retreiveWithSingleNotEqualCondition_matching() {
        final var result = wandService.retreive("wood!=" + RandomString.make(4));

        assertEquals(1, result.size());
        assertEquals(CORE, result.get(0).getCore());
        assertEquals(WOOD, result.get(0).getWood());
        assertEquals(LENGTH, result.get(0).getLength());
    }

    @Test
    void retreiveWithANDConditions_matching() {
        final var result = wandService.retreive("wood==" + WOOD + ";length>" + (LENGTH - 1));

        assertEquals(1, result.size());
        assertEquals(CORE, result.get(0).getCore());
        assertEquals(WOOD, result.get(0).getWood());
        assertEquals(LENGTH, result.get(0).getLength());
    }

    @Test
    void retreiveWithANDConditions_notMatching() {
        final var result = wandService.retreive("wood==" + RandomString.make(4) + ";core==" + CORE);

        assertEquals(0, result.size());
    }

    @Test
    void retreiveWithORConditions_matching() {
        final var result = wandService.retreive("wood==" + WOOD + ",length>" + (LENGTH + 100));

        assertEquals(1, result.size());
        assertEquals(CORE, result.get(0).getCore());
        assertEquals(WOOD, result.get(0).getWood());
        assertEquals(LENGTH, result.get(0).getLength());
    }

    @Test
    void retreiveWithORConditions_notMatching() {
        final var result = wandService.retreive("wood==" + RandomString.make(4) + ",core==" + RandomString.make(4));

        assertEquals(0, result.size());
    }

    @Test
    void retreiveWithEqualIgnoreCaseCondition_matching() {
        final var result = wandService.retreive("wood=eic=" + WOOD.toUpperCase());

        assertEquals(1, result.size());
        assertEquals(CORE, result.get(0).getCore());
        assertEquals(WOOD, result.get(0).getWood());
        assertEquals(LENGTH, result.get(0).getLength());
    }

    @Test
    void retreiveWithNotEqualIgnoreCaseCondition_matching() {
        final var result = wandService.retreive("wood=neic=" + WOOD.toUpperCase());

        assertEquals(0, result.size());
    }

}
