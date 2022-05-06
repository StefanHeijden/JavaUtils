package programs.filefinder;

import programs.Program;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterOnType extends Program {

    @Override
    public boolean run(Map<String, Object> input) {
        final Collection<Path> foundFiles = (Collection<Path>) input.get("FOUND_FILES");
        final String fileType = (String) input.get("FILE_TYPE");
        Collection<Path> files = foundFiles.stream()
                .filter(p -> p.toString().toLowerCase().endsWith(fileType))
                .collect(Collectors.toSet());
        foundFiles.clear();
        foundFiles.addAll(files);
        input.remove(INPUT_PARAMETER_1);
        return true;
    }
}
