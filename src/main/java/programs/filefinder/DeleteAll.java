package programs.filefinder;

import java.util.Map;

public class DeleteAll extends FileFinderProgram {

    @Override
    public boolean work(Map<String, Object> input) {
        foundFiles.forEach(ui::printLine);
        try {
            if (ui.getUserInput("Delete these files?(" + foundFiles.size() + ")").equals("y")) {
                foundFiles.forEach(f -> {
                            if (!f.toFile().delete()) {
                                ui.printLine("Failed to delete the file: " + f);
                            }
                        }
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to start delete");
        }
        return true;
    }
}
