package programs;

import utilities.UserInputReader;

import java.nio.file.Paths;
import java.util.Map;

public class ChangeCurrentPath implements Program{

    @Override
    public boolean run(Map<String, Object> input) {
        String newPath = (String) input.get(INPUT_PARAMETER_1);
        UserInputReader.setCurrentPath(Paths.get(newPath));
        return true;
    }
}
