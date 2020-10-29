package io.github.javaasasecondlanguage.homework01.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.mappers.RetainColumnsMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRowsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyToAllRows;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRows;
import static java.util.List.of;

public class RetainColumnsMapperTest {

    private static final List<Row> inputRows = convertToRows(
            new String[]{"Id", "Name", "Surname"},
            new Object[][]{
                    {13, "Roboute", "Guilliman"},
                    {3, "Fulgrim", "Phoenician"},
                    {6, "Leman", "Russ"},
            }
    );

    private static final List<Row> expectedRows = convertToRows(
            new String[]{"Id", "Name"},
            new Object[][]{
                    {13, "Roboute"},
                    {3, "Fulgrim"},
                    {6, "Leman"},
            }
    );

    @Test
    void general() {
        RetainColumnsMapper mapper = new RetainColumnsMapper(of("Id", "Name"));

        List<Row> actualRows = applyToAllRows(mapper, inputRows);
        assertRowsEqual(expectedRows, actualRows);
    }
}
