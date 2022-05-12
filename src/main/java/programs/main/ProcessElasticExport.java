package programs.main;

import applications.consoles.ElasticExportProcessor;
import programs.elasticexport.MakeDistinct;
import programs.elasticexport.Replace;
import programs.elasticexport.Start;
import programs.elasticexport.TrimString;
import utilities.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessElasticExport extends AbstractConsoleProgram {
    String elasticFileLocation;
    String jsonVariable;

    @Override
    public void init(Map<String, Object> input) {
        super.init(input);
        programs.put("replace", new Replace());
        programs.put("trim", new TrimString());
        programs.put("distinct", new MakeDistinct());
        programs.put("start", new Start());
        elasticFileLocation = input.containsKey(INPUT_PARAMETER_1) ? (String) input.get(INPUT_PARAMETER_1) : ui.getUserInput("Elastic file location?");
        jsonVariable = input.containsKey(INPUT_PARAMETER_2) ? (String) input.get(INPUT_PARAMETER_2) : ui.getUserInput("What JSON variable?");
    }

    @Override
    public boolean run(Map<String, Object> input) {
        boolean fileReadSuccessfully = scimJSONRequest();
        if (fileReadSuccessfully) {
            new ElasticExportProcessor("Main", programs, configs);
        }
        return fileReadSuccessfully;
    }

    private boolean scimJSONRequest(){
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(elasticFileLocation));
        } catch (IOException e) {
            Logger.log(e);
            return false;
        }
        List<String> savedLines = new ArrayList<>();
        boolean saveNextLine = false;
        for(String line : lines) {
            if(saveNextLine) {
                String newLine = line.trim();
                savedLines.add(newLine.substring(1, newLine.length() - 1));
                saveNextLine = false;
            }
            if(line.contains(jsonVariable)) {
                saveNextLine = true;
            }
        }
        configs.put("LINES", savedLines.stream());
        return true;
    }
}