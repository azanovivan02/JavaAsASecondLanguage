package io.github.javaasasecondlanguage.homework01.mappers;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.mappers.RetainColumnsMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyToAllRecords;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRecords;
import static java.util.List.of;

public class RetainColumnsMapperTest {

    private static final List<Record> INPUT_RECORDS = convertToRecords(
            new String[]{"Id", "Name", "Surname"},
            new Object[][]{
                    {13, "Roboute", "Guilliman"},
                    {3, "Fulgrim", "Phoenician"},
                    {6, "Leman", "Russ"},
            }
    );

    private static final List<Record> EXPECTED_RECORDS = convertToRecords(
            new String[]{"Id", "Name"},
            new Object[][]{
                    {13, "Roboute"},
                    {3, "Fulgrim"},
                    {6, "Leman"},
            }
    );

    @Test
    void general() {
        var mapper = new RetainColumnsMapper(of("Id", "Name"));

        var actualrecords = applyToAllRecords(mapper, INPUT_RECORDS);
        assertRecordsEqual(EXPECTED_RECORDS, actualrecords);
    }
}
