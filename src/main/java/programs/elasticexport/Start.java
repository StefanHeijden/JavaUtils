package programs.elasticexport;

import utilities.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Start extends AbstractElasticExportProgram {
    String targetFileLocation;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        targetFileLocation = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get("TARGET_FILE") : ui.getUserInput("Where to store results?");
    }

    @Override
    public boolean work(Map<String, Object> input) {
        Logger.createNewFile(targetFileLocation);
        try(FileWriter fw = new FileWriter(targetFileLocation)) {
            for (String line : lines.collect(Collectors.toList())) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            Logger.log(e);
        }
        return false;
    }

}
