package io.github.javaasasecondlanguage.homework04;

import io.github.javaasasecondlanguage.homework04.cases.BaseCase;
import io.github.javaasasecondlanguage.homework04.cases.JoinCase;
import io.github.javaasasecondlanguage.homework04.cases.TableCase;
import io.github.javaasasecondlanguage.homework04.cases.TestCase;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Legacy demo, to be removed
 */
public class CaseDemoTest {


    @Test
    void general() {
        List<TestCase> testCases = Arrays.asList(
                new BaseCase(),
                new TableCase(),
                new JoinCase()
        );

        System.out.printf("\n== Start Case Demo Test ===============================\n\n");
        for (TestCase testCase : testCases) {
            String caseName = testCase.getClass().getSimpleName();
            System.out.printf("\n== Case: %s ========\n\n", caseName);
            testCase.launch();
        }
        System.out.printf("\n== End Case Demo Test ==================================n\n");
    }
}
