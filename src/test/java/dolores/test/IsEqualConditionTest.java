package dolores.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

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
import com.behl.dolores.entity.constant.Gender;
import com.behl.dolores.entity.constant.Species;
import com.behl.dolores.repository.WizardRepository;
import com.behl.dolores.service.WizardService;

import net.bytebuddy.utility.RandomString;

@SpringBootTest(classes = RsqlSpringBootApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IsEqualConditionTest {

    private static final Species SPECIES = Species.HUMAN;
    private static final String PATRONUS = "Bull";
    private static final boolean IS_PROFESSOR = true;
    private static final Gender GENDER = Gender.MALE;
    private static final String FULL_NAME = "Hardik Singh Behl";
    private static final String COLOR = "black";
    private static final String DATE_OF_BIRTH = "1999-12-25";
    private static final boolean IS_ALIVE = true;

    private final WizardService wizardService;
    private final WizardRepository wizardRepository;

    @Autowired
    public IsEqualConditionTest(final WizardService wizardService, final WizardRepository wizardRepository) {
        this.wizardService = wizardService;
        this.wizardRepository = wizardRepository;
    }

    @BeforeAll
    void setUp() {
        final var wizard = new Wizard();
        wizard.setAlive(IS_ALIVE);
        wizard.setDateOfBirth(LocalDate.parse(DATE_OF_BIRTH));
        wizard.setEyeColor(COLOR);
        wizard.setFullName(FULL_NAME);
        wizard.setGender(GENDER);
        wizard.setHairColor(COLOR);
        wizard.setIsProfessor(IS_PROFESSOR);
        wizard.setPatronus(PATRONUS);
        wizard.setSpecies(SPECIES);
        wizardRepository.save(wizard);
    }

    @Test
    void stringEqualCheck_matching() {
        final String query = "fullName==\"" + FULL_NAME + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void stringEqualCheck_notMatching() {
        final String query = "fullName==\"" + RandomString.make(4) + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(0, result.size());
    }

    @Test
    void localDateEqualCheck_matching() {
        final String query = "dateOfBirth==\"" + DATE_OF_BIRTH + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void localDateEqualCheck_notMatching() {
        final String query = "dateOfBirth==\"" + LocalDate.parse(DATE_OF_BIRTH).plusDays(1).toString() + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(0, result.size());
    }

    @Test
    void integerEqualCheck_matching() {
        final String query = "id==1";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void integerEqualCheck_notMatching() {
        final String query = "id==1405";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(0, result.size());
    }

    @Test
    void enumEqualCheck_matching() {
        final String query = "species==" + SPECIES;
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void enumEqualCheck_notMatching() {
        final String query = "gender==" + Gender.FEMALE;
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(0, result.size());
    }

    @Test
    void booleanStringEqualCheck_matching() {
        final String query = "alive==" + IS_ALIVE;
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void booleanStringEqualCheck_notMatching() {
        final String query = "alive==" + !Boolean.valueOf(IS_ALIVE);
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(0, result.size());
    }

    @Test
    void booleanBitEqualCheck_matching() {
        final String query = "alive==" + (IS_ALIVE ? "1" : "0");
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void booleanBitEqualCheck_notMatching() {
        final String query = "alive==" + (!Boolean.valueOf(IS_ALIVE) ? "1" : "0");
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(0, result.size());
    }

    @Test
    void stringEqualCheckLikeStarting_matching() {
        final String query = "fullName==\"" + FULL_NAME.substring(0, 4) + "*\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void stringEqualCheckLikeEnding_matching() {
        final String query = "fullName==\"*" + FULL_NAME.substring(FULL_NAME.length() - 4) + "\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    @Test
    void stringEqualCheckLikeContaining_matching() {
        final String query = "fullName==\"*" + FULL_NAME.substring(3, 6) + "*\"";
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().query(query).build()).getResult();

        assertEquals(1, result.size());
        Wizard wizard = (Wizard) result.get(0);
        checkIfRetreivedObjectSameAsCreated(wizard);
    }

    private void checkIfRetreivedObjectSameAsCreated(Wizard wizard) {
        assertEquals(FULL_NAME, wizard.getFullName());
        assertEquals(SPECIES, wizard.getSpecies());
        assertEquals(PATRONUS, wizard.getPatronus());
        assertEquals(LocalDate.parse(DATE_OF_BIRTH), wizard.getDateOfBirth());
        assertEquals(COLOR, wizard.getEyeColor());
        assertEquals(COLOR, wizard.getHairColor());
        assertEquals(GENDER, wizard.getGender());
        assertEquals(IS_ALIVE, wizard.getAlive());
        assertEquals(IS_PROFESSOR, wizard.getIsProfessor());
    }

}
