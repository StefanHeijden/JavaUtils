package hello;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class HelloWorld {
    public final static String FILE_LOCATION = "/files/";
    public final static String TARGET_LOCATION = "/results/";
    public final static String[][] entries = {
            {"Command__________________", "Description_____________________________", "-1"},
            {"exit", "exit", "-1"},
            {"help", "List with commands", "0"},
            {"clean", "Delete results folder", "1"},
            {"test", "run a quick test for current implementation", "2"},
            {"ry | yaml | readyaml", "read a yaml file and create a tree structure of folder and files", "3"}
    };

    public static void main(String[] args) throws IOException {
        while(readInput()) {
            // keep reading input from user
        }
    }

    public static boolean readInput() throws IOException {
        System.out.println("What do you want to do? (type help or ? for help)");
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        switch (getCommandIndex(reader.readLine())) {
            case -1:
                return false;
            case 0:
                printHelpList();
                return true;
            case 1:
                deleteDirectory(new File(System.getProperty("user.dir") + TARGET_LOCATION).toPath(), true);
                return true;
            case 2:
                deleteDirectory(new File(System.getProperty("user.dir") + TARGET_LOCATION).toPath(), true);
                ReadYamlFile("nieuws.yaml");
                return true;
            case 3:
                System.out.println("Which file?");
                String fileName = reader.readLine();
                ReadYamlFile(fileName);
                return true;
            // Add new commands here ------------------------------------------
            default:
                System.out.println("Command was not found.");
                return true;
        }
    }

    private static int getCommandIndex(String action) {
        for(String[] entry : entries) {
            String[] commands = entry[0].split("\\|");
            for (String command : commands) {
                if (action.equalsIgnoreCase(command.trim())) {
                    return Integer.parseInt(entry[2]);
                }
            }
        }
        System.out.println("Command was not found.");
        return 0;
    }

    private static void ReadYamlFile(String fileName) {
        fileName = fileName.endsWith(".yaml") ? fileName : fileName + ".yaml";
        Path filePath = new File(System.getProperty("user.dir") + FILE_LOCATION + fileName).toPath();
        Path targetPath = new File(System.getProperty("user.dir") + TARGET_LOCATION).toPath();
        try {
            new YAMLReader(filePath, targetPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printHelpList(){
        for(String[] entry : entries) {
            System.out.print(entry[0]);
            for(int i = entry[0].length(); i< 25; i++) {
                System.out.print(" ");
            }
            System.out.println(entry[1]);
            if (entry[2].equals("2")) {
                System.out.println("");
            }
        }
        System.out.println("_________________________________________________________________");
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

    private static void StartTimer(String action) {
        // print dots every second until timer is stopped
    }

    private static void StopTimer() {

    }
}