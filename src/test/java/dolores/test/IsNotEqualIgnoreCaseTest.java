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
import com.behl.dolores.repository.WizardRepository;
import com.behl.dolores.service.WizardService;

import net.bytebuddy.utility.RandomString;

@SpringBootTest(classes = RsqlSpringBootApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IsNotEqualIgnoreCaseTest {

    private static final String FULLNAME = "Harry Potter";
    private final WizardService wizardService;
    private final WizardRepository wizardRepository;

    @Autowired
    public IsNotEqualIgnoreCaseTest(final WizardService wizardService, final WizardRepository wizardRepository) {
        this.wizardService = wizardService;
        this.wizardRepository = wizardRepository;
    }

    @BeforeAll
    void setUp() {
        final var wizard = new Wizard();
        wizard.setFullName(FULLNAME);
        wizardRepository.save(wizard);
    }

    @Test
    void notEqualIgnoreCaseMatchingCreatedRecord() {
        final String query = "fullName=neic=\"" + FULLNAME.toUpperCase() + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build());

        assertEquals(0, result.getResult().size());
    }

    @Test
    void notEqualIgnoreCaseNotMatchingCreatedRecord() {
        final String query = "fullName=neic=\"" + RandomString.make(4) + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build());

        assertEquals(1, result.getResult().size());
    }

}
