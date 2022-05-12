package programs.elasticexport;

import java.util.Map;

public class Replace extends AbstractElasticExportProgram {
    String from;
    String to;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        from = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("From?");
        to = input.containsKey(INPUT_PARAMETER_2) ? (String) input.get(INPUT_PARAMETER_2) : ui.getUserInput("To?");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        lines = lines.map(e -> e.replace(from, to));
        return true;
    }
}
