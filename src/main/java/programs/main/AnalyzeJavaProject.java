package programs.main;

import programs.Program;
import applications.javaprojectanalyzer.JavaProjectAnalyzer;
import utilities.Logger;
import utilities.UserInterface;

import java.nio.file.NoSuchFileException;
import java.util.Map;

public class AnalyzeJavaProject implements Program {

    @Override
    public boolean work(Map<String, Object> input) {
        try {
            UserInterface.printLine("start");
            new JavaProjectAnalyzer();
        } catch (Exception e) {
            UserInterface.printLine("Error happened! Error message: " + e.getMessage());
            Logger.log(e);
        }
        return true;
    }
}
