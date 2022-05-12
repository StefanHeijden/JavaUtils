package programs.filefinder;

import utilities.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class DeleteAll extends FileFinderProgram {

    @Override
    public boolean work(Map<String, Object> input) {
        foundFiles.forEach(ui::printLine);
        try {
            if (ui.getUserInput("Delete these files?(" + foundFiles.size() + ")").equals("y")) {
                foundFiles.forEach(f -> {
                    try{
                        Files.delete(f);
                    } catch (IOException e) {
                        ui.printLine("Failed to delete the file: " + f);
                        Logger.log(e);
                    }
                });
            }
        } catch(Exception e) {
            Logger.log(e);
            System.out.println("Failed to start delete");
        }
        return true;
    }
}
