package dolores.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.behl.dolores.RsqlSpringBootApplication;
import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.entity.Wand;
import com.behl.dolores.repository.WandRepository;
import com.behl.dolores.service.WandService;

@SpringBootTest(classes = RsqlSpringBootApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IsEqualIgnoreCaseTest {

    private static final double LENGTH = 14.5;
    private static final String WOOD = "Buloke";
    private static final String CORE = "human bones";

    private final WandService wandService;
    private final WandRepository wandRepository;

    @Autowired
    public IsEqualIgnoreCaseTest(WandService wandService, WandRepository wandRepository) {
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
    void equalIgnoreCaseString_matching() {
        final String query = "wood=eic=\"" + WOOD.toUpperCase() + "\"";

        final var result = wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();
        assertEquals(1, result.size());
        final Wand retreivedWand = (Wand) result.get(0);
        assertEquals(CORE, retreivedWand.getCore());
        assertEquals(WOOD, retreivedWand.getWood());
        assertEquals(LENGTH, retreivedWand.getLength());
    }

}
