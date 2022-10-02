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
    private List<String> implementsClasses;
    private List<String> methods;
    private boolean initialized = false;

    public JavaClassFile(List<String> implementsClasses, List<String> methods,
                         String className, String parentClassName) {
        this.implementsClasses = implementsClasses;
        this.methods = methods;
        this.className =  className;
        this.parentClassName = parentClassName;
        children = new ArrayList<>();
    }

    public String getClassName() {
        return className;
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
        List<String> methodsCopy = new ArrayList<>();
        methodsCopy.addAll(methods);
        return methodsCopy;
    }

    public void addMethod(String method) {
        if(!methods.contains(method)) {
            methods.add(method);
        }
    }

    public void setMethods(List<String> methods) {
        this.methods.removeAll(methods);
        addMethods(methods);
    }

    public void addMethods(List<String> methods) {
        this.methods.addAll(methods);
    }

    public void addChild(JavaClassFile javaClassFile) {
        children.add(javaClassFile);
    }

}