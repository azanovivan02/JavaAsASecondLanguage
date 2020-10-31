package io.github.javaasasecondlanguage.homework01.mappers;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.mappers.AddColumnMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyToAllRecords;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRecords;
import static java.lang.String.format;

public class AddColumnMapperTest {

    private static final List<Record> INPUT_RECORDS = convertToRecords(
            new String[]{"Id", "Name", "Surname"},
            new Object[][]{
                    {13, "Roboute", "Guilliman"},
                    {3, "Fulgrim", "Phoenician"},
                    {6, "Leman", "Russ"},
            }
    );

    private static final List<Record> EXPECTED_RECORDS = convertToRecords(
            new String[]{"Id", "Name", "Surname", "Full name"},
            new Object[][]{
                    {13, "Roboute", "Guilliman", "Roboute Guilliman"},
                    {3, "Fulgrim", "Phoenician", "Fulgrim Phoenician"},
                    {6, "Leman", "Russ", "Leman Russ"},
            }
    );

    @Test
    void general() {
        Function<Record, Object> lambda = record -> format("%s %s", record.get("Name"), record.get("Surname"));
        AddColumnMapper mapper = new AddColumnMapper("Full name", lambda);

        List<Record> actualRecords = applyToAllRecords(mapper, INPUT_RECORDS);
        assertRecordsEqual(EXPECTED_RECORDS, actualRecords);
    }
}
