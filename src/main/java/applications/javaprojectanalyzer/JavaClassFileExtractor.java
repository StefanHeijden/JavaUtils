package applications.javaprojectanalyzer;

import utilities.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaClassFileExtractor {

    public static JavaClassFile extractJavaClassFile(List<String> code) {
        List<String> codeForJavaFile = JavaCodeDecompiler.decompile(code);
        List<String> implementsClasses = new ArrayList<>();
        List<String> methods = new ArrayList<>();
        String className;
        String parentClassName = "";

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
            methods = JavaClassMethodsUtils.extractMethodFromLine(codeForJavaFile.get(i), methods);
        }
        return new JavaClassFile(implementsClasses, methods, className, parentClassName);
    }
}
