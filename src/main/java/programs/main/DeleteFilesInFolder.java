package programs.main;

import programs.Program;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;

public class DeleteFilesInFolder implements Program {
    @Override
    public boolean run(Map<String, Object> input) {
        final String USER_DIR = (String) input.get("USER_DIR");
        final String TARGET_LOCATION = (String) input.get("TARGET_LOCATION");
        try {
            deleteDirectory(new File(System.getProperty(USER_DIR) + TARGET_LOCATION).toPath(), true);
        } catch(IOException e) {
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
}
