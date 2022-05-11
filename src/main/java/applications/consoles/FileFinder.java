package applications.consoles;

import programs.Program;
import utilities.UserInputReader;

import java.util.Map;

public class FileFinder extends UserInputReader {

    public FileFinder(String inputReaderTitle, Map<String, Program> programs, Map<String, Object> configs) {
        super(inputReaderTitle, programs, configs);
        startReadingInputFromUser();
    }

}
