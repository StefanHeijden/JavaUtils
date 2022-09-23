package applications.javaprojectanalyzer;

import java.util.Arrays;

public class JavaLineEndFinder {
    public static final String[] LINE_ENDERS = {
            ";",
            "{",
            "}"
    };
    public static int getIndexOfALineEnderInLine(String currentLine) {
        return Arrays.stream(LINE_ENDERS).parallel().reduce(-1,
                (Integer index, String lineEnder) -> getIndexOfSpecificLineEnderInLine(lineEnder, currentLine, index),
                (Integer index1, Integer index2) -> returnLargestIndex(index1, index2));
    }

    private static int getIndexOfSpecificLineEnderInLine(String lineEnder, String currentLine, int index) {
        return currentLine.contains(lineEnder) && (index < 0 || currentLine.indexOf(lineEnder) < index)
                ? currentLine.indexOf(lineEnder)
                : index;
    }

    private static int returnLargestIndex(int index1, int index2) {
        return index2 > 0 && ((index1 > 0 && index2 < index1) || index1 < 0)
                ? index2
                : index1;
    }
}