package programs.main;

import programs.Program;
import utilities.UserInterface;

import java.util.Map;

public class RunTest extends Program {
    @Override
    public boolean run(Map<String, Object> input) {
        final UserInterface ui = (UserInterface) input.get("UI");
//        FileFinder ff = new FileFinder("C:\\Users\\stheijde\\Desktop\\dump").find("? /");
//        ff.getFoundFiles().forEach(ui::printLine);
        return true;
    }
}
