package programs.elasticexport;

import utilities.UserInterface;

import java.util.Map;

public class Replace extends AbstractElasticExportProgram {
    String from;
    String to;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        from = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : UserInterface.getUserInput("From?");
        to = input.containsKey(INPUT_PARAMETER_2) ? (String) input.get(INPUT_PARAMETER_2) : UserInterface.getUserInput("To?");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        lines = lines.map(e -> e.replace(from, to));
        return true;
    }
}
