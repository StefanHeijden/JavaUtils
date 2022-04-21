package hello;

import java.io.File;
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
    public static final String[] folderTypes= {
            "hippo:handle",
    };
    List<String> lines;
    List<String> savedLines;
    Path writePath;
    int counter = 0;
    int depth = 0;
    boolean duplicate = false;

    public YAMLReader(Path filePath, Path targetPath) {
        System.out.print("Start reading YAML file");
        try {
            lines = Files.readAllLines(filePath);
            System.out.println(".");
            savedLines = new ArrayList<>();
            writePath = targetPath;
            while(readYAML()) {

            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found: \n " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong reading the YAML file: \n " + e.getMessage());
        }
    }

    public boolean readYAML() {
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
            int targetDepth = lines.size() > counter ? (int) lines.get(counter).indexOf('/') / SPACES_PER_TAB : depth;
            if (savedLines.get(1).contains("jcr:primaryType: hippostd:folder")) {
                try {
                    Files.createDirectories(Paths.get(writePath + "\\" + getCurrentFileName()));
                } catch (IOException e) {
                    System.out.println("Error creating directory at " + writePath + "\n" + e.getMessage());
                }
            }
            if(targetDepth - depth < 1 || savedLines.get(1).contains("jcr:primaryType: hippostd:folder")) {
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

    public void createYAML() {
        try {
            FileWriter writer = new FileWriter(Paths.get(writePath +  "\\" + getCurrentFileName() + ".yaml").toFile());
            for(String str: savedLines) {
                if (str.length() < depth * SPACES_PER_TAB) {
                    writer.write(str + System.lineSeparator());
                } else {
                    writer.write(str.substring(depth * SPACES_PER_TAB) + System.lineSeparator());
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating YAML file at " + writePath +  "/" + getCurrentFileName() + ".yaml" + "\n" + e.getMessage());
        }
        writePath = Paths.get(writePath +  "\\" + getCurrentFileName());
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