package io.github.javaasasecondlanguage.homework04.mappers;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.mappers.IdentityMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.applyMapperToAllRecords;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;

public class IdentityMapperTest {

    @Test
    void general() {
        var mapper = new IdentityMapper();

        List<Record> actualRecords = applyMapperToAllRecords(mapper, inputRecords);
        assertRecordsEqual(inputRecords, actualRecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{"Id", "Name", "Surname"},
            new Object[][]{
                    {1, "Lion", "El'Johnson"},
                    {5, "Jaghatai", "Khan"},
                    {3, "Fulgrim", "Phoenician"},
                    {13, "Roboute", "Guilliman"},
                    {4, "Perturabo", "The Breaker"},
                    {6, "Leman", "Russ"},
            }
    );
}
