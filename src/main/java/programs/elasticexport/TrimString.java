package programs.elasticexport;

import java.util.Map;

public class TrimString extends AbstractElasticExportProgram {
    int start;
    int end;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        start = input.containsKey(INPUT_PARAMETER_1) ?
                (Integer) input.get(INPUT_PARAMETER_1) :
                Integer.parseInt(ui.getUserInput("How many to trim from starting index?"));
        end = input.containsKey(INPUT_PARAMETER_2) ?
                (Integer) input.get(INPUT_PARAMETER_2) :
                Integer.parseInt(ui.getUserInput("How many to trim from last index?"));
    }

    @Override
    public boolean work(Map<String, Object> input) {
        lines = lines.map(e -> e.substring(start, e.length() - end));
        return true;
    }
}
