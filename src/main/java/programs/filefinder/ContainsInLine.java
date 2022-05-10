package programs.filefinder;

import programs.Program;
import utilities.UserInterface;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ContainsInLine implements Program {

    @Override
    public boolean run(Map<String, Object> input) {
        @SuppressWarnings("unchecked") final Collection<Path> foundFiles = (Collection<Path>) input.get("FOUND_FILES");
        final UserInterface ui = (UserInterface) input.get("UI");
        final String element = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("Which element?");
        final String stringIndexes = input.containsKey(INPUT_PARAMETER_2) ? (String) input.get(INPUT_PARAMETER_2) : ui.getUserInput("Which indexes?");
        List<Integer> indexes = new ArrayList<>();
        for (String stringIndex: stringIndexes.split(",")) {
            indexes.add(Integer.parseInt(stringIndex));
        }
        List<Path> files = new ArrayList<>();
        for (Path path : foundFiles) {
            checkFile(ui, element, indexes, files, path);
        }
        foundFiles.clear();
        foundFiles.addAll(files);
        input.remove(INPUT_PARAMETER_1);
        return true;
    }

    private void checkFile(UserInterface ui, String element, List<Integer> indexes, List<Path> files, Path path) {
        String[] lines = path.toFile().list();
        if (lines != null) {
            for (int index : indexes) {
                if (index < lines.length && lines[index].equals(element)) {
                    files.add(path);
                    return;
                }
            }
        } else {
            ui.printLine("Failed to read file: " + path);
        }
    }
}
