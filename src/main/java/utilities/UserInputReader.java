package utilities;

import programs.Program;

import java.io.IOException;
import java.util.Map;

public class UserInputReader {
    public static final String START_MESSAGE = "What do you want to do? (type help or ? for help)";
    private String inputReaderTitle = "";
    public static final UserInterface ui = new UserInterface();
    private Map<String, Program> programMap;
    private Map<String, Object> configs;


    public UserInputReader(String inputReaderTitle, Map<String, Program> programMap, Map<String, Object> configs) {
        this.inputReaderTitle = inputReaderTitle;
        this.programMap = programMap;
        this.configs = configs;
        configs.put("UI", ui);
    }

    public void startReadingInputFromUser() {
        boolean keepGoing = true;
        while(keepGoing) {
            keepGoing = readSingleInputFromUser();
        }
    }

    boolean readSingleInputFromUser() {
        String[] userInputs = ui.getUserInput(inputReaderTitle + ": " + START_MESSAGE).split(" ");
        if (userInputs.length > 0 && programMap.containsKey(userInputs[0])) {
            for (int i = 1; i < userInputs.length; i++) {
                configs.put("INPUT_PARAMETER_" + i, userInputs[i]);
            }
            return programMap.get(userInputs[0]).run(configs);
        } else {
            ui.printLine(inputReaderTitle + ": Command was not found.");
            return true;
        }
    }

//    void printHelpList(){
//        for(String[] entry : getEntries()) {
//            ui.printWithTab(entry[0], entry[1], 25);
//        }
//        ui.printLine("_________________________________________________________________");
//    }
}
