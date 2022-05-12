package applications.consoles;

import programs.Program;
import utilities.UserInputReader;

import java.util.Map;

public class ElasticExportProcessor extends UserInputReader {

    public ElasticExportProcessor(String inputReaderTitle, Map<String, Program> programs, Map<String, Object> configs) {
        super(inputReaderTitle, programs, configs);
        startReadingInputFromUser();
    }

}
