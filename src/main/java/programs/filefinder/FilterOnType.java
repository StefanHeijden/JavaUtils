package programs.filefinder;

import java.util.Map;
import java.util.stream.Collectors;

public class FilterOnType extends FileFinderProgram {
    String fileType;

    @Override
    public void init(Map<String, Object> input) {
        fileType = (String) input.get("FILE_TYPE");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        files = foundFiles.stream()
                .filter(p -> p.toString().toLowerCase().endsWith(fileType))
                .collect(Collectors.toSet());

        return true;
    }
}
