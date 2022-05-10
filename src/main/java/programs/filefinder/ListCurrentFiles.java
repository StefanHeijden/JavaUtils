package programs.filefinder;

import programs.Program;
import utilities.UserInterface;

import java.util.Map;

public class ListCurrentFiles implements Program {

    @Override
    public boolean run(Map<String, Object> input) {
        final String USER_DIR = (String) input.get("USER_DIR");
        final String TARGET_LOCATION = (String) input.get("TARGET_LOCATION");
        final String FILE_LOCATION = (String) input.get("FILE_LOCATION");
        final UserInterface ui = (UserInterface) input.get("UI");

        input.remove(INPUT_PARAMETER_1);
        return true;
    }
}
