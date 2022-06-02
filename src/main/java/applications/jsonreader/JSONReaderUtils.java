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
    public static final int COLUMN_WIDTH = 44;

    protected static final String[] HEADERS_MAIN_FILE = {
            "Date", "Average time PR stays open", "Average #PR's open each day"
    };

    protected static final int[] COLUMN_WIDTH_MAIN_FILE = {
            10, 34, 30
    };

    protected static final String[] HEADERS_MONTH_FILE = {
            "Day", "Open PR's", "PR's opened"
    };

    protected static final int[] COLUMN_WIDTH_MONTH_FILE = {
            10, 12, 10
    };

    private JSONReaderUtils() {
        // empty constructor
    }

    public static void addGeneralStats(List<String> lines, List<Long> durations) {
        lines.add(getSpacedString(
                "Total amount of PR's:",
                durations.size() + ""));
        lines.add(getSpacedString(
                "Average time PR stays open: ",
                printDuration(durations)));
        lines.add(getSpacedString(
                "Maximum amount of time PR stayed open:",
                JSONReaderUtils.fromEpochToDuration(JSONReaderUtils.getMaximum(durations))));
    }

    public static String printDuration(List<Long> durations) {
        Optional<Long> result = durations.stream().reduce(Long::sum);
        if(result.isPresent()) {
            long averageInEpoch = Math.floorDiv(result.get(), durations.size());
            return fromEpochToDuration(averageInEpoch);
        } else {
            return "No results found";
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

    public static String printRow(String[] columns, int[] columnWidth) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i< columns.length;i++) {
            result.append(columns[i]);
            for(int o =columns[i].length();o<columnWidth[i];o++) {
                result.append(" ");
            }
        }
        return result + "\n";
    }
}
