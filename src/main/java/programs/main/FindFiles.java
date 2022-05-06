package programs.main;

import programs.FileFinder;
import programs.Program;
import utilities.UserInterface;

import java.util.Map;

public class FindFiles extends Program {
    @Override
    public boolean run(Map<String, Object> input) {
        final UserInterface ui = (UserInterface) input.get("UI");
        try{
            new FileFinder(ui.getUserInput("Which file?")).find();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
