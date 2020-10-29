package io.github.javaasasecondlanguage.homework01.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.mappers.TokenizerMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRowsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyToAllRows;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRows;

class TokenizerMapperTest {

    private static final List<Row> inputRows = convertToRows(
            new String[]{"DocId", "Text"},
            new Object[][]{
                    {1, "Elegant weapon for a more civilized age;"},
                    {2, "He said: \"Of course I know him, he's me!\""},
                    {3, "Where are you?"}
            }
    );

    private static final List<Row> expectedRows = convertToRows(
            new String[]{"DocId", "Word"},
            new Object[][]{
                    {1, "Elegant"},
                    {1, "weapon"},
                    {1, "for"},
                    {1, "a"},
                    {1, "more"},
                    {1, "civilized"},
                    {1, "age"},
                    {2, "He"},
                    {2, "said"},
                    {2, "Of"},
                    {2, "course"},
                    {2, "I"},
                    {2, "know"},
                    {2, "him"},
                    {2, "he"},
                    {2, "s"},
                    {2, "me"},
                    {3, "Where"},
                    {3, "are"},
                    {3, "you"}
            }
    );

    @Test
    void general() {
        var mapper = new TokenizerMapper("Text", "Word");

        var actualRows = applyToAllRows(mapper, inputRows);
        assertRowsEqual(expectedRows, actualRows);
    }
}