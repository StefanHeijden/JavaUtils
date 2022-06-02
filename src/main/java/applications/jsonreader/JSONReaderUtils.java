package applications.jsonreader;

import utilities.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JSONReaderUtils {
    public static final Integer SECONDS_IN_A_DAY = 86400;
    public static final String DOUBLE_FORMAT_PATTERN = "##.00";
    public static final int COLUMN_WIDTH = 42;

    private JSONReaderUtils() {
        // empty constructor
    }

    public static void addGeneralStats(List<String> lines, List<Long> durations) {
        lines.add(getSpacedString(
                "Total amount of PR's:",
                durations.size() + ""));
        printDuration(lines, durations);
        lines.add(getSpacedString(
                "Maximum amount of time PR stayed open:",
                JSONReaderUtils.fromEpochToDuration(JSONReaderUtils.getMaximum(durations))));
    }

    public static void printDuration(List<String> lines, List<Long> durations) {
        Optional<Long> result = durations.stream().reduce(Long::sum);
        if(result.isPresent()) {
            long averageInEpoch = Math.floorDiv(result.get(), durations.size());
            lines.add(getSpacedString(
                    "Average time PR stays open: ",
                    fromEpochToDuration(averageInEpoch)));
        } else {
            lines.add("No results found");
        }
    }
    public static long getMaximum(List<Long> durations) {
        Long maxDuration = 0L;
        for(Long duration : durations) {
            maxDuration = (duration > maxDuration) ? duration : maxDuration;
        }
        return maxDuration;
    }

    public static String fromEpochToDuration(Long epoch) {
        return Duration.ofSeconds(Math.floorDiv(epoch, 1000)).toString().substring(2)
                .replace("H", " hours ")
                .replace("M", " minutes ")
                .replace("S", " seconds ");
    }

    public static  LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static  void createResultFile(List<String> lines, String path) {
        try(FileWriter writer = new FileWriter(path)) {
            for (String line : lines) {
                writer.write(line  + System.lineSeparator());
            }
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    public static String getSpacedString(String... inputs) {
        StringBuilder result = new StringBuilder();
        for (String input : inputs) {
            result.append(input);
            for(int i =input.length();i<COLUMN_WIDTH;i++) {
                result.append(" ");
            }
        }
        return result.toString();
    }
}
