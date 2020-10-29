package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.CompGraph;
import io.github.javaasasecondlanguage.homework01.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.Printer;
import io.github.javaasasecondlanguage.homework01.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework01.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.FirstNReducer;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.DESCENDING;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRows;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.pushAllRowsThenTerminal;
import static java.util.List.of;

public class BaseCase implements TestCase {

    @Override
    public void launch() {
        var inputNode = createGraph().getInputNodes().get(0);
        var inputRows = createInputs().get(0);
        pushAllRowsThenTerminal(inputNode, inputRows);
    }

    @Override
    public CompGraph createGraph() {
        var inputPart = GraphPartBuilder
                .startWith(new TokenizerMapper("Text", "Word"))
                .then(new AddColumnMapper("Word", row -> row.getString("Word").toLowerCase()))
                .sortThenReduceBy(of("Word"), new CountReducer("WordCount"));

        var commonOutputNode = inputPart
                .branch()
                .sortBy(of("WordCount"), DESCENDING)
                .then(new FirstNReducer(5))
                .then(new Printer("+++ Top 5 common words"))
                .getEndNode();

        var rareOutputNode = inputPart
                .branch()
                .sortBy(of("WordCount"))
                .then(new FirstNReducer(10))
                .then(new Printer("--- Top 10 rare words"))
                .getEndNode();

        return new CompGraph(
                List.of(inputPart.getStartNode()),
                List.of(commonOutputNode, rareOutputNode)
        );
    }

    @Override
    public List<List<Row>> createInputs() {
        List<Row> rows = convertToRows(
                new String[]{"Text"},
                new Object[][]{
                        {"I am the Emperor's will made manifest"},
                        {"Faith is purest when it is unquestioned"},
                        {"I accept the challenge"},
                        {"By the Emperor, it shall be so!"},
                        {"Bless the mind too small for doubt"},
                        {"Your wise counsel belies your years"},
                        {"Difficult, to be sure... but it shall be so"},
                        {"I shall rally the troops to your cause!"},
                        {"May you lead us to victory!"},
                        {"Redeem them with sword and fire!"},
                        {"Brothers, we are together again!"},
                        {"Rally to me, brothers, and we will win!"},
                        {"Drive them back!"},
                        {"To the last, kill them all!"},
                        {"Only in war are we truly faithful!"},
                        {"May the Emperor watch over us all!"},
                        {"You cannot stand against my Faith!"},
                        {"For the glory of the Imperium, CHARGE!"},
                        {"Break the enemy line!"},
                        {"We live and die as brothers!"},
                        {"Pain now! Reward in the afterlife!"},
                        {"Our duty lies elsewhere so be quick!"},
                        {"Spirit us to our objective, driver"},
                        {"So this is where the Emperor's will brings us"},
                        {"Ahh, to walk upon the bloodstained ground"},
                        {"We have been humbled"},
                        {"Retreat, heed me and regroup!"},
                        {"I hear the drums of war again!"},
                        {"Make a stand! Here and now!"},
                        {"None can withstand our faith!"},
                        {"Mighty Emperor, guide my blow!"},
                        {"Come all you xeno scum and fallen heretics, come and face the one true might of the universe and wither under the Golden Throne's gaze!"},
                        {"Take them, and quickly!"}
                }
        );
        return of(rows);
    }

}
