package programs.filefinder;

import utilities.UserInterface;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Contains extends FileFinderProgram {
    String element;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        element = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("Which element?");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        for (Path path : foundFiles) {
            checkFile(element, files, path, ui);
        }
        return true;
    }

    private void checkFile(String element, Collection<Path> files, Path path, UserInterface ui) {
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.contains(element)) {
                    files.add(path);
                    return;
                }
            }
        } catch (Exception e) {
            ui.printLine("Failed to read file: " + path);
        }
    }

}
