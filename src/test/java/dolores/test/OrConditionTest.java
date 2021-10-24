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
public class OrConditionTest {

    private static final String FULLNAME = "Harry";

    private final WizardService wizardService;
    private final WizardRepository wizardRepository;

    @Autowired
    public OrConditionTest(final WizardService wizardService, final WizardRepository wizardRepository) {
        this.wizardService = wizardService;
        this.wizardRepository = wizardRepository;
    }

    @BeforeAll
    void setUp() {
        final var humanWizard = new Wizard();
        humanWizard.setAlive(true);
        humanWizard.setFullName(FULLNAME);
        wizardRepository.save(humanWizard);
    }

    @Test
    void orOneConditionMatch_responseReturned() {
        final String query = "fullName==" + FULLNAME + ",alive==0";
        final var respone = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build());

        assertEquals(1, respone.getCount());
    }

    @Test
    void orNoConditionMatch_noResponse() {
        final String query = "fullName==" + RandomString.make(4) + ";alive==0";
        final var respone = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build());

        assertEquals(0, respone.getCount());
    }

}
