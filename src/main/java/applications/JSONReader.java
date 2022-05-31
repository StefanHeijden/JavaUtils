package applications;

import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import utilities.UserInterface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JSONReader {
    List<Date> startDates;
    List<Date> endDates;

    List<Long> durations;

    public JSONReader (Path filePath) throws IOException {
        startDates = new ArrayList<>();endDates = new ArrayList<>();durations = new ArrayList<>();
        InputStream is = Files.newInputStream(filePath.toFile().toPath());
        String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8.toString());
        JSONObject json = new JSONObject(jsonTxt);
        UserInterface.printLine("size: " + json.getInt("size"));
        for(Object object : json.getJSONArray("values")){
            JSONObject jsonObject = (JSONObject) object;
            long startDate = jsonObject.getLong("createdDate");
            startDates.add(new Date(startDate));
            if(jsonObject.getBoolean("closed")) {
                long endDate = jsonObject.getLong("closedDate");
                endDates.add(new Date(endDate));
                durations.add(endDate - startDate);
            }
        }
        UserInterface.printLine("startdates: " + startDates.size());
        printDuration();
    }


    private void printDuration() {
        Optional<Long> result = durations.stream().reduce(Long::sum);
        if(result.isPresent()) {
            long average = Math.floorDiv(result.get(), 1000);
            long epochDays = Math.floorDiv(average, 86400);
            UserInterface.printLine("Average duration: " + Period.ofDays((int) Math.floorDiv(average, 86400)) + " " + Duration.ofSeconds(average));
        } else {
            UserInterface.printLine("No results found");
        }
    }
}
