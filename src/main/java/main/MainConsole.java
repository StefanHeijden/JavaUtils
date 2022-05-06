package main;

import programs.Program;
import utilities.UserInputReader;

import java.util.Map;

public class MainConsole extends UserInputReader {

    public MainConsole(String inputReaderTitle, Map<String, Program> programs, Map<String, Object> configs) {
        super(inputReaderTitle, programs, configs);
        startReadingInputFromUser();
    }

}
