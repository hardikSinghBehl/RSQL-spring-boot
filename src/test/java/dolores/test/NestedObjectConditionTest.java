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
import com.behl.dolores.entity.Wizard;
import com.behl.dolores.repository.WandRepository;
import com.behl.dolores.repository.WizardRepository;
import com.behl.dolores.service.WizardService;

@SpringBootTest(classes = RsqlSpringBootApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class NestedObjectConditionTest {

    private static final double LENGTH = 14.5;
    private static final String FULLNAME = "Harry Potter";
    private static final String CORE = "Human spine";

    private final WizardService wizardService;
    private final WizardRepository wizardRepository;
    private final WandRepository wandRepository;

    @Autowired
    public NestedObjectConditionTest(final WizardService wizardService, final WizardRepository wizardRepository,
            final WandRepository wandRepository) {
        this.wizardService = wizardService;
        this.wizardRepository = wizardRepository;
        this.wandRepository = wandRepository;
    }

    @BeforeAll
    void setUp() {
        final var wand = new Wand();
        wand.setCore(CORE);
        wand.setLength(LENGTH);
        final var savedWand = wandRepository.save(wand);

        final var wizard = new Wizard();
        wizard.setWand(savedWand);
        wizard.setFullName(FULLNAME);
        wizardRepository.save(wizard);
    }

    @Test
    void nestedObjectStringValueComparison() {
        final String query = "wand.core==\"" + CORE + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
    }

    @Test
    void nestedObjectNumericValueComparison() {
        final String query = "wand.length==" + LENGTH;
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
    }

}
