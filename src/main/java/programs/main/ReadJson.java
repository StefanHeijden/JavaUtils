package programs.main;

import applications.jsonreader.JSONReader;
import configurators.Configurator;
import programs.Program;
import utilities.Logger;
import utilities.UserInputReader;
import utilities.UserInterface;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;

public class ReadJson implements Program {
    String fileName;

    @Override
    public void init(Map<String, Object> input) {
        fileName = input.containsKey(INPUT_PARAMETER_1) ?
                (String) input.get(INPUT_PARAMETER_1) :
                UserInterface.getUserInput("Which file?");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        try {
            fileName = fileName.endsWith(".json") ? fileName : fileName + ".json";
            Path filePath = new File(UserInputReader.getCurrentPath() + Configurator.STRING_LINE_SEPARATOR + fileName).toPath();
            new JSONReader(filePath);
        } catch (NoSuchFileException e) {
            Logger.log(e);
            UserInterface.printLine(e.toString());
        } catch (Exception e) {
            Logger.log(e);
        }
        return true;
    }
}
