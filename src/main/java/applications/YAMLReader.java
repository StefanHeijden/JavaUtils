package applications;

import configurators.Configuration;
import configurators.Configurator;
import programs.main.DeleteFilesInFolder;
import utilities.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class YAMLReader {
    public static final Configuration configuration = new Configuration("configs\\yaml-reader.txt");
    public static final int SPACES_PER_TAB = configuration.getIntValue("SPACES_PER_TAB");
    public static final String ROOT_JCR_PATH  = configuration.getValue("ROOT_JCR_PATH");
    private static final String[] FOLDER_TYPES = Configurator.getArrayConfigurationFromFile(
            "configs\\folder-types.txt");
    private static final String JCR_PATH_SEPARATOR = "/";
    private final Path startPath;
    private final List<String> lines;
    private final List<String> savedLines;
    private Path writePath;
    private int counter = 0;
    private int depth = 0;
    private boolean duplicate = false;

    public YAMLReader(Path filePath, Path targetPath) throws IllegalAccessException, IOException {
        startPath = targetPath; writePath = targetPath; savedLines = new ArrayList<>();
        lines = Files.readAllLines(filePath);
        readYAML();
        while(processLines()) {
            readYAML();
        }
        removeUnnecessaryFiles(targetPath);
    }

    private void removeUnnecessaryFiles(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            if (path.getFileName().toString().endsWith("[1]") || path.getFileName().toString().endsWith("[2]")) {
                DeleteFilesInFolder.deleteDirectory(path, false);
            } else {
                if(path.getFileName().toString().endsWith("[3]")) {
                    String newFileName = path.toString().replace("[3]", "");
                    if(!path.toFile().renameTo(new File(newFileName))){
                        Logger.log("INFO: Successfully renamed file to: " + newFileName);
                    }
                }
                try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                    for (Path entry : entries) {
                        removeUnnecessaryFiles(entry);
                    }
                } catch(IOException e) {
                    Logger.log(e);
                }
            }
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

    public boolean processLines() throws IllegalAccessException {
        if (!savedLines.isEmpty()) {
            int targetDepth = lines.size() > counter ? lines.get(counter).indexOf('/') / SPACES_PER_TAB : depth;
            if (lines.size() > counter && lines.get(counter).trim().startsWith("? /")) {
                targetDepth--;
            }
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

    public void createFolder() throws IllegalAccessException {
        try {
            if (writePath.getNameCount() < startPath.getNameCount()) {
                throw new IllegalAccessException("Tried writing to root folder!");
            }
            Files.createDirectories(Paths.get(writePath + "\\" + getCurrentFileName(savedLines.get(0))));
        } catch (IOException e) {
            Logger.log(e);
        }
    }
    public void createYAML() {
        boolean writeToFile = true;
        try(FileWriter writer = new FileWriter(Paths.get(writePath +  "\\" + getCurrentFileName(savedLines.get(0)) + ".yaml").toFile())) {
            Iterator<String> iterator = savedLines.iterator();
            writer.write(getInnerFileName(iterator.next()) + System.lineSeparator());
            while(iterator.hasNext()) {
                String str = iterator.next();
                if(str.endsWith("[1]:")) {
                    writeToFile = false;
                }
                if(str.endsWith("[3]:")){
                    str = str.replace("[3]", "");
                    writeToFile = true;
                }
                if(str.contains(" resource: ")) {
                    str = str.replace("[3]", "");
                }
                if(writeToFile){
                    str = str.length() < depth * SPACES_PER_TAB ? str : str.substring(depth * SPACES_PER_TAB);
                    writer.write(str + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            Logger.log(e);
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
                writePath.subpath(startPath.getNameCount(), writePath.getNameCount()).toString().replace("\\", "/") + JCR_PATH_SEPARATOR:
                "";
        return ROOT_JCR_PATH + subPath + fileName + ":";
    }
}