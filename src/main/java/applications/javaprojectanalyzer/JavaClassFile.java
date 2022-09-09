package applications.javaprojectanalyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import utilities.UserInterface;
import utilities.Logger;

public class JavaClassFile {
    private String className;
    private String parentClassName;
    private JavaClassFile parent;
    private List<JavaClassFile> children;
    private List<String> codeForJavaFile;
    private List<String> implementsClasses;
    private List<String> methods;
    private boolean initialized = false;

    public JavaClassFile(List<String> codeForJavaFile) {
        children = new ArrayList<>();
        this.codeForJavaFile = codeForJavaFile;
        implementsClasses = new ArrayList<>();
        methods = new ArrayList<>();

        List<String> classNameLine = Arrays.asList(codeForJavaFile.get(0).split(" "));
        if(classNameLine.indexOf("class") > 0) {
            className = classNameLine.get(classNameLine.indexOf("class") + 1);
        } else {
            className = classNameLine.get(classNameLine.indexOf("interface") + 1);
        }
//           b. Store parentName
//           c. Store implements
//           e. Set methods = null
    }

    public String getClassName() {
        return className;
    }


    public boolean extendsJavaClass(JavaClassFile possibleParent) {
        boolean extendsJavaClass = codeForJavaFile.get(0).contains(possibleParent.getClassName());
        if (!extendsJavaClass) {
            extendsJavaClass = codeForJavaFile.get(1).contains(possibleParent.getClassName());
        }
        if (extendsJavaClass) {
            parent = possibleParent;
            parentClassName = possibleParent.className;
        }
        return extendsJavaClass;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean setInitialized(boolean initialized) {
        return this.initialized = initialized;
    }

    public List<String> getMethods() {
        return methods;
    }

    public boolean hasMethodToLookFor(String method) {
        boolean containsMethod = methods.contains(method);
        for(String line: codeForJavaFile) {
            if(line.contains(method)) {
                containsMethod = true;
            }
        }
        return  containsMethod;
    }

    public void addMethod(String method) {
        methods.add(method);
    }

    public void addMethods(List<String> method) {
        methods.addAll(method);
    }

    public void addChild(JavaClassFile javaClassFile) {
        children.add(javaClassFile);
    }

    public void printJavaClassResults(Path writePath) {
        String seperator = "------------------------------" + System.lineSeparator();
        try(FileWriter writer = new FileWriter(Paths.get(writePath +  "\\" + className + ".txt").toFile())) {
            writer.write(className + System.lineSeparator());
            writer.write(seperator);
            if(parent != null) {
                writer.write("Parent class:  " + parentClassName + System.lineSeparator());
            }
            writer.write(seperator);
            writer.write("Method matches found: " + System.lineSeparator());
            for (String line : methods) {
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

    private void findAllMethods() {
        int currentIndex = 0;
        codeForJavaFile
    }

    private String findNextMethod() {
        // look for (
        // Check
        return "";
    }

    private int findNext() {

    }

}