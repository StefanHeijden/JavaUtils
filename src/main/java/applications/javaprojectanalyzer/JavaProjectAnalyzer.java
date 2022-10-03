package applications.javaprojectanalyzer;

import utilities.Logger;
import utilities.UserInterface;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaProjectAnalyzer {
    public static final Path sourcePath = Paths.get("C:/Users/stheijde/Repositories/Java/JavaUtils/src/test/data/javaclasses");
    public static final Path writePath = Paths.get("C:/Users/stheijde/Repositories/Java/JavaUtils/results");
    public static final String[] rootClassNames = {
            "MyClass.java",
            "StreamExample",
            "Java8ForEachExample"
    };
    public static final String[] methodsToExclude = {
            "getRelatedDecorativeImage"
    };
    public static final String methodsToLookForThatStartWith = "getRelated";
    Map<String, JavaClassFile> javaClassFiles;

    public JavaProjectAnalyzer() {
        List<Path> javaFilePaths = findAllJavaFiles(sourcePath, new ArrayList<>());
        javaClassFiles = getJavaClassFiles(javaFilePaths);
        UserInterface.printLine("Found " + javaClassFiles.size() + " java files.");
        Logger.logInfo("Found " + javaClassFiles.size() + " java files.");
        for(String rootClassName : rootClassNames) {
            if(javaClassFiles.containsKey(rootClassName)) {
                JavaClassFileUtils.addMethodsFromParent(javaClassFiles.get(rootClassName), null);
                initializeJavaClassFiles(javaClassFiles.get(rootClassName));
                UserInterface.printLine("Print results in: " + writePath);
                JavaClassMethodsUtils.setMethodsToLookFor(javaClassFiles, methodsToExclude, methodsToLookForThatStartWith);
                printResults();
            } else {
                UserInterface.printLine("Rootclass not found: " + rootClassName);
            }
        }
    }

    private List<Path> findAllJavaFiles(Path sourcePath, List<Path> javaFilePaths) {
        if (Files.isDirectory(sourcePath, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(sourcePath)) {
                for (Path entry : entries) {
                    findAllJavaFiles(entry, javaFilePaths);
                }
            } catch (IOException e) {
                UserInterface.printLine("Error reading folder: " + sourcePath);
            }
        } else {
            if (sourcePath.toString().endsWith(".java")) {
                javaFilePaths.add(sourcePath);
            }
        }
        return javaFilePaths;
    }

    private Map<String, JavaClassFile> getJavaClassFiles(List<Path> javaFilePaths) {
        Map<String, JavaClassFile> javaClassFiles = new HashMap<>();
        for (Path javaFilePath : javaFilePaths) {
            for (List<String> javaClassSource : JavaClassFileFileSplitter.splitFilePerClass(javaFilePath)) {
                JavaClassFile javaClassFile = JavaClassFileExtractor.extractJavaClassFile(javaClassSource);
                javaClassFiles.put(javaClassFile.getClassName(), javaClassFile);
            }
        }
        return javaClassFiles;
    }

    private void initializeJavaClassFiles(JavaClassFile parent) {
        List<JavaClassFile> children = JavaClassFileUtils.findChildren(parent, javaClassFiles);
        for (JavaClassFile child : children) {
            JavaClassFileUtils.addMethodsFromParent(child, parent);
            initializeJavaClassFiles(child);
        }
    }

    private void printResults() {
        for (JavaClassFile javaClassFile: javaClassFiles.values()) {
            JavaClassPrinter.printJavaClassResults(writePath, javaClassFile);
        }
    }
}