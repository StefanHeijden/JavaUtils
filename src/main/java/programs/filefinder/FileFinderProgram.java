package programs.filefinder;

import programs.Program;
import utilities.UserInterface;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class FileFinderProgram implements Program {
    Collection<Path> foundFiles;
    UserInterface ui;
    Collection<Path> files;

    @Override
    public void init(Map<String, Object> input) {
        //noinspection unchecked
        foundFiles = (Collection<Path>) input.get("FOUND_FILES");
        ui = (UserInterface) input.get("UI");
        files = new ArrayList<>();
    }

    @Override
    public void close(Map<String, Object> input) {
        foundFiles.clear();
        foundFiles.addAll(files);
        input.remove(INPUT_PARAMETER_1);
        input.remove(INPUT_PARAMETER_2);
        input.remove(INPUT_PARAMETER_3);
    }
}
