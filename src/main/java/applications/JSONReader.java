package applications;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import utilities.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.*;
import java.util.*;

public class JSONReader {
    public static final String DOUBLE_FORMAT_PATTERN = "##.00";
    private static final Integer[][] MONTHS_TO_BE_PRINTED = {
            {2022, 5}, {2022,4}, {2022,3}, {2022, 2}, {2022, 1},
            {2021, 12}, {2021, 11}, {2021, 10}, {2021, 9}, {2021, 8}, {2021, 7},
            {2021, 6}, {2021, 5}, {2021, 4}, {2021, 3}, {2021, 2}, {2021, 1}
    };
    private final List<Date> startDates;
    private final List<Date> endDates;
    private final List<Long> durations;
    private final List<String> lines;
    public static final Integer SECONDS_IN_A_DAY = 86400;

    private final JSONArray jsonArray;
    private final Path filePath;

    public JSONReader (Path filePath) throws IOException {
        startDates = new ArrayList<>();endDates = new ArrayList<>();durations = new ArrayList<>();lines = new ArrayList<>();
        this.filePath = filePath;
        InputStream is = Files.newInputStream(filePath);
        String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8.toString());
        jsonArray = new JSONObject(jsonTxt).getJSONArray("values");
        run();
    }

    private void run() {
        processEntireFile();
        addGeneralStats(durations);
        createResultFile(filePath.getParent() + "\\" + "platform.txt");
        for(Integer[] month : MONTHS_TO_BE_PRINTED) {
            addGeneralStatsForMonth(month[0],month[1]);
            addResourceConsumptionForOneMonth(month[0], month[1]);
            createResultFile(filePath.getParent() + "\\" + "platform-" + month[0] + "-" + month[1] +".txt");
        }
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

    public void addGeneralStatsForMonth(int year, int month) {
        List<Long> durationsForThisMonth = new ArrayList<>();
        for (int i = 0; i < endDates.size();i++) {
            LocalDate localDate = toLocalDate(endDates.get(i));
            if(localDate.getYear() == year && localDate.getMonth().getValue() == month) {
                durationsForThisMonth.add(durations.get(i));
            }
        }
        addGeneralStats(durationsForThisMonth);
    }

    private void addGeneralStats(List<Long> durations) {
        lines.add("PR's: " + durations.size());
        printDuration(durations);
        lines.add("Maximum: " + fromEpochToDuration(getMaximum(durations)));
    }

    private void printDuration(List<Long> durations) {
        Optional<Long> result = durations.stream().reduce(Long::sum);
        if(result.isPresent()) {
            long averageInEpoch = Math.floorDiv(result.get(), durations.size());
            lines.add("Average duration: " + Duration.ofSeconds(Math.floorDiv(averageInEpoch, 1000)).toString().substring(2));
        } else {
            lines.add("No results found");
        }
    }

    private void addResourceConsumptionForOneMonth(int year, int month) {
        lines.add("Resource consumption: ");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, 1);
        double total = 0.0;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInMonth; i++) {
            double result = getResourceConsumptionForOneDay(year, month, i);
            lines.add("Day " + i + ": " + ((result < 1) ? "0" : "") + new DecimalFormat(DOUBLE_FORMAT_PATTERN).format(result));
            total += result;
        }
        lines.add("Total: " + ((total < 1) ? "0" : "") + new DecimalFormat(DOUBLE_FORMAT_PATTERN).format(total));
        double average = total / daysInMonth;
        lines.add("Average: " + ((average < 1) ? "0" : "") + new DecimalFormat(DOUBLE_FORMAT_PATTERN).format(average));
    }

    private double getResourceConsumptionForOneDay(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        double resourceConsumption = 0.0;
        for (int i = 0;i< startDates.size();i++) {
            resourceConsumption += calculateResourceConsumption(startDates.get(i), endDates.get(i), localDate);
        }
        return resourceConsumption;
    }

    private double calculateResourceConsumption (Date pullRequestStartDate, Date pullRequestEndDate, LocalDate dateToCheck) {
        LocalDate pullRequestStartLocalDate = toLocalDate(pullRequestStartDate);
        LocalDate pullRequestEndLocalDate = toLocalDate(pullRequestEndDate);
        if(pullRequestStartLocalDate.compareTo(dateToCheck) < 0 && pullRequestEndLocalDate.compareTo(dateToCheck) > 0) {
            return 1.0;
        }
        if(pullRequestStartLocalDate.compareTo(dateToCheck) == 0) {
            return 1 - getSecondsSinceMidnight(pullRequestStartDate)  / SECONDS_IN_A_DAY;
        }
        if(pullRequestEndLocalDate.compareTo(dateToCheck) == 0) {
            return getSecondsSinceMidnight(pullRequestEndDate)  / SECONDS_IN_A_DAY;
        }
        return 0.0;
    }
    
    private LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private double getSecondsSinceMidnight(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return (double) localDateTime.getSecond() + (localDateTime.getMinute() * 60) + (localDateTime.getHour() * 3600);
    }

    private long getMaximum(List<Long> durations) {
        Long maxDuration = 0L;
        for(Long duration : durations) {
            maxDuration = (duration > maxDuration) ? duration : maxDuration;
        }
        return maxDuration;
    }

    private String fromEpochToDuration(Long epoch) {
        return Duration.ofSeconds(Math.floorDiv(epoch, 1000)).toString().substring(2);
    }

    public void createResultFile(String path) {
        try(FileWriter writer = new FileWriter(path)) {
            for (String line : lines) {
                writer.write(line  + System.lineSeparator());
            }
        } catch (IOException e) {
            Logger.log(e);
        }
        lines.clear();
    }

}
