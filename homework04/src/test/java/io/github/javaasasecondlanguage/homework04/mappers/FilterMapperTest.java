package io.github.javaasasecondlanguage.homework04.mappers;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.mappers.FilterMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.applyMapperToAllRecords;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;

class FilterMapperTest {

    @Test
    void general() {
        var mapper = new FilterMapper(r -> r.getDouble("Height") > 180);

        var actualrecords = applyMapperToAllRecords(mapper, inputRecords);
        assertRecordsEqual(expectedRecords, actualrecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{"Name", "Surname", "Height"},
            new Object[][]{
                    {"Brad", "Pitt", 181},
                    {"Tom", "Cruise", 171},
                    {"Shaquille", "O'neal", 216},
                    {"Daniel", "Radcliffe", 165},
                    {"Channing", "Tatum", 185}
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Name", "Surname", "Height"},
            new Object[][]{
                    {"Brad", "Pitt", 181},
                    {"Shaquille", "O'neal", 216},
                    {"Channing", "Tatum", 185}
            }
    );
}