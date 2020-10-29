package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.cases.TfIdfCase;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.pushAllRowsThenTerminal;

public class TfIdfTest {

    @Test
    void general() {
        TfIdfCase tfIdfCase = new TfIdfCase();

        List<Row> inputRows = tfIdfCase.createInputs().get(0);
        CompNode startNode = tfIdfCase.createGraph().get(0);
        pushAllRowsThenTerminal(startNode, inputRows);
    }
}
