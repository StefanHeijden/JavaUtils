package hello;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class YAMLReader {
    public static final int SPACES_PER_TAB = 2;
    public static final String FOLDER_TYPE = "jcr:primaryType: hippostd:folder";
    private final Path startPath;
    private List<String> lines;
    private final List<String> savedLines;
    private Path writePath;
    private int counter = 0;
    private int depth = 0;
    private boolean duplicate = false;

    public YAMLReader(Path filePath, Path targetPath) throws Exception {
        startPath = targetPath; writePath = targetPath; savedLines = new ArrayList<>();
        try {
            lines = Files.readAllLines(filePath);
            readYAML();
            while(processLines()) {
                readYAML();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found: \n " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong reading the YAML file: \n " + e.getMessage());
        }
    }

    public void readYAML() {
        if (lines.size() > counter) {
            if (lines.get(counter).trim().startsWith("? /")) {
                duplicate = true;
            }
            savedLines.add(lines.get(counter));
            counter++;
            while (lines.size() > counter && !(lines.get(counter).trim().startsWith("/") || lines.get(counter).trim().startsWith("? /"))) {
                savedLines.add(lines.get(counter));
                counter++;
            }
        }
    }

    public boolean processLines() throws Exception {
        if (!savedLines.isEmpty()) {
            int targetDepth = lines.size() > counter ? lines.get(counter).indexOf('/') / SPACES_PER_TAB : depth;
            if (savedLines.get(1).contains(FOLDER_TYPE)) {
                createFolder();
            }
            if(targetDepth - depth < 1 || savedLines.get(1).contains(FOLDER_TYPE)) {
                createYAML();
                for (int i = 0; i <= depth - targetDepth; i++) {
                    writePath = writePath.getParent();
                }
                depth = targetDepth;
            }
            return true;
        }
        return false;
    }

    public void createFolder() throws Exception {
        try {
            if (writePath.getNameCount() < startPath.getNameCount()) {
                throw new Exception("Tried writing to root folder!");
            }
            Files.createDirectories(Paths.get(writePath + "\\" + getCurrentFileName()));
        } catch (IOException e) {
            System.out.println("Error creating directory at " + writePath + "\n" + e.getMessage());
        }
    }
    public void createYAML() {
        try(FileWriter writer = new FileWriter(Paths.get(writePath +  "\\" + getCurrentFileName() + ".yaml").toFile())) {
            for(String str: savedLines) {
                if (str.length() < depth * SPACES_PER_TAB) {
                    writer.write(str + System.lineSeparator());
                } else {
                    writer.write(str.substring(depth * SPACES_PER_TAB) + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating YAML file at " + writePath +  "/" + getCurrentFileName() + ".yaml" + "\n" + e.getMessage());
        }
        writePath = Paths.get(String.format("%s\\%s", writePath, getCurrentFileName()));
        duplicate = false;
        savedLines.clear();
    }

    private String getCurrentFileName() {
        String trimmedName = savedLines.get(0).trim();
        if(duplicate) {
            return trimmedName.substring(3, trimmedName.length() - 1);
        } else {
            return trimmedName.substring(1, trimmedName.length() - 1);
        }
    }
}