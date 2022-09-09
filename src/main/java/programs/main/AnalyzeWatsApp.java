package programs.main;

import configurators.Configurator;
import programs.Program;
import applications.watsappanalyzer.WatsAppAnalyzer;
import utilities.Logger;
import utilities.UserInputReader;
import utilities.UserInterface;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class AnalyzeWatsApp implements Program {
    String fileName;
    String targetLocation;

    @Override
    public void init(Map<String, Object> input) {
        fileName = input.containsKey(INPUT_PARAMETER_1) ?
                (String) input.get(INPUT_PARAMETER_1) :
                UserInterface.getUserInput("Which file?");
        targetLocation = input.containsKey(INPUT_PARAMETER_2) ?
                (String) input.get(INPUT_PARAMETER_2) :
                UserInterface.getUserInput("Where to store the results?");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        try {
            Path filePath = new File(UserInputReader.getCurrentPath() + Configurator.STRING_LINE_SEPARATOR + fileName).toPath();
            Path targetPath = new File(UserInputReader.getCurrentPath().resolve(Paths.get(targetLocation)).normalize().toString()).toPath();
            new WatsAppAnalyzer(filePath, targetPath);
        } catch (NoSuchFileException e) {
            Logger.log(e);
            UserInterface.printLine("Did not find file: " + e.toString());
        } catch (Exception e) {
            Logger.log(e);
        }
        return true;
    }
}
