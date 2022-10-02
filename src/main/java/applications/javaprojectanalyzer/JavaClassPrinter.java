package applications.javaprojectanalyzer;

import utilities.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaClassPrinter {

    public static void printJavaClassResults(Path writePath, JavaClassFile javaClassFile) {
        String seperator = "------------------------------" + System.lineSeparator();
        try(FileWriter writer = new FileWriter(Paths.get(writePath +  "\\" + methodsToLookFor.size() + " " +
                className + " - " + parentClassName + ".txt").toFile())) {
            writer.write(className + System.lineSeparator());
            writer.write(seperator);
            if(parentClassName != null) {
                writer.write("Parent class:  " + parentClassName + System.lineSeparator());
                writer.write(seperator);
            }
            writer.write("Methods found: " + System.lineSeparator());
            for (String line : methods) {
                writer.write(line + System.lineSeparator());
            }
            writer.write(seperator);
            writer.write("Method matches found: " + methodsToLookFor.size() + System.lineSeparator());
            for (String line : methodsToLookFor) {
                writer.write(line + System.lineSeparator());
            }
            writer.write(seperator);
            for (String line : codeForJavaFile) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            Logger.log(e);
        }
    }
}
