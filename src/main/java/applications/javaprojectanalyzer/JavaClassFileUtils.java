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

    public static boolean containsKeyWord(String line) {
        line = line.replaceAll("[^a-zA-Z0-9]", " ");
        for(String keyword : keywords) {
            if(line.contains(" " + keyword + " ")) {
                return true;
            }
            if(line.startsWith(keyword + " ")) {
                Logger.logInfo("Line contains a keyword" + keyword);
                return true;
            }
        }
        return false;
    }
}