package programs.main;

import programs.Program;
import utilities.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;

public class DeleteFilesInFolder implements Program {
    String userDirectory;
    String targetLocation;

    @Override
    public void init(Map<String, Object> input) {
        userDirectory = (String) input.get("USER_DIR");
        targetLocation = (String) input.get("TARGET_LOCATION");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        try {
            deleteDirectory(new File(System.getProperty(userDirectory) + targetLocation).toPath(), true);
        } catch(IOException e) {
            Logger.log(e);
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
}
