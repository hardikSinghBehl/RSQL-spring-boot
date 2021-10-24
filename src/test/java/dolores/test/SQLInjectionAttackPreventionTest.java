package dolores.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.behl.dolores.RsqlSpringBootApplication;
import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.exception.PossibleSqlInjectionAttackException;
import com.behl.dolores.service.WandService;

@SpringBootTest(classes = RsqlSpringBootApplication.class)
@ExtendWith(SpringExtension.class)
public class SQLInjectionAttackPreventionTest {

    private final WandService wandService;

    @Autowired
    public SQLInjectionAttackPreventionTest(WandService wandService) {
        this.wandService = wandService;
    }

    @Test
    void dropTableSyntaxPrevention() {
        final String query = "wood==\"DROP TABLE users;--\"";

        assertThrows(PossibleSqlInjectionAttackException.class,
                () -> wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()));
    }

    @Test
    void commentingFurtherConditionSyntaxAttackPrevention() {
        final String query = "wood==\"holly'--\"";

        assertThrows(PossibleSqlInjectionAttackException.class,
                () -> wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()));
    }

    @Test
    void dataRetreivalFromOtherTableAttackPrevention() {
        final String query = "wood==\"' UNION SELECT username, password FROM users--\"";

        assertThrows(PossibleSqlInjectionAttackException.class,
                () -> wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()));
    }

    @Test
    void databaseContentRetreivalAttackPrevention() {
        final String query = "wood==\"SELECT * FROM information_schema.tables--\"";

        assertThrows(PossibleSqlInjectionAttackException.class,
                () -> wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()));
    }

    @Test
    void databaseVersionRetreivalAttackPrevention() {
        final String query = "wood==\"SELECT @@version--\"";

        assertThrows(PossibleSqlInjectionAttackException.class,
                () -> wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()));
    }

    @Test
    void conditionalErrorAttackPrevention() {
        final String query = "wood==\"SELECT IF(true=1,(SELECT users FROM information_schema.tables),'a')--\"";

        assertThrows(PossibleSqlInjectionAttackException.class,
                () -> wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()));
    }

    @Test
    void timeDelayAttackPrevention() {
        final String query = "wood==\"SELECT sleep(10)--\"";

        assertThrows(PossibleSqlInjectionAttackException.class,
                () -> wandService.retreive(RsqlSearchRequestDto.builder().query(query).build()));
    }
}
