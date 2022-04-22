package hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class YAMLReader {
    public static final int SPACES_PER_TAB = 2;
    public static final String[] FOLDER_TYPES = {
            "jcr:primaryType: hippostd:folder",
            "jcr:primaryType: hst:channel",
            "jcr:primaryType: hst:workspace",
            "jcr:primaryType: hst:sitemenus",
            "jcr:primaryType: hst:sitemap",
            "jcr:primaryType: hst:pages"
    };
    public static final String ROOT_JCR_PATH = "/content/documents/netherlandsandyou/";
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
            if (shouldBeDirectory()) {
                createFolder();
            }
            if(targetDepth - depth < 1 || shouldBeDirectory()) {
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

    private boolean shouldBeDirectory() {
        for(String folderType : FOLDER_TYPES) {
            if(savedLines.get(1).trim().equals(folderType)){
                return true;
            }
        }
        return false;
    }

    public void createFolder() throws Exception {
        try {
            if (writePath.getNameCount() < startPath.getNameCount()) {
                throw new Exception("Tried writing to root folder!");
            }
            Files.createDirectories(Paths.get(writePath + "\\" + getCurrentFileName(savedLines.get(0))));
        } catch (IOException e) {
            System.out.println("Error creating directory at " + writePath + "\n" + e.getMessage());
        }
    }
    public void createYAML() {
        try(FileWriter writer = new FileWriter(Paths.get(writePath +  "\\" + getCurrentFileName(savedLines.get(0)) + ".yaml").toFile())) {
            Iterator<String> iterator = savedLines.iterator();
            writer.write(getInnerFileName(iterator.next()) + System.lineSeparator());
            while(iterator.hasNext()) {
                String str = iterator.next();
                str = str.length() < depth * SPACES_PER_TAB ? str : str.substring(depth * SPACES_PER_TAB);
                writer.write(str + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error creating YAML file at " + writePath +  "/" + getCurrentFileName(savedLines.get(0)) + ".yaml" + "\n" + e.getMessage());
        }
        writePath = Paths.get(String.format("%s\\%s", writePath, getCurrentFileName(savedLines.get(0))));
        duplicate = false;
        savedLines.clear();
    }

    private String getCurrentFileName(String name) {
        String trimmedName = name.trim().replace("hst:", "");
        if(duplicate) {
            return trimmedName.substring(3, trimmedName.length() - 1);
        } else {
            return trimmedName.substring(1, trimmedName.length() - 1);
        }
    }

    private String getInnerFileName(String name) {
        String fileName = getCurrentFileName(name);
        String subPath = startPath.getNameCount() < writePath.getNameCount() ?
                writePath.subpath(startPath.getNameCount(), writePath.getNameCount()).toString().replace("\\", "/") + "/":
                "";
        return ROOT_JCR_PATH + subPath + fileName + ":";
    }
}