package programs.main;

import programs.Program;
import utilities.UserInputReader;

import java.nio.file.Paths;
import java.util.Map;

public class ChangeCurrentPath implements Program {
    String newPath;

    @Override
    public void init(Map<String, Object> input) {
        newPath = (String) input.get(INPUT_PARAMETER_1);
    }

    @Override
    public boolean work(Map<String, Object> input) {
        UserInputReader.setCurrentPath(Paths.get(newPath));
        return true;
    }

}
