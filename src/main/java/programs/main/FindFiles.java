package programs.main;

import applications.consoles.FileFinder;
import programs.filefinder.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindFiles extends ConsoleProgram {

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        String fileName = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("Which file?");

        programs.put("contains", new Contains());
        programs.put("col", new ContainsInLine());
        programs.put("list", new ListCurrentFiles());
        programs.put("type", new FilterOnType());
        programs.put("delete", new DeleteAll());

        Collection<Path> foundFiles = listFilesUsingFileWalk(fileName);
        configs.put("FOUND_FILES", foundFiles);
    }

    @Override
    public boolean work(Map<String, Object> input) {
        try{
            new FileFinder("FileFinder", programs, configs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        input.remove(INPUT_PARAMETER_1);
        return true;
    }

    private Set<Path> listFilesUsingFileWalk(String dir) {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), Integer.MAX_VALUE)) {
            return stream
                    .filter(path -> !Files.isDirectory(path))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println("Something went wrong walking the dir");
        }
        return new HashSet<>();
    }
}
