package dolores.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.behl.dolores.RsqlSpringBootApplication;
import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.entity.Wand;
import com.behl.dolores.repository.WandRepository;
import com.behl.dolores.service.WandService;

@SpringBootTest(classes = RsqlSpringBootApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SortingRecordTest {

    private static final List<Double> LENGTH = List.of(14.5, 6.0);
    private static final List<String> WOOD = List.of("Buloke", "Vitae");
    private static final List<String> CORE = List.of("Human scull", "Human spine");

    private final WandService wandService;
    private final WandRepository wandRepository;

    @Autowired
    public SortingRecordTest(WandService wandService, WandRepository wandRepository) {
        this.wandService = wandService;
        this.wandRepository = wandRepository;
    }

    @BeforeAll
    void setUp() {
        final var wands = new ArrayList<Wand>();
        for (int i = 0; i < 2; i++) {
            final var wand = new Wand();
            wand.setCore(CORE.get(i));
            wand.setWood(WOOD.get(i));
            wand.setLength(LENGTH.get(i));
            wands.add(wand);
        }
        wandRepository.saveAll(wands);
    }

    @Test
    void sortByWoodDescending() {
        final String sort = "$wood";
        final var result = wandService.retreive(RsqlSearchRequestDto.builder().sort(sort).build()).getResult();

        assertEquals(2, result.size());
        assertThat(((Wand) result.get(0)).getWood()).isGreaterThan(((Wand) result.get(1)).getWood());
    }

    @Test
    void sortByWoodAscending() {
        final String sort = "@wood";
        final var result = wandService.retreive(RsqlSearchRequestDto.builder().sort(sort).build()).getResult();

        assertEquals(2, result.size());
        assertThat(((Wand) result.get(0)).getWood()).isLessThan(((Wand) result.get(1)).getWood());
    }

    @Test
    void sortByLengthDescending() {
        final String sort = "$length";
        final var result = wandService.retreive(RsqlSearchRequestDto.builder().sort(sort).build()).getResult();

        assertEquals(2, result.size());
        assertThat(((Wand) result.get(0)).getLength()).isGreaterThan(((Wand) result.get(1)).getLength());
    }

    @Test
    void sortByLengthAscending() {
        final String sort = "@length";
        final var result = wandService.retreive(RsqlSearchRequestDto.builder().sort(sort).build()).getResult();

        assertEquals(2, result.size());
        assertThat(((Wand) result.get(0)).getLength()).isLessThan(((Wand) result.get(1)).getLength());
    }

}
