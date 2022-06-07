package programs.main;

import programs.Program;
import utilities.Logger;
import utilities.UserInputReader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;

public class DeleteFilesInFolder implements Program {

    @Override
    public boolean work(Map<String, Object> input) {
        try {
            deleteDirectory(UserInputReader.getCurrentPath(), true);
        } catch(IOException e) {
            Logger.log(e);
        }
        return true;
    }

    public static void deleteDirectory(Path path, boolean rootFolder) throws IOException {
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
