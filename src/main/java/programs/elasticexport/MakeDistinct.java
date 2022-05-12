package programs.elasticexport;

import java.util.Map;

public class MakeDistinct extends AbstractElasticExportProgram {

    @Override
    public boolean work(Map<String, Object> input) {
        lines = lines.distinct();
        return true;
    }
}
