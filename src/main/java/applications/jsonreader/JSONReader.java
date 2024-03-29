package applications.jsonreader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/*
TODO
	• Verschil tussen PR naar master en PR's naar andere / totaal?
	• Range?
    • Refactoring?
 */
public class JSONReader {
    private static final Integer[][] MONTHS_TO_BE_PRINTED = {
            {2022, 5}, {2022,4}, {2022,3}, {2022, 2}, {2022, 1},
            {2021, 12}, {2021, 11}, {2021, 10}, {2021, 9}, {2021, 8}, {2021, 7},
            {2021, 6}, {2021, 5}, {2021, 4}, {2021, 3}, {2021, 2}, {2021, 1}
    };

    private final List<Date> startDates;
    private final List<Date> endDates;
    private final List<Long> durations;

    private final List<String> linesForGeneralFile;

    private final JSONArray jsonArray;
    private final Path filePath;
    private final String fileName;
    protected static final List<String> GRAPHICAL_DATA = new ArrayList<>();

    public JSONReader (Path filePath) throws IOException {
        startDates = new ArrayList<>();endDates = new ArrayList<>();durations = new ArrayList<>();linesForGeneralFile = new ArrayList<>();
        this.filePath = filePath;
        fileName = filePath.getFileName().toString().substring(0,filePath.getFileName().toString().length() - 5);
        InputStream is = Files.newInputStream(filePath);
        String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8.toString());
        jsonArray = new JSONObject(jsonTxt).getJSONArray("values");
        run();
    }

    private void run() {
        processEntireFile();
        linesForGeneralFile.add(JSONReaderUtils.printRow(JSONReaderUtils.HEADERS_MAIN_FILE));
        GRAPHICAL_DATA.add("Datum" + "\t" + "Open pull requests");
        for(Integer[] month : MONTHS_TO_BE_PRINTED) {
            List<String> lines = new ArrayList<>();
            List<String> linesForMainFile = new ArrayList<>();
            linesForMainFile.add(month[0] + "-" + (month[1] < 10 ? "0" + month[1] : month[1]));
            JSONReaderMonthly.addGeneralStatsForMonth(linesForMainFile, durations, endDates, lines, month[0], month[1]);
            JSONReaderMonthly.addResourceConsumptionForOneMonth(linesForMainFile, lines, startDates, endDates, month[0], month[1]);
            JSONReaderUtils.createResultFile(lines, filePath.getParent() + "\\" + fileName + "-" + month[0] + "-" + month[1] +".txt");
            String[] resultArray = linesForMainFile.toArray(new String[0]);
            linesForGeneralFile.add(JSONReaderUtils.printRow(resultArray));
        }
        JSONReaderUtils.createResultFile(linesForGeneralFile, filePath.getParent() + "\\" + fileName + ".txt");
        JSONReaderUtils.createResultFile(GRAPHICAL_DATA.stream().sorted().collect(Collectors.toList()),
                filePath.getParent() + "\\" + fileName + "-graphical-data.txt");
        JSONReader.GRAPHICAL_DATA.clear();
    }

    private void processEntireFile() {
        for(Object object : jsonArray){
            JSONObject jsonObject = (JSONObject) object;
            if(jsonObject.getBoolean("closed")) {
                Date startDate = new Date(jsonObject.getLong("createdDate"));
                Date endDate = new Date(jsonObject.getLong("closedDate"));
                startDates.add(startDate);
                endDates.add(endDate);
                durations.add(Math.abs(startDate.getTime() - endDate.getTime()));
            }
        }
    }

}
