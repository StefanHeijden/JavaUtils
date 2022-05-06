package programs.filefinder;

import programs.Program;
import utilities.UserInterface;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Contains extends Program {

    @Override
    public boolean run(Map<String, Object> input) {
        final Collection<Path> foundFiles = (Collection<Path>) input.get("FOUND_FILES");
        final UserInterface ui = (UserInterface) input.get("UI");
        final String element = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("Which element?");
        List<Path> files = new ArrayList<>();
        outer:
        for (Path path : foundFiles) {
            try {
                List<String> lines = Files.readAllLines(path);
                inner:
                for (String line : lines) {
                    if (line.contains(element)) {
                        files.add(path);
                        continue outer;
                    }
                }
            } catch (Exception e) {
                System.out.println("Something went wrong in contain method for " + path.toString());
            }
        }
        foundFiles.clear();
        foundFiles.addAll(files);
        input.remove(INPUT_PARAMETER_1);
        return true;
    }
}
