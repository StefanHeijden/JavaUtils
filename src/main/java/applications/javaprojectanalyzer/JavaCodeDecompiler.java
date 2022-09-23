package applications.javaprojectanalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utilities.Logger;

public class JavaCodeDecompiler {

    public static List<String> decompile(List<String> codeForJavaFile) {
        codeForJavaFile = JavaDecompilerPreProcessor.splitLinesWithLineEnder(codeForJavaFile);
        for(String line: codeForJavaFile) {
            Logger.logInfo("Line of code: " + line);
        }
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
        boolean isCommentedOut = false;
        while(index < codeForJavaFile.size()) {
            String currentLine = codeForJavaFile.get(index).trim();
            addLineSegmentToStringBuffer(currentLine, currentString);
            index++;
            if(JavaLineEndFinder.getIndexOfALineEnderInLine(currentLine) > 0) {
                return index;
            }
            if(currentLine.startsWith("//")) {
                return index;
            }
            if(currentLine.startsWith("/*")) {
                isCommentedOut = true;
            }

            if(currentLine.startsWith("*/")) {
                isCommentedOut = true;
            }
        }
        return -1;
    }

    private static void addLineSegmentToStringBuffer(String line, StringBuffer stringBuffer) {
        if(stringBuffer.length() <= 0) {
            stringBuffer.append(line);
        } else {
            stringBuffer.append(" " + line);
        }
    }


}