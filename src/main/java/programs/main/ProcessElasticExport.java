package programs.main;

import applications.ElasticExportProcessor;
import programs.Program;
import utilities.UserInterface;

import java.util.Map;

public class ProcessElasticExport implements Program {
    @Override
    public boolean run(Map<String, Object> input) {
        final UserInterface ui = (UserInterface) input.get("UI");
        new ElasticExportProcessor("", "");
        ui.printLine("To do put this in extra file-------------------------------------------------");
        return true;
    }
}
