package programs.filefinder;

import utilities.UserInterface;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ContainsInLine extends Contains {
    String stringIndexes;
    List<Integer> indexes;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        stringIndexes = input.containsKey(INPUT_PARAMETER_2) ? (String) input.get(INPUT_PARAMETER_2) : UserInterface.getUserInput("Which indexes?");
        indexes = new ArrayList<>();
        for (String stringIndex: stringIndexes.split(",")) {
            indexes.add(Integer.parseInt(stringIndex));
        }
    }

    @Override
    public boolean work(Map<String, Object> input) {
        for (Path path : foundFiles) {
            checkFile(element, indexes, files, path);
        }
        return true;
    }

    private void checkFile(String element, List<Integer> indexes, Collection<Path> files, Path path) {
        String[] lines = path.toFile().list();
        if (lines != null) {
            for (int index : indexes) {
                if (index < lines.length && lines[index].equals(element)) {
                    files.add(path);
                    return;
                }
            }
        } else {
            UserInterface.printLine("Failed to read file: " + path);
        }
    }
}
