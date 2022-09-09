package applications.javaprojectanalyzer;

import utilities.UserInterface;
import utilities.Logger;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import java.nio.file.*;
import java.util.Map;

public class JavaClassFileUtils {

    public static List<List<String>> splitFilePerClass(Path sourceFile) {
        List<List<String>> javaClassFiles = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(sourceFile);
        } catch(IOException e) {
            UserInterface.printLine("Error reading file: " + sourceFile.toString());
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
        UserInterface.printLine("FindChildren for: " + parentJavaClassFile.getClassName());
        List<JavaClassFile> children = new ArrayList<>();
        for (JavaClassFile javaClassFile: javaClassFiles.values()) {
            if(!javaClassFile.equals(parentJavaClassFile) && javaClassFile.extendsJavaClass(parentJavaClassFile)) {
                UserInterface.printLine("Child: " + javaClassFile.getClassName());
                children.add(javaClassFile);
                parentJavaClassFile.addChild(javaClassFile);
            }
        }
        return children;
    }

    public static void addMethodsFromFileAndParent(JavaClassFile javaClassFile, JavaClassFile parentJavaClassFile,
                                                  List<String> methodsToLookFor) {
        UserInterface.printLine("Add methods to: " + javaClassFile.getClassName());
        for (String method : methodsToLookFor) {
            if(javaClassFile.hasMethodToLookFor(method)){
                javaClassFile.addMethod(method);
            }
        }
        if(parentJavaClassFile != null) {
            javaClassFile.addMethods(parentJavaClassFile.getMethods());
        }
        javaClassFile.setInitialized(true);
    }

    private static boolean lineIsCommented(String line) {
        return line.startsWith("/**") || line.startsWith("*") || line.startsWith("*/") ||
                line.startsWith("//");
    }
}