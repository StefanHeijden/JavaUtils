package applications.javaprojectanalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utilities.Logger;

public class JavaDecompilerPreProcessor {

    public static List<String> splitLinesWithLineEnder(List<String> codeLines) {
        List<String> codeLinesThatDoNotContainMultipleLine = new ArrayList<>();
        for(String codeLine: codeLines) {
            Logger.logInfo("Spliting line: " + codeLine);
            codeLinesThatDoNotContainMultipleLine.addAll(extractLinePieces(codeLine));
        }
        return codeLinesThatDoNotContainMultipleLine;
    }

    private static List<String> extractLinePieces(String codeLine) {
        List<String> codeLinesThatDoNotContainMultipleLine = new ArrayList<>();
        boolean done = false;
        while(!done) {
            int indexOfLineEnder = JavaLineEndFinder.getIndexOfALineEnderInLine(codeLine);
            Logger.logInfo("indexOfLineEnder: " + indexOfLineEnder);
            if(indexOfLineEnder < 0) {
                Logger.logInfo("Line of code does not contain line ender");
                if(codeLine.length() > 0) {
                    codeLinesThatDoNotContainMultipleLine.add(codeLine);
                }
                done = true;
            } else {
                Logger.logInfo("Line of code contains line ender");
                String codePiece = codeLine.substring(0, indexOfLineEnder + 1);
                Logger.logInfo("codePiece: " + codeLine.substring(0, indexOfLineEnder + 1));
                if(codePiece.length() > 0) {
                    codeLinesThatDoNotContainMultipleLine.add(codePiece);
                }
                codeLine = codeLine.substring(indexOfLineEnder + 1);
            }
        }
        for(String line: codeLinesThatDoNotContainMultipleLine) {
            Logger.logInfo("Line of code: " + line);
        }
        return codeLinesThatDoNotContainMultipleLine;
    }
}