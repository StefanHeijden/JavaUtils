package applications.jsonreader;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JSONReaderMonthly {

    private JSONReaderMonthly() {
        // empty
    }

    public static void addGeneralStatsForMonth(List<String> linesForGeneralFile, List<Long> durations, List<Date> endDates, List<String> lines, int year, int month) {
        List<Long> durationsForThisMonth = new ArrayList<>();
        for (int i = 0; i < endDates.size();i++) {
            LocalDate localDate = JSONReaderUtils.toLocalDate(endDates.get(i));
            if(localDate.getYear() == year && localDate.getMonth().getValue() == month) {
                durationsForThisMonth.add(durations.get(i));
            }
        }
        JSONReaderUtils.addGeneralStats(lines, durationsForThisMonth);
        linesForGeneralFile.add(JSONReaderUtils.printDuration(durationsForThisMonth));
    }

    public static void addResourceConsumptionForOneMonth(List<String> linesForGeneralFile, List<String> lines, List<Date> startDates, List<Date> endDates, int year, int month) {
        lines.add("\n");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, 1);
        double total = 0.0;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        lines.add(JSONReaderUtils.printRow(JSONReaderUtils.HEADERS_MONTH_FILE, JSONReaderUtils.COLUMN_WIDTH_MONTH_FILE));
        for (int i = 1; i <= daysInMonth; i++) {
            double result = getResourceConsumptionForOneDay(startDates, endDates, year, month, i);
            String[] toPrint = {
                     i + "",
                    ((result < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(result),
                    0 + ""
            };
            lines.add(JSONReaderUtils.printRow(toPrint, JSONReaderUtils.COLUMN_WIDTH_MONTH_FILE));
            total += result;
        }

        lines.add(3, JSONReaderUtils.getSpacedString(
                "Total amount of resource used this month:",
                ((total < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(total)));
        double average = total / daysInMonth;
        lines.add(4, JSONReaderUtils.getSpacedString(
                "Average amount of resources used per day: ",
                ((average < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(average)));
        linesForGeneralFile.add(((average < 1) ? "0" : "") + new DecimalFormat(JSONReaderUtils.DOUBLE_FORMAT_PATTERN).format(average));
    }

    private static double getResourceConsumptionForOneDay(List<Date> startDates, List<Date> endDates, int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        double resourceConsumption = 0.0;
        for (int i = 0;i< startDates.size();i++) {
            resourceConsumption += calculateResourceConsumption(startDates.get(i), endDates.get(i), localDate);
        }
        return resourceConsumption;
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
