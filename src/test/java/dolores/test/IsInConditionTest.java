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
import com.behl.dolores.entity.Wizard;
import com.behl.dolores.entity.constant.Species;
import com.behl.dolores.repository.WizardRepository;
import com.behl.dolores.service.WizardService;

import net.bytebuddy.utility.RandomString;

@SpringBootTest(classes = RsqlSpringBootApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IsInConditionTest {

    private final WizardService wizardService;
    private final WizardRepository wizardRepository;

    @Autowired
    public IsInConditionTest(WizardService wizardService, WizardRepository wizardRepository) {
        this.wizardService = wizardService;
        this.wizardRepository = wizardRepository;
    }

    @BeforeAll
    void setUp() {
        final var catWizard = new Wizard();
        catWizard.setSpecies(Species.CAT);
        catWizard.setFullName(RandomString.make());
        wizardRepository.save(catWizard);

        final var halfGiantWizard = new Wizard();
        halfGiantWizard.setSpecies(Species.HALF_GIANT);
        halfGiantWizard.setFullName(RandomString.make());
        wizardRepository.save(halfGiantWizard);

        final var humanWizard = new Wizard();
        humanWizard.setSpecies(Species.HUMAN);
        humanWizard.setFullName(RandomString.make());
        wizardRepository.save(humanWizard);
    }

    @Test
    void testInMatching() {
        final var query = "species=in=('human', 'cat')";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build());

        assertEquals(2, result.getResult().size());
    }

}
