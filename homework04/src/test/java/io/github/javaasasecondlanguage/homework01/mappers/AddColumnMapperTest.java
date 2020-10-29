package io.github.javaasasecondlanguage.homework01.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.mappers.AddColumnMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRowsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyToAllRows;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRows;
import static java.lang.String.format;

public class AddColumnMapperTest {

    private static final List<Row> inputRows = convertToRows(
            new String[]{"Id", "Name", "Surname"},
            new Object[][]{
                    {13, "Roboute", "Guilliman"},
                    {3, "Fulgrim", "Phoenician"},
                    {6, "Leman", "Russ"},
            }
    );

    private static final List<Row> expectedRows = convertToRows(
            new String[]{"Id", "Name", "Surname", "Full name"},
            new Object[][]{
                    {13, "Roboute", "Guilliman", "Roboute Guilliman"},
                    {3, "Fulgrim", "Phoenician", "Fulgrim Phoenician"},
                    {6, "Leman", "Russ", "Leman Russ"},
            }
    );

    @Test
    void general() {
        Function<Row, Object> lambda = row -> format("%s %s", row.get("Name"), row.get("Surname"));
        AddColumnMapper mapper = new AddColumnMapper("Full name", lambda);

        List<Row> actualRows = applyToAllRows(mapper, inputRows);
        assertRowsEqual(expectedRows, actualRows);
    }
}
