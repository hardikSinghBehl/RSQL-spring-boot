package dolores.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

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
public class PaginationTest {

    private final static Integer NUMBER_OF_WIZARDS_IN_DB = 100;

    private final WizardService wizardService;
    private final WizardRepository wizardRepository;

    @Autowired
    public PaginationTest(WizardService wizardService, WizardRepository wizardRepository) {
        this.wizardService = wizardService;
        this.wizardRepository = wizardRepository;
    }

    @BeforeAll
    void setUp() {
        final List<Wizard> wizards = new ArrayList<Wizard>();
        for (int i = 0; i < NUMBER_OF_WIZARDS_IN_DB; i++) {
            final var wizard = new Wizard();
            wizard.setFullName(RandomString.make(10));
            wizards.add(wizard);
        }
        wizardRepository.saveAll(wizards);
    }

    @Test
    void correctPageAndCount() {
        final Integer count = NUMBER_OF_WIZARDS_IN_DB / 10;
        final Integer page = 1;
        final String query = "id>=1";
        final var result = wizardService
                .retreive(RsqlSearchRequestDto.builder().query(query).count(count).page(page).build());

        assertEquals(10, result.getTotalPages());
        assertEquals(page, result.getCurrentPage());
        assertEquals(count, result.getCount());
    }

    @Test
    void pageAndCountIgnoredWhenNoQueryProvided() {
        final Integer count = NUMBER_OF_WIZARDS_IN_DB / 10;
        final Integer page = 3;
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().count(count).page(page).build());

        assertNotEquals(10, result.getTotalPages());
        assertEquals(1, result.getTotalPages());
        assertNotEquals(page, result.getCurrentPage());
        assertEquals(1, result.getCurrentPage());
        assertEquals(NUMBER_OF_WIZARDS_IN_DB, result.getCount());
    }

    @Test
    void firstPageReturnedWhenPageIsNegative() {
        final Integer count = NUMBER_OF_WIZARDS_IN_DB / 10;
        final Integer page = -5;
        final String query = "id>=1";
        final var result = wizardService
                .retreive(RsqlSearchRequestDto.builder().query(query).count(count).page(page).build());

        assertEquals(10, result.getTotalPages());
        assertEquals(1, result.getCurrentPage());
        assertEquals(count, result.getCount());
    }

    @Test
    void firstPageReturnedWhenPageIsZero() {
        final Integer count = NUMBER_OF_WIZARDS_IN_DB / 10;
        final Integer page = 0;
        final String query = "id>=1";
        final var result = wizardService
                .retreive(RsqlSearchRequestDto.builder().query(query).count(count).page(page).build());

        assertEquals(10, result.getTotalPages());
        assertEquals(1, result.getCurrentPage());
        assertEquals(count, result.getCount());
    }

    @Test
    void firstPageContainingAllRecordsReturnedWhenNoPaginationFieldProvided() {
        final var result = wizardService.retreive(RsqlSearchRequestDto.builder().build());

        assertEquals(1, result.getTotalPages());
        assertEquals(1, result.getCurrentPage());
        assertEquals(NUMBER_OF_WIZARDS_IN_DB, result.getCount());
    }

    @Test
    void lastPageReturnedWhenPageIsGreaterThanTotalPages() {
        final Integer count = NUMBER_OF_WIZARDS_IN_DB / 10;
        final Integer page = NUMBER_OF_WIZARDS_IN_DB * 100;
        final String query = "id>=1";
        final var result = wizardService
                .retreive(RsqlSearchRequestDto.builder().query(query).count(count).page(page).build());

        assertNotEquals(page, result.getTotalPages());
        assertEquals(result.getCurrentPage(), result.getTotalPages());
    }

}
