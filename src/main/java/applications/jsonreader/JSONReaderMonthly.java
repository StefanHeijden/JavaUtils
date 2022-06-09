package applications.jsonreader;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class JSONReaderMonthly {

    public static final String OPEN_PULL_REQUESTS = "openPullRequests";
    public static final String PULL_REQUEST_OPENED = "pullRequestOpened";
    public static final String PULL_REQUEST_CLOSED = "pullRequestClosed";

    private JSONReaderMonthly() {
        // empty
    }

    public static void addGeneralStatsForMonth(List<String> linesForGeneralFile, List<Long> durations,
                                               List<Date> endDates, List<String> lines, int year, int month) {
        List<Long> durationsForThisMonth = new ArrayList<>();
        for (int i = 0; i < endDates.size();i++) {
            LocalDate localDate = JSONReaderUtils.toLocalDate(endDates.get(i));
            if(localDate.getYear() == year && localDate.getMonth().getValue() == month) {
                durationsForThisMonth.add(durations.get(i));
            }
        }
        JSONReaderUtils.addGeneralStats(lines, durationsForThisMonth);
        linesForGeneralFile.add(JSONReaderUtils.getAverageDuration(durationsForThisMonth));
    }

    public static void addResourceConsumptionForOneMonth(List<String> linesForGeneralFile, List<String> lines,
                                                         List<Date> startDates, List<Date> endDates, int year, int month) {
        lines.add("\n");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, 1);
        double total = 0.0;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        lines.add(JSONReaderUtils.printRow(JSONReaderUtils.HEADERS_MONTH_FILE, JSONReaderUtils.COLUMN_WIDTH_MONTH_FILE));
        List<Double> totals = new ArrayList<>();
        for (int i = 1; i <= daysInMonth; i++) {
            Map<String, Double> recourseConsumption = new HashMap<>();
            getResourceConsumptionForOneDay(recourseConsumption, startDates, endDates, year, month, i);
            totals.add(recourseConsumption.get(OPEN_PULL_REQUESTS));
            String[] toPrint = {
                     i + "",
                    ((recourseConsumption.get(OPEN_PULL_REQUESTS) < 1) ? "0" : "") +
                            new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(recourseConsumption.get(OPEN_PULL_REQUESTS)),
                    recourseConsumption.get(PULL_REQUEST_OPENED) + "",
                    recourseConsumption.get(PULL_REQUEST_CLOSED) + ""
            };
            String graphDate = year + "-" + (month < 10 ? "0" : "") + month + "-" + (i < 10 ? "0" : "") + i + "\t" +
                    ((recourseConsumption.get(OPEN_PULL_REQUESTS) < 1) ? "0" : "") +
                    new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(recourseConsumption.get(OPEN_PULL_REQUESTS));
            JSONReader.GRAPHICAL_DATA.add(graphDate);
            lines.add(JSONReaderUtils.printRow(toPrint, JSONReaderUtils.COLUMN_WIDTH_MONTH_FILE));
            total += recourseConsumption.get(OPEN_PULL_REQUESTS);
        }

        lines.add(3, "\nResources used this month");
        lines.add(4, JSONReaderUtils.getSpacedString(
                "Total:",
                ((total < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(total)));
        double average = total / daysInMonth;
        lines.add(5, JSONReaderUtils.getSpacedString(
                "Mean:",
                ((average < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(average)));
        double sd = JSONReaderUtils.calculateStandardDeviation(totals, average);
        lines.add(6, JSONReaderUtils.getSpacedString(
                "Standard deviation:",
                ((sd < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(sd)));
        linesForGeneralFile.add(((average < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(average));
        linesForGeneralFile.add(((sd < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(sd));
    }

    private static void getResourceConsumptionForOneDay(Map<String, Double> recourseConsumption, List<Date> startDates,
                                                        List<Date> endDates, int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        double openPullRequests = 0.0;
        double pullRequestOpened = 0.0;
        double pullRequestClosed = 0.0;
        for (int i = 0;i< startDates.size();i++) {
            openPullRequests += calculateResourceConsumption(startDates.get(i), endDates.get(i), localDate);
            if(JSONReaderUtils.toLocalDate(startDates.get(i)).compareTo(localDate) == 0) {
                pullRequestOpened += 1.0;
            }
            if(JSONReaderUtils.toLocalDate(endDates.get(i)).compareTo(localDate) == 0) {
                pullRequestClosed += 1.0;
            }
        }
        recourseConsumption.put(OPEN_PULL_REQUESTS, openPullRequests);
        recourseConsumption.put(PULL_REQUEST_OPENED, pullRequestOpened);
        recourseConsumption.put(PULL_REQUEST_CLOSED, pullRequestClosed);
    }

    private static double calculateResourceConsumption(Date pullRequestStartDate, Date pullRequestEndDate, LocalDate dateToCheck) {
        LocalDate pullRequestStartLocalDate = JSONReaderUtils.toLocalDate(pullRequestStartDate);
        LocalDate pullRequestEndLocalDate = JSONReaderUtils.toLocalDate(pullRequestEndDate);
        if(pullRequestStartLocalDate.compareTo(dateToCheck) < 0 && pullRequestEndLocalDate.compareTo(dateToCheck) > 0) {
            return 1.0;
        }
        if(pullRequestStartLocalDate.compareTo(dateToCheck) == 0) {
            return 1 - getSecondsSinceMidnight(pullRequestStartDate)  / JSONReaderUtils.SECONDS_IN_A_DAY;
        }
        if(pullRequestEndLocalDate.compareTo(dateToCheck) == 0) {
            return getSecondsSinceMidnight(pullRequestEndDate)  / JSONReaderUtils.SECONDS_IN_A_DAY;
        }
        return 0.0;
    }

    private static double getSecondsSinceMidnight(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return (double) localDateTime.getSecond() + (localDateTime.getMinute() * 60) + (localDateTime.getHour() * 3600);
    }
}
