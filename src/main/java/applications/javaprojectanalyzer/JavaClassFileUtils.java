package applications.javaprojectanalyzer;

import utilities.UserInterface;
import utilities.Logger;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.nio.file.*;
import java.util.Map;

public class JavaClassFileUtils {

    public static final String[] keywords = {
            "if",
            "for",
            "else",
            "while",
            "switch",
            "try",
            "catch",
            "finally"
    };

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

    public static List<JavaClassFile> findChildren(JavaClassFile parentJavaClassFile,
                                                   Map<String, JavaClassFile> javaClassFiles){
        Logger.logInfo("FindChildren for: " + parentJavaClassFile.getClassName());
        List<JavaClassFile> children = new ArrayList<>();
        for (JavaClassFile javaClassFile: javaClassFiles.values()) {
            if(!javaClassFile.equals(parentJavaClassFile) && javaClassFile.extendsJavaClass(parentJavaClassFile)) {
                Logger.logInfo("This is a child: " + javaClassFile.getClassName());
                children.add(javaClassFile);
                parentJavaClassFile.addChild(javaClassFile);
            }
        }
        return children;
    }

    public static void addMethodsFromParent(JavaClassFile javaClassFile, JavaClassFile parentJavaClassFile) {
        if(parentJavaClassFile != null) {
            javaClassFile.addMethods(parentJavaClassFile.getMethods());
        }
        javaClassFile.setInitialized(true);
    }

    private static boolean lineIsCommented(String line) {
        return line.startsWith("/**") || line.startsWith("*") || line.startsWith("*/") ||
                line.startsWith("//");
    }

    public static boolean containsKeyWord(String line) {
//        Logger.logInfo("Check line for keyword: " + line);
        line = line.replaceAll("[^a-zA-Z0-9]", " ");
//        Logger.logInfo("Line after regex: " + line);
        for(String keyword : keywords) {
            if(line.contains(" " + keyword + " ")) {
//                Logger.logInfo("Linecontains a keyword: " + keyword);
                return true;
            }
            if(line.startsWith(keyword + " ")) {
                Logger.logInfo("Line contains a keyword" + keyword);
                return true;
            }
        }
//        Logger.logInfo("Line does not contain a keyword");
        return false;
    }
}