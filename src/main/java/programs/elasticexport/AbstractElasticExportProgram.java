package programs.elasticexport;

import programs.Program;
import utilities.UserInterface;

import java.util.Map;
import java.util.stream.Stream;

public class AbstractElasticExportProgram implements Program {
    Stream<String> lines;
    UserInterface ui;

    @Override
    public void init(Map<String, Object> input) {
        //noinspection unchecked
        lines = (Stream<String>) input.get("LINES");
        ui = (UserInterface) input.get("UI");
    }

}
