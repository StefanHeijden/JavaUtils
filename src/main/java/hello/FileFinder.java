package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFinder {
    private Collection<Path> foundFiles;
    private String task;

    public FileFinder(String path) {
        foundFiles = listFilesUsingFileWalk(path, Integer.MAX_VALUE);
        System.out.println("Found " + foundFiles.size() + " files on starting path");
    }

    public Set<Path> listFilesUsingFileWalk(String dir, int depth) {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return stream
                    .filter(path -> !Files.isDirectory(path))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println("Something went wrong walking the dir");
        }
        return new HashSet<>();
    }

    public FileFinder(Collection<Path> foundFiles) {
        this.foundFiles = foundFiles;
    }

    public Collection<Path> getFoundFiles() {
        return foundFiles;
    }

    public FileFinder contains(String element) {
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
        return new FileFinder(files);
    }

    public FileFinder containsInLine(String element, int... indexes) {
        List<Path> files = new ArrayList<>();
        outer:
        for (Path path : foundFiles) {
            String[] lines = path.toFile().list();
            inner:
            for (int index : indexes) {
                if (index < lines.length && lines[index].equals(element)) {
                    files.add(path);
                    continue outer;
                }
            }
        }
        return new FileFinder(files);
    }

    public FileFinder filterOnType(String fileType) {
        return new FileFinder(foundFiles.stream()
                .filter(p -> p.toString().toLowerCase().endsWith(fileType))
                .collect(Collectors.toSet()));
    }

    public FileFinder find() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("What do you want to find?");
        try {
            return contains(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
    }

    public FileFinder find(String searchString) {
        return filterOnType("yaml").contains(searchString).deleteAll();
    }

    private FileFinder deleteAll() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        getFoundFiles().forEach(System.out::println);
        System.out.println("Delete these files?(" + foundFiles.size() + ")");
        try {
            if (reader.readLine().equals("y")) {
                foundFiles.forEach(f -> {
                            if (!f.toFile().delete()) {
                                System.out.println("Failed to delete the file: " + f.toString());
                            }
                        }
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to start delete");
        }
        return new FileFinder(new ArrayList<>());
    }
}
