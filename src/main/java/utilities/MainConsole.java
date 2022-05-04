package utilities;

import programs.ElasticExportProcessor;
import programs.FileFinder;
import programs.YAMLReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class MainConsole implements UserInputReader {
    public static final String FILE_LOCATION = "/files/";
    public static final String TARGET_LOCATION = "/results/";
    public static final String USER_DIR = "user.dir";

    public MainConsole() {
        startReadingInputFromUser();
    }

    @Override
    public boolean firstMethod(Object... input) {
        try {
            deleteDirectory(new File(System.getProperty(USER_DIR) + TARGET_LOCATION).toPath(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean secondMethod(Object... input) {
        FileFinder ff = new FileFinder("C:\\Users\\stheijde\\Desktop\\dump").find("? /");
        ff.getFoundFiles().forEach(ui::printLine);
        return true;
    }

    @Override
    public boolean thirdMethod(Object... input) {
        try {
            String fileName = ui.getUserInput("Which file?");
            fileName = fileName.endsWith(".yaml") ? fileName : fileName + ".yaml";
            Path filePath = new File(System.getProperty(USER_DIR) + FILE_LOCATION + fileName).toPath();
            Path targetPath = new File(System.getProperty(USER_DIR) + TARGET_LOCATION).toPath();
            new YAMLReader(filePath, targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean fourthMethod(Object... input) {
        new ElasticExportProcessor("", "");
        ui.printLine("To do put this in extra file-------------------------------------------------");
        return true;
    }

    @Override
    public boolean fifthMethod(Object... input) {
        try{
            new FileFinder(ui.getUserInput("Which file?")).find();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static void deleteDirectory(Path path, boolean rootFolder) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectory(entry, false);
                }
            }
        }
        if (!rootFolder) {
            Files.delete(path);
        }
    }

    @Override
    public String[][] getEntries() {
        return new String[][]{
                {"Command__________________", "Description_____________________________", "-1"},
                {"exit", "exit", "-1"},
                {"help", "List with commands", "0"},
                {"clean", "Delete results folder", "1"},
                {"test", "run a quick test for current implementation", "2"},
                {"ry | yaml", "read a yaml file and create a tree structure of folder and files", "3"},
                {"pee | process elastic export", "read a yaml file and create a tree structure of folder and files", "4"},
                {"find", "find files", "5"}
        };
    }
}
