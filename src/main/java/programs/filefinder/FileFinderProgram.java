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
        Program.super.init(input);
        foundFiles.clear();
        foundFiles.addAll(files);
    }
}
