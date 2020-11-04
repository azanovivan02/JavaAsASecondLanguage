package io.github.javaasasecondlanguage.homework04.mappers;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.applyToAllRecords;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static java.util.List.of;

public class RetainColumnsMapperTest {

    @Test
    void general() {
        var mapper = new RetainColumnsMapper(of("Id", "Name"));

        var actualrecords = applyToAllRecords(mapper, inputRecords);
        assertRecordsEqual(expectedRecords, actualrecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{"Id", "Name", "Surname"},
            new Object[][]{
                    {13, "Roboute", "Guilliman"},
                    {3, "Fulgrim", "Phoenician"},
                    {6, "Leman", "Russ"},
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Id", "Name"},
            new Object[][]{
                    {13, "Roboute"},
                    {3, "Fulgrim"},
                    {6, "Leman"},
            }
    );
}
