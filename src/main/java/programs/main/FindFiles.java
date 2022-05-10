package programs.main;

import programs.FileFinder;
import programs.PrintHelp;
import programs.Program;
import programs.filefinder.*;
import utilities.ExitProgram;
import utilities.UserInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindFiles implements Program {
    @Override
    public boolean run(Map<String, Object> input) {
        final UserInterface ui = (UserInterface) input.get("UI");
        try{
            String fileName = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("Which file?");
            Map<String, Program> programs = getPrograms();
            new FileFinder("FileFinder", programs, getConfigs(programs, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        input.remove(INPUT_PARAMETER_1);
        return true;
    }

    private Map<String, Program> getPrograms() {
        Map<String, Program> programs = new HashMap<>();
        programs.put("help", new PrintHelp());
        programs.put("contains", new Contains());
        programs.put("col", new ContainsInLine());
        programs.put("list", new ListCurrentFiles());
        programs.put("type", new FilterOnType());
        programs.put("delete", new DeleteAll());
        programs.put("exit", new ExitProgram());
        return programs;
    }

    private Map<String, Object> getConfigs(Map<String, Program> programs, String fileName) {
        Map<String, Object> configs = new HashMap<>();
        Collection<Path> foundFiles = listFilesUsingFileWalk(fileName, Integer.MAX_VALUE);
        configs.put("PROGRAMS", programs);
        configs.put("FOUND_FILES", foundFiles);
        return configs;
    }

    private Set<Path> listFilesUsingFileWalk(String dir, int depth) {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return stream
                    .filter(path -> !Files.isDirectory(path))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println("Something went wrong walking the dir");
        }
        return new HashSet<>();
    }
}
