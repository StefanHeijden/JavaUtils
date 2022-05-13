package utilities;

import configurators.Configurator;
import programs.Program;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class UserInputReader {
    private static Path currentPath = Paths.get(Configurator.APPLICATION_PATH);
    private final String inputReaderTitle;
    private final Map<String, Program> programMap;
    private final Map<String, Object> configs;


    public UserInputReader(String inputReaderTitle, Map<String, Program> programMap, Map<String, Object> configs) {
        this.inputReaderTitle = inputReaderTitle;
        this.programMap = programMap;
        this.configs = configs;
    }

    public void startReadingInputFromUser() {
        boolean keepGoing = true;
        while(keepGoing) {
            keepGoing = readSingleInputFromUser();
        }
    }

    boolean readSingleInputFromUser() {
        UserInterface.printLine(inputReaderTitle + ":");
        String[] userInputs = UserInterface.getUserInput(currentPath.toString()).split(" ");
        if (userInputs.length > 0 && programMap.containsKey(userInputs[0])) {
            for (int i = 1; i < userInputs.length; i++) {
                configs.put("INPUT_PARAMETER_" + i, userInputs[i]);
            }
            configs.put("CURRENT_PATH", currentPath);
            return programMap.get(userInputs[0]).run(configs);
        } else {
            UserInterface.printLine("Command was not found.");
            return true;
        }
    }

    public static void setCurrentPath(Path path) {
        currentPath = currentPath.resolve(path).normalize();
    }

    public static Path getCurrentPath() {
        return currentPath;
    }
}
