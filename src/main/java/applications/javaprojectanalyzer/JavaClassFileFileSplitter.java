package applications.javaprojectanalyzer;

import utilities.Logger;
import utilities.UserInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JavaClassFileFileSplitter {

    public static List<List<String>> splitFilePerClass(Path sourceFile) {
        List<List<String>> javaClassFiles = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(sourceFile);
        } catch(IOException e) {
            UserInterface.printLine("Error reading file: " + sourceFile.toString());
            Logger.log("Error reading file: " + sourceFile.toString());
        }
        List<String> codeForJavaFile = new ArrayList<>();
        int numberOfBrackets = 0;

        for (String line : lines) {
            line = line.trim();
            if(numberOfBrackets <= 0 && lineIsCommented(line)) {
                continue;
            }
            if (numberOfBrackets == 0 && (line.contains("class ") || line.contains("interface "))) {
                codeForJavaFile = new ArrayList<>();
                codeForJavaFile.add(line);
                if(line.contains("{")) {
                    numberOfBrackets++;
                    continue;
                }
            }
            if(line.contains("{")) {
                numberOfBrackets++;
            }
            if(numberOfBrackets > 0) {
                codeForJavaFile.add(line);
            }
            if(line.contains("}")) {
                numberOfBrackets--;
                if(numberOfBrackets <= 0 && codeForJavaFile.size() > 0) {
                    javaClassFiles.add(codeForJavaFile);
                    codeForJavaFile = new ArrayList<>();
                }
            }
            if(numberOfBrackets < 0) {
                // error
            }
        }
        return javaClassFiles;
    }

    private static boolean lineIsCommented(String line) {
        return line.startsWith("/**") || line.startsWith("*") || line.startsWith("*/") ||
                line.startsWith("//");
    }
}
