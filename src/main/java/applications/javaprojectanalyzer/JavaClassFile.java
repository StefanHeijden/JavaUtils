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
    private String parentClassName = "";
    private JavaClassFile parent;
    private List<JavaClassFile> children;
    private List<String> codeForJavaFile;
    private List<String> implementsClasses;
    private List<String> methods;
    private List<String> methodsToLookFor;
    private boolean initialized = false;

    public JavaClassFile(List<String> code) {
        children = new ArrayList<>();
        codeForJavaFile = code;
        implementsClasses = new ArrayList<>();
        methods = new ArrayList<>();
        methodsToLookFor = new ArrayList<>();

        codeForJavaFile = JavaCodeDecompiler.decompile(codeForJavaFile);
        List<String> classNameLine = Arrays.asList(codeForJavaFile.get(0).replaceAll("[^a-zA-Z0-9]", " ").split(" "));
        if(classNameLine.indexOf("class") > 0) {
            if(classNameLine.indexOf("class") < classNameLine.size() - 1) {
                className = classNameLine.get(classNameLine.indexOf("class") + 1);
            } else {
                className = classNameLine.get(classNameLine.indexOf("class") );
            }
        } else {
            className = classNameLine.get(classNameLine.indexOf("interface") + 1);
        }
        Logger.logInfo("Classname: " + className);
        if(classNameLine.indexOf("extends") > 0) {
            parentClassName = classNameLine.get(classNameLine.indexOf("extends") + 1);
            Logger.logInfo("Extends: " + parentClassName);
        }
        if(classNameLine.indexOf("implements") > 0) {
            String[] implementations = classNameLine.get(classNameLine.indexOf("implements") + 1).split(",");
            for (String implementation : implementations) {
                implementsClasses.add(implementation.trim());
                Logger.logInfo("Implements: " + implementation.trim());
            }
        }
        for(int i=1;i<codeForJavaFile.size();i++) {
            extractMethodFromLine(codeForJavaFile.get(i));
        }
    }

    private void extractMethodFromLine(String line) {
        if(line.contains("{")) {
            if(!JavaClassFileUtils.containsKeyWord(line)) {
                String[] codePieces = line.split("\\s+");
                if(codePieces.length > 0) {
                    String methodName = "";
                    for(int i=0;i<codePieces.length && !methodName.contains("(");i++) {
                        methodName = codePieces[i];
                    }
                    if(methodName.contains("(")) {
                        methodName = methodName.substring(0, methodName.indexOf("("));
                    }
                    methods.add(methodName);
                }
            }
        }
    }

    public String getClassName() {
        return className;
    }

    public void setMethodsToLookFor(List<String> methodsToLookFor) {
        this.methodsToLookFor.addAll(methods);
        this.methodsToLookFor.retainAll(methodsToLookFor);
    }

    public void setMethodsToLookForThatStartWith(String methodsToLookForThatStartWith, String[] methodsToExclude) {
        for (String method : methods) {
            if(method.startsWith(methodsToLookForThatStartWith)) {
                if(!Arrays.asList(methodsToExclude).contains(method)) {
                    methodsToLookFor.add(method);
                }
            }
        }
    }

    public boolean extendsJavaClass(JavaClassFile possibleParent) {
        if(implementsClasses.contains(possibleParent.getClassName())) {
            return true;
        }
        return possibleParent.getClassName().equals(parentClassName);
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
        if(methods.contains(method)) {
            methods.add(method);
        }
    }

    public void addMethods(List<String> methods) {
        this.methods.removeAll(methods);
        this.methods.addAll(methods);
    }

    public void addChild(JavaClassFile javaClassFile) {
        children.add(javaClassFile);
    }

    public void printJavaClassResults(Path writePath) {
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