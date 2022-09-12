package applications.javaprojectanalyzer;

import java.util.ArrayList;
import java.util.List;

public class JavaCodeDecompiler {

    public static final String LINE_ENDER = ";";
    public static final String LINE_ENDER2 = "{";
    public static final String LINE_ENDER3 = "}";

    public static List<String> decompile(List<String> codeForJavaFile) {
        List<String> codeLines = new ArrayList<>();
        int currentIndex = 0;

        while(currentIndex >= 0) {
            StringBuffer currentString = new StringBuffer();
            currentIndex = findEndOfNextLinePiece(codeForJavaFile, currentString, currentIndex);
            codeLines.add(codeLines.size() + 1 + " " + currentString.toString());
        }
        return codeLines;
    }

    private static int findEndOfNextLinePiece(List<String> codeForJavaFile, StringBuffer currentString, int currentIndex) {
        int index = currentIndex;
        while(index < codeForJavaFile.size()) {
            String currentLine = codeForJavaFile.get(index);
            currentString.append(currentLine);
            index++;
            if(currentLine.contains(LINE_ENDER) || currentLine.contains(LINE_ENDER2) || currentLine.contains(LINE_ENDER3)) {
                return index;
            }
        }
        return -1;
    }

}