package programs.filefinder;

import utilities.Logger;
import utilities.UserInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class DeleteAll extends AbstractFileFinderProgram {

    @Override
    public boolean work(Map<String, Object> input) {
        foundFiles.forEach(UserInterface::printLine);
        try {
            if (UserInterface.getUserInput("Delete these files?(" + foundFiles.size() + ")").equals("y")) {
                foundFiles.forEach(f -> {
                    try{
                        Files.delete(f);
                    } catch (IOException e) {
                        UserInterface.printLine("Failed to delete the file: " + f);
                        Logger.log(e);
                    }
                });
            }
        } catch(Exception e) {
            Logger.log(e);
        }
        return true;
    }
}
