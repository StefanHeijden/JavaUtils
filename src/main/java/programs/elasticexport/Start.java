package programs.elasticexport;

import utilities.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

public class Start extends ElasticExportProgram {
    String targetFileLocation;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        targetFileLocation = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get("TARGET_FILE") : ui.getUserInput("Where to store results?");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        createNewFile();
        try(FileWriter fw = new FileWriter(targetFileLocation)) {
            for (String line : lines.collect(Collectors.toList())) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            Logger.log(e);
        }
        return false;
    }

    private void createNewFile() {
        try {
            File targetFile = new File(targetFileLocation);
            if (targetFile.createNewFile()) {
                System.out.println("File created: " + targetFile.getName());
            } else {
                System.out.println("File already exists. Clear content");
                PrintWriter writer = new PrintWriter(targetFile);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            Logger.log(e);
        }
    }
}
