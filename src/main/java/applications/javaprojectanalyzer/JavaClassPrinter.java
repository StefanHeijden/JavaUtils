package applications.javaprojectanalyzer;

import utilities.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaClassPrinter {
    public static final String SEPARATOR = "------------------------------" + System.lineSeparator();
    public static void printJavaClassResults(Path writePath, JavaClassFile javaClassFile) {
        try(FileWriter writer = new FileWriter(Paths.get(writePath +  javaClassFile.getClassName() + " - " + javaClassFile.getParentClassName() + ".txt").toFile())) {
            writer.write(javaClassFile.getClassName() + System.lineSeparator());
            printParent(writer, javaClassFile);
            printMethods(writer, javaClassFile);
            printMethodsToLookFor(writer, javaClassFile);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    private static void printParent(FileWriter writer, JavaClassFile javaClassFile) throws IOException {
        writer.write(SEPARATOR);
        if(javaClassFile.getParentClassName() != null) {
            writer.write("Parent class:  " + javaClassFile.getParentClassName() + System.lineSeparator());
            writer.write(SEPARATOR);
        }
    }

    private static void printMethods(FileWriter writer, JavaClassFile javaClassFile) throws IOException {
        writer.write("Methods found: " + System.lineSeparator());
        for (String line : javaClassFile.getMethods()) {
            writer.write(line + System.lineSeparator());
        }
    }

    private static void printMethodsToLookFor(FileWriter writer, JavaClassFile javaClassFile) throws IOException {
        if(javaClassFile instanceof JavaClassFileWithMethodsToLookFor) {
            JavaClassFileWithMethodsToLookFor javaClassFileWithMethodsToLookFor = (JavaClassFileWithMethodsToLookFor) javaClassFile;
            writer.write(SEPARATOR);
            writer.write("Method matches found: " + javaClassFileWithMethodsToLookFor.getMethodsToLookFor().size() + System.lineSeparator());
            for (String line : javaClassFileWithMethodsToLookFor.getMethodsToLookFor()) {
                writer.write(line + System.lineSeparator());
            }
        }
    }
}