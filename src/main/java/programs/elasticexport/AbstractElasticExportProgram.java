package programs.elasticexport;

import programs.Program;

import java.util.Map;
import java.util.stream.Stream;

public class AbstractElasticExportProgram implements Program {
    Stream<String> lines;

    @Override
    public void init(Map<String, Object> input) {
        //noinspection unchecked
        lines = (Stream<String>) input.get("LINES");
    }

}
