package programs.main;

import programs.Program;
import applications.YAMLReader;
import utilities.Logger;
import utilities.UserInterface;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;

public class ReadYaml implements Program {

    @Override
    public boolean run(Map<String, Object> input) {
        final String USER_DIR = (String) input.get("USER_DIR");
        final String TARGET_LOCATION = (String) input.get("TARGET_LOCATION");
        final String FILE_LOCATION = (String) input.get("FILE_LOCATION");
        final UserInterface ui = (UserInterface) input.get("UI");
        try {
            String fileName = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("Which file?");
            fileName = fileName.endsWith(".yaml") ? fileName : fileName + ".yaml";
            Path filePath = new File(System.getProperty(USER_DIR) + FILE_LOCATION + fileName).toPath();
            Path targetPath = new File(System.getProperty(USER_DIR) + TARGET_LOCATION).toPath();
            new YAMLReader(filePath, targetPath);
        } catch (NoSuchFileException e) {
            Logger.log(e);
            ui.printLine(e.toString());
        } catch (Exception e) {
            Logger.log(e);
        }
        input.remove(INPUT_PARAMETER_1);
        return true;
    }
}
